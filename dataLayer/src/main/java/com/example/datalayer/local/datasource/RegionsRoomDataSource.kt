package com.example.datalayer.local.datasource

import com.example.datalayer.local.model.RegionRowEntity
import com.example.datalayer.local.dao.RegionsRoomDao
import com.example.datalayer.local.model.WeatherHistoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RegionsRoomDataSource (
    private val regionDao : RegionsRoomDao
) {

    suspend fun getAllRegionsData() : List<RegionRowEntity> {
        return regionDao.getAllRegionsList()
    }

    fun getSearchedRegionsList(textField:String) : Flow<List<RegionRowEntity>> {
        return regionDao.getSearchedRegionsList(textField)
    }

    suspend fun getClosestRegion(longtitude:Double, latitude:Double) : RegionRowEntity {
        return regionDao.getClosestRegion(longtitude, latitude)
    }

    suspend fun insertRegionData(regionRowEntity: RegionRowEntity) {
        withContext(Dispatchers.IO) {
            regionDao.insertRegionRowEntity(regionRowEntity)
        }
    }

    suspend fun getAllWeatherHistoryList() : List<WeatherHistoryEntity> {
        return regionDao.getAllWeatherHistoryList()
    }

    suspend fun getSameDayWeatherHistoryList(date: String) : List<WeatherHistoryEntity> {
        return regionDao.getSameDayWeatherHistoryList(date)
    }

    suspend fun insertWeatherHistoryData(weatherHistoryEntity: WeatherHistoryEntity) {
        regionDao.insertWeatherHistoryEntity(weatherHistoryEntity)
    }
}