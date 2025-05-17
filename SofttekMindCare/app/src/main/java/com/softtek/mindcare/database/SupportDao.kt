package com.softtek.mindcare.database

import androidx.room.*
import com.softtek.mindcare.models.SupportResource
import kotlinx.coroutines.flow.Flow

@Dao
interface SupportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(resources: List<SupportResource>)

    @Query("SELECT * FROM support_resources")
    suspend fun getAll(): List<SupportResource>

    @Query("SELECT * FROM support_resources WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<SupportResource>>

    @Query("UPDATE support_resources SET isFavorite = NOT isFavorite WHERE id = :resourceId")
    suspend fun toggleFavorite(resourceId: Int)

    @Query("DELETE FROM support_resources")
    suspend fun clearAll()
}