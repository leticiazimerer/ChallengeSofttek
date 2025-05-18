package com.softtek.mindcare.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "stress_entries")
data class StressEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val level: Int,
    val note: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)