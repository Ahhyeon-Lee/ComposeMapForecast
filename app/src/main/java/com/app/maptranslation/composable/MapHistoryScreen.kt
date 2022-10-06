package com.app.maptranslation.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.maptranslation.R
import com.app.maptranslation.viewmodel.MapHistoryViewModel
import com.example.domain.model.WeatherForecast
import com.example.domain.util.DateTimeHelper

@Composable
fun MapHistoryScreen(
    navController: NavController,
    mapHistoryViewModel: MapHistoryViewModel
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
                val weatherHistoryList = mapHistoryViewModel.weatherHistoryList
                itemsIndexed(weatherHistoryList) { index, weather ->
                    if (index == 0) {
                        DateItem(navController, weather.date)
                    }

                    HistoryItem(weather)

                    if (index < weatherHistoryList.size -1 && weatherHistoryList[index].date != weatherHistoryList[index+1].date) {
                        DateItem(navController, weatherHistoryList[index+1].date)
                    }
                }
            }
        }
    }
}

@Composable
fun DateItem(
    navController: NavController,
    date: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = DateTimeHelper.parseStringToDateFormat(date, "yyyyMMdd", "yyyy년 MM월 dd일"),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
        )
        IconButton(
            onClick = {
                navController.navigate("WeatherMapScreen/$date")
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
fun HistoryItem(weather : WeatherForecast) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
        elevation = 5.dp
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Text(
                text = DateTimeHelper.parseStringToDateFormat(weather.time, "HHmm", "HH:mm"),
                fontSize = 12.sp
            )
            Text(
                text = "${weather.city} ${weather.gu} ${weather.dong} : ${stringResource(weather.weatherMark)}",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}