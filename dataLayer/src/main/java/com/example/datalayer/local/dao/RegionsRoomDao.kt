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

    @Insert
    suspend fun insert(vararg region : RegionRowEntity)

    @Update
    suspend fun updateRegions(vararg region : RegionRowEntity)
}