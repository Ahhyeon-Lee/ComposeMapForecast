package com.app.maptranslation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.ResultUiState
import com.example.domain.model.Regions
import com.example.domain.model.WeatherForecast
import com.example.domain.usecase.map.CheckRegionsDbDataUseCase
import com.example.domain.usecase.map.GetWeatherInfoUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val checkRegionsDbDataUsecase: CheckRegionsDbDataUseCase,
    private val getWeatherInfoUsecase : GetWeatherInfoUsecase
) : ViewModel() {

    private var regionsList: List<Regions> = listOf()

    var weatherState by mutableStateOf(WeatherForecast())
        private set

    fun getWeatherInfo(nx:String, ny:String, longtitude:String, latitude:String) = viewModelScope.launch {
        getWeatherInfoUsecase.invoke(nx, ny, getDateTime()[0], getDateTime()[1], longtitude, latitude)
            .collectLatest {
                when(it) {
                    is ResultUiState.Success -> {
                        weatherState = it.data
                        Log.i("아현", "$it")
                    }
                    else -> {

                    }
                }
            }
    }

    fun getRegionsList(textField:String) : List<Regions> {
        return regionsList.filter {
            "${it.city} ${it.gu} ${it.dong}".contains(textField)
        }
    }

    fun checkRegionsData(applicationContext: Context) = viewModelScope.launch {
        regionsList = checkRegionsDbDataUsecase.invoke(applicationContext)
    }

    private fun getDateTime() : List<String> {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HHmm", Locale.getDefault())
        val date = Date()
        return listOf<String>(dateFormat.format(date), "1000")
    }
}