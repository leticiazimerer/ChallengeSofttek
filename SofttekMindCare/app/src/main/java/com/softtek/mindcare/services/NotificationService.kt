package com.softtek.mindcare.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.softtek.mindcare.R

class NotificationService(private val context: Context) {
    companion object {
        const val CHANNEL_ID = "mindcare_notifications"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "MindCare Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Wellbeing reminders and alerts"
        }

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    fun showReminderNotification() {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("MindCare Check-in")
            .setContentText("Time to log your mood and stress levels")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(1, notification)
    }
}