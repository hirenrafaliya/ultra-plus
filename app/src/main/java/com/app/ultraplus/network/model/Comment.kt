package com.app.ultraplus.network.model

import com.google.firebase.firestore.PropertyName
import java.util.*

data class Comment(
    @field:[JvmField PropertyName("text")] val text: String="",
    @field:[JvmField PropertyName("created_on")] val createdOn: Date=Date(),
    @field:[JvmField PropertyName("created_by")] val createdBy: String="",
    @field:[JvmField PropertyName("user_name")] val userName: String=""
) {
    constructor() : this("", Date(), "", "")
}