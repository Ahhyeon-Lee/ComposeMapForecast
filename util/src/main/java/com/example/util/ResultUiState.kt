package com.example.util

sealed class ResultUiState<out R> {

    object UnInitialize : ResultUiState<Nothing>()
    object Loading : ResultUiState<Nothing>()
    data class Success<out T>(val data: T) : ResultUiState<T>()
    data class Error(val exception: Exception) : ResultUiState<Nothing>()
    object Finish : ResultUiState<Nothing>()

//    fun toString(): String {
//        return when (this) {
//            is Success<*> -> "Success[data=$data]"
//            is Error -> "Error[exception=$exception]"
//            is Loading ->
//        }
//    }
}

/**
 * `true` if [ResultUiState] is of type [Success] & holds non-null [Success.data].
 */
val ResultUiState<*>.succeeded
    get() = this is ResultUiState.Success && data != null