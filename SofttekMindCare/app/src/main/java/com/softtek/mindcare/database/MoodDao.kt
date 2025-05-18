package com.softtek.mindcare.database

import androidx.room.*
import com.softtek.mindcare.models.MoodEntry
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface MoodDao {
    @Insert
    suspend fun insert(entry: MoodEntry)

    @Query("SELECT * FROM mood_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<MoodEntry>>

    @Query("SELECT * FROM mood_entries WHERE timestamp BETWEEN :start AND :end ORDER BY timestamp DESC")
    suspend fun getEntriesBetweenDates(start: Long, end: Long): List<MoodEntry>

    @Query("SELECT AVG(intensity) FROM mood_entries WHERE timestamp BETWEEN :start AND :end")
    suspend fun getAverageMoodBetweenDates(start: Long, end: Long): Float?

    @Query("SELECT * FROM mood_entries WHERE id = :id")
    suspend fun getEntryById(id: Int): MoodEntry?

    @Query("DELETE FROM mood_entries WHERE id = :id")
    suspend fun deleteById(id: Int)
}