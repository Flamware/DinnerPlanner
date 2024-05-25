package com.example.dinnerplanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _authenticationState = MutableLiveData<Boolean>()
    val authenticationState: LiveData<Boolean> = _authenticationState

    fun authenticate(username: String, password: String) {
        viewModelScope.launch {
            val isAuthenticated = userRepository.authenticate(username, password)
            _authenticationState.value = isAuthenticated
        }
    }
}
