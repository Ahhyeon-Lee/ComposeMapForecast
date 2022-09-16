package com.app.maptranslation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datalayer.remote.RestApiService
import com.example.datalayer.remote.WeatherRepository
import kotlinx.coroutines.launch

class MapScreenViewModel : ViewModel() {

    fun getWeatherInfo() {
        viewModelScope.launch {
            val result = WeatherRepository().getWeatheInfo("58", "75", "20220916", "0600", viewModelScope)
            Log.i("아현", "결과 : $result")
        }
    }
}