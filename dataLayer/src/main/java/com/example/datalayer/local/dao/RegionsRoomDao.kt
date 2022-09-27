package com.example.datalayer.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.datalayer.local.model.RegionRowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RegionsRoomDao {

    @Query("SELECT * FROM regions")
    suspend fun getAll() : List<RegionRowEntity>

    @Query("SELECT * FROM regions WHERE city LIKE :textField " +
            "OR gu LIKE :textField " +
            "OR dong LIKE :textField ")
    fun getSearchedRegionsList(textField:String) : Flow<List<RegionRowEntity>>

    @Query("SELECT * FROM regions " +
            "ORDER BY ((longtitude - :longtitude)*(longtitude - :longtitude) + (latitude - :latitude)*(latitude - :latitude)) " +
            "LIMIT 1")
    suspend fun getClosestRegion(longtitude:Double, latitude:Double) : RegionRowEntity

    @Insert
    suspend fun insert(vararg region : RegionRowEntity)

    @Update
    suspend fun updateRegions(vararg region : RegionRowEntity)
}