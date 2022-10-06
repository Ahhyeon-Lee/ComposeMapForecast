package com.example.domain.usecase.map

import com.example.domain.model.ResultUiState
import com.example.domain.model.Regions
import com.example.domain.model.WeatherForecast
import com.example.domain.repository.map.WeatherRepository
import com.example.domain.util.DateTimeHelper
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetWeatherInfoUseCase @Inject constructor(
    private val weatherRepository : WeatherRepository
) {
    suspend fun invoke(regionData:Regions)
    : Flow<ResultUiState<WeatherForecast>> {
        return weatherRepository.getWeatherInfo(regionData, DateTimeHelper.getDate("yyyyMMdd"), DateTimeHelper.getTimeOneHourBefore("HHmm"))
            .map {
                if (it.weather == "-1") {
                    ResultUiState.ErrorWithData(it)
                } else {
                    ResultUiState.Success(it)
                }
            }
    }
}