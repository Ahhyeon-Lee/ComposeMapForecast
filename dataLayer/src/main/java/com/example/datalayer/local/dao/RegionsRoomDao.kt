package com.example.datalayer.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.datalayer.local.model.RegionRowEntity
import com.example.datalayer.local.model.SearchedRegionsWeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RegionsRoomDao {

    @Query("SELECT * FROM regions")
    suspend fun getAll() : List<RegionRowEntity>

    @Query("SELECT * FROM regions WHERE address LIKE :textField")
    fun getSearchedRegionsList(textField:String) : Flow<List<RegionRowEntity>>

    @Query("SELECT * FROM regions " +
            "ORDER BY ((longtitude - :longtitude)*(longtitude - :longtitude) + (latitude - :latitude)*(latitude - :latitude)) " +
            "LIMIT 1")
    suspend fun getClosestRegion(longtitude:Double, latitude:Double) : RegionRowEntity

    @Query("SELECT * FROM SearchedRegionsWeatherEntity")
    suspend fun getSearchedRegionsWeatherList() : List<SearchedRegionsWeatherEntity>

    @Query("SELECT * FROM SearchedRegionsWeatherEntity WHERE :date = date")
    suspend fun getSameDaySearchedRegionsWeatherList(date:String) : List<SearchedRegionsWeatherEntity>

    @Insert
    suspend fun insert(vararg region : RegionRowEntity)
}