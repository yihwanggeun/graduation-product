package edu.kaist.cs.teamfinder

import retrofit2.http.GET
import retrofit2.http.Query

class UserRepository(
//    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) {
    suspend fun isKnownUser(email: String, password: String): LoginResponse {
        return remoteDataSource.checkUser(email, password)
    }
}

//class UserLocalDataSource {  }
class UserRemoteDataSource(
    private val loginService: LoginRetrofitService = RetrofitInstance.loginService
) {
    suspend fun checkUser(email: String, password: String): LoginResponse {
        return loginService.checkUser(email, password)
    }
}

interface LoginRetrofitService {
    @GET("/checkUser")
    suspend fun checkUser(
        @Query("email") email: String,
        @Query("password") password: String,
    ): LoginResponse
}

data class LoginResponse(val token: String)