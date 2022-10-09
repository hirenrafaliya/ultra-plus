package com.app.ultraplus.network.model

import androidx.compose.runtime.Composable
import com.app.ultraplus.ui.theme.AppTheme
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import java.util.*

data class User(
    @get:Exclude @set:Exclude var id:String = "",
    @field:[JvmField PropertyName("user_id")] val userId: String = "",
    @field:[JvmField PropertyName("phone_number")] val phoneNumber: String = "",
    @field:[JvmField PropertyName("user_name")] val userName: String = "",
    @field:[JvmField PropertyName("password")] val password: String = "",
    @field:[JvmField PropertyName("created_on")] val createdOn: Date = Date(),
    @field:[JvmField PropertyName("status")] var status: String = UserStatus.INACTIVE.text,
    @field:[JvmField PropertyName("user_type")] val userType: String = "",
    @field:[JvmField PropertyName("fcm_token")] val fcmToken: String = "",
    @field:[JvmField PropertyName("email")] val email: String = "",
    @field:[JvmField PropertyName("bio")] val bio: String = "",
    @field:[JvmField PropertyName("reporting_manager_id")] var reportingMangerId: String = "",
    @field:[JvmField PropertyName("reporting_manager_name")] var reportingMangerName: String = ""
) {
    constructor() : this("")

    companion object {
        @Composable
        fun getStatusColor(status: String) = when (status) {
            UserStatus.PENDING.text -> AppTheme.colors.StatusRed
            UserStatus.ACTIVE.text -> AppTheme.colors.StatusGreen
            UserStatus.INACTIVE.text -> AppTheme.colors.StatusRed
            else -> AppTheme.colors.StatusRed
        }
    }
}

enum class UserStatus(val text: String) {
    PENDING("pending"),
    ACTIVE("active"),
    INACTIVE("inactive")
}

enum class UserType(val text: String, val display: String) {
    AREA_MANAGER("area_manager", "Area manager"),
    REPORTING_MANAGER("reporting_manager", "Reporting manager"),
    ADMIN("admin", "Admin")
}