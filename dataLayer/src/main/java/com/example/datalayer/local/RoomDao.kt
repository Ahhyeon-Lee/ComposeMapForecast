package com.example.datalayer.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RoomDao {
    @Query("SELECT * FROM regions")
    suspend fun getAll() : List<RegionRowEntity>

    @Insert
    suspend fun insert(vararg region : RegionRowEntity)

    @Update
    suspend fun updateRegions(vararg region : RegionRowEntity)
}