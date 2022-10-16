package com.app.ultraplus.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.app.ultraplus.R

object NotificationHelper {

    enum class NotificationChannel(val id: String, val title: String) {
        REMINDER("reminder_notification", "Reminder"),
    }


    @OptIn(androidx.compose.material.ExperimentalMaterialApi::class)
    fun showNotification(context: Context, title: String, message: String, intent: Intent, reqCode: Int, channel: NotificationChannel) {
        createNotificationChannel(context, channel)

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
        }

        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, NotificationChannel.REMINDER.id)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.icon)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(reqCode, notificationBuilder.build())
    }

    private fun createNotificationChannel(context: Context, channel: NotificationChannel) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel =
                NotificationChannel(channel.id, channel.title, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}