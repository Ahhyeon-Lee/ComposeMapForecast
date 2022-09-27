package com.example.domain.model

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