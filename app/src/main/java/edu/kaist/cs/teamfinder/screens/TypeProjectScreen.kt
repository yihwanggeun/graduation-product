package edu.kaist.cs.teamfinder.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
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
fun TypeProjectScreen(frame : String) {
    println(frame)
    val projectList = remember { mutableStateListOf<Project>() }
    val context = LocalContext.current
    gettypeproject(projectList,context,frame)
    println(projectList)
    Box(modifier = Modifier.fillMaxSize().padding(16.dp))
    LazyColumn {
        items(projectList) { project ->
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .width(400.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(size = 6.dp)
                    )
                    .height(IntrinsicSize.Min),

                ) {
                Column() {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .size(60.dp) // 이미지 크기를 조절하십시오.
                                .padding(start = 16.dp, top = 16.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF9F9F9))
                        ) {
                            val image = getImageResourceForFront(project.front)
                            Image(
                                painter = painterResource(id = image),
                                contentDescription = project.projectName,
                                modifier = Modifier
                                    .size(48.dp) // 이미지 크기를 조절하십시오.
                                    .clip(CircleShape)
                                    .align(Alignment.Center)

                            )

                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = project.projectName,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.dmsans)),
                                    fontWeight = FontWeight(700),
                                    color = Color(0xFF150B3D),
                                ),
                                modifier = Modifier.padding(start = 16.dp,end = 16.dp, top = 20.dp)
                            )
                            Text(
                                text = project.projectType,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.dmsans)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF524B6B),
                                ),
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Box(
                            modifier = Modifier.padding(top = 16.dp, end = 16.dp),
                            Alignment.TopEnd
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.save2),
                                contentDescription = "save",
                                modifier = Modifier
                                    .size(24.dp) // 이미지 크기를 조절하십시오.
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.padding(start = 18.dp)) {
                        val text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Black,
                                    fontFamily = FontFamily(Font(R.font.dmsansbold))
                                )
                            ) {
                                append(project.current.toString() + "명 / ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFFFCA34D),
                                    fontFamily = FontFamily(Font(R.font.dmsansbold)),
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append(project.max.toString() + "명\n")
                            }
                        }

                        Text(
                            text = text,
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.dmsansbold)),
                            modifier = Modifier.height(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Box(modifier = Modifier.weight(1f)) {
                                // 첫번째 Box
                                Row {
                                    Box(
                                        modifier = Modifier
                                            .wrapContentWidth() // 가용 공간을 차지하도록 설정
                                            .padding(bottom = 8.dp, start = 16.dp)
                                            .height(30.dp)
                                            .background(
                                                color = Color(0x1ACBC9D4),
                                                shape = RoundedCornerShape(size = 6.dp)
                                            ),
                                        contentAlignment = Alignment.CenterStart // 왼쪽 정렬
                                    ) {
                                        Text(
                                            text = project.front + " Developer",
                                            style = TextStyle(
                                                fontSize = 10.sp,
                                                fontFamily = FontFamily(Font(R.font.dmsans)),
                                                fontWeight = FontWeight(400),
                                                color = Color(0xFF524B6B),
                                            ),
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }

                                    // 두번째 Box
                                    Box(
                                        modifier = Modifier
                                            .wrapContentWidth() // 가용 공간을 차지하도록 설정
                                            .height(30.dp)
                                            .padding(start = 8.dp)
                                            .background(
                                                color = Color(0x1ACBC9D4),
                                                shape = RoundedCornerShape(size = 6.dp)
                                            ),
                                        contentAlignment = Alignment.CenterStart // 왼쪽 정렬
                                    ) {
                                        Text(
                                            text = project.back + " Developer",
                                            style = TextStyle(
                                                fontSize = 10.sp,
                                                fontFamily = FontFamily(Font(R.font.dmsans)),
                                                fontWeight = FontWeight(400),
                                                color = Color(0xFF524B6B),
                                            ),
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                }
                            }
                            // 'Apply' 버튼
                            Box(
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(30.dp)
                                    .padding(end = 16.dp)
                                    .background(
                                        color = Color(0x1AFF6B2C),
                                        shape = RoundedCornerShape(size = 6.dp)
                                    ),
                                contentAlignment = Alignment.Center,

                                ) {
                                val text = buildAnnotatedString {
                                    pushStringAnnotation(
                                        tag = "Apply",
                                        annotation = "Apply"
                                    ) // Use a tag to identify the clickable text
                                    withStyle(
                                        style = SpanStyle(
                                            color = Color(0xFF524B6B),
                                            textDecoration = TextDecoration.Underline
                                        )
                                    ) {
                                        append("Apply")
                                    }
                                    pop()
                                }
                                ClickableText(
                                    text = text,
                                    style = TextStyle(
                                        fontSize = 10.sp,
                                        fontFamily = FontFamily(Font(R.font.dmsans)),
                                        fontWeight = FontWeight(400),
                                    ),
                                    modifier = Modifier.padding(4.dp),
                                    onClick = { offset ->
                                        text.getStringAnnotations(
                                            tag = "Apply",
                                            start = offset,
                                            end = offset
                                        )


                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun gettypeproject(projectList : MutableList<Project>, ctx : Context, frame : String){
    var gson = GsonBuilder().setLenient().create()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://7349-192-249-19-234.ngrok-free.app/") // API의 베이스 URL을 설정합니다
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson)) // 문자열 응답을 처리하기 위해 ScalarsConverterFactory를 사용합니다
        .build()
    Log.d("Get TEST",frame)
    val apiService = retrofit.create(ApiService::class.java)
    val call : Call<ArrayList<Project>> = apiService.typeproject(frame)
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