package com.example.domain.usecase.map

import com.example.datalayer.remote.model.NetworkWeatherForecastData
import com.example.domain.ResultUiState
import com.example.domain.model.WeatherForecast
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.*

class GetWeatherInfoUsecase(
    private val weatherRepository : WeatherRepository
) {
    suspend fun invoke(nx:String, ny:String, date:String, time:String, longtitude:String, latitude:String)
    : Flow<ResultUiState<WeatherForecast>> {
        var weather = "-1"
        return weatherRepository.getWeatherInfo(nx, ny, date, time)
            .map {
                it.response?.body?.items?.item?.filter {
                    it.category == "PTY" || it.category == "SKY"
                }?.let {
                    it.forEach {
                        weather = when(it.category) {
                            "PTY" -> {
                                it.fcstValue ?: "-1"
                            }
                            else -> {
                                when(it.fcstValue) {
                                    "1" -> "8"
                                    "3" -> "9"
                                    else -> "10"
                                }
                            }
                        }
                    }
                }
                if (weather == "-1") {
                    ResultUiState.Error
                } else {
                    ResultUiState.Success(WeatherForecast(nx, ny, weather, longtitude, latitude))
                }
            }
    }
}