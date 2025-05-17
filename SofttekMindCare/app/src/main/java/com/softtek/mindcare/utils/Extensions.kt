package com.softtek.mindcare.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.softtek.mindcare.R
import java.text.SimpleDateFormat
import java.util.*

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resId, duration).show()
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Long.toFormattedDate(): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}

fun BarChart.setupDefaultConfig() {
    description.isEnabled = false
    setDrawGridBackground(false)
    setTouchEnabled(true)
    isDragEnabled = true
    setScaleEnabled(true)
    setPinchZoom(true)

    xAxis.position = XAxis.XAxisPosition.BOTTOM
    xAxis.setDrawGridLines(false)
    xAxis.granularity = 1f

    axisLeft.apply {
        axisMinimum = 0f
        granularity = 1f
    }

    axisRight.isEnabled = false
    legend.isEnabled = false
}

fun List<BarEntry>.toBarDataSet(label: String, color: Int): BarDataSet {
    return BarDataSet(this, label).apply {
        this.color = color
        valueTextColor = color
    }
}