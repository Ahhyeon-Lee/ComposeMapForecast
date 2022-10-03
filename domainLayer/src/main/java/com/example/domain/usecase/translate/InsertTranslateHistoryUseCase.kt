package com.example.domain.usecase.translate

import com.example.datalayer.local.model.TranslateHistoryEntity
import com.example.domain.repository.translate.LanguageRepository
import javax.inject.Inject

class InsertTranslateHistoryUseCase @Inject constructor(
    private val repository: LanguageRepository
) {
    suspend fun invoke(sourceCode: String, sourceText: String, targetCode: String, targetText: String) {
        repository.insertTranslateHistory(
            TranslateHistoryEntity(sourceCode = sourceCode, sourceText = sourceText, targetCode = targetCode, targetText = targetText, insertDate = System.currentTimeMillis())
        )
    }
}