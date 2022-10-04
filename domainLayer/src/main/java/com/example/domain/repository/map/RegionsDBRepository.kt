package com.example.domain.repository.map

import com.example.domain.model.Regions
import com.example.domain.model.WeatherForecast
import jxl.Cell
import kotlinx.coroutines.flow.Flow

interface RegionsDBRepository {

    suspend fun checkRegionsDbData() : List<Regions>

    fun getSearchedRegionsList(textField:String) : Flow<List<Regions>>

    suspend fun getClosestRegion(longtitude:Double, latitude:Double) : Regions

    suspend fun insertRegions(cell: Array<Cell>)

    suspend fun getAllWeatherHistoryList() : List<WeatherForecast>

    suspend fun getSameDayWeatherHistoryList(date:String) : List<WeatherForecast>

    suspend fun insertWeatherHistoryData(weather: WeatherForecast)
}