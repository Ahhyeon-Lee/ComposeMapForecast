package com.example.domain.repository.translate

import com.example.domain.model.LanguageCodeData
import com.example.domain.model.LanguageTargetData
import com.example.domain.model.TranslateHistoryData

interface LanguageRepository {
    suspend fun getLanguageCode() : List<LanguageCodeData>
    suspend fun getLanguageTarget() : List<LanguageTargetData>
    suspend fun insertLanguageCode(items: List<LanguageCodeData>)
    suspend fun insertLanguageTarget(items: List<LanguageTargetData>)

    suspend fun insertTranslateHistory(item: TranslateHistoryData)
    suspend fun getTranslateHistory() : List<TranslateHistoryData>
}