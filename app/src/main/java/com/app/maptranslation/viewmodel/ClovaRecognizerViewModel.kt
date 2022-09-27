package com.app.maptranslation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ClovaRecognizerViewModel: ViewModel() {

    private val _sourceLanguage = mutableStateOf("")
    val sourceLanguage: State<String> = _sourceLanguage

    private val _resultText = mutableStateOf("")
    val resultText: State<String> = _resultText

    val languageList = listOf("한국어", "영어", "일본어", "중국어")
    fun setSourceLanguage(text: String) {
        _sourceLanguage.value = text
    }

    fun clearSourceLanguage() {
        _sourceLanguage.value = ""
        _resultText.value = ""
    }

    fun setResultText(result: List<String>) {
        result.forEach {
            _resultText.value += "$it\n"
        }
    }
}