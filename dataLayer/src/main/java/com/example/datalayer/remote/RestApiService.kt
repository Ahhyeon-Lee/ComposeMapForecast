package com.example.datalayer.remote

import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApiService {
    val weather_api_key: String
        get() = "HhI0V8a2pV09ssFf6Ui%2Bfn9LCPwa05YgfDHax8PHxs8T0nhmhIir%2FnuoEptT0R0xBJv%2FX5rF%2BMrOyEYzuKej6w%3D%3D"

    @GET("getUltraSrtNcst?pageNo=1&numOfRows=1000&dataType=JSON")
    fun getWeatherInfo(
        @Query("nx") nx:String,
        @Query("ny") ny:String,
        @Query("base_date") date:String,
        @Query("base_time") time:String,
        @Query("serviceKey") apiKey: String = "HhI0V8a2pV09ssFf6Ui+fn9LCPwa05YgfDHax8PHxs8T0nhmhIir/nuoEptT0R0xBJv/X5rF+MrOyEYzuKej6w=="
    ) : Flow<Response>
}
