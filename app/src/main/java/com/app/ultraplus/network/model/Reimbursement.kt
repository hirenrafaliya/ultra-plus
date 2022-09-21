package com.app.ultraplus.network.model

import com.google.firebase.firestore.PropertyName
import java.util.*

data class Reimbursement(
    @get:PropertyName("created_by") val createdBy: String = "",
    @get:PropertyName("created_on") val createdOn: Date = Date(),
    @get:PropertyName("updated_on") val updatedOn: Date = Date(),
    @get:PropertyName("distance") val distance: Float = 0.0f,
    @get:PropertyName("unit") val unit: String = "",
    @get:PropertyName("user_name") val userName: String = "",
) {
    constructor() : this("")
}