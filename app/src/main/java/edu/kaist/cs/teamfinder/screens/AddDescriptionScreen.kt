package edu.kaist.cs.teamfinder.edu.kaist.cs.teamfinder.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.gson.GsonBuilder
import edu.kaist.cs.teamfinder.ApiService
import edu.kaist.cs.teamfinder.Globals
import edu.kaist.cs.teamfinder.LoginRoute
import edu.kaist.cs.teamfinder.Project
import edu.kaist.cs.teamfinder.R
import edu.kaist.cs.teamfinder.User
import edu.kaist.cs.teamfinder.apiService
import edu.kaist.cs.teamfinder.mGoogleSignInClient
import edu.kaist.cs.teamfinder.resultLauncher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDescriptionScreen() {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth().verticalScroll(rememberScrollState())
                .height(3000.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Web",
                modifier = Modifier
                    .size(24.dp)// 이미지 크기를 조절하십시오.
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Add a project information",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF150B3D),
                )
            )

            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth()){
                Image(
                    painter = painterResource(id = R.drawable.sample),
                    contentDescription = "sample",
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        .size(36.dp) // 이미지 크기를 조절하십시오.
                        .clip(CircleShape)

                )
                Column(modifier = Modifier.weight(1f)){
                    Text(
                        text = "Michael Jackson",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.dmsans)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF150B3D),
                        ),
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                    Text(
                        text = "Web Developer",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.dmsans)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF524B6B),
                        ),
                        modifier = Modifier.padding(start = 16.dp,top = 2.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )


                }
            }

            Spacer(modifier = Modifier.height(24.dp))  // Add this line

            Text(
                text = "Project Name",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF150B3D),
                )
            )
            Spacer(modifier = Modifier.height(8.dp))  // Add this line
            var project_name by remember{
                mutableStateOf("")
            }
            TextField(
                value = project_name,
                onValueChange = { project_name = it },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFFFFFFF),
                    textColor = Color.Gray,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(24.dp))  // Add this line
            Text(
                text = "Project Type",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF150B3D),
                )
            )
            Spacer(modifier = Modifier.height(8.dp))  // Add this line
            var project_type by remember{
                mutableStateOf("")
            }
            TextField(
                value = Globals.project_type,
                onValueChange = { project_type = it },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFFFFFFF),
                    textColor = Color.Gray,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(24.dp))  // Add this line
            Text(
                text = "Based On Framework",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF150B3D),
                )
            )
            Spacer(modifier = Modifier.height(8.dp))  // Add this line
            var framework by remember{
                mutableStateOf("")
            }
            TextField(
                value = Globals.front + " ," + Globals.back,
                onValueChange = { framework = it },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFFFFFFF),
                    textColor = Color.Gray,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(24.dp))  // Add this line
            Text(
                text = "Description",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF150B3D),
                )
            )
            Spacer(modifier = Modifier.height(8.dp))  // Add this line
            var project_description by remember{
                mutableStateOf("")
            }
            TextField(
                value = project_description,
                onValueChange = { project_description = it },
                modifier = Modifier
                    .fillMaxWidth().height(100.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFFFFFFF),
                    textColor = Color.Gray,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(16.dp))  // Add this line
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                Button(
                    onClick = {

                    },
                    shape = RoundedCornerShape(size = 6.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFD6CDFE)),
                    modifier = Modifier
                        .width(266.dp)
                        .height(50.dp)
                        .shadow(
                            elevation = 62.dp,
                            spotColor = Color(0x2E99ABC6),
                            ambientColor = Color(0x2E99ABC6)
                        )


                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Gather",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.dmsans)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFFFFFFFF),
                                letterSpacing = 10.sp,
                            ),
                            modifier = Modifier.clickable {
                               val project = Project(project_name, project_description,"4년제 대학교 졸업자 우대:React Native 개발 경험 1년 이상 개발자 우대:회사 인턴 1년 이상 진행한 개발자 우대",
                               Globals.project_type,Globals.globalUser,Globals.front,Globals.back,4,0)
                                println(project)
                                createproject(project,context)
                            }
                        )


                    }
                }
            }
        }
        }

}

fun createproject(project : Project,ctx : Context){
    var gson = GsonBuilder().setLenient().create()
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.2:8080/") // API의 베이스 URL을 설정합니다
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson)) // 문자열 응답을 처리하기 위해 ScalarsConverterFactory를 사용합니다
        .build()

    val apiService = retrofit.create(ApiService::class.java)
    val call = apiService.createProject(project)

    call.enqueue(object: Callback<Project> {
        override fun onResponse(call: Call<Project>, response: Response<Project>) {
            if (response.isSuccessful) {
                println("SUCESS")
                // Project was successfully created on the server.
                // The server's response can be obtained from the 'response' object.
            }
        }

        override fun onFailure(call: Call<Project>, t: Throwable) {
            println("FAIL")
            // An error occurred while trying to communicate with the server.
            // The error can be obtained from the 't' object.
        }
    })


}
@Preview
@Composable
fun AddDescriptionScreenPreview(){
    val navController = rememberNavController()
    AddDescriptionScreen()
}