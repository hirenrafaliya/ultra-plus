package com.app.ultraplus.local

import com.app.ultraplus.network.model.User
import com.chibatching.kotpref.KotprefModel

object UserPref : KotprefModel() {
    private var userId by stringPref()
    private var phoneNumber by stringPref()
    private var userName by stringPref()
    private var status by stringPref()
    private var userType by stringPref()
    private var fcmToken by stringPref()
    private var email by stringPref()
    private var bio by stringPref()
    private val assignedManagers by stringSetPref()

    fun setUser(user: User) {
        userId = user.userId
        phoneNumber = user.phoneNumber
        userName = user.userName
        status = user.status
        userType = user.userType
        fcmToken = user.fcmToken
        email = user.email
        bio = user.bio
    }

    fun getUser() = User(
        userId = userId,
        phoneNumber = phoneNumber,
        userName = userName,
        status = status,
        userType = userType,
        fcmToken = fcmToken,
        email = email,
        bio = bio
    )

    fun isLoggedIn() = userId != ""
}