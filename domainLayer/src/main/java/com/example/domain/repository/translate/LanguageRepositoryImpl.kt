package com.example.domain.repository.translate

import com.example.datalayer.local.datasource.TranslateRoomDataSource
import com.example.datalayer.local.model.LanguageCodeEntity
import com.example.datalayer.local.model.LanguageTargetEntity
import com.example.domain.model.LanguageCodeData
import com.example.domain.model.LanguageTargetData

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
}