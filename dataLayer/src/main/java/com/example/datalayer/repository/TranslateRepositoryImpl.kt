package com.example.datalayer.repository

import android.util.Log
import com.example.datalayer.remote.datasource.TranslateApiDataSource
import com.example.domain.model.TranslateResult
import com.example.domain.model.TranslateState
import com.example.domain.repository.translate.TranslateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class TranslateRepositoryImpl(
    private val dataSource: TranslateApiDataSource
): TranslateRepository {
    override suspend fun doTranslateText(source: String, target: String, text: String): Flow<TranslateResult> =
        flow {
            emit(dataSource.getTranslate(source, target, text))
        }
            .map {
                TranslateResult(TranslateState.SUCCESS, it.message?.result?.translatedText)
            }
            .flowOn(Dispatchers.IO)

            .catch {
                Log.i("햄", this.toString())
            }
        /*flowOf(dataSource.getTranslate(source, target, text))
            .map {
                TranslateResult(TranslateState.SUCCESS, it.message?.result?.translatedText)
            }
            .flowOn(Dispatchers.IO)
            .catch {
                Log.i("햄", this.toString())
            }*/
        /*val data = dataSource.getTranslate(source, target, text)
        return data.message?.let {
            it.result?.let { result ->
                TranslateResult(TranslateState.SUCCESS, result.translatedText)
            } ?: TranslateResult(TranslateState.FAIL)
        } ?: TranslateResult(TranslateState.FAIL)*/
}