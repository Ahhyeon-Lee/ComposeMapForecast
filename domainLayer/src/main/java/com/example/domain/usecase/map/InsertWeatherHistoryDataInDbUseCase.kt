package com.example.domain.usecase.map

import com.example.domain.model.WeatherForecast
import com.example.domain.repository.map.RegionsDBRepository
import javax.inject.Inject

class InsertWeatherHistoryDataInDbUseCase @Inject constructor(
    private val getADayWeatherHistoryUseCase: GetADayWeatherHistoryUseCase,
    private val regionsDBRepository: RegionsDBRepository
) {
    suspend fun invoke(weather: WeatherForecast) {
        val sameDayWeatherList = getADayWeatherHistoryUseCase.invoke(weather.date)
        sameDayWeatherList.filter {
            it == weather
        }.let {
            if (it.isEmpty()){
                regionsDBRepository.insertWeatherHistoryData(weather)
            }
        }
    }
}