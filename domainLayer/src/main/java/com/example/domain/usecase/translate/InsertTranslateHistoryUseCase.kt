package com.example.domain.usecase.translate

import com.example.datalayer.local.model.TranslateHistoryEntity
import com.example.domain.repository.translate.LanguageRepository

class InsertTranslateHistoryUseCase(
    private val repository: LanguageRepository
) {
    suspend fun invoke(sourceCode: String, sourceText: String, targetCode: String, targetText: String) {
        repository.insertTranslateHistory(
            TranslateHistoryEntity(sourceCode = sourceCode, sourceText = sourceText, targetCode = targetCode, targetText = targetText, insertDate = System.currentTimeMillis())
        )
    }
}