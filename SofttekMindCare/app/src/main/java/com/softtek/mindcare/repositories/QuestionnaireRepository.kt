package com.softtek.mindcare.repositories

import com.softtek.mindcare.api.ApiClient
import com.softtek.mindcare.database.QuestionnaireDao
import com.softtek.mindcare.models.Questionnaire
import com.softtek.mindcare.models.QuestionnaireResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuestionnaireRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val questionnaireDao: QuestionnaireDao
) {
    suspend fun getQuestionnaires(): List<Questionnaire> {
        return apiClient.apiService.getQuestionnaires().body() ?: emptyList()
    }

    suspend fun saveQuestionnaireResponse(response: QuestionnaireResponse) {
        questionnaireDao.insertResponse(response)
    }

    fun getQuestionnaireResponses(): Flow<List<QuestionnaireResponse>> {
        return questionnaireDao.getAllResponses()
    }
}