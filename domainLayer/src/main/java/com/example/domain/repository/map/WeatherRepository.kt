package com.example.domain.repository.map

import com.example.domain.model.Regions
import com.example.domain.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeatherInfo(regionData: Regions, date:String, time:String): Flow<WeatherForecast>
}