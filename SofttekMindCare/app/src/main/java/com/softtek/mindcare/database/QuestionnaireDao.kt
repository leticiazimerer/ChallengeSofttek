package com.softtek.mindcare.database

import androidx.room.*
import com.softtek.mindcare.models.QuestionnaireResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionnaireDao {
    @Insert
    suspend fun insert(response: QuestionnaireResponse)

    @Query("SELECT * FROM questionnaire_responses ORDER BY timestamp DESC")
    fun getAllResponses(): Flow<List<QuestionnaireResponse>>

    @Query("SELECT * FROM questionnaire_responses WHERE questionnaireId = :questionnaireId ORDER BY timestamp DESC")
    suspend fun getResponsesForQuestionnaire(questionnaireId: Int): List<QuestionnaireResponse>

    @Query("SELECT * FROM questionnaire_responses WHERE id = :id")
    suspend fun getResponseById(id: Int): QuestionnaireResponse?
}