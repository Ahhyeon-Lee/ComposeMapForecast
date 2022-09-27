package com.app.maptranslation.util

import android.content.Context
import android.util.Log
import com.app.maptranslation.viewmodel.ClovaRecognizerViewModel
import com.naver.speech.clientapi.*
import com.naver.speech.clientapi.SpeechConfig.EndPointDetectType
import com.naver.speech.clientapi.SpeechConfig.LanguageType

class ClovaRecognizer {
    private val TAG = ClovaRecognizer::class.java.simpleName

    private var mRecognizer : SpeechRecognizer? = null
    lateinit var viewModel: ClovaRecognizerViewModel

    fun init(context: Context, clientId: String, viewModel: ClovaRecognizerViewModel) {
        mRecognizer = SpeechRecognizer(context, clientId)
        mRecognizer?.setSpeechRecognitionListener(speechRecognitionListener)
        mRecognizer?.initialize()
        this.viewModel = viewModel
    }

    fun recognize(language: String) {
        try {
            val languageType =
                when(language) {
                    "한국어" -> LanguageType.KOREAN
                    "영어" -> LanguageType.ENGLISH
                    "일본어" -> LanguageType.JAPANESE
                    "중국어" -> LanguageType.CHINESE
                    else -> LanguageType.KOREAN
                }
            mRecognizer?.recognize(SpeechConfig(languageType, EndPointDetectType.MANUAL))
        } catch (e: SpeechRecognitionException) {
            e.printStackTrace()
        }
    }

    fun getSpeechRecognizer() = mRecognizer

    private val speechRecognitionListener = object : SpeechRecognitionListener{
        override fun onInactive() {
            Log.d(TAG, "onInactive")
        }

        override fun onReady() {
            Log.d(TAG, "onReady")
        }

        override fun onRecord(speech: ShortArray?) {
            Log.d(TAG, "onRecord")
        }

        // 음성 인식 진행중 결과
        override fun onPartialResult(partialResult: String?) {
            partialResult?.let { viewModel.setSourceLanguage(partialResult) }
            Log.d(TAG, "onPartialResult : $partialResult")
        }

        override fun onEndPointDetected() {
            Log.d(TAG, "onEndPointDetected")
        }

        // 인식 완료후 유사결과 출력
        override fun onResult(finalResult: SpeechRecognitionResult?) {
            val result = finalResult?.results
            result?.let {
                viewModel.setResultText(it)
            }

        }

        override fun onError(errorCode: Int) {
            Log.d(TAG, "onError")
        }

        override fun onEndPointDetectTypeSelected(epdType: SpeechConfig.EndPointDetectType?) {
            Log.d(TAG, "onEndPointDetectTypeSelected")
        }

    }
}