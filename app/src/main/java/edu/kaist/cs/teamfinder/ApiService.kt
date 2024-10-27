package edu.kaist.cs.teamfinder

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("/") // 실제 엔드포인트에 맞게 경로를 지정해야 합니다
    fun getHelloKaist(): Call<String> // 데이터를 가져오는 GET 요청을 수행하기 위한 메서드

    @POST("/createUser") // Replace with the actual endpoint path to create a user
    fun createUser(@Body user: User): Call<String>

    @POST("/createProject") // Replace with the actual endpoint path to create a user
    fun createProject(@Body project: Project): Call<Project>

    @POST("/applyProject")
    fun applyProject(@Body application: ProjectApplication): Call<ApplicationResponse>

    @GET("/checkUser")
    fun checkUser(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<ResponseBody>

    @GET("/typeproject")
    fun typeproject(
        @Query("frame") frame: String,
    ): Call<ArrayList<Project>>

    @GET("/detailproject")
    fun detailproject(
        @Query("projectName") projectName: String,
    ): Call<ArrayList<ProjectResponse>>


    @GET("/allproject")
    fun allproject(): Call<ArrayList<Project>>


    @POST("createfeed")
    fun createFeed(@Body feed: FeedCreate): Call<FeedResponse>

    @GET("/allfeed")
    fun getAllFeed(): Call<ArrayList<Feed>>

    @GET("/detailfeed")
    fun detailfeed(
        @Query("post_id") feedID: Int,
    ): Call<ArrayList<Feed>>
}


data class FeedCreate(
    val title: String,
    val content: String,
    val author: String,
    val name: String
)

data class FeedResponse(
    val message: String,
    val feed_id: Int
)

// 필요한 데이터 클래스들
data class ProjectApplication(
    val project_id: Int,
    val applicant_email: String,
    val message: String
)

data class ApplicationResponse(
    val message: String,
    val application_id: Int
)
