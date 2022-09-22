package com.example.domain.model

import com.example.domain.R

data class Regions(
    val city:String = "",
    val gu:String = "",
    val dong:String = "",
    val nx:String = "",
    val ny:String = "",
    val longtitude:String = "0.0",
    val latitude:String = "0.0"
)

data class WeatherForecast(
    val nx: String="",
    val ny: String="",
    // 0:비/눈 없음, 1:비, 2:비/눈, 3:눈, 5:빗방울, 6:빗방울&눈날림, 7:눈날림,
    // 8:맑음, 9:구르많음, 10:흐림
    val weather: String="",
    val longtitude: String = "0.0",
    val latitude: String = "0.0"
) {
    val icon = weather.map {
        when(it) {
//            "0" -> R.drawable.ic
        }
    }
}