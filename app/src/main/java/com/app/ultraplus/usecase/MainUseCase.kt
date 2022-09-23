package com.app.ultraplus.usecase

import android.content.Context
import com.app.ultraplus.base.safeExecute
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.network.model.Feedback
import com.app.ultraplus.network.model.Reimbursement
import com.app.ultraplus.network.model.UserType
import com.app.ultraplus.util.FsConstant
import com.app.ultraplus.util.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class MainUseCase @Inject constructor(
    private val context: Context,
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    suspend fun addFeedback(feedback: Feedback, onSuccess: () -> Unit, onFailure: (String) -> Unit) = safeExecute(onFailure) {
        fireStore.collection(FsConstant.FEEDBACK_CL).add(feedback).await()
        onSuccess()
    }

    suspend fun addReimbursement(reimbursement: Reimbursement, onSuccess: () -> Unit, onFailure: (String) -> Unit) =
        safeExecute(onFailure) {
            fireStore.collection(FsConstant.REIMBURSEMENT_CL).add(reimbursement).await()
            onSuccess()
        }

    suspend fun getFeedbacks(onSuccess: (List<Feedback>) -> Unit, onFailure: (String) -> Unit) = safeExecute(onFailure) {
        val colRef = fireStore.collection(FsConstant.FEEDBACK_CL)

        val query = when (UserPref.getUser().userType) {
            UserType.REPORTING_MANAGER.text -> colRef.whereEqualTo("assigned_to", UserPref.getUser().userName)
            UserType.ADMIN.text -> colRef
            else -> colRef.whereEqualTo("created_by", UserPref.getUser().userId)
        }

        val documents = query.get().await()
        val feedbacks = if (!documents.isEmpty) documents.toObjects(Feedback::class.java) else listOf<Feedback>()
        onSuccess(feedbacks)
    }

    suspend fun getReimbursements(onSuccess: (List<Reimbursement>) -> Unit, onFailure: (String) -> Unit) = safeExecute(onFailure) {
        val colRef = fireStore.collection(FsConstant.REIMBURSEMENT_CL)

        val query = when (UserPref.getUser().userType) {
            UserType.REPORTING_MANAGER.text -> colRef.whereEqualTo("assigned_to", UserPref.getUser().userName)
            UserType.ADMIN.text -> colRef
            else -> colRef.whereEqualTo("created_by", UserPref.getUser().userId)
        }

        val documents = query.get().await()
        val reimbursements = if (!documents.isEmpty) documents.toObjects(Reimbursement::class.java) else listOf<Reimbursement>()
        onSuccess(reimbursements)
    }
}