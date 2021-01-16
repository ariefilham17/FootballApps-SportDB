package com.example.footballapps.helpers.utils

import java.text.SimpleDateFormat
import java.util.*

private var tz: TimeZone = TimeZone.getTimeZone("WIB")

fun String.formatTimeToMatch(
    inputFormat: String = "HH:mm:ss",
    outputFormat: String? = "HH:mm"): String {

    val timeFormat = SimpleDateFormat(inputFormat, Locale.ENGLISH)
    timeFormat.timeZone = tz
    val time = timeFormat.parse(this)

    val returnFormat = SimpleDateFormat(outputFormat, Locale.ENGLISH)
    return returnFormat.format(time)
}

fun Date.formatDateToMatch(): String? {
    return SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(this)
}