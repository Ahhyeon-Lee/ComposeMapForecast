package com.app.maptranslation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.LanguageCodeData
import com.example.domain.model.LanguageTargetData
import com.example.domain.model.TranslateState
import com.example.domain.usecase.translate.GetLanguageCodeUseCase
import com.example.domain.usecase.translate.GetLanguageTargetUseCase
import com.example.domain.usecase.translate.TranslateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranslateViewModel @Inject constructor(
    private val languageCodeUseCase: GetLanguageCodeUseCase,
    private val languageTargetUseCase: GetLanguageTargetUseCase,
    private val translateUseCase: TranslateUseCase
) : ViewModel() {

    private val _sourceText = mutableStateOf("")
    val sourceText: State<String> = _sourceText

    private val _targetText = mutableStateOf("")
    val targetText: State<String> = _targetText

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
        if(languageCodeList.value.isNotEmpty()) return@launch

        _languageCodeList.value = languageCodeUseCase.invoke(context)
        translateList = languageTargetUseCase.invoke(context)

        if(languageCodeList.value.isEmpty())
            LanguageCodeData("", "데이터없음")
        else {
            setSelectSource(_languageCodeList.value[0])
            resetTargetList()
        }
    }

    fun setSourceText(text: String) {
        _sourceText.value = text
    }

    fun setSelectSource(code: LanguageCodeData) {
        _selectSource.value = code
        resetTargetList()
    }

    fun setSelectTarget(code: LanguageCodeData) {
        _selectTarget.value = code
    }

    private fun resetTargetList() {
        if(translateList.isNotEmpty() && languageCodeList.value.isNotEmpty()) {
            _languageTargetList.value = translateList.filter { it.source == _selectSource.value.code }.map { target ->
                LanguageCodeData(target.target, _languageCodeList.value.filter { it.code == target.target }[0].language)
            }
            setSelectTarget(languageTargetList.value[0])
        }
    }

    fun translateText(text: String) = viewModelScope.launch {
        if(text.isEmpty()) return@launch
        translateUseCase.invoke(selectSource.value.code, selectTarget.value.code, text)
            .catch {
                Log.i("햄catch", this.toString())
            }
            .collect {
                when(it.state) {
                    TranslateState.FAIL -> TODO()
                    TranslateState.SUCCESS -> {
                        _targetText.value = it.translatedText ?: ""
                    }
                }
            }
    }
}