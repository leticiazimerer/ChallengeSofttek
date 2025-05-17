package com.softtek.mindcare.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softtek.mindcare.database.MoodDao
import com.softtek.mindcare.models.MoodEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val moodDao: MoodDao
) : ViewModel() {
    private val _currentTimeRange = MutableLiveData(TimeRange.TODAY)
    val currentTimeRange: LiveData<TimeRange> = _currentTimeRange

    private val _averageMood = MutableLiveData<Float?>()
    val averageMood: LiveData<Float?> = _averageMood

    private val _latestMood = MutableLiveData<MoodEntry?>()
    val latestMood: LiveData<MoodEntry?> = _latestMood

    private val _reminderMessage = MutableLiveData<String?>()
    val reminderMessage: LiveData<String?> = _reminderMessage

    fun setTimeRange(range: TimeRange) {
        _currentTimeRange.value = range
    }

    fun loadData(range: TimeRange) {
        viewModelScope.launch {
            val (start, end) = when (range) {
                TimeRange.TODAY -> getTodayRange()
                TimeRange.WEEKLY -> getWeekRange()
                TimeRange.MONTHLY -> getMonthRange()
            }

            _averageMood.value = moodDao.getAverageMoodBetweenDates(start, end)
            _latestMood.value = moodDao.getLatestMoodEntry()

            checkForReminders()
        }
    }

    private suspend fun checkForReminders() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val lastWeek = calendar.timeInMillis

        val avgMood = moodDao.getAverageMoodBetweenDates(lastWeek, Date().time)
        if (avgMood != null && avgMood < 2.5f) {
            _reminderMessage.postValue("Seu humor está abaixo da média esta semana. Que tal tentar alguns de nossos recursos de apoio?")
        }
    }

    private fun getTodayRange(): Pair<Long, Long> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val start = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val end = calendar.timeInMillis

        return start to end
    }

    private fun getWeekRange(): Pair<Long, Long> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val start = calendar.timeInMillis

        calendar.add(Calendar.DAY_OF_YEAR, 6)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val end = calendar.timeInMillis

        return start to end
    }

    private fun getMonthRange(): Pair<Long, Long> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val start = calendar.timeInMillis

        calendar.set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val end = calendar.timeInMillis

        return start to end
    }
}