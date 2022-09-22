package com.example.domain.repository

import android.util.Log
import com.example.datalayer.ResultNetworkState
import com.example.datalayer.remote.datasource.WeatherApiDataSource
import com.example.datalayer.remote.model.NetworkWeatherForecastData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class WeatherRepositoryImpl(
    private val weatherApiDataSource: WeatherApiDataSource
) : WeatherRepository {

    override suspend fun getWeatherInfo(
        nx:String, ny:String, date:String, time:String
    ): Flow<NetworkWeatherForecastData> =
        flowOf(
            weatherApiDataSource.getWeatherInfo(nx = nx, ny = ny, date = date, time = time)
        )
        .flowOn(Dispatchers.IO)
        .catch { e->
//            collect.invoke(ResultNetworkState.Error(e))
            Log.i("아현", "$e")
        }


}