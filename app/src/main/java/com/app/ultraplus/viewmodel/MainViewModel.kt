package com.app.ultraplus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ultraplus.network.model.Feedback
import com.app.ultraplus.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MainUseCase
): ViewModel() {

    fun addFeedback(feedback: Feedback, onSuccess: () -> Unit, onFailure: (String) -> Unit) = viewModelScope.launch {
        useCase.addFeedback(feedback, onSuccess, onFailure)
    }
}