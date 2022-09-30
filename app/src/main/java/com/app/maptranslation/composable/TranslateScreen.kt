package com.app.maptranslation.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.maptranslation.viewmodel.SttRecognizerViewModel
import com.app.maptranslation.viewmodel.TranslateViewModel

@Composable
fun TranslateScreen(
    viewModel: TranslateViewModel = hiltViewModel(),
    sttViewModel: SttRecognizerViewModel = viewModel()
) {
    val applicationContext = LocalContext.current.applicationContext
    viewModel.getLanguageCode(applicationContext)

    val focusManager = LocalFocusManager.current
    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .addFocusCleaner(focusManager)
    ) {
        LanguageDropDownMenu(viewModel)
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = sttViewModel.sttText.value,
                onValueChange = {
                    sttViewModel.setSttText(it)
                    viewModel.setSourceText(it)
                },
                modifier = Modifier
                    .height(200.dp)
                    .focusRequester(focusRequester = focusRequester),
                label = { Text(text = "텍스트 입력")}
            )
            SttRecognizer(sttViewModel, viewModel.selectSource.value.code)
        }

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 50.dp),
            onClick = {
                viewModel.translateText(sttViewModel.sttText.value)
                focusManager.clearFocus()
            }) {
            Text(text = "번역")
        }

        DropdownMenuLanguageTarget(viewModel)
        Text(modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(5)
            )
            .fillMaxWidth(0.8f)
            .height(200.dp)
            .padding(10.dp),
            text = viewModel.targetText.value)
    }
}

@Composable
private fun LanguageDropDownMenu(viewModel: TranslateViewModel) {
    var isExpanded by remember { mutableStateOf(false) }

    Column {
        Button(onClick = { isExpanded = true }) {
            Row {
                Text(text = viewModel.selectSource.value.language )
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
            viewModel.languageCodeList.value.forEach {
                DropdownMenuItem(onClick = {
                    viewModel.setSelectSource(it)
                    isExpanded = false
                }) {
                    Text(text = it.language)
                }
            }
        }
    }
}

@Composable
private fun DropdownMenuLanguageTarget(viewModel: TranslateViewModel) {
    var isExpanded by remember { mutableStateOf(false) }

    Column {
        Button(onClick = { isExpanded = true }) {
            Row {
                Text(text = viewModel.selectTarget.value.language )
                Spacer(modifier = Modifier.width(50.dp))
                Icon(imageVector = if(isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null,)
            }
        }

        DropdownMenu(
            modifier = Modifier.heightIn(0.dp, 300.dp),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            viewModel.languageTargetList.value.forEach {
                DropdownMenuItem(onClick = {
                    viewModel.setSelectTarget(it)
                    isExpanded = false
                }) {
                    Text(text = it.language)
                }
            }
        }
    }
}

fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}