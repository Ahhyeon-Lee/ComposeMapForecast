package com.example.datalayer.repository

import com.example.datalayer.local.datasource.TranslateRoomDataSource
import com.example.datalayer.local.model.LanguageCodeEntity
import com.example.datalayer.local.model.LanguageTargetEntity
import com.example.datalayer.local.model.TranslateHistoryEntity
import com.example.domain.model.LanguageCodeData
import com.example.domain.model.LanguageTargetData
import com.example.domain.model.TranslateHistoryData
import com.example.domain.repository.translate.LanguageRepository

class LanguageRepositoryImpl(
    private val dataSource : TranslateRoomDataSource
) : LanguageRepository {
    override suspend fun getLanguageCode(): List<LanguageCodeData> =
        dataSource.getLanguageCode().map {
            LanguageCodeData(it.code, it.language)
        }

    override suspend fun getLanguageTarget(): List<LanguageTargetData> =
        dataSource.getLanguageTarget().map {
            LanguageTargetData(it.source, it.target)
        }

    override suspend fun insertLanguageCode(items: List<LanguageCodeEntity>) = dataSource.insertLanguageCode(items)
    override suspend fun insertLanguageTarget(items: List<LanguageTargetEntity>) = dataSource.insertLanguageTarget(items)

    override suspend fun insertTranslateHistory(item: TranslateHistoryEntity) = dataSource.insertTranslateHistory(item)
    override suspend fun getTranslateHistory(): List<TranslateHistoryData> =
        dataSource.getTranslateHistory().map {
            TranslateHistoryData(it.sourceCode, it.sourceLanguage, it.sourceText, it.targetCode, it.targetLanguage, it.targetText)
        }
}