package com.example.domain

sealed class ResultUiState<out R> {

    object UnInitialize : ResultUiState<Nothing>()
    object Loading : ResultUiState<Nothing>()
    data class Success<out T>(val data: T) : ResultUiState<T>()
    object Error : ResultUiState<Nothing>()
    object Finish : ResultUiState<Nothing>()

}

/**
 * `true` if [ResultUiState] is of type [Success] & holds non-null [Success.data].
 */
val ResultUiState<*>.succeeded
    get() = this is ResultUiState.Success && data != null