package edu.kaist.cs.teamfinder

import android.content.Context
import android.content.Intent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // LocalHost
    private const val BASE_URL = "http://192.168.0.2:8080/"

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