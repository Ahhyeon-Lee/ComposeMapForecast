package com.app.maptranslation.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.maptranslation.R
import com.app.maptranslation.viewmodel.TranslateViewModel
import com.example.domain.model.TranslateHistoryData

@Composable
fun HistoryScreen(viewModel: TranslateViewModel = hiltViewModel()) {
    viewModel.getTranslateHistory()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.padding(20.dp, 50.dp),
                text = stringResource(id = R.string.map_history),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )

            LazyColumn {
                items(viewModel.historyList.value) { data ->
                    HistoryCard(data)
                }
            }
        }
    }

}

@Composable
fun HistoryCard(translateData: TranslateHistoryData) {
    Card(
        Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        elevation = 10.dp
    ) {
        TranslateRow(translateData)
    }
}


@Composable
fun TranslateRow(data: TranslateHistoryData) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Row {
            Row(modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
            ) {
                Text(text = data.sourceLanguage)
                Text(text = "(${data.sourceCode})")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "->")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = data.targetLanguage)
                Text(text = "(${data.targetCode})")
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null
                )
            }
        }
        if (expanded) {
            ExpandHistory(data)
        }
    }
}

@Composable
fun ExpandHistory(data: TranslateHistoryData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)
    ) {
        Text(
            text = data.sourceLanguage,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(10.dp)
        )
        Text(
            text = data.sourceText,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(5)
                )
                .padding(10.dp)
                .height(50.dp)
                .fillMaxWidth()
        )
        Icon(
            imageVector = Icons.Default.ArrowDownward,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
            )
        Text(
            text = data.targetLanguage,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(10.dp)
        )
        Text(
            text = data.targetText,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(5)
                )
                .padding(10.dp)
                .height(50.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun DefaultPreview() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}