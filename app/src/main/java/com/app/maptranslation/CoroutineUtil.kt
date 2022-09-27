package com.app.maptranslation

import com.example.domain.ResultUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

inline fun <T> ResultUiState<T>.onUiState(
    loading: () -> Unit = {},
    success: (T) -> Unit = {},
    error: () -> Unit = {},
    finish: () -> Unit = {}
) {
    when (this) {
        ResultUiState.Loading -> loading()
        is ResultUiState.Success -> success(this.data)
        is ResultUiState.Error -> error()
        ResultUiState.Finish -> finish()
        else -> Unit
    }
}