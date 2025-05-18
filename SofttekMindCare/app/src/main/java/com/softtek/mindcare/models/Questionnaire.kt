package com.softtek.mindcare.models

data class Questionnaire(
    val id: Int,
    val title: String,
    val description: String,
    val questions: List<Question>
)