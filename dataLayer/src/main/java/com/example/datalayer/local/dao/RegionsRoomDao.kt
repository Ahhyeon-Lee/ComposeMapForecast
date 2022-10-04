package com.example.datalayer.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.datalayer.local.model.RegionRowEntity
import com.example.datalayer.local.model.WeatherHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RegionsRoomDao {

    @Query("SELECT * FROM regions")
    suspend fun getAllRegionsList() : List<RegionRowEntity>

    @Query("SELECT * FROM regions WHERE address LIKE :textField")
    fun getSearchedRegionsList(textField:String) : Flow<List<RegionRowEntity>>

    @Query("SELECT * FROM regions " +
            "ORDER BY ((longtitude - :longtitude)*(longtitude - :longtitude) + (latitude - :latitude)*(latitude - :latitude)) " +
            "LIMIT 1")
    suspend fun getClosestRegion(longtitude:Double, latitude:Double) : RegionRowEntity

    @Query("SELECT * FROM WeatherHistoryEntity")
    suspend fun getAllWeatherHistoryList() : List<WeatherHistoryEntity>

    @Query("SELECT * FROM WeatherHistoryEntity WHERE :date = date")
    suspend fun getSameDayWeatherHistoryList(date:String) : List<WeatherHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegionRowEntity(vararg region : RegionRowEntity)

    @Insert
    suspend fun insertWeatherHistoryEntity(vararg searchedRegionsWeather : WeatherHistoryEntity)
}