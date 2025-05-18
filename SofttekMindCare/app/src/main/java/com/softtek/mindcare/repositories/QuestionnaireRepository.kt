package com.softtek.mindcare.repositories

import com.softtek.mindcare.api.ApiInterface
import com.softtek.mindcare.database.QuestionnaireDao
import com.softtek.mindcare.models.Questionnaire
import com.softtek.mindcare.models.QuestionnaireResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuestionnaireRepository @Inject constructor(
    private val api: ApiInterface,
    private val dao: QuestionnaireDao
) {
    suspend fun fetchQuestionnaires(): List<Questionnaire> = api.getQuestionnaires()
    suspend fun saveResponse(response: QuestionnaireResponse) = dao.insert(response)
    fun getResponses(): Flow<List<QuestionnaireResponse>> = dao.getAllResponses()
}