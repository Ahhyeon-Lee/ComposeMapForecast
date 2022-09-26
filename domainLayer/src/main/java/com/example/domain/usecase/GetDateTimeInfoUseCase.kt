package com.example.domain.usecase

import java.text.SimpleDateFormat
import java.util.*

class GetDateTimeInfoUseCase {
    fun getDate(format:String) : String {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    fun getTime(format:String) : String {
        val timeFormat = SimpleDateFormat(format, Locale.getDefault())
        val date = Date()
//        return timeFormat.format(date)
        return "1000"
    }
}