package com.example.datalayer.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.datalayer.local.model.LanguageCodeEntity
import com.example.datalayer.local.model.LanguageTargetEntity

@Dao
interface TranslateRoomDao {

    @Query("SELECT * FROM LanguageCodeEntity")
    suspend fun getLanguageAll() : List<LanguageCodeEntity>

    @Query("SELECT * FROM LanguageTargetEntity")
    suspend fun getTargetAll() : List<LanguageTargetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguageCodeAll(vararg language: LanguageCodeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguageTargetAll(vararg language: LanguageTargetEntity)
}