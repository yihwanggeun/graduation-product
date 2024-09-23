package edu.kaist.cs.teamfinder

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://0bfb-192-249-19-234.ngrok-free.app"

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