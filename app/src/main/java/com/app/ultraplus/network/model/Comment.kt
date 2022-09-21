package com.app.ultraplus.network.model

import com.google.firebase.firestore.PropertyName
import java.util.*

data class Comment(
    @get:PropertyName("text") val text: String="",
    @get:PropertyName("created_on") val createdOn: Date=Date(),
    @get:PropertyName("created_by") val createdBy: String="",
    @get:PropertyName("user_name") val userName: String=""
) {
    constructor() : this("", Date(), "", "")
}