package com.softtek.mindcare.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "support_resources")
data class SupportResource(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val type: String,
    val url: String,
    val isFavorite: Boolean = false
)