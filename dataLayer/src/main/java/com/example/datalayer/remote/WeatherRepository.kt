package com.example.datalayer.remote

import com.example.datalayer.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.json.JSONObject

class WeatherRepository {

    private val weatherRetrofit = RetrofitFactory.getRetrofitInstance().create(RestApiService::class.java)

    suspend fun getWeatherInfo(nx:String, ny:String, date:String, time:String) =
        flowOf(
            weatherRetrofit.getWeatherInfo(
                nx = nx,
                ny = ny,
                date = date,
                time = time
            )
        ).flowOn(Dispatchers.IO)



}