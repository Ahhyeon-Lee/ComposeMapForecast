package com.app.maptranslation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ResultUiState
import com.example.domain.model.Regions
import com.example.domain.model.WeatherForecast
import com.example.domain.usecase.map.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val checkRegionsDbDataUseCase: CheckRegionsDbDataUseCase,
    private val getSearchedRegionsUseCase: GetSearchedRegionsUseCase,
    private val getClosestRegionInDbUseCase: GetClosestRegionInDbUseCase,
    private val getWeatherInfoUseCase : GetWeatherInfoUseCase,
    private val insertWeatherHistoryDataInDbUseCase: InsertWeatherHistoryDataInDbUseCase,
    private val getADayWeatherHistoryUseCase: GetADayWeatherHistoryUseCase,
) : ViewModel() {

    var dbLoading by mutableStateOf(true)
        private set

    var toast by mutableStateOf(Pair(false, ""))
        private set

    var weatherState by mutableStateOf(WeatherForecast())
        private set

    var weatherApiLoading by mutableStateOf(false)
        private set

    var regionsList : List<Regions> by mutableStateOf(listOf())
        private set

    var weatherHistroyList : List<WeatherForecast> by mutableStateOf(listOf())
        private set

    fun getWeatherInfo(regionData:Regions) = viewModelScope.launch {
        getWeatherInfoUseCase.invoke(regionData)
            .onStart {
                weatherApiLoading = true
            }
            .collectLatest {
                weatherApiLoading = false
                when(it) {
                    is ResultUiState.Success -> {
                        weatherState = it.data
                        insertWeatherHistoryDataInDbUseCase.invoke(it.data)
                        Log.i("getWeatherInfo", "날씨 검색 : $it")
                    }
                    is ResultUiState.ErrorWithData -> {
                        weatherState = it.data
                        toast = Pair(true, "날씨 검색 오류가 발생했습니다.")
                    }
                }
            }
    }

    fun getSearchingRegionsList(textField:String) = viewModelScope.launch {
        getSearchedRegionsUseCase.invoke("%$textField%")
            .collectLatest {
                regionsList = it
            }
    }

    fun getCurrentLocWeatherInfo(longtitude:Double, latitude:Double) = viewModelScope.launch {
        val closestRegion = getClosestRegionInDbUseCase.invoke(longtitude, latitude)
        getWeatherInfo(closestRegion)
        Log.i("getCurrentLocWeatherInfo", "현재 위치와 가장 가까운 지역 : $closestRegion")
    }

    fun checkDbAndInsertData(applicationContext: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            checkRegionsDbDataUseCase.invoke(applicationContext)
            dbLoading = false
        }
    }

    fun getADayWeatherHistoryList(date:String) = CoroutineScope(Dispatchers.IO).launch {
        weatherHistroyList = getADayWeatherHistoryUseCase.invoke(date)
    }

    fun resetToastData() {
        toast = Pair(false, "")
    }
}