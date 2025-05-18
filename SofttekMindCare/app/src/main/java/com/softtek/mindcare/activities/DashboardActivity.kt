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
        viewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
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
        viewModel.userName.observe(this) { name ->
            binding.welcomeText.text = "Olá, $name"
        }
        viewModel.lastMood.observe(this) { mood ->
            binding.lastMoodText.text = mood?.moodType ?: "Não registrado"
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, DashboardActivity::class.java)
    }
}