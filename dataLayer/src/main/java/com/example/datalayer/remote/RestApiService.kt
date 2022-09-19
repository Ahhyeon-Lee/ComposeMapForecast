package com.example.datalayer.remote

import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApiService {

    companion object {
        const val weather_api_key= "HhI0V8a2pV09ssFf6Ui+fn9LCPwa05YgfDHax8PHxs8T0nhmhIir/nuoEptT0R0xBJv/X5rF+MrOyEYzuKej6w=="
    }

    @GET("getUltraSrtNcst")
    suspend fun getWeatherInfo(
        @Query("nx") nx:String,
        @Query("ny") ny:String,
        @Query("base_date") date:String,
        @Query("base_time") time:String,
        @Query("pageNo") pageNo:String = "1",
        @Query("numOfRows") numOfRows:String = "1000",
        @Query("dataType") dataType:String = "JSON",
        @Query("serviceKey") serviceKey:String = weather_api_key
    ) : Weather
}
