package com.example.datalayer.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.datalayer.local.dao.RegionsRoomDao
import com.example.datalayer.local.dao.TranslateRoomDao
import com.example.datalayer.local.model.*

@Database(
    entities = [
        RegionRowEntity::class,
        LanguageCodeEntity::class,
        LanguageTargetEntity::class,
        TranslateHistoryEntity::class,
        WeatherHistoryEntity::class
    ],
    version = 1
)
@TypeConverters(RoomTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun regionDao() : RegionsRoomDao
    abstract fun translateDao() : TranslateRoomDao
}