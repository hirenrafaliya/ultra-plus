package com.app.ultraplus.network.model

import com.google.firebase.firestore.PropertyName
import java.util.*

data class User(
    @PropertyName("user_id") val userId: String = "",
    @PropertyName("phone_number") val phoneNumber: String = "",
    @PropertyName("user_name") val userName: String = "",
    @PropertyName("password") val password: String = "",
    @PropertyName("created_on") val createdOn: Date = Date(),
    @PropertyName("status") val status: String = UserStatus.PENDING.text,
    @PropertyName("user_type") val userType: String = "",
    @PropertyName("fcm_token") val fcmToken: String = "",
    @PropertyName("email") val email: String = "",
    @PropertyName("bio") val bio: String = "",
    @PropertyName("assigned_to") val assignedTo: String = ""
) {
    constructor() : this("")
}

enum class UserStatus(val text: String) {
    PENDING("pending"),
    ACTIVE("active"),
    INACTIVE("inactive")
}