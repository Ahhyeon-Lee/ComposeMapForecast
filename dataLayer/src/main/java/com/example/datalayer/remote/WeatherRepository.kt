package com.example.datalayer.remote

import com.example.datalayer.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.json.JSONObject

class WeatherRepository {

    private val weatherRetrofit = RetrofitFactory.getRetrofitInstance().create(RestApiService::class.java)

    fun getWeatheInfo(nx:String, ny:String, date:String, time:String, coroutineScope: CoroutineScope) = flowOf(
            weatherRetrofit.getWeatherInfo(nx, ny, date, time)
        ).flowOn(Dispatchers.IO)


}