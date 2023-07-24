package com.hwaryun.common.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertUnixToDate(time: Long, pattern: String): String {
    val unixTime = if (time == 0L) {
        System.currentTimeMillis()
    } else {
        time
    }
    val date = Date(unixTime)
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(date)
}