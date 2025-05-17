package com.softtek.mindcare.database

import androidx.room.*
import com.softtek.mindcare.models.UserSettings

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: UserSettings)

    @Query("SELECT * FROM user_settings LIMIT 1")
    suspend fun getSettings(): UserSettings?

    @Query("UPDATE user_settings SET notificationsEnabled = :enabled")
    suspend fun updateNotifications(enabled: Boolean)

    @Query("UPDATE user_settings SET remindersEnabled = :enabled")
    suspend fun updateReminders(enabled: Boolean)

    @Query("UPDATE user_settings SET reminderHour = :hour, reminderMinute = :minute")
    suspend fun updateReminderTime(hour: Int, minute: Int)
}