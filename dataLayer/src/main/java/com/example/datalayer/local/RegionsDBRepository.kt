package com.example.datalayer.local

import android.content.Context
import androidx.room.Room

class RegionsDBRepository {
    fun checkDB(applicationContext: Context) {
        val db = Room.databaseBuilder(
            applicationContext,
            RegionsDatabase::class.java, "database-region"
        )
    }
}