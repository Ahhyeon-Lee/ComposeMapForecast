package com.example.datalayer

import java.lang.Exception

sealed class ResultNetworkState<out R> {

    data class Success<out T>(val data: T) : ResultNetworkState<T>()
    data class Error(val exception: Throwable) : ResultNetworkState<Nothing>()

}

data class ResultNetworkData<out T>(
    val data: T
) {
    val success = Success(data)
    val error = Error
}

data class Success<out T>(
    val data: T
)

object Error