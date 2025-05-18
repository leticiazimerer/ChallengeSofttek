package com.softtek.mindcare.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toast(@StringRes messageRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, messageRes, duration).show()
}

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    requireContext().toast(message, duration)
}

fun Fragment.toast(@StringRes messageRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    requireContext().toast(messageRes, duration)
}

fun View.showSnackbar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    actionText: String? = null,
    action: (() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, message, duration)
    actionText?.let {
        snackbar.setAction(it) { action?.invoke() }
    }
    snackbar.show()
}

fun Date.toFormattedString(format: String = "dd/MM/yyyy HH:mm"): String {
    val sdf = java.text.SimpleDateFormat(format, Locale.getDefault())
    return sdf.format(this)
}