package com.example.datalayer.remote.datasource

import com.example.datalayer.remote.model.WeatherForecastResponse
import com.example.datalayer.remote.service.WeatherApiService

class WeatherApiDataSource(
    private val weatherApiService: WeatherApiService
) {
    suspend fun getWeatherInfo(nx:String, ny:String, date:String, time:String) : WeatherForecastResponse =
        weatherApiService.getWeatherInfo(
            nx = nx,
            ny = ny,
            date = date,
            time = time
        )
}