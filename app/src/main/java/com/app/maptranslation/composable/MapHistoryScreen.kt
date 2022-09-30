package com.app.maptranslation.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.maptranslation.R

@Composable
fun MapHistoryScreen(
    navController: NavController?=null
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.map_history),
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(50.dp),
                fontWeight = FontWeight.ExtraBold
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                items(listOf("1", "",  "2", "", "3")) { item ->
                    if (item.isEmpty()) {
                        DateItem(navController!!)
                    } else {
                        HistoryItem(item)
                    }
                }
            }
        }
    }
}

@Composable
fun DateItem(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "2022-09-30",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
        )
        IconButton(
            onClick = {
                navController.navigate(WEATHER_MAP_SCREEN)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
                contentDescription = null
            )
        }
    }
}

@Composable
fun HistoryItem(item : String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = 5.dp
    ) {
        Text(
            text = item,
            modifier = Modifier.padding(20.dp)
        )
    }

}

@Preview
@Composable
fun MapHistoryScreenPreview() {
    MapHistoryScreen()
}