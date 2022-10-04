package com.example.domain.usecase.map

import com.example.domain.model.WeatherForecast
import com.example.domain.repository.map.RegionsDBRepository
import javax.inject.Inject

class GetWeatherHistoryUseCase @Inject constructor(
    private val regionsDBRepository: RegionsDBRepository
) {
    suspend fun invoke() : List<WeatherForecast> {
        return regionsDBRepository.getAllWeatherHistoryList()
    }
}