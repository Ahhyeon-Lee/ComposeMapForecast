package com.app.maptranslation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.domain.model.WeatherForecast
import com.example.domain.usecase.map.GetADayWeatherHistoryUseCase
import com.example.domain.usecase.map.GetWeatherHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapHistoryViewModel @Inject constructor(
    private val getWeatherHistoryUseCase: GetWeatherHistoryUseCase,
    private val getADayWeatherHistoryUseCase: GetADayWeatherHistoryUseCase
) : ViewModel() {

    var weatherHistoryList : List<WeatherForecast> by mutableStateOf(listOf())
        private set

    fun getAllWeatherHistoryList() = CoroutineScope(Dispatchers.IO).launch {
        weatherHistoryList = getWeatherHistoryUseCase.invoke()
    }

    fun getADayWeatherHistoryList(date:String) = CoroutineScope(Dispatchers.IO).launch {
        getADayWeatherHistoryUseCase.invoke(date)
    }
}