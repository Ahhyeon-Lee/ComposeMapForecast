package com.example.datalayer.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RegionRowEntity::class], version = 1)
abstract class RegionsDatabase : RoomDatabase() {
    abstract fun regionDao() : RoomDao

    companion object {

        var regionsDbInstance : RegionsDatabase? = null

        fun getDatabaseInstance(applicationContext: Context) : RegionsDatabase {
            return regionsDbInstance ?: synchronized(RegionsDatabase::class) {
                newDatabaseInstance(applicationContext, "regionsDb")
            }
        }

        private fun newDatabaseInstance(applicationContext: Context, dbName:String): RegionsDatabase {
            return Room.databaseBuilder(
                applicationContext,
                RegionsDatabase::class.java, dbName
            ).build()
        }

    }
}