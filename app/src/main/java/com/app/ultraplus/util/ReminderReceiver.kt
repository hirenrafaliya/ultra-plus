package com.app.ultraplus.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.ultraplus.MainActivity

class ReminderReceiver : BroadcastReceiver() {

    @OptIn( androidx.compose.material.ExperimentalMaterialApi::class)
    override fun onReceive(context: Context, intent: Intent?) {
        val title = intent?.getStringExtra("title") ?: "Reminder triggered"
        val intentMain = Intent(context, MainActivity::class.java)

        NotificationHelper.showNotification(
            context,
            title,
            "",
            intentMain,
            12,
            NotificationHelper.NotificationChannel.REMINDER
        )
    }
}

