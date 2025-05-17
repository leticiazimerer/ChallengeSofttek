package com.softtek.mindcare.api

import com.softtek.mindcare.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    @GET("wellbeing/questionnaires")
    suspend fun getQuestionnaires(): Response<List<Questionnaire>>

    @GET("support/resources")
    suspend fun getSupportResources(): Response<List<SupportResource>>

    @POST("analytics/mood-data")
    suspend fun sendAnonymousMoodData(
        @Body data: MoodAnalyticsRequest
    ): Response<AnalyticsResponse>

    @GET("app/updates")
    suspend fun checkForUpdates(
        @Query("version") currentVersion: String
    ): Response<AppUpdate>
}