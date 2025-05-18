package com.softtek.mindcare.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey val id: Int = 1,
    val notificationsEnabled: Boolean = true,
    val remindersEnabled: Boolean = true,
    val reminderHour: Int = 18,
    val reminderMinute: Int = 0
)