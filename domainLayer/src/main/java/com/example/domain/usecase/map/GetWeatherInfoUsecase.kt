package com.example.domain.usecase.map

import com.example.domain.ResultUiState
import com.example.domain.model.Regions
import com.example.domain.model.WeatherForecast
import com.example.domain.repository.map.WeatherRepository
import com.example.domain.usecase.DateTimeHelper
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetWeatherInfoUsecase @Inject constructor(
    private val weatherRepository : WeatherRepository
) {
    suspend fun invoke(regionData:Regions)
    : Flow<ResultUiState<WeatherForecast>> {
        return weatherRepository.getWeatherInfo(regionData, DateTimeHelper.getDate("yyyyMMdd"), DateTimeHelper.getTimeOneHourBefore("HHmm"))
            .map {
                if (it.weather == "-1") {
                    ResultUiState.Error
                } else {
                    ResultUiState.Success(it)
                }
            }
    }
}