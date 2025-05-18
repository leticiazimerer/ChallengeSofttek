package com.softtek.mindcare.repositories

import com.softtek.mindcare.api.ApiInterface
import com.softtek.mindcare.database.SupportDao
import com.softtek.mindcare.models.SupportResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SupportRepository @Inject constructor(
    private val api: ApiInterface,
    private val dao: SupportDao
) {
    suspend fun refreshSupportResources(): List<SupportResource> {
        val resources = api.getSupportResources()
        dao.insertAll(resources)
        return resources
    }

    fun getFavorites(): Flow<List<SupportResource>> = dao.getFavorites()
    suspend fun toggleFavorite(resource: SupportResource) =
        dao.updateFavoriteStatus(resource.id, !resource.isFavorite)
}