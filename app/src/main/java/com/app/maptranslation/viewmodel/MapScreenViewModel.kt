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
import com.example.domain.usecase.map.GetWeatherInfoUsecase
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
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

    fun drawBitmap(emojiText:String) : BitmapDescriptor? {
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

    fun getRegionsList(textField:String) : List<Regions> {
        return regionsList.filter {
            "${it.city} ${it.gu} ${it.dong}".contains(textField)
        }
    }

    fun checkRegionsData(applicationContext: Context) = viewModelScope.launch {
        regionsList = checkRegionsDbDataUsecase.invoke(applicationContext)
    }
}