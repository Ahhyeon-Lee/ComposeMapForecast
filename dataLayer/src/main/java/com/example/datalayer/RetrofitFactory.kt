package com.example.datalayer

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    private var retrofit : Retrofit? = null
    const val weatherApiBaseUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"

    private val gson = GsonBuilder().setLenient().create()

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient().newBuilder()
        .addNetworkInterceptor(interceptor)
        .build()

    fun getRetrofitInstance() : Retrofit {
        return retrofit ?: synchronized(this) {
            newRetrofitInstance().also {
                retrofit = it
            }
        }
    }

    private fun newRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(weatherApiBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }


}