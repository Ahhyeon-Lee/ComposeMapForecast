package com.example.domain.model

import com.example.domain.R

data class Regions(
    val city:String = "",
    val gu:String = "",
    val dong:String = "",
    val address:String = "",
    val nx:String = "",
    val ny:String = "",
    val longtitude:Double = 0.0,
    val latitude:Double = 0.0
)

data class WeatherForecast(
    val city:String = "",
    val gu:String = "",
    val dong:String = "",
    val nx: String="",
    val ny: String="",
    val longtitude: Double = 0.0,
    val latitude: Double = 0.0,
    val weather: String="" // 0:맑음, 1:비, 2:눈, 3:구름많음, 4:흐림
) {
    val icon = with(weather) {
        when(this) {
            "0" -> "☀"
            "1" -> "💧"
            "2" -> "❄"
            "3" -> "☁"
            "4" -> "⛅"
            else -> ""
        }
    }

    val weatherMark = with(weather) {
        when(this) {
            "0" -> "맑음"
            "1" -> "비"
            "2" -> "눈"
            "3" -> "구름 많음"
            else -> "흐림"
        }
    }

    fun getMarkTitle() : String {
        val location = dong.takeIf { it.isNotEmpty() } ?: gu.takeIf { it.isNotEmpty() } ?: city
        return "$location : $weatherMark"
    }
}