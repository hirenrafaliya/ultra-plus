package com.app.ultraplus.network.model

import androidx.compose.runtime.Composable
import com.app.ultraplus.ui.theme.AppTheme
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import java.util.*

data class Feedback(
    @get:Exclude @set:Exclude var id:String = "",
    @field:[JvmField PropertyName("description")] val description: String = "",
    @field:[JvmField PropertyName("created_on")] val createdOn: Date = Date(),
    @field:[JvmField PropertyName("created_by")] val createdBy: String = "",
    @field:[JvmField PropertyName("updated_on")] val updatedOn: Date = Date(),
    @field:[JvmField PropertyName("status")] val status: String = FeedbackStatus.PENDING.text,
    @field:[JvmField PropertyName("shop_name")] val shopName: String = "",
    @field:[JvmField PropertyName("owner_name")] val ownerName: String = "",
    @field:[JvmField PropertyName("shop_address")] val shopAddress: String = "",
    @field:[JvmField PropertyName("owner_number")] val ownerNumber: String = "",
    @field:[JvmField PropertyName("user_name")] val userName: String = "",
    @field:[JvmField PropertyName("city")] val city: String = "",
    @field:[JvmField PropertyName("pin_code")] val pinCode: String = "",
) {
    constructor() : this("")

    companion object {
        @Composable
        fun getStatusColor(status: String) = when (status) {
            FeedbackStatus.PENDING.text -> AppTheme.colors.StatusRed
            FeedbackStatus.REVIEWED.text -> AppTheme.colors.StatusYellow
            FeedbackStatus.CLOSED.text -> AppTheme.colors.StatusGreen
            else -> AppTheme.colors.StatusRed
        }
    }

    @Exclude
    fun getFeedbackStatus(): FeedbackStatus = when (status) {
        FeedbackStatus.PENDING.text -> FeedbackStatus.PENDING
        FeedbackStatus.REVIEWED.text -> FeedbackStatus.REVIEWED
        FeedbackStatus.CLOSED.text -> FeedbackStatus.CLOSED
        else -> FeedbackStatus.PENDING
    }

    data class Comment(
        @get:Exclude @set:Exclude var id:String = "",
        @field:[JvmField PropertyName("text")] val text: String = "",
        @field:[JvmField PropertyName("created_on")] val createdOn: Date = Date(),
        @field:[JvmField PropertyName("created_by")] val createdBy: String = "",
        @field:[JvmField PropertyName("user_name")] val userName: String = ""
    ) {
        constructor() : this("","", Date(), "", "")
    }
}

enum class FeedbackStatus(val text: String, val display: String) {
    PENDING("pending", "Pending"),
    REVIEWED("reviewed", "Reviewed"),
    CLOSED("closed", "Closed")
}