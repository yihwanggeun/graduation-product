package edu.kaist.cs.teamfinder.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.GsonBuilder
import edu.kaist.cs.teamfinder.ApiService
import edu.kaist.cs.teamfinder.Feed
import edu.kaist.cs.teamfinder.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Composable
fun FeedDetail(feedNum: Int) {
    val feedDetail = remember { mutableStateListOf<Feed>() }
    val context = LocalContext.current
    LaunchedEffect(feedNum) {
        try {
            getFeedDetail(feedNum, context, feedDetail)
        } catch (e: Exception) {
            Log.e("TAG", "Failed to fetch project detail", e)
        }
    }
    if (feedDetail.isNotEmpty()) {
        Log.d("FeedDetail", feedDetail[0].name)
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .fillMaxSize()
        ) {
            Column() {
                feedDetail.let {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.sample),
                            contentDescription = it[0].name,
                            modifier = Modifier
                                .padding(start = 16.dp, top = 16.dp)
                                .size(36.dp) // 이미지 크기를 조절하십시오.
                                .clip(CircleShape)

                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = it[0].name,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.dmsans)),
                                    fontWeight = FontWeight(700),
                                    color = Color(0xFF150B3D),
                                ),
                                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                            )
                            Text(
                                text = it[0].date ?: "00",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.dmsans)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF524B6B),
                                ),
                                modifier = Modifier.padding(start = 16.dp, top = 2.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = it[0].title,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.dmsans)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF150B3D),
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                    //Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = it[0].content,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.dmsans)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF524B6B),
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 16.dp,
                                    bottomEnd = 16.dp
                                )
                            )// Change to your preferred purple color
                            .background(color = Color(0xFFD6CDFE))

                    ) {
                        // 댓글
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "하나")
                            Text(text = "둘")
                        }
                    }
                }

            }
        }
    } else {

    }
}


fun getFeedDetail(feedNum: Int, ctx: Context, feedList: MutableList<Feed>) {
    var gson = GsonBuilder().setLenient().create()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://7349-192-249-19-234.ngrok-free.app") // API의 베이스 URL을 설정합니다
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson)) // 문자열 응답을 처리하기 위해 ScalarsConverterFactory를 사용합니다
        .build()
    val apiService = retrofit.create(ApiService::class.java)
    val call: Call<ArrayList<Feed>> = apiService.detailfeed(feedNum)
    call!!.enqueue(object : Callback<ArrayList<Feed>?> {
        override fun onResponse(
            call: Call<ArrayList<Feed>?>,
            response: Response<ArrayList<Feed>?>
        ) {
            // on below line we are checking if response is successful.
            if (response.isSuccessful) {
                // on below line we are creating a new list
                var lst: ArrayList<Feed> = ArrayList()

                // on below line we are passing
                // our response to our list
                lst = response.body()!!
                println("RESOPONSE")
                println(response.body())
                // on below line we are passing
                // data from lst to course list.
                for (i in 0 until lst.size) {
                    // on below line we are adding data to course list.
                    feedList.add(lst.get(i))
                }
            }
        }

        override fun onFailure(call: Call<ArrayList<Feed>?>, t: Throwable) {
            // displaying an error message in toast
            println("FAIL")
        }
    })
}

