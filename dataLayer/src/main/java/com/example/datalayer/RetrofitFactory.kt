package com.example.datalayer

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    private var retrofit : Retrofit? = null

    fun getRetrofitInstance() : Retrofit {
        return retrofit ?: synchronized(this) {
            newRetrofitInstance().also {
                retrofit = it
            }
        }
    }

    val gson = GsonBuilder().setLenient().create()

    val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient = OkHttpClient().newBuilder()
        .addNetworkInterceptor(interceptor)
        .build()

    private fun newRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }


}