package com.example.domain.usecase.map

import com.example.datalayer.remote.model.Weather
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetWeatherInfoUsecase(
    private val weatherRepository : WeatherRepository
) {
    suspend fun invoke(nx:String, ny:String, date:String, time:String) : Flow<Weather> {
        return weatherRepository.getWeatherInfo(nx, ny, date, time)
    }
}