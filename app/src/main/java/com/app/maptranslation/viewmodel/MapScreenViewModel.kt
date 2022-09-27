package com.app.maptranslation.viewmodel

import android.content.Context
import android.graphics.*
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datalayer.local.model.RegionRowEntity
import com.example.domain.ResultUiState
import com.example.domain.model.Regions
import com.example.domain.model.WeatherForecast
import com.example.domain.usecase.map.CheckRegionsDbDataUseCase
import com.example.domain.usecase.map.GetSearchedRegionsUseCase
import com.example.domain.usecase.map.GetWeatherInfoUsecase
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val checkRegionsDbDataUsecase: CheckRegionsDbDataUseCase,
    private val getSearchedRegionsUseCase: GetSearchedRegionsUseCase,
    private val getWeatherInfoUsecase : GetWeatherInfoUsecase
) : ViewModel() {

    private val _dbLoading = MutableStateFlow(true)
    private var _regionsList = MutableStateFlow<List<Regions>>(listOf())
    val textFieldUiState = combine(_dbLoading, _regionsList) { dbLoading, regionsList ->
        Pair(dbLoading, regionsList)
    }

    var weatherState by mutableStateOf(WeatherForecast())
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
                textSize = 100f
                textAlign = Paint.Align.CENTER
                color = Color.WHITE
            }
            val strokePaint = Paint()
            strokePaint.apply {
                style = Paint.Style.STROKE
                color = Color.BLACK
                strokeWidth = 10f
            }
            val baseLine = -paint.ascent()
            val width = (paint.measureText(it) + 20f).toInt()
            val height = (baseLine + paint.descent() + 20f).toInt()
            val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val rect = Rect(0, 0, width, height)
            val rectF = RectF(rect)
            Canvas(image).apply {
                drawRoundRect(rectF, 10f, 10f, paint)
                drawRoundRect(rectF, 10f, 10f, strokePaint)
                drawText(it, width / 2f, (height+50) / 2f, paint)
            }
            BitmapDescriptorFactory.fromBitmap(image)
        }
    }

    fun getSearchingRegionsList(textField:String) = viewModelScope.launch {
        getSearchedRegionsUseCase.invoke("%$textField%")
            .collectLatest {
                _regionsList.value = it
            }
    }

    fun locationTest(longtitude:Double, latitude:Double) = viewModelScope.launch {
        val region = getSearchedRegionsUseCase.test(longtitude, latitude)
        getWeatherInfo(region)
        Log.i("아현 location", "$region")
    }

    fun checkDbAndInsertData(applicationContext: Context) {
        _dbLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            checkRegionsDbDataUsecase.invoke(applicationContext)
            _dbLoading.value = false
        }
    }
}