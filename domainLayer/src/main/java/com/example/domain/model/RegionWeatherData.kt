package com.example.domain.model

import androidx.annotation.StringRes
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
    val weather: String="", // 0:맑음, 1:비, 2:눈, 3:구름많음, 4:흐림
    val date: String="",
    val time:String=""
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

    @StringRes val weatherMark : Int = with(weather) {
        when(this) {
            "0" -> R.string.sunny
            "1" -> R.string.rain
            "2" -> R.string.snow
            "3" -> R.string.cloudy
            else -> R.string.bad
        }
    }
}