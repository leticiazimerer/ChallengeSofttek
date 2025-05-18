package com.softtek.mindcare.repositories

import com.softtek.mindcare.database.MoodDao
import com.softtek.mindcare.models.MoodEntry
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class MoodRepository @Inject constructor(private val moodDao: MoodDao) {
    suspend fun insertMoodEntry(entry: MoodEntry) = moodDao.insert(entry)
    fun getAllMoodEntries(): Flow<List<MoodEntry>> = moodDao.getAllEntries()
    suspend fun getMoodEntriesBetween(start: Date, end: Date): List<MoodEntry> =
        moodDao.getEntriesBetweenDates(start.time, end.time)
    suspend fun getAverageMoodBetween(start: Date, end: Date): Float? =
        moodDao.getAverageMoodBetweenDates(start.time, end.time)
}