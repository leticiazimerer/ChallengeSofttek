package com.softtek.mindcare.api

import com.softtek.mindcare.models.*
import retrofit2.Response
import retrofit2.http.*
import javax.inject.Inject

class MockApiService @Inject constructor() : ApiInterface {
    override suspend fun getQuestionnaires(): Response<List<Questionnaire>> {
        return Response.success(listOf(
            Questionnaire(
                id = 1,
                title = "Wellbeing Check",
                description = "Assess your current mental state",
                questions = listOf(
                    Question(
                        id = 1,
                        text = "How would you rate your stress level today?",
                        options = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
                        type = QuestionType.SCALE
                    ),
                    Question(
                        id = 2,
                        text = "Which emotions did you experience today?",
                        options = listOf("Anxiety", "Sadness", "Frustration", "Happiness", "Calmness"),
                        type = QuestionType.CHECKBOX
                    )
                )
            )
        ))
    }

    override suspend fun getSupportResources(): Response<List<SupportResource>> {
        return Response.success(listOf(
            SupportResource(
                id = 1,
                title = "Psychological Support",
                description = "24/7 professional help",
                type = "PHONE",
                url = "188"
            ),
            SupportResource(
                id = 2,
                title = "Meditation Guides",
                description = "Mindfulness exercises",
                type = "URL",
                url = "https://mindcare.softtek.com/meditation"
            )
        ))
    }

    override suspend fun sendAnonymousMoodData(data: MoodAnalyticsRequest): Response<AnalyticsResponse> {
        return Response.success(AnalyticsResponse("Data received successfully"))
    }

    override suspend fun checkForUpdates(version: String): Response<AppUpdate> {
        return Response.success(AppUpdate(false, ""))
    }
}