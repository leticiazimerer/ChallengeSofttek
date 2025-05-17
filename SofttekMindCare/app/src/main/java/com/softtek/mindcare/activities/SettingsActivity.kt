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

        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        setupUI()
        observeViewModel()
        viewModel.loadSettings()
    }

    private fun setupUI() {
        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setNotificationsEnabled(isChecked)
        }

        binding.remindersSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setRemindersEnabled(isChecked)
        }

        binding.reminderTimePicker.setIs24HourView(true)
        binding.reminderTimePicker.setOnTimeChangedListener { _, hour, minute ->
            viewModel.setReminderTime(hour, minute)
        }

        binding.privacyPolicyButton.setOnClickListener {
            viewModel.openPrivacyPolicy(this)
        }

        binding.aboutButton.setOnClickListener {
            viewModel.showAboutDialog(this)
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.notificationsEnabled.observe(this) { enabled ->
            binding.notificationsSwitch.isChecked = enabled
        }

        viewModel.remindersEnabled.observe(this) { enabled ->
            binding.remindersSwitch.isChecked = enabled
            binding.reminderTimePicker.visibility = if (enabled) View.VISIBLE else View.GONE
        }

        viewModel.reminderTime.observe(this) { time ->
            binding.reminderTimePicker.hour = time.first
            binding.reminderTimePicker.minute = time.second
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, SettingsActivity::class.java)
    }
}