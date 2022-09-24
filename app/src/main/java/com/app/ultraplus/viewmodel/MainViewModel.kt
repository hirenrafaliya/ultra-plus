package com.app.ultraplus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var feedbacks by mutableStateOf(listOf<Feedback>())
    var reimbursements by mutableStateOf(listOf<Reimbursement>())

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

    fun getComments(feedback: Feedback, onSuccess: (List<Feedback.Comment>) -> Unit, onFailure: (String) -> Unit) =
        viewModelScope.launch {
            useCase.getComments(feedback, onSuccess, onFailure)
        }

    fun addComment(feedback: Feedback, comment: Feedback.Comment, onSuccess: () -> Unit, onFailure: (String) -> Unit) =
        viewModelScope.launch {
            useCase.addComment(feedback, comment, onSuccess, onFailure)
        }

    fun updateStatus(feedback: Feedback, onSuccess: () -> Unit, onFailure: (String) -> Unit) = viewModelScope.launch {
        useCase.updateStatus(feedback, onSuccess, onFailure)
    }
}