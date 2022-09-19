package com.app.ultraplus.local

import com.app.ultraplus.network.model.User
import com.chibatching.kotpref.KotprefModel

object UserPref : KotprefModel() {
    var userId by stringPref()
    var phoneNumber by stringPref()
    var userName by stringPref()
    var status by stringPref()
    var userType by stringPref()
    var fcmToken by stringPref()
    var email by stringPref()
    var bio by stringPref()
    var assignedTo by stringPref()

    fun setUser(user: User) {
        userId = user.userId
        phoneNumber = user.phoneNumber
        userName = user.userName
        status = user.status
        userType = user.userType
        fcmToken = user.fcmToken
        email = user.email
        bio = user.bio
        assignedTo = user.assignedTo
    }

    fun getUser() = User(
        userId = userId,
        phoneNumber = phoneNumber,
        userName = userName,
        status = status,
        userType = userType,
        fcmToken = fcmToken,
        email = email,
        bio = bio,
        assignedTo = assignedTo
    )
}