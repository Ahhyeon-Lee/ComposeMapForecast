package com.app.maptranslation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datalayer.local.repository.RegionsDBRepository
import com.example.datalayer.remote.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val regionsDBRepository: RegionsDBRepository
)  : ViewModel() {

    fun getWeatherInfo() = viewModelScope.launch {
        val result = WeatherRepository().getWeatherInfo("58", "75", "20220919", "0600")
        Log.i("아현", "$result")
    }

    fun checkRegionsData(applicationContext: Context) {
        regionsDBRepository.checkRegionsDbData(applicationContext)
    }
}