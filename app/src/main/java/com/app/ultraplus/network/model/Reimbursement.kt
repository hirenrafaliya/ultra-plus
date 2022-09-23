package com.app.ultraplus.network.model

import com.google.firebase.firestore.PropertyName
import java.util.*

data class Reimbursement(
    @field:[JvmField PropertyName("created_by")] val createdBy: String = "",
    @field:[JvmField PropertyName("created_on")] val createdOn: Date = Date(),
    @field:[JvmField PropertyName("updated_on")] val updatedOn: Date = Date(),
    @field:[JvmField PropertyName("distance")] val distance: Float = 0.0f,
    @field:[JvmField PropertyName("unit")] val unit: String = "",
    @field:[JvmField PropertyName("user_name")] val userName: String = "",
) {
    constructor() : this("")
}