package com.softtek.mindcare.repositories

import com.softtek.mindcare.api.ApiClient
import com.softtek.mindcare.database.SupportDao
import com.softtek.mindcare.models.SupportResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SupportRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val supportDao: SupportDao
) {
    suspend fun getSupportResources(): List<SupportResource> {
        val apiResources = try {
            apiClient.apiService.getSupportResources().body() ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        if (apiResources.isNotEmpty()) {
            supportDao.insertAll(apiResources)
        }

        return supportDao.getAll()
    }

    suspend fun getEmergencyResources(): List<SupportResource> {
        return listOf(
            SupportResource(
                id = 1,
                title = "Emergency Psychological Support",
                description = "24/7 crisis support line",
                type = "PHONE",
                url = "188"
            ),
            SupportResource(
                id = 2,
                title = "Workplace Harassment Hotline",
                description = "Report workplace issues",
                type = "PHONE",
                url = "180"
            ),
            SupportResource(
                id = 3,
                title = "Softtek Internal Support",
                description = "HR confidential channel",
                type = "EMAIL",
                url = "hr-support@softtek.com"
            )
        )
    }

    fun getFavoriteResources(): Flow<List<SupportResource>> {
        return supportDao.getFavorites()
    }

    suspend fun toggleFavorite(resourceId: Int) {
        supportDao.toggleFavorite(resourceId)
    }
}