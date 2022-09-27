package com.example.domain.repository.translate

import com.example.domain.model.TranslateResult
import kotlinx.coroutines.flow.Flow

interface TranslateRepository {
    suspend fun doTranslateText(source: String, target: String, text: String) : Flow<TranslateResult>
}