package com.app.maptranslation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.domain.model.WeatherForecast
import com.example.domain.usecase.map.GetWeatherHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapHistoryViewModel @Inject constructor(
    private val ioScope: CoroutineScope,
    private val getWeatherHistoryUseCase: GetWeatherHistoryUseCase
) : ViewModel() {

    var weatherHistoryList : List<WeatherForecast> by mutableStateOf(listOf())
        private set

    fun getAllWeatherHistoryList() = ioScope.launch {
        weatherHistoryList = getWeatherHistoryUseCase.invoke()
    }

}