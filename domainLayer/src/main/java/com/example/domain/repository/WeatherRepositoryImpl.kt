package com.example.domain.repository

import com.example.datalayer.remote.datasource.WeatherApiDataSource
import com.example.datalayer.remote.model.Weather
import com.example.datalayer.remote.service.WeatherApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

class WeatherRepositoryImpl(
    private val weatherApiDataSource: WeatherApiDataSource
) : WeatherRepository {

    override suspend fun getWeatherInfo(nx:String, ny:String, date:String, time:String) : Flow<Weather> =
        flowOf(
            weatherApiDataSource.getWeatherInfo(
                nx = nx,
                ny = ny,
                date = date,
                time = time
            )
        ).flowOn(Dispatchers.IO)
}