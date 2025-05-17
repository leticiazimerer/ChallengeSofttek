package com.softtek.mindcare.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.softtek.mindcare.databinding.ActivityStatisticsBinding
import com.softtek.mindcare.viewmodels.StatisticsViewModel
import java.util.Calendar

class StatisticsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var viewModel: StatisticsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(StatisticsViewModel::class.java)

        setupCharts()
        setupTimeRangeButtons()
        observeViewModel()
        viewModel.loadData(TimeRange.WEEKLY)
    }

    private fun setupCharts() {
        binding.moodChart.apply {
            description.isEnabled = false
            setDrawGridBackground(false)
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
            }

            axisLeft.apply {
                axisMinimum = 0f
                axisMaximum = 5f
                granularity = 1f
            }

            axisRight.isEnabled = false
            legend.isEnabled = false
        }

        binding.stressChart.apply {
            description.isEnabled = false
            setDrawGridBackground(false)
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
            }

            axisLeft.apply {
                axisMinimum = 0f
                axisMaximum = 10f
                granularity = 1f
            }

            axisRight.isEnabled = false
            legend.isEnabled = false
        }
    }

    private fun setupTimeRangeButtons() {
        binding.weekButton.setOnClickListener { viewModel.loadData(TimeRange.WEEKLY) }
        binding.monthButton.setOnClickListener { viewModel.loadData(TimeRange.MONTHLY) }
        binding.yearButton.setOnClickListener { viewModel.loadData(TimeRange.YEARLY) }
    }

    private fun observeViewModel() {
        viewModel.moodData.observe(this) { moodEntries ->
            val entries = moodEntries.mapIndexed { index, entry ->
                BarEntry(index.toFloat(), entry.intensity.toFloat())
            }

            val dataSet = BarDataSet(entries, "Mood").apply {
                color = resources.getColor(R.color.softtek_blue)
                valueTextColor = resources.getColor(R.color.softtek_dark)
            }

            binding.moodChart.data = BarData(dataSet)
            binding.moodChart.invalidate()

            val days = moodEntries.map {
                val calendar = Calendar.getInstance().apply { timeInMillis = it.timestamp }
                "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}"
            }

            binding.moodChart.xAxis.valueFormatter = IndexAxisValueFormatter(days)
        }

        viewModel.stressData.observe(this) { stressEntries ->
            val entries = stressEntries.mapIndexed { index, entry ->
                BarEntry(index.toFloat(), entry.level.toFloat())
            }

            val dataSet = BarDataSet(entries, "Stress").apply {
                color = resources.getColor(R.color.softtek_green)
                valueTextColor = resources.getColor(R.color.softtek_dark)
            }

            binding.stressChart.data = BarData(dataSet)
            binding.stressChart.invalidate()
        }

        viewModel.summaryData.observe(this) { summary ->
            binding.avgMoodValue.text = String.format("%.1f", summary.avgMood)
            binding.avgStressValue.text = String.format("%.1f", summary.avgStress)
            binding.highStressDaysValue.text = summary.highStressDays.toString()
            binding.lowMoodDaysValue.text = summary.lowMoodDays.toString()
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, StatisticsActivity::class.java)
    }
}