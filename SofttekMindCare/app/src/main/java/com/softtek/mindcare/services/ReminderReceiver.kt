package com.softtek.mindcare.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED -> scheduleDailyReminder(context)
        }
    }

    private fun scheduleDailyReminder(context: Context) {
        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}