package com.app.ultraplus.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

fun scheduleReminder(context: Context, title: String, time: Long) {
    val mAlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, ReminderReceiver::class.java)

    intent.putExtra("title", title)

    val alarmIntent = PendingIntent.getBroadcast(
        context,
        12,
        intent,
        PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()

    mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, time, alarmIntent)
}

