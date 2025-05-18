package com.softtek.mindcare.utils

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.softtek.mindcare.R
import java.text.SimpleDateFormat
import java.util.*

object ChartUtils {

    fun setupMoodChart(chart: BarChart, entries: List<Pair<String, Float>>) {
        val barEntries = entries.mapIndexed { index, pair ->
            BarEntry(index.toFloat(), pair.second)
        }

        val dataSet = BarDataSet(barEntries, "Mood").apply {
            color = R.color.primary
            valueTextColor = R.color.text_primary
            valueTextSize = 12f
        }

        val xAxisLabels = entries.map { it.first }

        chart.apply {
            data = BarData(dataSet)
            xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(xAxisLabels)
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                setDrawGridLines(false)
            }
            axisLeft.setDrawGridLines(false)
            axisRight.isEnabled = false
            description.isEnabled = false
            legend.isEnabled = false
            setFitBars(true)
            animateY(1000)
            invalidate()
        }
    }

    fun formatDateRange(start: Date, end: Date): String {
        val sdf = SimpleDateFormat("MMM dd", Locale.getDefault())
        return "${sdf.format(start)} - ${sdf.format(end)}"
    }
}