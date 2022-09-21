package com.example.datalayer.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.datalayer.local.dao.RegionsRoomDao
import com.example.datalayer.local.model.RegionRowEntity

@Database(entities = [RegionRowEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun regionDao() : RegionsRoomDao
}