package com.example.datalayer.local

import androidx.room.*

@Dao
interface RoomDao {
    @Query("SELECT * FROM regions")
    suspend fun getAll() : List<RegionRowEntity>

    @Insert
    suspend fun insert(vararg region : RegionRowEntity)

    @Update
    suspend fun updateRegions(vararg region : RegionRowEntity)
}