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
    ): Call<ArrayList<Project>>

    @GET("/allproject")
    fun allproject(): Call<ArrayList<Project>>

    @GET("/allfeed")
    fun getAllFeed(): Call<ArrayList<Feed>>

    @GET("/detailfeed")
    fun detailfeed(
        @Query("post_id") feedID: Int,
    ): Call<ArrayList<Feed>>
}