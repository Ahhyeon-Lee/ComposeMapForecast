package com.example.datalayer.remote.model

data class TranslateResponse (
    val message : Message? = Message(),
    val errorCode: String = "",
    val errorMessage: String = ""
) {
    data class Message(
        val result: Result? = Result()
    )

    data class Result(
        val srcLangType: String = "",
        val tarLangType: String = "",
        val translatedText: String = ""
    )
}