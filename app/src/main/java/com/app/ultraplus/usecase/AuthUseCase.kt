package com.app.ultraplus.usecase

import android.content.Context
import com.app.ultraplus.base.safeExecute
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.network.model.User
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

    suspend fun registerUser(user: User, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) =
        safeExecute(onFailure) {
            val docID = fireStore.collection(FsConstant.USER_CL).document().id
            val newUser = user.copy(userId = docID, createdOn = Date())

            fireStore.collection(FsConstant.USER_CL).document(newUser.userId).set(newUser).await()
            UserPref.setUser(newUser)
            onSuccess(newUser)
        }

    suspend fun loginUser(
        number: String,
        password: String,
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    ) =
        safeExecute(onFailure) {
            val user =
                fireStore.collection(FsConstant.USER_CL).whereEqualTo("phone_number", number)
                    .whereEqualTo("password", password).get().await()
            if (user.documents.isNotEmpty()) {
                val document = user.documents.firstOrNull()
                val user = document?.toObject(User::class.java)!!
                UserPref.setUser(user)
                onSuccess(user)
            } else {
                onFailure("User doesn't exist in DB")
            }
        }

    suspend fun fetchUser(userId: String, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) =
        safeExecute(onFailure) {
            val document = fireStore.collection(FsConstant.USER_CL).document(userId).get().await()
            val user = document.toObject(User::class.java)!!
            UserPref.setUser(user)

            if (document.exists()) onSuccess(user)
            else onFailure("User doesn't exist in DB")
        }

}