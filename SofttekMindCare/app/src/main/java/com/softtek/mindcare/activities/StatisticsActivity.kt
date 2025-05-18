package com.softtek.mindcare.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.softtek.mindcare.databinding.ActivityStatisticsBinding
import com.softtek.mindcare.viewmodels.StatisticsViewModel

class StatisticsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var viewModel: StatisticsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[StatisticsViewModel::class.java]
        setupCharts()
        observeViewModel()
    }

    private fun setupCharts() {
        binding.moodChart.setupDefaultConfig()
        binding.stressChart.setupDefaultConfig()
    }

    private fun observeViewModel() {
        viewModel.moodData.observe(this) { entries ->
            binding.moodChart.data = entries.toBarDataSet("Humor", getColor(R.color.softtek_blue))
            binding.moodChart.invalidate()
        }
        viewModel.stressData.observe(this) { entries ->
            binding.stressChart.data = entries.toLineDataSet("Estresse", getColor(R.color.softtek_green))
            binding.stressChart.invalidate()
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, StatisticsActivity::class.java)
    }
}