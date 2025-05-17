package com.softtek.mindcare.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "questionnaire_responses")
data class Questionnaire(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val questionnaireId: Int,
    val answers: Map<Int, Any>,
    val timestamp: Long = Date().time
)