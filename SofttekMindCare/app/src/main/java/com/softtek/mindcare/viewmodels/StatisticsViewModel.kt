package com.softtek.mindcare.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softtek.mindcare.database.MoodDao
import com.softtek.mindcare.database.StressDao
import com.softtek.mindcare.models.MoodEntry
import com.softtek.mindcare.models.StressEntry
import com.softtek.mindcare.models.SummaryData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val moodDao: MoodDao,
    private val stressDao: StressDao
) : ViewModel() {
    private val _moodData = MutableLiveData<List<MoodEntry>>()
    val moodData: LiveData<List<MoodEntry>> = _moodData

    private val _stressData = MutableLiveData<List<StressEntry>>()
    val stressData: LiveData<List<StressEntry>> = _stressData

    private val _summaryData = MutableLiveData<SummaryData>()
    val summaryData: LiveData<SummaryData> = _summaryData

    fun loadData(range: TimeRange) {
        viewModelScope.launch {
            val (start, end) = when (range) {
                TimeRange.WEEKLY -> getWeekRange()
                TimeRange.MONTHLY -> getMonthRange()
                TimeRange.YEARLY -> getYearRange()
            }

            val moods = moodDao.getMoodEntriesBetweenDates(start, end)
            _moodData.postValue(moods)

            val stress = stressDao.getStressEntriesBetweenDates(start, end)
            _stressData.postValue(stress)

            val avgMood = moodDao.getAverageMoodBetweenDates(start, end) ?: 0f
            val avgStress = stressDao.getAverageStressBetweenDates(start, end) ?: 0f
            val highStressDays = stressDao.getHighStressDaysBetweenDates(start, end, 7f)
            val lowMoodDays = moodDao.getLowMoodDaysBetweenDates(start, end, 2.5f)

            _summaryData.postValue(
                SummaryData(
                    avgMood = avgMood,
                    avgStress = avgStress,
                    highStressDays = highStressDays,
                    lowMoodDays = lowMoodDays
                )
            )
        }
    }

    private fun getWeekRange(): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val start = calendar.timeInMillis

        calendar.add(Calendar.DAY_OF_YEAR, 6)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val end = calendar.timeInMillis

        return start to end
    }

    private fun getMonthRange(): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val start = calendar.timeInMillis

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val end = calendar.timeInMillis

        return start to end
    }

    private fun getYearRange(): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_YEAR, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val start = calendar.timeInMillis

        calendar.set(Calendar.MONTH, 11)
        calendar.set(Calendar.DAY_OF_MONTH, 31)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val end = calendar.timeInMillis

        return start to end
    }
}

enum class TimeRange {
    WEEKLY, MONTHLY, YEARLY
}