package com.app.maptranslation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datalayer.remote.repository.WeatherRepository
import com.example.domain.model.Regions
import com.example.domain.repository.RegionsDBRepository
import com.example.domain.usecase.map.CheckRegionsDbDataUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val checkRegionsDbDataUsecase: CheckRegionsDbDataUsecase
) : ViewModel() {

    private var regionsList: List<Regions> = listOf()

    fun getWeatherInfo() = viewModelScope.launch {
        val result = WeatherRepository().getWeatherInfo("58", "75", "20220919", "0600")
        Log.i("아현", "$result")
    }

    fun getRegionsList(textField:String) : List<Regions> {
        return regionsList.filter {
            "${it.city} ${it.gu} ${it.dong}".contains(textField)
        }
    }

    fun checkRegionsData(applicationContext: Context) = viewModelScope.launch {
        checkRegionsDbDataUsecase.invoke(applicationContext) {
            regionsList = it
        }
    }
}