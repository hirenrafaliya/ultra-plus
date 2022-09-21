package com.app.ultraplus.usecase

import android.content.Context
import com.app.ultraplus.base.safeExecute
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.network.model.Feedback
import com.app.ultraplus.network.model.Reimbursement
import com.app.ultraplus.util.FsConstant
import com.app.ultraplus.util.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date
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

    suspend fun addReimbursement(reimbursement: Reimbursement,onSuccess: () -> Unit,onFailure: (String) -> Unit) = safeExecute(onFailure){
        fireStore.collection(FsConstant.REIMBURSEMENT_CL).add(reimbursement).await()
        onSuccess()
    }
}