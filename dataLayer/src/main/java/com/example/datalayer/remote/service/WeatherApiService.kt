package com.example.datalayer.remote.service

import com.example.datalayer.remote.model.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    companion object {
        const val weather_api_key= "HhI0V8a2pV09ssFf6Ui+fn9LCPwa05YgfDHax8PHxs8T0nhmhIir/nuoEptT0R0xBJv/X5rF+MrOyEYzuKej6w=="
    }

    @GET("getUltraSrtFcst")
    suspend fun getWeatherInfo(
        @Query("serviceKey") serviceKey:String = weather_api_key,
        @Query("numOfRows") numOfRows:String = "1000",
        @Query("pageNo") pageNo:String = "1",
        @Query("dataType") dataType:String = "JSON",
        @Query("base_date") date:String,
        @Query("base_time") time:String,
        @Query("nx") nx:String,
        @Query("ny") ny:String
    ) : WeatherForecastResponse
}
