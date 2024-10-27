package edu.kaist.cs.teamfinder.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.GsonBuilder
import edu.kaist.cs.teamfinder.ApiService
import edu.kaist.cs.teamfinder.Engineer
import edu.kaist.cs.teamfinder.Feed
import edu.kaist.cs.teamfinder.R
import edu.kaist.cs.teamfinder.edu.kaist.cs.teamfinder.screens.TopRateEngineer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Composable
fun FeedScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val feedList = remember { mutableStateListOf<Feed>() }
    getAllFeed(feedList, context)
    var feedId = 0
    NavHost(navController = navController, "feedlist") {
        composable("feedlist") {
            FeedList(feedList, onPostClick = { it ->
                feedId = it.postId
                navController.navigate("feedDetail")
            },
                onDevelopClick = {
                    navController.navigate("chatScreen")
                })

        }
        composable("feedDetail") { FeedDetail(feedNum = feedId) }
        composable("chatScreen") { ChatScreen()}
    }
}

@Composable
fun FeedList(feedList: List<Feed>, onPostClick: (feed: Feed) -> Unit, onDevelopClick: () -> Unit) {
    val navController = rememberNavController()
    Box(modifier = Modifier) {
        Column() {


        Box(
            modifier = Modifier
                .padding(16.dp)
                .width(width = 400.dp)
                .background(
                    color = Color(0xFFE7E7E7),
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .height(200.dp)
        ) {
            Column {
                Text(
                    text = "Developer List",
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.dmsans)),
                        fontWeight = FontWeight(700),
                    )
                )
                val engineerList = listOf(
                    Engineer("Hwang Geun", R.drawable.sample),
                    Engineer("Michael Jackson", R.drawable.sample2),
                    Engineer("전산", R.drawable.sample3),
                    Engineer("Test", R.drawable.sample),
                    // 기타 기술자 추가
                )
                TopRateEngineer(engineerList, navController,onDevelopClick)

            }
        }
        LazyColumn {
            items(feedList) { feed ->
                Column() {

                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .width(width = 400.dp)
                            .background(
                                color = Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(size = 16.dp)
                            )
                            .height(IntrinsicSize.Min)
                    ) {
                        Column() {

                            Row(modifier = Modifier.fillMaxWidth()) {
                                Image(
                                    painter = painterResource(id = R.drawable.sample),
                                    contentDescription = "feed.name",
                                    modifier = Modifier
                                        .padding(start = 16.dp, top = 16.dp)
                                        .size(36.dp) // 이미지 크기를 조절하십시오.
                                        .clip(CircleShape)

                                )
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = feed.name ?: "name",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontFamily = FontFamily(Font(R.font.dmsans)),
                                            fontWeight = FontWeight(700),
                                            color = Color(0xFF150B3D),
                                        ),
                                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                                    )
                                    Text(
                                        text = "", //feed.date,
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
                                text = feed.title ?: "title",
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
                                text = feed.content ?: "content",
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
                                    .clickable {
                                        onPostClick(feed)
                                    }

                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.like),
                                        contentDescription = "like",
                                        modifier = Modifier
                                            .padding(start = 16.dp, top = 16.dp)
                                            .size(18.dp) // 이미지 크기를 조절하십시오.

                                    )
                                    Text(
                                        text = feed.like.toString() ?: "0",
                                        style = TextStyle(
                                            fontSize = 10.sp,
                                            fontFamily = FontFamily(Font(R.font.dmsans)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF6F6B80),
                                        ),
                                        modifier = Modifier.padding(start = 4.dp, top = 16.dp)
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.comment),
                                        contentDescription = "like",
                                        modifier = Modifier
                                            .padding(start = 16.dp, top = 16.dp)
                                            .size(18.dp) // 이미지 크기를 조절하십시오.

                                    )
                                    Text(
                                        text = feed.comment.toString() ?: "0",
                                        style = TextStyle(
                                            fontSize = 10.sp,
                                            fontFamily = FontFamily(Font(R.font.dmsans)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF6F6B80),
                                        ),
                                        modifier = Modifier.padding(start = 4.dp, top = 16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            }
        }

    }
}

fun getAllFeed(feedList: MutableList<Feed>, ctx: Context) {
    var gson = GsonBuilder().setLenient().create()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://7349-192-249-19-234.ngrok-free.app") // API의 베이스 URL을 설정합니다
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson)) // 문자열 응답을 처리하기 위해 ScalarsConverterFactory를 사용합니다
        .build()

    val apiService = retrofit.create(ApiService::class.java)
    val call: Call<ArrayList<Feed>> = apiService.getAllFeed()
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

//@Preview(showBackground = true)
//@Composable
//fun FeedView(){
//    TeamFinderTheme {
//        val feedList:ArrayList<Feed> = arrayListOf(
//            Feed("Michael Jackson",
//                R.drawable.sample,"What are the characteristics of a fake job call form?",
//                "Because I always find fake job calls so I'm confused which job to take can you share your knowledge here? thank you","1hour ago",
//                14,30)
//            ,
//            Feed("Michael Jackson",
//                R.drawable.sample,"What are the characteristics of a fake job call form?",
//                "Because I always find fake job calls so I'm confused which job to take can you share your knowledge here? thank you","1hour ago",
//                14,30)
//            ,
//            Feed("Michael Jackson",
//                R.drawable.sample,"What are the characteristics of a fake job call form?",
//                "Because I always find fake job calls so I'm confused which job to take can you share your knowledge here? thank you","1hour ago",
//                14,30)
//            ,
//            Feed("Michael Jackson",
//                R.drawable.sample,"What are the characteristics of a fake job call form?",
//                "Because I always find fake job calls so I'm confused which job to take can you share your knowledge here? thank you","1hour ago",
//                14,30)
//            ,
//            Feed("Michael Jackson",
//                R.drawable.sample,"What are the characteristics of a fake job call form?",
//                "Because I always find fake job calls so I'm confused which job to take can you share your knowledge here? thank you","1hour ago",
//                14,30)
//            ,
//            Feed("Michael Jackson",
//                R.drawable.sample,"What are the characteristics of a fake job call form?",
//                "Because I always find fake job calls so I'm confused which job to take can you share your knowledge here? thank you","1hour ago",
//                14,30)
//        )
//
//        FeedScreen()
//    }
//}