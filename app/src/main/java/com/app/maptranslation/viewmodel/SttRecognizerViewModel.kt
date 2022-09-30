package com.app.maptranslation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.app.maptranslation.composable.RecognizeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SttRecognizerViewModel: ViewModel() {

    private val _recognizeState = MutableStateFlow<RecognizeState>(RecognizeState.Ready)
    val recognizeState = _recognizeState.asStateFlow()

    private val _sttText = mutableStateOf("")
    val sttText: State<String> = _sttText

    fun setRecognizeState(state: RecognizeState) {
        _recognizeState.value = state
    }

    fun setSttText(text: String) {
        _sttText.value = text
    }
}