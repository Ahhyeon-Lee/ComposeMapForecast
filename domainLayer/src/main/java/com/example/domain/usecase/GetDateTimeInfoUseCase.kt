package com.example.domain.usecase

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetDateTimeInfoUseCase @Inject constructor() {
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
}