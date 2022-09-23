package com.app.maptranslation.composable

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.maptranslation.viewmodel.TranslateViewModel
import java.util.*

@Composable
fun TranslateScreen(
    viewModel: TranslateViewModel = viewModel()
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        LanguageDropDownMenu(viewModel)
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CustomTextField(viewModel)
            SttButton(viewModel)
        }

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 50.dp),
            onClick = { /*TODO*/ }) {
            Text(text = "번역")
        }

        LanguageDropDownMenu(viewModel)
        Text(modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(5)
            )
            .fillMaxWidth(0.8f)
            .height(200.dp),
            text = "")
    }
}

@Composable
private fun LanguageDropDownMenu(viewModel: TranslateViewModel) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectLanguage by remember { mutableStateOf("한국어") }

    Column {
        Button(onClick = { isExpanded = true }) {
            Row {
                Text(text = selectLanguage )
                Spacer(modifier = Modifier.width(50.dp))
                Icon(imageVector = if(isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null,)
            }
        }

        DropdownMenu(
            modifier = Modifier.height(300.dp),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            viewModel.addLanguageCode().forEach {
                DropdownMenuItem(onClick = {
                    selectLanguage = it.language
                    isExpanded = false
                }) {
                    Text(text = it.language)
                }
            }
        }
    }
}

@Composable
private fun CustomTextField(viewModel: TranslateViewModel) {

    OutlinedTextField(
        value = viewModel.sourceLanguage.value,
        onValueChange = { viewModel.setSourceLanguage(it) },
        modifier = Modifier
            .height(200.dp)
    )
}

@Composable
private fun SttButton(viewModel: TranslateViewModel) {
    val context = LocalContext.current

    val requestPermission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        if (!result) {
            Toast.makeText(context, "권한 허용 필요", Toast.LENGTH_SHORT).show()
        } else {
            startSTT(context, viewModel)
        }
    }

    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        onClick = {
            checkSttPermission(context, requestPermission, viewModel)
        }
    ) {
        Icon(
            imageVector = Icons.Default.Mic,
            contentDescription = null,
        )
    }
}

/** 권한 체크 */
private fun checkSttPermission(context: Context, requestPermission: ManagedActivityResultLauncher<String, Boolean>, viewModel: TranslateViewModel) {
    if(checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
        requestPermission.launch(android.Manifest.permission.RECORD_AUDIO)
    } else {
        startSTT(context, viewModel)
    }
}

private var speechRecognizer: SpeechRecognizer? = null

/***
 *  SpeechToText 설정 및 동작
 */
private fun startSTT(context: Context, viewModel: TranslateViewModel) {
    val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
    }

    speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
        setRecognitionListener(recognitionListener(context, viewModel))
        startListening(speechRecognizerIntent)
    }
}

/***
 *  SpeechToText 기능 세팅
 */
private fun recognitionListener(context: Context, viewModel: TranslateViewModel) = object : RecognitionListener {

    override fun onReadyForSpeech(params: Bundle?) = Toast.makeText(context, "음성인식 시작", Toast.LENGTH_SHORT).show()

    override fun onError(error: Int) {
        when(error) {
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> Toast.makeText(context, "퍼미션 없음", Toast.LENGTH_SHORT).show()
            else -> {}
        }
    }

    override fun onResults(results: Bundle) {
        Toast.makeText(context, "음성인식 종료", Toast.LENGTH_SHORT).show()
        viewModel.setSourceLanguage(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)!![0])
    }

    override fun onPartialResults(p0: Bundle?) {}
    override fun onEvent(p0: Int, p1: Bundle?) {}
    override fun onBeginningOfSpeech() {}
    override fun onRmsChanged(p0: Float) {}
    override fun onBufferReceived(p0: ByteArray?) {}
    override fun onEndOfSpeech() {}
}

@Preview
@Composable
fun DefaultPreview() {
    TranslateScreen()
}