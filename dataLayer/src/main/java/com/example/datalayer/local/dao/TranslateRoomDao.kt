package com.example.datalayer.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.datalayer.local.model.LanguageCodeEntity
import com.example.datalayer.local.model.LanguageTargetEntity
import com.example.datalayer.local.model.TranslateHistoryEntity
import com.example.datalayer.local.model.TranslateHistoryListEntity

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

    @Insert
    suspend fun insertTranslateHistory(vararg history: TranslateHistoryEntity)

    @Query("SELECT a.sourceCode as sourceCode, b.language as sourceLanguage, a.sourceText as sourceText, a.targetCode as targetCode, " +
            " c.language as targetLanguage, a.targetText as targetText, a.id" +
            "  FROM TranslateHistoryEntity a" +
            "  LEFT JOIN LanguageCodeEntity b ON a.sourceCode = b.code" +
            "  LEFT JOIN LanguageCodeEntity c ON a.targetCode = c.code")
    suspend fun getTranslateHistory() : List<TranslateHistoryListEntity>
}