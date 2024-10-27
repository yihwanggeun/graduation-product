package edu.kaist.cs.teamfinder.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.GsonBuilder
import edu.kaist.cs.teamfinder.ApiService
import edu.kaist.cs.teamfinder.Project
import edu.kaist.cs.teamfinder.edu.kaist.cs.teamfinder.screens.WebTypeScreen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory



@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val projectList = remember { mutableStateListOf<Project>() }
    getallproject(projectList,context)
//    val projectTag:ArrayList<String> = arrayListOf("React Native","Node Js")
//    projectList = listOf(
//        Project("MadCamp Project", R.drawable.apple,"Web Project",4, projectTag),
//        Project("MadCamp Project", R.drawable.apple,"Web Project",4, projectTag),
//        Project("MadCamp Project", R.drawable.apple,"Web Project",4, projectTag),
//        Project("MadCamp Project", R.drawable.apple,"Web Project",4, projectTag),
//        Project("MadCamp Project", R.drawable.apple,"Web Project",4, projectTag),
//        Project("MadCamp Project", R.drawable.apple,"Web Project",4, projectTag),
//        Project("MadCamp Project", R.drawable.apple,"Web Project",4, projectTag)
//        // 기타 기술자 추가
//    )
    var projectName = ""
    var frame = ""
    NavHost(navController, "projectList") {
        composable("projectList") {
            println("NavHost")
            println(projectList)
            ProjectList(
                projectList,
                onProjectListClick = { it ->
                    projectName = it.projectName

                    navController.navigate("projectDetail")
                    println(it.projectName)
                },
                onWebProjectClick = {
                    navController.navigate("webTypeScreen")
                },
                onAppProjectClick = {
                    navController.navigate("appTypeScreen")
                },
                onSoftwareProjectClick = {
                    navController.navigate("softwareTypeScreen")
                }
            
            )
        }
        composable("projectDetail") { ProjectDetailScreen(projectName) }
        composable("webTypeScreen") { WebTypeScreen(navController) }
        composable("appTypeScreen") { AppTypeScreen(navController) }
        composable("softwareTypeScreen") { SoftwareTypeScreen(navController) }
        composable("typeProject/{frame}") { backStackEntry ->
            val frame = backStackEntry.arguments?.getString("frame")
            if (frame != null) {
                TypeProjectScreen(frame)
            } else {
                // 오류 처리
            }
        }
        }

}

fun getallproject(projectList : MutableList<Project>,ctx : Context){
    var gson = GsonBuilder().setLenient().create()
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.2:8080/") // API의 베이스 URL을 설정합니다
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson)) // 문자열 응답을 처리하기 위해 ScalarsConverterFactory를 사용합니다
        .build()

    val apiService = retrofit.create(ApiService::class.java)
    val call : Call<ArrayList<Project>> = apiService.allproject()
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


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}