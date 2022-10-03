package com.app.maptranslation.viewmodel

import android.content.Context
import android.graphics.*
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.ResultUiState
import com.example.domain.model.Regions
import com.example.domain.model.WeatherForecast
import com.example.domain.usecase.map.CheckRegionsDbDataUseCase
import com.example.domain.usecase.map.GetClosestRegionInDbUseCase
import com.example.domain.usecase.map.GetSearchedRegionsUseCase
import com.example.domain.usecase.map.GetWeatherInfoUsecase
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

                        Log.i("아현", "$it")
                    }
                    else -> {

                    }
                }
            }
    }

    fun drawBitmapIcon(emojiText:String) : BitmapDescriptor? {
        return emojiText.takeIf { it.isNotEmpty() }?.let {
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.apply {
                style = Paint.Style.FILL
                color = Color.WHITE
                textSize = 100f
                textAlign = Paint.Align.CENTER
            }

            val baseLine = -paint.ascent()
            val width = (paint.measureText(it) + 20f).toInt() // 사각형 너비
            val height = (baseLine + paint.descent() + 20f).toInt()
            val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888) // 사각형을 그릴 캔버스 너비

            val rect = Rect(0, 0, width, height)
            val rectF = RectF(rect)
            Canvas(image).apply {
                drawRoundRect(rectF, 20f, 20f, paint)
                drawText(it, width / 2f, (height+50) / 2f, paint)
            }
            BitmapDescriptorFactory.fromBitmap(image)
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