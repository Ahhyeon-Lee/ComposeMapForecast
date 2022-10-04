package com.example.datalayer.repository

import com.example.datalayer.local.datasource.TranslateRoomDataSource
import com.example.datalayer.local.model.LanguageCodeEntity
import com.example.datalayer.local.model.LanguageTargetEntity
import com.example.datalayer.local.model.TranslateHistoryEntity
import com.example.domain.model.LanguageCodeData
import com.example.domain.model.LanguageTargetData
import com.example.domain.model.TranslateHistoryData
import com.example.domain.repository.translate.LanguageRepository
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
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

    override suspend fun insertLanguageCode(items: List<LanguageCodeData>) {
        dataSource.insertLanguageCode(items.map {
            LanguageCodeEntity(it.code, it.language)
        })
    }
    override suspend fun insertLanguageTarget(items: List<LanguageTargetData>) {
        dataSource.insertLanguageTarget(items.map {
            LanguageTargetEntity(it.source, it.target)
        })
    }

    override suspend fun insertTranslateHistory(item: TranslateHistoryData) {
        dataSource.insertTranslateHistory(
            TranslateHistoryEntity(
                item.sourceCode,
                item.sourceText,
                item.targetCode,
                item.targetText,
                System.currentTimeMillis()
            )
        )
    }

    override suspend fun getTranslateHistory(): List<TranslateHistoryData> =
        dataSource.getTranslateHistory().map {
            TranslateHistoryData(it.sourceCode, it.sourceLanguage, it.sourceText, it.targetCode, it.targetLanguage, it.targetText)
        }
}