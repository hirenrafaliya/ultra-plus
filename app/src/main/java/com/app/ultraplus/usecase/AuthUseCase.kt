package com.app.ultraplus.usecase

import android.content.Context
import com.app.ultraplus.network.model.User
import com.app.ultraplus.util.Constant
import com.app.ultraplus.util.FsConstant
import com.app.ultraplus.util.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val context: Context,
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    suspend fun registerUser(user: User, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) = try {
        val firebaseUser = auth.createUserWithEmailAndPassword(user.email, user.password).await()
        val newUser = user.copy(userId = firebaseUser.user?.uid!!, createdOn = Date())

        fireStore.collection(FsConstant.USER_CL).document(newUser.userId).set(newUser).await()
        onSuccess(newUser)
    } catch (e: Exception){
        onFailure(e.message ?: Constant.UNKNOWN_ERROR_TEXT)
    }

}