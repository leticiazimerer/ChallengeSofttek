package com.softtek.mindcare.api

import com.softtek.mindcare.models.Questionnaire
import com.softtek.mindcare.models.SupportResource
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("wellbeing/questionnaires")
    suspend fun getQuestionnaires(): Response<List<Questionnaire>>

    @GET("support/resources")
    suspend fun getSupportResources(): Response<List<SupportResource>>
}