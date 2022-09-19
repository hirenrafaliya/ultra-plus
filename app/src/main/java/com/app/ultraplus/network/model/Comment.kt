package com.app.ultraplus.network.model

import com.google.firebase.firestore.PropertyName
import java.util.*

data class Comment(
    @PropertyName("text") val text: String="",
    @PropertyName("created_on") val createdOn: Date=Date(),
    @PropertyName("created_by") val createdBy: String="",
    @PropertyName("user_name") val userName: String=""
) {
    constructor() : this("", Date(), "", "")
}