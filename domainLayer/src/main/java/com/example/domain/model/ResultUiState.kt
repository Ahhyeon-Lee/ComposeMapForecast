package com.example.domain.model

sealed class ResultUiState<out R> {

    object UnInitialize : ResultUiState<Nothing>()
    object Loading : ResultUiState<Nothing>()
    data class Success<out T>(val data: T) : ResultUiState<T>()
    object Error : ResultUiState<Nothing>()
    data class ErrorWithData<out T>(val data: T) : ResultUiState<T>()
    object Finish : ResultUiState<Nothing>()

}