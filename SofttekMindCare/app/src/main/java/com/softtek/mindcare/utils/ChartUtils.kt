package com.softtek.mindcare.utils

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.softtek.mindcare.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object ChartUtils {

    fun setupBarChart(chart: BarChart) {
        with(chart) {
            description.isEnabled = false
            setDrawGridBackground(false)
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            legend.isEnabled = false

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
                labelRotationAngle = -45f
            }

            axisLeft.apply {
                axisMinimum = 0f
                granularity = 1f
                setDrawGridLines(true)
            }

            axisRight.isEnabled = false

            legend.isEnabled = false
        }
    }

    fun setupLineChart(chart: LineChart) {
        with(chart) {
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true)
            setScaleEnabled(true)
            setPinchZoom(true)
            legend.isEnabled = false

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
            }

            axisLeft.apply {
                axisMinimum = 0f
                granularity = 1f
            }

            axisRight.isEnabled = false
        }
    }

    fun createMoodBarData(entries: List<BarEntry>, color: Int): BarData {
        val dataSet = BarDataSet(entries, "Mood").apply {
            this.color = color
            valueTextColor = color
            valueFormatter = MoodValueFormatter()
        }

        return BarData(dataSet).apply {
            barWidth = 0.5f
            setValueTextSize(10f)
        }
    }

    fun createStressLineData(entries: List<Entry>, color: Int): LineData {
        val dataSet = LineDataSet(entries, "Stress").apply {
            this.color = color
            valueTextColor = color
            lineWidth = 2.5f
            circleRadius = 4f
            setCircleColor(color)
            valueFormatter = StressValueFormatter()
        }

        return LineData(dataSet)
    }

    fun setupDateLabels(chart: BarChart, timestamps: List<Long>) {
        val dates = timestamps.map { timestamp ->
            SimpleDateFormat("dd/MM", Locale.getDefault()).format(Date(timestamp))
        }

        chart.xAxis.valueFormatter = IndexAxisValueFormatter(dates)
    }

    private class MoodValueFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return value.roundToInt().toString()
        }
    }

    private class StressValueFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return value.roundToInt().toString()
        }
    }

    fun getChartColor(context: Context, type: String): Int {
        return when (type) {
            "mood" -> context.getColor(R.color.softtek_blue)
            "stress" -> context.getColor(R.color.softtek_green)
            else -> context.getColor(R.color.softtek_dark)
        }
    }
}