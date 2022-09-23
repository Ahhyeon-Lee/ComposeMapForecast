package com.app.maptranslation.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.maptranslation.composable.RecognizeState
import com.example.domain.model.LanguageCodeData
import com.example.domain.model.LanguageTargetData
import com.example.domain.usecase.translate.GetLanguageCodeUseCase
import com.example.domain.usecase.translate.GetLanguageTargetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranslateViewModel @Inject constructor(
    private val languageCodeUseCase: GetLanguageCodeUseCase,
    private val languageTargetUseCase: GetLanguageTargetUseCase
) : ViewModel() {

    private val _sourceLanguage = mutableStateOf("")
    val sourceLanguage: State<String> = _sourceLanguage

    private val _languageCodeList = mutableStateOf<List<LanguageCodeData>>(listOf())
    val languageCodeList: State<List<LanguageCodeData>> = _languageCodeList

    private var translateList : List<LanguageTargetData> = listOf()

    private val _selectSource = mutableStateOf(LanguageCodeData())
    val selectSource: State<LanguageCodeData> = _selectSource

    private val _languageTargetList = mutableStateOf<List<LanguageCodeData>>(listOf())
    val languageTargetList: State<List<LanguageCodeData>> = _languageTargetList

    private val _selectTarget = mutableStateOf(LanguageCodeData())
    val selectTarget: State<LanguageCodeData> = _selectTarget

    fun getLanguageCode(context: Context) = viewModelScope.launch{
        _languageCodeList.value = languageCodeUseCase.invoke(context)
        if(_languageCodeList.value.isEmpty())
            LanguageCodeData("", "데이터없음")
        else {
            setSelectSource(_languageCodeList.value[0])
            resetTargetList()
        }
    }

    fun getLanguageTarget(context: Context) = viewModelScope.launch{
        translateList = languageTargetUseCase.invoke(context)
    }

    fun setSourceLanguage(text: String) {
        _sourceLanguage.value = text
    }

    fun setSelectSource(code: LanguageCodeData) {
        _selectSource.value = code
        resetTargetList()
    }

    fun setSelectTarget(code: LanguageCodeData) {
        _selectTarget.value = code
    }

    private fun resetTargetList() {
        if(translateList.isNotEmpty()) {
            _languageTargetList.value = translateList.filter { it.source == _selectSource.value.code }.map { target ->
                LanguageCodeData(target.target, _languageCodeList.value.filter { it.code == target.target }[0].language)
            }
            setSelectTarget(_languageTargetList.value[0])
        }
    }
}