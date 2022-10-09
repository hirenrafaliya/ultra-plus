package com.app.ultraplus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ultraplus.network.model.User
import com.app.ultraplus.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCase: AuthUseCase
) : ViewModel() {

    fun registerUser(user: User, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) = viewModelScope.launch {
        useCase.registerUser(user, onSuccess, onFailure)
    }

    fun loginUser(number: String, password: String, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) = viewModelScope.launch {
        useCase.loginUser(number, password, onSuccess, onFailure)
    }

    fun fetchUser(userId: String, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) = viewModelScope.launch {
        useCase.fetchUser(userId, onSuccess, onFailure)
    }

}