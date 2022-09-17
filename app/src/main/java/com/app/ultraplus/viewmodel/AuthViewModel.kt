package com.app.ultraplus.viewmodel

import androidx.lifecycle.ViewModel
import com.app.ultraplus.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCase: AuthUseCase
) : ViewModel() {

}