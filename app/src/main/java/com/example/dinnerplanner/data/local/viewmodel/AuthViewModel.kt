package com.example.dinnerplanner.data.local.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinnerplanner.data.local.database.entity.User
import com.example.dinnerplanner.data.local.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _authenticationState = MutableLiveData<Boolean>()

    val users: Flow<List<User>> = userRepository.getAllUsers()

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _loginState = MutableLiveData<Result<Unit>?>()
    val loginState: LiveData<Result<Unit>?> = _loginState

    private val _registerState = MutableLiveData<Result<Unit>>()
    val registrationState: LiveData<Result<Unit>> = _registerState

    private val _searchResults = MutableLiveData<List<User>>()
    val searchResults: LiveData<List<User>> = _searchResults

    fun getUserById(id: Int): Flow<User> {
        return userRepository.getUserById(id)
    }
    fun login(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            _loginState.value = Result.Error(IllegalArgumentException("Username and password must not be empty"))
            return
        }

        viewModelScope.launch {
            try {
                val user = userRepository.authenticate(username, password)
                if (user != null) {
                    _currentUser.value = user
                    _authenticationState.value = true
                    _loginState.value = Result.Success(Unit)
                    println("authenticated")
                    println("user id: ${user.id}")
                    println("username: $username")
                } else {
                    _authenticationState.value = false
                    _loginState.value = Result.Error(Exception("Invalid credentials"))
                    println("not authenticated")
                }
            } catch (e: Exception) {
                _loginState.value = Result.Error(e)
            }
        }
    }
    fun searchUser(newText: String) {
        viewModelScope.launch {
            val results = userRepository.getAllUsers()
                .firstOrNull()
                ?.filter { user -> user.username.contains(newText, ignoreCase = true) }
                ?: emptyList()
            _searchResults.value = results
        }
    }
    fun register(username: String, password: String) {
        viewModelScope.launch {
            try {
                userRepository.insertUser(username, password)
                _registerState.value = Result.Success(Unit)
            } catch (e: Exception) {
                _registerState.value = Result.Error(e)
            }
        }
    }


    fun logout() {
        _currentUser.value = null
        _authenticationState.value = false
        _loginState.value = null
    }

    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
    }
}