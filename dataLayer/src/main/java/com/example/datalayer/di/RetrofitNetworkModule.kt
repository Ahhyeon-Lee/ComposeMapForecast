package com.example.datalayer.di

import com.example.datalayer.remote.service.TranslateApiService
import com.example.datalayer.remote.service.WeatherApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitNetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Weather

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Translation

    @Weather
    @Provides
    fun provideWeatherApiBaseUrl() = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"

    @Translation
    @Provides
    fun provideTranslationApiBaseUrl() = "https://openapi.naver.com/"

    @Provides
    fun provideGson() : Gson = GsonBuilder().setLenient().create()

    @Provides
    fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ) : OkHttpClient {
        return OkHttpClient().newBuilder()
            .addNetworkInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ) : Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
    }

    @Weather
    @Provides
    fun provideWeatherRetrofit(
        retrofitBuilder: Retrofit.Builder,
        @Weather BASE_URL: String
    ): Retrofit {
        return retrofitBuilder
            .baseUrl(BASE_URL)
            .build()
    }

    @Translation
    @Provides
    fun provideTranslationRetrofit(
        retrofitBuilder: Retrofit.Builder,
        @Translation BASE_URL: String
    ): Retrofit {
        return retrofitBuilder
            .baseUrl(BASE_URL)
            .build()
    }

    @Weather
    @Provides
    fun provideWeatherApiService(
        @Weather retrofit: Retrofit
    ) : WeatherApiService = retrofit.create(WeatherApiService::class.java)

    @Translation
    @Provides
    fun provideTranslateApiService(
        @Translation retrofit: Retrofit
    ) : TranslateApiService = retrofit.create(TranslateApiService::class.java)
}