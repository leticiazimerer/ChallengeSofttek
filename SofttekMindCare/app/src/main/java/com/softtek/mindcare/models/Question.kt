package com.softtek.mindcare.models

data class Question(
    val id: Int,
    val text: String,
    val options: List<String>,
    val type: QuestionType
)

enum class QuestionType {
    RADIO, CHECKBOX, SCALE
}