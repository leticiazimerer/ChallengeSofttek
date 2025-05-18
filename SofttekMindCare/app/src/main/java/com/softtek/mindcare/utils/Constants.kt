package com.softtek.mindcare.utils

object Constants {
    // Mood types
    const val MOOD_HAPPY = "happy"
    const val MOOD_GOOD = "good"
    const val MOOD_NEUTRAL = "neutral"
    const val MOOD_SAD = "sad"
    const val MOOD_ANGRY = "angry"

    // SharedPreferences keys
    const val PREFS_NAME = "MindCarePrefs"
    const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
    const val KEY_REMINDERS_ENABLED = "reminders_enabled"
    const val KEY_REMINDER_TIME = "reminder_time"

    // API
    const val BASE_URL = "https://api.softtek-mindcare.com/v1/"
    const val API_TIMEOUT = 30L // seconds

    // WorkManager
    const val REMINDER_WORK_TAG = "mindcare_daily_reminder"

    // Notification
    const val NOTIFICATION_CHANNEL_ID = "mindcare_notifications"
    const val NOTIFICATION_ID = 1

    // Default values
    const val DEFAULT_REMINDER_HOUR = 18
    const val DEFAULT_REMINDER_MINUTE = 0
}