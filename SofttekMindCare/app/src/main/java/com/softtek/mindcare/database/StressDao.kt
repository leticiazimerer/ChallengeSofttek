package com.softtek.mindcare.database

import androidx.room.*
import com.softtek.mindcare.models.StressEntry
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface StressDao {
    @Insert
    suspend fun insertStressEntry(entry: StressEntry)

    @Query("SELECT * FROM stress_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<StressEntry>>

    @Query("SELECT * FROM stress_entries WHERE timestamp BETWEEN :start AND :end ORDER BY timestamp DESC")
    suspend fun getEntriesBetweenDates(start: Long, end: Long): List<StressEntry>

    @Query("SELECT AVG(level) FROM stress_entries WHERE timestamp BETWEEN :start AND :end")
    suspend fun getAverageStressBetweenDates(start: Long, end: Long): Float?

    @Query("SELECT COUNT(*) FROM stress_entries WHERE level > :threshold AND timestamp BETWEEN :start AND :end")
    suspend fun getHighStressDaysBetweenDates(start: Long, end: Long, threshold: Float): Int
}