package com.app.maptranslation.viewmodel

import android.content.Context
import android.graphics.*
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.ResultUiState
import com.example.domain.model.Regions
import com.example.domain.model.WeatherForecast
import com.example.domain.usecase.map.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val checkRegionsDbDataUsecase: CheckRegionsDbDataUseCase,
    private val getSearchedRegionsUseCase: GetSearchedRegionsUseCase,
    private val getClosestRegionInDbUseCase: GetClosestRegionInDbUseCase,
    private val insertWeatherHistoryDataInDbUseCase: InsertWeatherHistoryDataInDbUseCase,
    private val getWeatherInfoUsecase : GetWeatherInfoUsecase
) : ViewModel() {

    var dbLoading = mutableStateOf(true)
        private set

    var weatherState by mutableStateOf(WeatherForecast())
        private set

    var regionsList : List<Regions> by mutableStateOf(listOf())
        private set

    fun getWeatherInfo(regionData:Regions) = viewModelScope.launch {
        getWeatherInfoUsecase.invoke(regionData)
            .collectLatest {
                when(it) {
                    is ResultUiState.Success -> {
                        weatherState = it.data
                        insertWeatherHistoryDataInDbUseCase.invoke(it.data)
                        Log.i("아현", "$it")
                    }
                    else -> {

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
        Log.i("아현 location", "$closestRegion")
    }

    fun checkDbAndInsertData(applicationContext: Context) {
        dbLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            checkRegionsDbDataUsecase.invoke(applicationContext)
            dbLoading.value = false
        }
    }


}