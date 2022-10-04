package com.example.domain.usecase.translate

import com.example.domain.model.TranslateHistoryData
import com.example.domain.repository.translate.LanguageRepository
import javax.inject.Inject

class InsertTranslateHistoryUseCase @Inject constructor(
    private val repository: LanguageRepository
) {
    suspend fun invoke(sourceCode: String, sourceText: String, targetCode: String, targetText: String) {
        repository.insertTranslateHistory(
            TranslateHistoryData(sourceCode = sourceCode, sourceText = sourceText, targetCode = targetCode, targetText = targetText)
        )
    }
}