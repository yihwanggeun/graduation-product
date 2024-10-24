package edu.kaist.cs.teamfinder

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // LocalHost
    private const val BASE_URL = "10.0.2.2:8000"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val loginService: LoginRetrofitService by lazy {
        retrofit.create(LoginRetrofitService::class.java)
    }
}