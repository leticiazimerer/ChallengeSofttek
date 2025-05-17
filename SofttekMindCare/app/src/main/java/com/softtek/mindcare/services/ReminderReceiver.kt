package com.softtek.mindcare.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.softtek.mindcare.database.AppDatabase
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED,
            "android.intent.action.TIME_SET",
            "android.intent.action.TIMEZONE_CHANGED" -> {
                rescheduleReminders(context)
            }
        }
    }

    private fun rescheduleReminders(context: Context) {
        runBlocking {
            val settings = AppDatabase.getDatabase(context).settingsDao().getSettings()
            if (settings?.remindersEnabled == true) {
                scheduleDailyReminder(
                    context,
                    settings.reminderHour,
                    settings.reminderMinute
                )
            }
        }
    }

    private fun scheduleDailyReminder(context: Context, hour: Int, minute: Int) {
        val currentTime = System.currentTimeMillis()
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        var triggerTime = calendar.timeInMillis
        if (triggerTime <= currentTime) {
            triggerTime += 24 * 60 * 60 * 1000
        }

        val delay = triggerTime - currentTime

        val reminderWork = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(
                Data.Builder()
                    .putInt("hour", hour)
                    .putInt("minute", minute)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueue(reminderWork)
    }
}