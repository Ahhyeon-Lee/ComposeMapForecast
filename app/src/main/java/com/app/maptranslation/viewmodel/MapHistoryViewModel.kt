package com.app.maptranslation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.domain.model.WeatherForecast
import com.example.domain.usecase.map.GetWeatherHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapHistoryViewModel @Inject constructor(
    private val getWeatherHistoryUseCase: GetWeatherHistoryUseCase
) : ViewModel() {

    var weatherHistoryList : List<WeatherForecast> by mutableStateOf(listOf())
        private set

    fun getAllWeatherHistoryList() = CoroutineScope(Dispatchers.IO).launch {
        weatherHistoryList = getWeatherHistoryUseCase.invoke()
    }

}