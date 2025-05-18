package com.softtek.mindcare.api

import com.softtek.mindcare.models.Questionnaire
import com.softtek.mindcare.models.SupportResource
import retrofit2.Response

class MockApiService : ApiInterface {
    override suspend fun getQuestionnaires(): Response<List<Questionnaire>> {
        return Response.success(listOf(
            Questionnaire(
                id = 1,
                title = "Wellbeing Check",
                questions = listOf(
                    Question(
                        id = 1,
                        text = "How are you feeling today?",
                        type = QuestionType.SCALE,
                        options = listOf("1", "2", "3", "4", "5")
                    )
                )
            )
        ))
    }

    override suspend fun getSupportResources(): Response<List<SupportResource>> {
        return Response.success(listOf(
            SupportResource(
                id = 1,
                title = "Crisis Support",
                description = "24/7 helpline",
                type = "PHONE",
                url = "188"
            )
        ))
    }
}