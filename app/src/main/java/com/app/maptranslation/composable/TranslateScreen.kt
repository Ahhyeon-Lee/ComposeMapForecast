package com.app.maptranslation.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.maptranslation.viewmodel.SttRecognizerViewModel
import com.app.maptranslation.viewmodel.TranslateViewModel

@Composable
fun TranslateScreen(
    viewModel: TranslateViewModel,
    sttViewModel: SttRecognizerViewModel = viewModel()
) {
    val applicationContext = LocalContext.current.applicationContext
    viewModel.getLanguageCode(applicationContext)
    viewModel.getLanguageTarget(applicationContext)

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
            CustomTextField(sttViewModel)
            SttRecognizer(sttViewModel)
        }

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 50.dp),
            onClick = { /*TODO*/ }) {
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
            .height(200.dp),
            text = "")
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

@Composable
private fun CustomTextField(sttViewModel: SttRecognizerViewModel) {
    OutlinedTextField(
        value = sttViewModel.sourceLanguage.value,
        onValueChange = { sttViewModel.setSourceLanguage(it) },
        modifier = Modifier
            .height(200.dp)
    )
}

@Preview
@Composable
fun DefaultPreview() {
    //TranslateScreen()
}