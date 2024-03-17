package com.muhmmad.qaree.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    fun getBookYear(timeStamp: String): String {
        val date = Date(timeStamp.toLong())
        return SimpleDateFormat("yyyy", Locale.getDefault()).format(date)
    }
}