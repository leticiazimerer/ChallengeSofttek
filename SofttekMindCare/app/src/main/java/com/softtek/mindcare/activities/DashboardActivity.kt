package com.softtek.mindcare.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.softtek.mindcare.databinding.ActivityDashboardBinding
import com.softtek.mindcare.viewmodels.DashboardViewModel

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var viewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.todayButton.setOnClickListener { viewModel.setTimeRange(TimeRange.TODAY) }
        binding.weeklyButton.setOnClickListener { viewModel.setTimeRange(TimeRange.WEEKLY) }
        binding.monthlyButton.setOnClickListener { viewModel.setTimeRange(TimeRange.MONTHLY) }

        binding.moodTrackerCard.setOnClickListener {
            startActivity(MoodTrackerActivity.newIntent(this))
        }

        binding.questionnaireCard.setOnClickListener {
            startActivity(QuestionnaireActivity.newIntent(this))
        }

        binding.supportCard.setOnClickListener {
            startActivity(SupportActivity.newIntent(this))
        }

        binding.statisticsCard.setOnClickListener {
            startActivity(StatisticsActivity.newIntent(this))
        }
    }

    private fun observeViewModel() {
        viewModel.currentTimeRange.observe(this) { range ->
            updateTimeRangeUI(range)
            viewModel.loadData(range)
        }

        viewModel.averageMood.observe(this) { avg ->
            binding.avgMoodValue.text = String.format("%.1f", avg ?: 0f)
        }

        viewModel.latestMood.observe(this) { mood ->
            binding.currentMoodValue.text = mood?.moodType ?: "N/A"
        }
    }

    private fun updateTimeRangeUI(range: TimeRange) {
        binding.todayButton.isSelected = range == TimeRange.TODAY
        binding.weeklyButton.isSelected = range == TimeRange.WEEKLY
        binding.monthlyButton.isSelected = range == TimeRange.MONTHLY
    }
}

enum class TimeRange {
    TODAY, WEEKLY, MONTHLY
}