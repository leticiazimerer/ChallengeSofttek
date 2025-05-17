package com.softtek.mindcare.services

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.softtek.mindcare.R
import com.softtek.mindcare.activities.DashboardActivity

class NotificationService(private val context: Context) {
    fun showWellbeingReminder() {
        val intent = Intent(context, DashboardActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, MIND_CARE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_mindcare_notification)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText("Time to check your wellbeing")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(WELLBEING_NOTIFICATION_ID, notification)
    }

    companion object {
        const val MIND_CARE_CHANNEL_ID = "mind_care_channel"
        const val WELLBEING_NOTIFICATION_ID = 1
    }
}