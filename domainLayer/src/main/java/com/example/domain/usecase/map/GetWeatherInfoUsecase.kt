package com.example.domain.usecase.map

import com.example.domain.ResultUiState
import com.example.domain.model.Regions
import com.example.domain.model.WeatherForecast
import com.example.domain.repository.WeatherRepository
import com.example.domain.usecase.GetDateTimeInfoUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetWeatherInfoUsecase @Inject constructor(
    private val weatherRepository : WeatherRepository,
    private val dateTimeInfoUseCase: GetDateTimeInfoUseCase
) {
    suspend fun invoke(regionData:Regions)
    : Flow<ResultUiState<WeatherForecast>> {
        return weatherRepository.getWeatherInfo(regionData, dateTimeInfoUseCase.getDate("yyyyMMdd"), dateTimeInfoUseCase.getTimeOneHourBefore("HHmm"))
            .map {
                if (it.weather == "-1") {
                    ResultUiState.Error
                } else {
                    ResultUiState.Success(it)
                }
            }
    }
}