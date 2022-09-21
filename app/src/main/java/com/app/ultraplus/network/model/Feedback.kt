package com.app.ultraplus.network.model

import com.google.firebase.firestore.PropertyName
import java.util.*

data class Feedback(
    @PropertyName("description") val description: String = "",
    @PropertyName("created_on") val createdOn: Date = Date(),
    @PropertyName("created_by") val createdBy: String = "",
    @PropertyName("updated_on") val updatedOn: Date = Date(),
    @PropertyName("status") val status: String = FeedbackStatus.PENDING.text,
    @PropertyName("shop_name") val shopName: String = "",
    @PropertyName("owner_name") val ownerName: String = "",
    @PropertyName("shop_address") val shopAddress: String = "",
    @PropertyName("owner_number") val ownerNumber: String = "",
    @PropertyName("user_name") val userName: String = "",
    @PropertyName("city") val city: String = "",
    @PropertyName("pin_code") val pinCode: String = "",
) {
    constructor() : this("")
}

enum class FeedbackStatus(val text: String) {
    PENDING("pending"),
    REVIEWED("reviewed"),
    CLOSED("closed")
}