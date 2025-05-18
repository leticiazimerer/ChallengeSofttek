package com.softtek.mindcare.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softtek.mindcare.repositories.SettingsRepository
import com.softtek.mindcare.utils.WorkManagerUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository,
    private val workManagerUtils: WorkManagerUtils
) : ViewModel() {

    private val _settings = MutableLiveData<UserSettings>()
    val settings: LiveData<UserSettings> = _settings

    private val _saveSuccess = MutableLiveData<Boolean>()
    val saveSuccess: LiveData<Boolean> = _saveSuccess

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            repository.getSettings()?.let { settings ->
                _settings.postValue(settings)
                updateReminderSchedule(settings)
            }
        }
    }

    fun updateNotificationSetting(enabled: Boolean) {
        viewModelScope.launch {
            _settings.value?.let { currentSettings ->
                val updatedSettings = currentSettings.copy(notificationsEnabled = enabled)
                repository.saveSettings(updatedSettings)
                _settings.postValue(updatedSettings)
            }
        }
    }

    fun updateReminderSetting(enabled: Boolean) {
        viewModelScope.launch {
            _settings.value?.let { currentSettings ->
                val updatedSettings = currentSettings.copy(remindersEnabled = enabled)
                repository.saveSettings(updatedSettings)
                _settings.postValue(updatedSettings)
                updateReminderSchedule(updatedSettings)
            }
        }
    }

    fun updateReminderTime(hour: Int, minute: Int) {
        viewModelScope.launch {
            _settings.value?.let { currentSettings ->
                val updatedSettings = currentSettings.copy(
                    reminderHour = hour,
                    reminderMinute = minute
                )
                repository.saveSettings(updatedSettings)
                _settings.postValue(updatedSettings)
                updateReminderSchedule(updatedSettings)
            }
        }
    }

    private fun updateReminderSchedule(settings: UserSettings) {
        if (settings.remindersEnabled) {
            workManagerUtils.scheduleDailyReminder(
                context = getApplication(),
                hour = settings.reminderHour,
                minute = settings.reminderMinute
            )
        } else {
            workManagerUtils.cancelReminders(getApplication())
        }
    }
}