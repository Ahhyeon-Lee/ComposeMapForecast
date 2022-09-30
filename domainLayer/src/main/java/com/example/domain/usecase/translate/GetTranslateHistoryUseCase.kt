package com.example.domain.usecase.translate

import com.example.domain.repository.translate.LanguageRepository

class GetTranslateHistoryUseCase(
    private val repository: LanguageRepository
) {
    suspend fun invoke() = repository.getTranslateHistory()
}