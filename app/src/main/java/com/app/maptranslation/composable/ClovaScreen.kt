package com.app.maptranslation.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.maptranslation.util.ClovaRecognizer
import com.app.maptranslation.viewmodel.ClovaRecognizerViewModel


@Composable
fun ClovaTest(
    viewModel: ClovaRecognizerViewModel = viewModel()
) {
    val context = LocalContext.current

    val clovaRecognizer = ClovaRecognizer()

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_CREATE -> clovaRecognizer.init(context, "mvtemyolk2", viewModel)
                Lifecycle.Event.ON_START -> clovaRecognizer.getSpeechRecognizer()?.initialize()
                Lifecycle.Event.ON_RESUME -> {}
                Lifecycle.Event.ON_PAUSE -> {}
                Lifecycle.Event.ON_STOP -> clovaRecognizer.getSpeechRecognizer()?.release()
                Lifecycle.Event.ON_DESTROY -> {}
                else -> throw IllegalStateException()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    var buttonText by remember { mutableStateOf("START") }
    var isExpanded by remember { mutableStateOf(false) }
    var selectLanguage by remember { mutableStateOf("한국어") }

    Surface(
        modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Button(onClick = { isExpanded = true }) {
                    Row {
                        Text(text = selectLanguage)
                        Spacer(modifier = Modifier.width(50.dp))
                        Icon(
                            imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = null,
                        )
                    }
                }
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    viewModel.languageList.forEach {
                        DropdownMenuItem(onClick = {
                            selectLanguage = it
                            isExpanded = false
                        }) {
                            Text(text = it)
                        }
                    }
                }
            }

            Button(onClick = {
                clovaRecognizer.getSpeechRecognizer()?.let {
                    if(!it.isRunning){
                        buttonText = "STOP"
                        viewModel.clearSourceLanguage()
                        clovaRecognizer.recognize(selectLanguage)
                    }
                    else {
                        buttonText = "START"
                        it.stop()
                    }
                }
            }) {
                Text(text = buttonText)
            }

            Spacer(Modifier.height(20.dp))

            Text(fontWeight = FontWeight.Bold,
                text = "실시간 결과")
            Text(text = viewModel.sourceLanguage.value)

            Spacer(Modifier.height(30.dp))

            Text(fontWeight = FontWeight.Bold,
                text = "유사 결과")
            Text(text = viewModel.resultText.value)
        }
    }

}