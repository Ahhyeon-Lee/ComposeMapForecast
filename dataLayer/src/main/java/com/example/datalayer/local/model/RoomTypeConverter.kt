package com.example.datalayer.local.model

import androidx.room.TypeConverter
import com.example.domain.model.WeatherForecast
import com.google.gson.Gson

class RoomTypeConverter {

    @TypeConverter
    fun weatherForecastToJson(value : WeatherForecast) : String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToWeatherForecast(value : String) : WeatherForecast {
        return Gson().fromJson(value, WeatherForecast::class.java)
    }
}