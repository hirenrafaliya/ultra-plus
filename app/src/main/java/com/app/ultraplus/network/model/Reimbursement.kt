package com.app.ultraplus.network.model

import com.google.firebase.firestore.PropertyName
import java.util.*

data class Reimbursement(
    @PropertyName("created_by") val createdBy: String,
    @PropertyName("created_on") val createdOn: Date,
    @PropertyName("distance") val distance: Float,
    @PropertyName("unit") val unit: String,
    @PropertyName("user_name") val userName: String,
)