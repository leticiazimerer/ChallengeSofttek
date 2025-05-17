package com.softtek.mindcare.repositories

import com.softtek.mindcare.database.MoodDao
import com.softtek.mindcare.models.MoodEntry
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class MoodRepository @Inject constructor(
    private val moodDao: MoodDao
) {
    suspend fun insertMoodEntry(moodEntry: MoodEntry) {
        moodDao.insertMoodEntry(moodEntry)
    }

    fun getMoodEntriesBetweenDates(start: Long, end: Long): Flow<List<MoodEntry>> {
        return moodDao.getMoodEntriesBetweenDates(start, end)
    }

    suspend fun getAverageMoodBetweenDates(start: Long, end: Long): Float? {
        return moodDao.getAverageMoodBetweenDates(start, end)
    }

    suspend fun getLatestMoodEntry(): MoodEntry? {
        return moodDao.getLatestMoodEntry()
    }
}