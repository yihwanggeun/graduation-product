package edu.kaist.cs.teamfinder.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.GsonBuilder
import edu.kaist.cs.teamfinder.ApiService
import edu.kaist.cs.teamfinder.Project
import edu.kaist.cs.teamfinder.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


@Composable
fun ProjectDetailScreen(projectName: String) {
    val detailproject = remember { mutableStateListOf<Project>() }
    val context = LocalContext.current
    LaunchedEffect(projectName) {
        try {
            getdetailproject(projectName, context, detailproject)
        } catch (e: Exception) {
            Log.e("TAG", "Failed to fetch project detail", e)
        }
    }
    if (detailproject.isNotEmpty()) {
        // your composable code here
        // ...

        Log.i("TAG", detailproject[0].projectName)
        println(detailproject[0].projectRequire)
        val parts = detailproject[0].projectRequire.split(":")

        val part1 = parts[0]
        val part2 = parts[1]
        val part3 = parts[2]
        println(detailproject[0].front)
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))) {

            // Big Box including Image

            //Spacer(modifier = Modifier.height(100.dp))
            // Box with background and Text
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .height(3000.dp)) {
                Box(modifier = Modifier.padding(top = 70.dp)) {

                    Box(
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .fillMaxWidth()
                            .height(114.dp)
                            .background(Color(0xFFF3F2F2)),
                        contentAlignment = Center
                    ) {
                        Column() {
                            Spacer(modifier = Modifier.height(8.dp))
                            detailproject?.let {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = it[0].projectName,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(Font(R.font.dmsans)),
                                        fontWeight = FontWeight(700),
                                        color = Color(0xFF0D0140),
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row {
                                detailproject?.let {
                                    Text(
                                        modifier = Modifier.weight(1f), text = it[0].projectType,
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(Font(R.font.dmsans)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF0D0140),
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                }

                                detailproject?.let {
                                    Text(
                                        modifier = Modifier.weight(1f), text = it[0].front,
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(Font(R.font.dmsans)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF0D0140),
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                }

                                detailproject?.let {
                                    Text(
                                        modifier = Modifier.weight(1f), text = it[0].back,
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(Font(R.font.dmsans)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF0D0140),
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                }
                            }
                        }

                    }


                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp) // Adjust the size of your image
                                .clip(CircleShape)
                                .background(Color(0xFFF9F9F9)),
                            contentAlignment = Center
                        ) {
                            val imageResource = getImageResourceForFront(detailproject[0].front)
                            Image(
                                painter = painterResource(id = imageResource),
                                contentDescription = "apple",
                                modifier = Modifier
                                    .size(48.dp) // Adjust the size of your image
                                    .clip(CircleShape)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = "Project Description",
                    modifier = Modifier.padding(start = 16.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.dmsans)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF150B3D),
                    )
                )

                Text(
                    text = detailproject[0].projectDescription,
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.dmsans)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF524B6B),
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Requirements",
                    modifier = Modifier.padding(start = 16.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.dmsans)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF150B3D),
                    )
                )
                Text(
                    text = "· " + part1,
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.dmsans)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF524B6B),
                    )
                )
                Text(
                    text = "· " + part2,
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.dmsans)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF524B6B),
                    )
                )
                Text(
                    text = "· " + part3,
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.dmsans)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF524B6B),
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Center) {
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(size = 6.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF130160)),
                        modifier = Modifier
                            .width(266.dp)
                            .height(50.dp)
                            .shadow(
                                elevation = 62.dp,
                                spotColor = Color(0x2E99ABC6),
                                ambientColor = Color(0x2E99ABC6)
                            )


                    ) {
                        Text(
                            text = "APPLY NOW",
                            color = Color.White
                        )
                    }


                }
            }

        }
    }
    else{}
}

fun getdetailproject(projectName : String, ctx : Context, projectList : MutableList<Project>){
    println(projectName)
    var gson = GsonBuilder().setLenient().create()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://7349-192-249-19-234.ngrok-free.app") // API의 베이스 URL을 설정합니다
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson)) // 문자열 응답을 처리하기 위해 ScalarsConverterFactory를 사용합니다
        .build()
    val apiService = retrofit.create(ApiService::class.java)
    val call : Call<ArrayList<Project>> = apiService.detailproject(projectName)
    call!!.enqueue(object : Callback<ArrayList<Project>?> {
        override fun onResponse(
            call: Call<ArrayList<Project>?>,
            response: Response<ArrayList<Project>?>
        ) {
            // on below line we are checking if response is successful.
            if (response.isSuccessful) {
                // on below line we are creating a new list
                var lst: ArrayList<Project> = ArrayList()

                // on below line we are passing
                // our response to our list
                lst = response.body()!!
                println("RESOPONSE")
                println(response.body())
                // on below line we are passing
                // data from lst to course list.
                for (i in 0 until lst.size) {
                    // on below line we are adding data to course list.
                    projectList.add(lst.get(i))
                }
            }
        }

        override fun onFailure(call: Call<ArrayList<Project>?>, t: Throwable) {
            // displaying an error message in toast
            println("FAIL")
        }
    })
}
fun getImageResourceForFront(front: String): Int {
    return when (front) {
        "Vue.js" -> R.drawable.vue
        "JavaScript" -> R.drawable.javascript
        "React" -> R.drawable.react
        else -> R.drawable.react// default image for unexpected cases
    }
}
@Preview
@Composable
fun ProjectDetailScreenPreview() {
    ProjectDetailScreen(projectName = "")
}
