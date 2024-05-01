package com.example.applydigitalchallenge.util

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.text.SimpleDateFormat
import java.util.*

/**
 * Converts a value from density-independent pixels (dp) to pixels (px) based on the device's screen density.
 *
 * @param dp The value in density-independent pixels (dp) to be converted.
 * @return The equivalent value in pixels (px).
 */
fun Float.dp(): Float = this * density + 0.5f

/**
 * Retrieves the device's screen density.
 *
 * @return The screen density of the device.
 */
val density: Float
    get() = Resources.getSystem().displayMetrics.density

/**
 * Checks whether network connectivity is available.
 *
 * @param context The context reference to access system services.
 * @return True if network connectivity is available, otherwise false.
 */
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager

    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}


/**
 * Calculates the time difference between the current time and a standard time string.
 *
 * @param standardTime The standard time string in "yyyy-MM-dd'T'HH:mm:ss'Z'" format.
 * @return A string representing the time difference, formatted as "Yesterday", "<hours>h", or "<minutes>m".
 */
fun diffInTimeFromNowToStandardTime(standardTime: String): String {
    val currentTime = Date()
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")

    try {
        val standardDate = sdf.parse(standardTime)
        if (standardDate != null) {
            val diffInMillis = currentTime.time - standardDate.time
            val diffInHours = (diffInMillis / (1000 * 60 * 60)).toDouble()
            val diffInMinutes = (diffInMillis / (1000 * 60)).toInt()

            return when {
                diffInHours > 24 -> "Yesterday"
                diffInMinutes > 60 && (diffInMinutes/60) > 29 ->
                    "" + diffInHours + (diffInMinutes/60) + "h"
                diffInMinutes > 60 && (diffInMinutes/60) <= 29 -> "" + diffInHours.toInt() + "h"
                else -> "" + diffInMinutes + "m"
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return "Invalid standard time format"
}