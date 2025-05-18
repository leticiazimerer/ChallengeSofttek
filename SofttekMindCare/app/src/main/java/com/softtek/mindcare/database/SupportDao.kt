package com.softtek.mindcare.database

import androidx.room.*
import com.softtek.mindcare.models.SupportResource
import kotlinx.coroutines.flow.Flow

@Dao
interface SupportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resource: SupportResource)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(resources: List<SupportResource>)

    @Query("SELECT * FROM support_resources")
    fun getAll(): Flow<List<SupportResource>>

    @Query("SELECT * FROM support_resources WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<SupportResource>>

    @Query("UPDATE support_resources SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

    @Query("DELETE FROM support_resources WHERE id = :id")
    suspend fun deleteById(id: Int)
}