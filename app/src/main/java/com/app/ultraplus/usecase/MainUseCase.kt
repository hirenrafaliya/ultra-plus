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
import com.google.firebase.firestore.Query
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

        val documents = query.orderBy("created_on",Query.Direction.DESCENDING).get().await()
        val feedbacks = if (!documents.isEmpty) {
            documents.toObjects(Feedback::class.java).apply {
                forEachIndexed { index, value ->
                    value.id = documents.documents[index].id
                }
            }
        } else listOf<Feedback>()
        onSuccess(feedbacks)
    }


    suspend fun getReimbursements(onSuccess: (List<Reimbursement>) -> Unit, onFailure: (String) -> Unit) =
        safeExecute(onFailure) {
            val colRef = fireStore.collection(FsConstant.REIMBURSEMENT_CL)

            val query = when (UserPref.getUser().userType) {
                UserType.REPORTING_MANAGER.text -> colRef.whereEqualTo("assigned_to", UserPref.getUser().userName)
                UserType.ADMIN.text -> colRef
                else -> colRef.whereEqualTo("created_by", UserPref.getUser().userId)
            }

            val documents = query.orderBy("created_on",Query.Direction.DESCENDING).get().await()
            val reimbursements =
                if (!documents.isEmpty) {
                    documents.toObjects(Reimbursement::class.java).apply {
                        forEachIndexed { index, value ->
                            value.id = documents.documents[index].id
                        }
                    }
                } else listOf<Reimbursement>()
            onSuccess(reimbursements)
        }

    suspend fun getComments(feedback: Feedback, onSuccess: (List<Feedback.Comment>) -> Unit, onFailure: (String) -> Unit) =
        safeExecute(onFailure) {
            val documents =
                fireStore.collection(FsConstant.FEEDBACK_CL).document(feedback.id).collection(FsConstant.COMMENT_CL).orderBy("created_on",Query.Direction.DESCENDING).get().await()

            val comments = if (!documents.isEmpty) {
                documents.toObjects(Feedback.Comment::class.java).apply {
                    forEachIndexed { index, value ->
                        value.id = documents.documents[index].id
                    }
                }
            } else listOf<Feedback.Comment>()
            onSuccess(comments)
        }

    suspend fun addComment(feedback: Feedback, comment: Feedback.Comment, onSuccess: () -> Unit, onFailure: (String) -> Unit) =
        safeExecute(onFailure) {
            fireStore.collection(FsConstant.FEEDBACK_CL).document(feedback.id).collection(FsConstant.COMMENT_CL).add(comment)
                .await()
            onSuccess()
        }

    suspend fun updateStatus(feedback: Feedback, onSuccess: () -> Unit, onFailure: (String) -> Unit) = safeExecute(onFailure) {
        fireStore.collection(FsConstant.FEEDBACK_CL).document(feedback.id).update("status", feedback.status)
            .await()
        onSuccess()
    }
}