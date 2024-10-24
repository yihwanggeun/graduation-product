package edu.kaist.cs.teamfinder

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// Log In View Model
class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    // 로그인 상태를 관리하는 LiveData 등을 정의할 수 있음
    var signInSuccess = mutableStateOf(false)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val loginResponse = userRepository.isKnownUser(email, password)

                // 로그인 응답 처리 로직
                // 예를 들어, 로그인 성공 시 토큰을 저장하거나 다음 화면으로 이동하는 등의 동작 수행
                if (loginResponse.token == "[]") {
                    println("no data")
                } else {
                    // 로그인 성공 시 메인 화면으로 이동
                    println(loginResponse.token)

                }
            } catch (e: Exception) {
                Log.d("Exception", e.toString())
            }
        }
    }
}

//
// View Model Factory
class LoginViewModelFactory(private val userRepository: UserRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}