package com.example.datalayer.remote.repository

import com.example.datalayer.RetrofitFactory
import com.example.datalayer.remote.service.RestApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

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