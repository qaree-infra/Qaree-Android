package com.muhmmad.qaree.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    fun getBookYear(timeStamp: String): String {
        return try {
            val date = Date(timeStamp.toLong())
            SimpleDateFormat("yyyy", Locale.getDefault()).format(date)
        } catch (ex: Exception) {
            ""
        }
    }
}