package com.example.domain.repository.translate

import com.example.datalayer.local.model.LanguageCodeEntity
import com.example.datalayer.local.model.LanguageTargetEntity
import com.example.domain.model.LanguageCodeData
import com.example.domain.model.LanguageTargetData

interface LanguageRepository {
    suspend fun getLanguageCode() : List<LanguageCodeData>
    suspend fun getLanguageTarget() : List<LanguageTargetData>
    suspend fun insertLanguageCode(items: List<LanguageCodeEntity>)
    suspend fun insertLanguageTarget(items: List<LanguageTargetEntity>)
}