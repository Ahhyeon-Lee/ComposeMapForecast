package com.example.domain.usecase.map

import com.example.datalayer.remote.model.NetworkWeatherForecastData
import com.example.domain.ResultUiState
import com.example.domain.model.Regions
import com.example.domain.model.WeatherForecast
import com.example.domain.repository.WeatherRepository
import com.example.domain.usecase.GetDateTimeInfoUseCase
import kotlinx.coroutines.flow.*

class GetWeatherInfoUsecase(
    private val weatherRepository : WeatherRepository,
    private val dateTimeInfoUseCase: GetDateTimeInfoUseCase
) {
    suspend fun invoke(regionData:Regions)
    : Flow<ResultUiState<WeatherForecast>> {
        var weather = "-1"
        return weatherRepository.
        getWeatherInfo(regionData.nx, regionData.ny, dateTimeInfoUseCase.getDate("yyyyMMdd"), dateTimeInfoUseCase.getTime("HHmm"))
            .map {
                it.response?.body?.items?.item?.filter {
                    it.category == "PTY" || it.category == "SKY"
                }?.let {
                    val map = it.map { it.category to it.fcstValue }.toMap()
                    weather = when {
                        map["PTY"] == "0" -> with(map["SKY"] ?: "-1") {
                            when(this) {
                                "1" -> "0" // 맑음
                                "-1" -> "-1" // 오류
                                else -> this // 구름많음, 흐림
                            }
                        }
                        else -> with(map["PTY"] ?: "-1") {
                            when(this) {
                                "1","2","5","6" -> "1" // 비
                                "3","7" -> "2" // 눈
                                "-1" -> "-1" // 오류
                                else -> this
                            }
                        }
                    }
                }
                if (weather == "-1") {
                    ResultUiState.Error
                } else {
                    ResultUiState.Success(WeatherForecast(regionData.city, regionData.gu, regionData.dong, regionData.nx, regionData.ny, regionData.longtitude, regionData.latitude, weather))
                }
            }
    }
}