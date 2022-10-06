package com.app.maptranslation.composable

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.airbnb.lottie.compose.*
import com.app.maptranslation.R
import com.app.maptranslation.viewmodel.SttRecognizerViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SttRecognizer(
    viewModel: SttRecognizerViewModel,
    languageCode: String
) {
    val speechState by viewModel.recognizeState.collectAsStateWithLifecycleRemember(initial = RecognizeState.Ready)

    val context = LocalContext.current
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
    }
    val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    speechRecognizer.setRecognitionListener(recognitionListener(context, viewModel))

    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.RECORD_AUDIO
        )
    )

    IconButton(
        modifier = Modifier.wrapContentSize(),
        onClick = {
            checkSttPermission(multiplePermissionsState) {
                speechRecognizer?.startListening(intent)
            }
        }) {
        when(speechState) {
            RecognizeState.Start -> LoadingLottie()
            else -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_mic_24),
                    tint = colorResource(id = R.color.sky),
                    contentDescription = "VoiceBtn"
                )
            }
        }
    }
}

private fun recognitionListener(context: Context, viewModel: SttRecognizerViewModel) = object : RecognitionListener {

    override fun onReadyForSpeech(params: Bundle?) {
        viewModel.setRecognizeState(RecognizeState.Start)
        Toast.makeText(context, "음성인식 시작", Toast.LENGTH_SHORT).show()
    }

    override fun onError(error: Int) {
        when(error) {
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> Toast.makeText(context, "퍼미션 없음", Toast.LENGTH_SHORT).show()
            else -> {}
        }
    }

    override fun onResults(results: Bundle) {
        Toast.makeText(context, "음성인식 종료", Toast.LENGTH_SHORT).show()
        viewModel.setRecognizeState(RecognizeState.Result(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)!![0]))
        viewModel.setSttText(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)!![0])
    }

    override fun onPartialResults(p0: Bundle?) {}
    override fun onEvent(p0: Int, p1: Bundle?) {}
    override fun onBeginningOfSpeech() {}
    override fun onRmsChanged(p0: Float) {}
    override fun onBufferReceived(p0: ByteArray?) {}
    override fun onEndOfSpeech() {}
}

@OptIn(ExperimentalPermissionsApi::class)
fun checkSttPermission(multiplePermissionsState: MultiplePermissionsState, callback: () -> Unit) {
    if(multiplePermissionsState.allPermissionsGranted) {
        callback.invoke()
    } else {
        multiplePermissionsState.launchMultiplePermissionRequest()
    }
}

@Composable
fun <T> Flow<T>.collectAsStateWithLifecycleRemember(
    initial: T,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
): State<T> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val flowLifecycleAware = remember(this, lifecycleOwner) {
        flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
    }
    return flowLifecycleAware.collectAsState(initial)
}

sealed class RecognizeState {

    object Ready : RecognizeState()
    data class Result(val text: String) : RecognizeState()
    object Start : RecognizeState()
    object End : RecognizeState()

}

@Composable
fun LoadingLottie() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val lottieProgress by animateLottieCompositionAsState( composition = composition, iterations = LottieConstants.IterateForever )

    LottieAnimation(
        composition = composition,
        progress = { lottieProgress },
    )
}