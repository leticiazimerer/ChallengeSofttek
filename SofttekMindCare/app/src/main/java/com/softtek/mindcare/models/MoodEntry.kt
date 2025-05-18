package com.softtek.mindcare.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "mood_entries")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val moodType: String,
    val intensity: Int,
    val note: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)