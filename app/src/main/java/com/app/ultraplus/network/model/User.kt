package com.app.ultraplus.network.model

import com.google.firebase.firestore.PropertyName
import java.util.*

data class User(
    @get:PropertyName("user_id") val userId: String = "",
    @get:PropertyName("phone_number") val phoneNumber: String = "",
    @get:PropertyName("user_name") val userName: String = "",
    @get:PropertyName("password") val password: String = "",
    @get:PropertyName("created_on") val createdOn: Date = Date(),
    @get:PropertyName("status") val status: String = UserStatus.PENDING.text,
    @get:PropertyName("user_type") val userType: String = "",
    @get:PropertyName("fcm_token") val fcmToken: String = "",
    @get:PropertyName("email") val email: String = "",
    @get:PropertyName("bio") val bio: String = "",
    @get:PropertyName("assigned_to") val assignedTo: String = ""
) {
    constructor() : this("")
}

enum class UserStatus(val text: String) {
    PENDING("pending"),
    ACTIVE("active"),
    INACTIVE("inactive")
}