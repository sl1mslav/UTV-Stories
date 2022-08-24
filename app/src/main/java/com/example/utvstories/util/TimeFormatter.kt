package com.example.utvstories.util

import java.text.SimpleDateFormat
import java.util.*

object TimeFormatter {
    fun convertToDate(time: Long?): String {
        time ?: return ""
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return dateFormat.format(Date(time * 1000))
    }
}