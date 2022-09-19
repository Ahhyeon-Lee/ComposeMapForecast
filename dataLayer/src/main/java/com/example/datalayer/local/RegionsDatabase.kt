package com.example.datalayer.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RegionRowEntity::class], version = 1)
abstract class RegionsDatabase : RoomDatabase() {
    abstract fun regionDao() : RoomDao
}