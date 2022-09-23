package com.example.domain.repository

import com.example.datalayer.ResultNetworkState
import com.example.datalayer.remote.model.NetworkWeatherForecastData
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherInfo(
        nx:String, ny:String, date:String, time:String
    ): Flow<NetworkWeatherForecastData>
}