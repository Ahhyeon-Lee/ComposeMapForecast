package com.app.maptranslation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datalayer.remote.WeatherRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MapScreenViewModel : ViewModel() {

    fun getWeatherInfo() = viewModelScope.launch {
        val result = WeatherRepository().getWeatheInfo("58", "75", "20220918", "0600")
        result.onEach {
            Log.i("아현", "$it")
        }.launchIn(viewModelScope)
    }
}