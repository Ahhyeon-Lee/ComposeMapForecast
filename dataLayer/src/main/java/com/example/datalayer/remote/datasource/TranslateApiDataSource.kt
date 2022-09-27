package com.example.datalayer.remote.datasource

import com.example.datalayer.remote.model.TranslateResponse
import com.example.datalayer.remote.service.TranslateApiService
import javax.inject.Inject

class TranslateApiDataSource @Inject constructor(
    private val service: TranslateApiService
) {
    suspend fun getTranslate(source: String, target: String, text: String) : TranslateResponse =
        service.papagoTranslate(source = source, target = target, text = text)
}