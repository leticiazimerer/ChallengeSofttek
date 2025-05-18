package com.softtek.mindcare.services

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.softtek.mindcare.repositories.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val settings = SettingsRepository(applicationContext).getSettings()
            if (settings?.remindersEnabled == true) {
                NotificationService(applicationContext).showReminderNotification()
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}