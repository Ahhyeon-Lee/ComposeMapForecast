package com.example.domain.util

import java.text.SimpleDateFormat
import java.util.*

object DateTimeHelper {
    fun getDate(format:String) : String {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    fun getTimeOneHourBefore(format:String) : String {
        val timeFormat = SimpleDateFormat(format, Locale.getDefault())
        val timeOneHourBefore = Date(System.currentTimeMillis() - 3600*1000)
        return timeFormat.format(timeOneHourBefore)
    }

    fun parseStringToDateFormat(dateString:String, originFormat:String, changeFormat:String) : String {
        val stringFormat = SimpleDateFormat(originFormat)
        val date = stringFormat.parse(dateString)
        val dateFormat = SimpleDateFormat(changeFormat)
        return dateFormat.format(date)
    }
}