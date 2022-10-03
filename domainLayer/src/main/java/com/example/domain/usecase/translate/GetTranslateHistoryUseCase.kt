package com.example.domain.usecase.translate

import com.example.domain.repository.translate.LanguageRepository
import javax.inject.Inject

class GetTranslateHistoryUseCase @Inject constructor(
    private val repository: LanguageRepository
) {
    suspend fun invoke() = repository.getTranslateHistory()
}