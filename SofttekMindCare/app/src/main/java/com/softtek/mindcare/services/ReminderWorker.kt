package com.softtek.mindcare.services

import android.app.NotificationManager
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.softtek.mindcare.R
import com.softtek.mindcare.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            showReminderNotification()

            val hour = inputData.getInt("hour", 18)
            val minute = inputData.getInt("minute", 0)
            scheduleNextReminder(applicationContext, hour, minute)

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private suspend fun showReminderNotification() {
        val settings = AppDatabase.getDatabase(applicationContext)
            .settingsDao()
            .getSettings()

        if (settings?.notificationsEnabled == true) {
            val notificationService = NotificationService(applicationContext)
            notificationService.showWellbeingReminder()
        }
    }

    private fun scheduleNextReminder(context: Context, hour: Int, minute: Int) {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            action = "android.intent.action.TIME_SET"
        }
        context.sendBroadcast(intent)
    }
}