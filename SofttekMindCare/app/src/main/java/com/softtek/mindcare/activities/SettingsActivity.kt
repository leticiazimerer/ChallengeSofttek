package com.softtek.mindcare.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.softtek.mindcare.databinding.ActivitySettingsBinding
import com.softtek.mindcare.viewmodels.SettingsViewModel

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        setupSwitches()
        setupTimePicker()
    }

    private fun setupSwitches() {
        binding.notificationsSwitch.isChecked = viewModel.notificationsEnabled
        binding.remindersSwitch.isChecked = viewModel.remindersEnabled

        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setNotificationsEnabled(isChecked)
        }

        binding.remindersSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setRemindersEnabled(isChecked)
            binding.reminderTimePicker.visibility = if (isChecked) VISIBLE else GONE
        }
    }

    private fun setupTimePicker() {
        binding.reminderTimePicker.hour = viewModel.reminderHour
        binding.reminderTimePicker.minute = viewModel.reminderMinute
        binding.reminderTimePicker.setOnTimeChangedListener { _, hour, minute ->
            viewModel.setReminderTime(hour, minute)
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, SettingsActivity::class.java)
    }
}