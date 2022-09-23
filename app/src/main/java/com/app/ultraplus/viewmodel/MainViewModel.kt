package com.app.ultraplus.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ultraplus.network.model.Feedback
import com.app.ultraplus.network.model.Reimbursement
import com.app.ultraplus.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MainUseCase
): ViewModel() {

    var currentSelectedBottomBarItem = mutableStateOf("Feedback")

    var selectedFeedback : Feedback = Feedback()

    fun addFeedback(feedback: Feedback, onSuccess: () -> Unit, onFailure: (String) -> Unit) = viewModelScope.launch {
        useCase.addFeedback(feedback, onSuccess, onFailure)
    }

    fun addReimbursement(reimbursement: Reimbursement, onSuccess: () -> Unit, onFailure: (String) -> Unit) =
        viewModelScope.launch {
            useCase.addReimbursement(reimbursement, onSuccess, onFailure)
        }

    fun getFeedbacks(onSuccess: (List<Feedback>) -> Unit, onFailure: (String) -> Unit) = viewModelScope.launch {
        useCase.getFeedbacks(onSuccess, onFailure)
    }

    fun getReimbursements(onSuccess: (List<Reimbursement>) -> Unit, onFailure: (String) -> Unit) = viewModelScope.launch {
        useCase.getReimbursements(onSuccess, onFailure)
    }
}