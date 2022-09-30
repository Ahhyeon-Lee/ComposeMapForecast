package com.example.domain.repository.translate

import com.example.datalayer.local.model.LanguageCodeEntity
import com.example.datalayer.local.model.LanguageTargetEntity
import com.example.datalayer.local.model.TranslateHistoryEntity
import com.example.domain.model.LanguageCodeData
import com.example.domain.model.LanguageTargetData
import com.example.domain.model.TranslateHistoryData

interface LanguageRepository {
    suspend fun getLanguageCode() : List<LanguageCodeData>
    suspend fun getLanguageTarget() : List<LanguageTargetData>
    suspend fun insertLanguageCode(items: List<LanguageCodeEntity>)
    suspend fun insertLanguageTarget(items: List<LanguageTargetEntity>)

    suspend fun insertTranslateHistory(item: TranslateHistoryEntity)
    suspend fun getTranslateHistory() : List<TranslateHistoryData>
}