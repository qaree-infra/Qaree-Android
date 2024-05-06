package com.muhmmad.qaree.utils

import android.content.Context
import android.util.Log
import com.muhmmad.qaree.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

object DateUtils {
    fun getBookYear(timeStamp: String): String {
        return try {
            val date = Date(timeStamp.toLong())
            SimpleDateFormat("yyyy", Locale.getDefault()).format(date)
        } catch (ex: Exception) {
            ""
        }
    }

    fun getMessageDate(context: Context, createdAt: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        format.timeZone = TimeZone.getDefault()

        return format.parse(createdAt)?.let {
            timeAgo(context, it)
        } ?: ""
    }

    private fun timeAgo(context: Context, date: Date): String {
        val diff = Date().time - date.time
        val r = context.resources
        val prefix = r.getString(R.string.time_ago_prefix)
        val suffix = r.getString(R.string.time_ago_suffix)
        val seconds = (abs(diff.toDouble()) / 1000).toDouble()
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val years = days / 365

        val words: String = when {
            seconds < 45 -> {
                r.getString(R.string.time_ago_seconds)
            }

            seconds < 90 -> {
                r.getString(R.string.time_ago_minute)
            }

            minutes < 45 -> {
                r.getString(R.string.time_ago_minutes, Math.round(minutes))
            }

            minutes < 90 -> {
                r.getString(R.string.time_ago_hour)
            }

            hours < 24 -> {
                r.getString(R.string.time_ago_hours, Math.round(hours))
            }

            hours < 42 -> {
                r.getString(R.string.time_ago_day)
            }

            days < 30 -> {
                r.getString(R.string.time_ago_days, Math.round(days))
            }

            days < 45 -> {
                r.getString(R.string.time_ago_month)
            }

            days < 365 -> {
                r.getString(R.string.time_ago_months, Math.round(days / 30))
            }

            years < 1.5 -> {
                r.getString(R.string.time_ago_year)
            }

            else -> {
                r.getString(R.string.time_ago_years, Math.round(years))
            }
        }
        val sb = StringBuilder()
        if (prefix.isNotEmpty()) {
            sb.append(prefix).append(" ")
        }
        sb.append(words)
        if (suffix.isNotEmpty()) {
            sb.append(" ").append(suffix)
        }
        return sb.toString().trim { it <= ' ' }
    }
}