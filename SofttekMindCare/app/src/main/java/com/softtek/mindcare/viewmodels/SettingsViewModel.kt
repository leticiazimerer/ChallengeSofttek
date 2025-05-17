package com.softtek.mindcare.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softtek.mindcare.R
import com.softtek.mindcare.utils.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    private val _notificationsEnabled = MutableLiveData<Boolean>()
    val notificationsEnabled: LiveData<Boolean> = _notificationsEnabled

    private val _remindersEnabled = MutableLiveData<Boolean>()
    val remindersEnabled: LiveData<Boolean> = _remindersEnabled

    private val _reminderTime = MutableLiveData<Pair<Int, Int>>()
    val reminderTime: LiveData<Pair<Int, Int>> = _reminderTime

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            _notificationsEnabled.postValue(preferencesManager.getNotificationsEnabled())
            _remindersEnabled.postValue(preferencesManager.getRemindersEnabled())
            _reminderTime.postValue(preferencesManager.getReminderTime())
        }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setNotificationsEnabled(enabled)
            _notificationsEnabled.postValue(enabled)
        }
    }

    fun setRemindersEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setRemindersEnabled(enabled)
            _remindersEnabled.postValue(enabled)
        }
    }

    fun setReminderTime(hour: Int, minute: Int) {
        viewModelScope.launch {
            preferencesManager.setReminderTime(hour, minute)
            _reminderTime.postValue(hour to minute)
        }
    }

    fun openPrivacyPolicy(context: Context) {
        Toast.makeText(context, "Opening Privacy Policy", Toast.LENGTH_SHORT).show()
    }

    fun showAboutDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("About Softtek MindCare")
            .setMessage("Version 1.0\n\nDeveloped for Softtek employees' mental health support")
            .setPositiveButton("OK", null)
            .show()
    }
}