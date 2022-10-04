package com.example.domain.usecase.map

import com.example.domain.model.WeatherForecast
import com.example.domain.repository.map.RegionsDBRepository
import javax.inject.Inject

class GetADayWeatherHistoryUseCase @Inject constructor(
    private val regionsDBRepository: RegionsDBRepository
) {
    suspend fun invoke(date:String) : List<WeatherForecast> {
        return regionsDBRepository.getSameDayWeatherHistoryList(date)
    }
}