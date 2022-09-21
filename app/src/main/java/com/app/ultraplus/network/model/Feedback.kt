package com.app.ultraplus.network.model

import androidx.compose.runtime.Composable
import com.app.ultraplus.ui.theme.AppTheme
import com.google.firebase.firestore.PropertyName
import java.util.*

data class Feedback(
    @get:PropertyName("description") val description: String = "",
    @get:PropertyName("created_on") val createdOn: Date = Date(),
    @get:PropertyName("created_by") val createdBy: String = "",
    @get:PropertyName("updated_on") val updatedOn: Date = Date(),
    @get:PropertyName("status") val status: String = FeedbackStatus.PENDING.text,
    @get:PropertyName("shop_name") val shopName: String = "",
    @get:PropertyName("owner_name") val ownerName: String = "",
    @get:PropertyName("shop_address") val shopAddress: String = "",
    @get:PropertyName("owner_number") val ownerNumber: String = "",
    @get:PropertyName("user_name") val userName: String = "",
    @get:PropertyName("city") val city: String = "",
    @get:PropertyName("pin_code") val pinCode: String = "",
) {
    constructor() : this("")

    @Composable
    fun getStatusColor() = when (status) {
        FeedbackStatus.PENDING.text -> AppTheme.colors.StatusRed
        FeedbackStatus.REVIEWED.text -> AppTheme.colors.StatusYellow
        FeedbackStatus.CLOSED.text -> AppTheme.colors.StatusGreen
        else -> AppTheme.colors.StatusRed
    }
}

enum class FeedbackStatus(val text: String) {
    PENDING("pending"),
    REVIEWED("reviewed"),
    CLOSED("closed")
}