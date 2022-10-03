package com.example.domain.usecase.translate

import com.example.domain.repository.translate.TranslateRepository
import javax.inject.Inject

class TranslateUseCase @Inject constructor(
    private val repository: TranslateRepository
) {
    suspend fun invoke(source: String, target: String, text: String) =
        repository.doTranslateText(source, target, text)
}