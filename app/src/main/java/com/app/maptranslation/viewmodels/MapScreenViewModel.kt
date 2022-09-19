package com.app.maptranslation.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datalayer.local.ExcelReadHelper
import com.example.datalayer.remote.WeatherRepository
import kotlinx.coroutines.launch

class MapScreenViewModel : ViewModel() {

    fun getWeatherInfo() = viewModelScope.launch {
        val result = WeatherRepository().getWeatherInfo("58", "75", "20220919", "0600")
        Log.i("아현", "$result")
    }

    fun checkRegionsData() {

    }

    fun readRegionsExcelFile(_context:Context) {
        ExcelReadHelper.readExcel(_context, "regions.xls")
    }
}