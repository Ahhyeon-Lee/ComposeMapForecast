package com.example.datalayer.repository

import android.util.Log
import com.example.datalayer.remote.datasource.WeatherApiDataSource
import com.example.domain.model.Regions
import com.example.domain.model.WeatherForecast
import com.example.domain.repository.map.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiDataSource: WeatherApiDataSource
) : WeatherRepository {

    override suspend fun getWeatherInfo(
        regionData:Regions, date:String, time:String
    ): Flow<WeatherForecast> =
        flow {
            emit(weatherApiDataSource.getWeatherInfo(nx = regionData.nx, ny = regionData.ny, date = date, time = time))
        }
        .flowOn(Dispatchers.IO)
        .transform {
            val ptyData = it.response?.body?.items?.item?.firstOrNull { item -> item.category == "PTY" }
            val skyData = it.response?.body?.items?.item?.firstOrNull { item -> item.category == "SKY" }

            val map = mapOf(
                (ptyData?.category ?: "PTY") to (ptyData?.fcstValue),
                (skyData?.category ?: "SKY") to (skyData?.fcstValue),
            )

            val weather = if (!map["PTY"].isNullOrEmpty() && !map["SKY"].isNullOrEmpty()) {
                when {
                    map["PTY"] == "0" -> with(map["SKY"] ?: "-1") {
                        when(this) {
                            "1" -> "0" // 맑음
                            "-1" -> "-1" // 오류
                            else -> this // 구름많음, 흐림
                        }
                    }
                    else -> with(map["PTY"] ?: "-1") {
                        when(this) {
                            "1","2","5","6" -> "1" // 비
                            "3","7" -> "2" // 눈
                            "-1" -> "-1" // 오류
                            else -> this
                        }
                    }
                }
            } else "-1"

            emit(
                WeatherForecast(
                    regionData.city,
                    regionData.gu,
                    regionData.dong,
                    regionData.nx,
                    regionData.ny,
                    regionData.longtitude,
                    regionData.latitude,
                    weather,
                    date,
                    ptyData?.fcstTime ?: time
                )
            )
        }
        .catch { e->
//            collect.invoke(ResultNetworkState.Error(e))
            Log.i("아현", "$e")
        }


}