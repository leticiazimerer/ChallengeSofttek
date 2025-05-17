package com.softtek.mindcare

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.softtek.mindcare.database.AppDatabase
import com.softtek.mindcare.services.NotificationService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        initializeNotificationChannels()
        initializeDefaultSettings()
    }

    private fun initializeNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationService.MIND_CARE_CHANNEL_ID,
                "MindCare Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for wellbeing reminders and updates"
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun initializeDefaultSettings() {
        viewModelScope.launch {
            if (database.settingsDao().getSettings() == null) {
                database.settingsDao().saveSettings(
                    UserSettings(
                        id = 1,
                        notificationsEnabled = true,
                        remindersEnabled = true,
                        reminderHour = 18,
                        reminderMinute = 0
                    )
                )
            }
        }
    }

    companion object {
        fun getAppContext(): Context {
            return instance.applicationContext
        }

        private lateinit var instance: App

        val appInstance: App
            get() = instance
    }

    init {
        instance = this
    }
}