package com.example.dinnerplanner.data.local.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinnerplanner.data.local.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _authenticationState = MutableLiveData<Boolean>()
    val authenticationState: LiveData<Boolean> = _authenticationState

    //user id by default is -1
    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId
    fun getUserId(): Int {
        return _userId.value ?: -1
    }


    private val _loginState = MutableLiveData<Result<Unit>>()
    val loginState: LiveData<Result<Unit>> = _loginState
    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val isAuthenticated = userRepository.authenticate(username, password)
                _userId.value = isAuthenticated
                _authenticationState.value = isAuthenticated != -1
                _loginState.value = Result.Success(Unit)
                if (isAuthenticated != -1) {
                    println("authenticated")
                    println("user id: $_userId")
                    println("username: $username")
                } else {
                    println("not authenticated")
                }
            } catch (e: Exception) {
                _loginState.value = Result.Error(e)
            }
        }
    }

    fun validateUsername(username: String): Boolean {
        // Add your validation logic here. For example:
        return username.isNotEmpty() && username.length >= 3
    }

    fun validatePassword(password: String): Boolean {
        // Add your validation logic here. For example:
        return password.isNotEmpty()
    }

    private val _registrationState = MutableLiveData<Result<Unit>>()
    val registrationState: LiveData<Result<Unit>> = _registrationState

    fun register(username: String, password: String) {
        println("register: $username, $password")
        viewModelScope.launch {
            try {
                userRepository.insertUser(username, password)
                _registrationState.value = Result.Success(Unit)
            } catch (e: Exception) {
                _registrationState.value = Result.Error(e)
                println("error: $e")
            }
        }
    }


    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
    }
}