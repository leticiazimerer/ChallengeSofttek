package com.softtek.mindcare.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softtek.mindcare.repositories.MoodRepository
import com.softtek.mindcare.repositories.StressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val moodRepository: MoodRepository,
    private val stressRepository: StressRepository
) : ViewModel() {

    private val _moodData = MutableLiveData<List<Pair<String, Float>>>()
    val moodData: LiveData<List<Pair<String, Float>>> = _moodData

    private val _stressData = MutableLiveData<List<Pair<String, Float>>>()
    val stressData: LiveData<List<Pair<String, Float>>> = _stressData

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun loadWeeklyData() {
        loadData(7)
    }

    fun loadMonthlyData() {
        loadData(30)
    }

    private fun loadData(days: Int) {
        _loading.postValue(true)

        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val endDate = calendar.time

            calendar.add(Calendar.DAY_OF_YEAR, -days)
            val startDate = calendar.time

            val moodEntries = moodRepository.getMoodEntriesBetween(startDate, endDate)
                .groupBy {
                    SimpleDateFormat("EEE", Locale.getDefault()).format(Date(it.timestamp))
                }
                .mapValues { (_, entries) -> entries.map { it.intensity }.average().toFloat() }
                .toList()
                .sortedBy { it.first }

            _moodData.postValue(moodEntries)

            val stressEntries = stressRepository.getStressEntriesBetween(startDate, endDate)
                .groupBy {
                    SimpleDateFormat("EEE", Locale.getDefault()).format(Date(it.timestamp))
                }
                .mapValues { (_, entries) -> entries.map { it.level }.average().toFloat() }
                .toList()
                .sortedBy { it.first }

            _stressData.postValue(stressEntries)

            _loading.postValue(false)
        }
    }
}