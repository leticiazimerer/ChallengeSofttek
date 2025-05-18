package com.softtek.mindcare.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softtek.mindcare.models.MoodEntry
import com.softtek.mindcare.repositories.MoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MoodTrackerViewModel @Inject constructor(
    private val moodRepository: MoodRepository
) : ViewModel() {

    private val _moodEntries = MutableLiveData<List<MoodEntry>>()
    val moodEntries: LiveData<List<MoodEntry>> = _moodEntries

    private val _saveSuccess = MutableLiveData<Boolean>()
    val saveSuccess: LiveData<Boolean> = _saveSuccess

    init {
        loadMoodHistory()
    }

    fun loadMoodHistory() {
        viewModelScope.launch {
            moodRepository.getAllMoodEntries().collect { entries ->
                _moodEntries.postValue(entries)
            }
        }
    }

    fun saveMoodEntry(moodType: String, intensity: Int, note: String?) {
        viewModelScope.launch {
            val entry = MoodEntry(
                moodType = moodType,
                intensity = intensity,
                note = note,
                timestamp = Date().time
            )
            moodRepository.insertMoodEntry(entry)
            _saveSuccess.postValue(true)
        }
    }
}