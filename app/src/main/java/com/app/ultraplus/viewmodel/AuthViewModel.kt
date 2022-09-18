package com.app.ultraplus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ultraplus.network.model.User
import com.app.ultraplus.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RegisterUserState {
    object Idle: RegisterUserState()
    data class Loading(val isLoading: Boolean): RegisterUserState()
    data class Success(val user: User): RegisterUserState()
    data class Failure(val error: String) : RegisterUserState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCase: AuthUseCase
) : ViewModel() {

    val registerUserStates = MutableStateFlow<RegisterUserState>(RegisterUserState.Idle)
    fun registerUser(user: User) = viewModelScope.launch {
        registerUserStates.value = RegisterUserState.Loading(true)
        useCase.registerUser(user, onSuccess = {
            registerUserStates.value = RegisterUserState.Success(it)
        }, onFailure = {
            registerUserStates.value = RegisterUserState.Failure(it)
        })
    }

}