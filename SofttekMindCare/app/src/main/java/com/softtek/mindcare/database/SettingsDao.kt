package com.softtek.mindcare.database

import androidx.room.*
import com.softtek.mindcare.models.UserSettings

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: UserSettings)

    @Query("SELECT * FROM user_settings LIMIT 1")
    suspend fun getSettings(): UserSettings?

    @Update
    suspend fun update(settings: UserSettings)

    @Query("UPDATE user_settings SET notificationsEnabled = :enabled")
    suspend fun updateNotificationsEnabled(enabled: Boolean)

    @Query("UPDATE user_settings SET remindersEnabled = :enabled")
    suspend fun updateRemindersEnabled(enabled: Boolean)

    @Query("UPDATE user_settings SET reminderHour = :hour, reminderMinute = :minute")
    suspend fun updateReminderTime(hour: Int, minute: Int)
}