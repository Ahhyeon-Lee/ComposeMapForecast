package com.example.datalayer

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import tech.thdev.network.flowcalladapterfactory.FlowCallAdapterFactory

object RetrofitFactory {

    private var retrofit : Retrofit? = null

    fun getRetrofitInstance() : Retrofit {
        return retrofit ?: synchronized(this) {
            newRetrofitInstance().also {
                retrofit = it
            }
        }
    }

    val client = OkHttpClient.Builder().build()

    private fun newRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/")
            .client(client)
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }


}