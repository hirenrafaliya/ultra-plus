package com.app.ultraplus.usecase

import android.content.Context
import com.app.ultraplus.base.safeExecute
import com.app.ultraplus.local.UserPref
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

    suspend fun registerUser(user: User, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) = safeExecute(onFailure) {
        val firebaseUser = auth.createUserWithEmailAndPassword(user.email, user.password).await()
        val newUser = user.copy(userId = firebaseUser.user?.uid!!, createdOn = Date())

        fireStore.collection(FsConstant.USER_CL).document(newUser.userId).set(newUser).await()
        auth.signInWithEmailAndPassword(newUser.email, newUser.password).await()
        UserPref.setUser(newUser)
        onSuccess(newUser)
    }

    suspend fun loginUser(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) =
        safeExecute(onFailure) {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val document =
                fireStore.collection(FsConstant.USER_CL).document(result.user!!.uid).get().await()
            if (document.exists()) {
                UserPref.setUser(document.toObject(User::class.java)!!)
                onSuccess()
            }
            else onFailure("Error 601 : User doesn't exist in DB")
        }

    suspend fun fetchUser(userId: String, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) = safeExecute(onFailure) {
        val document = fireStore.collection(FsConstant.USER_CL).document(userId).get().await()
        if(document.exists()) onSuccess(document.toObject(User::class.java)!!)
        else onFailure("Error 602 : User doesn't exist in DB")
    }

}