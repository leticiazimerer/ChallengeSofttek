package com.softtek.mindcare.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softtek.mindcare.database.MoodDao
import com.softtek.mindcare.models.MoodEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MoodTrackerViewModel @Inject constructor(
    private val moodDao: MoodDao
) : ViewModel() {
    private val _selectedMood = MutableLiveData<String?>()
    val selectedMood: LiveData<String?> = _selectedMood

    private val _saveSuccess = MutableLiveData<Boolean>()
    val saveSuccess: LiveData<Boolean> = _saveSuccess

    fun selectMood(mood: String) {
        _selectedMood.value = mood
    }

    fun saveMoodEntry(moodType: String, intensity: Int, note: String?) {
        viewModelScope.launch {
            val entry = MoodEntry(
                moodType = moodType,
                intensity = intensity,
                note = note
            )
            moodDao.insertMoodEntry(entry)
            _saveSuccess.postValue(true)
        }
    }
}