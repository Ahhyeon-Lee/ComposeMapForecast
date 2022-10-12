package com.example.datalayer.di

import com.example.datalayer.local.database.AppDatabase
import com.example.datalayer.local.datasource.RegionsRoomDataSource
import com.example.datalayer.local.datasource.TranslateRoomDataSource
import com.example.datalayer.remote.datasource.TranslateApiDataSource
import com.example.datalayer.remote.datasource.WeatherApiDataSource
import com.example.datalayer.remote.service.TranslateApiService
import com.example.datalayer.remote.service.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideRegionsRoomDataSource(
        database: AppDatabase
    ): RegionsRoomDataSource {
        return RegionsRoomDataSource(database.regionDao())
    }

    @Singleton
    @Provides
    fun provideWeatherApiDataSource(
        @RetrofitNetworkModule.Weather apiService: WeatherApiService
    ): WeatherApiDataSource {
        return WeatherApiDataSource(apiService)
    }

    @Singleton
    @Provides
    fun provideTranslateRoomDataSource(
        database: AppDatabase
    ): TranslateRoomDataSource = TranslateRoomDataSource(database.translateDao())

    @Singleton
    @Provides
    fun provideTranslateApiDataSource(
        @RetrofitNetworkModule.Translation service: TranslateApiService
    ): TranslateApiDataSource = TranslateApiDataSource(service)
}