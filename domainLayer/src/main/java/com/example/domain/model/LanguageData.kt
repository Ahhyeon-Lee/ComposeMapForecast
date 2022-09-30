package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class LanguageCodeData(
    val code: String = "",
    val language: String = ""
)

data class LanguageTargetData(
    val source: String = "",
    val target: String = ""
)

data class TranslateResult(
    val state: TranslateState = TranslateState.FAIL,
    val translatedText: String? = ""
)

sealed class TranslateState {
    object SUCCESS : TranslateState()
    object FAIL : TranslateState()
}

@Parcelize
data class TranslateHistoryData(
    val sourceCode: String = "",
    val sourceLanguage: String = "",
    val sourceText: String = "",
    val targetCode: String = "",
    val targetLanguage: String = "",
    val targetText: String = ""
): Parcelable