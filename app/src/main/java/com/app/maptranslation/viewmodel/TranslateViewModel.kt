package com.app.maptranslation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.domain.model.LanguageCodeData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TranslateViewModel @Inject constructor() : ViewModel() {

    private val _sourceLanguage = mutableStateOf("")
    val sourceLanguage: State<String> = _sourceLanguage

    fun addLanguageCode() = arrayListOf(
            LanguageCodeData("ko", "한국어"),
            LanguageCodeData("en", "영어"),
            LanguageCodeData("ja", "일본어"),
            LanguageCodeData("zh-CN", "중국어 간체"),
            LanguageCodeData("zh-TW", "중국어 번체"),
            LanguageCodeData("vi", "베트남어"),
        )

    fun setSourceLanguage(text: String) {
        _sourceLanguage.value = text
    }
}