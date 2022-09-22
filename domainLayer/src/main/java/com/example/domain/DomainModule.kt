package com.example.domain

import com.example.datalayer.local.datasource.RegionsRoomDataSource
import com.example.datalayer.remote.datasource.WeatherApiDataSource
import com.example.domain.repository.RegionsDBRepository
import com.example.domain.repository.RegionsDBRepositoryImpl
import com.example.domain.repository.WeatherRepository
import com.example.domain.repository.WeatherRepositoryImpl
import com.example.domain.usecase.map.CheckRegionsDbDataUsecase
import com.example.domain.usecase.map.GetWeatherInfoUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsecaseModule {

    @Singleton
    @Provides
    fun provideCheckRegionsDbDataUsecase(
        regionsDBRepository: RegionsDBRepository
    ): CheckRegionsDbDataUsecase {
        return CheckRegionsDbDataUsecase(regionsDBRepository)
    }

    @Singleton
    @Provides
    fun provideGetWeatherInfoUsecaseUsecase(
        weatherRepository : WeatherRepository
    ): GetWeatherInfoUsecase {
        return GetWeatherInfoUsecase(weatherRepository)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRegionsDBRepository(
        localDataSource: RegionsRoomDataSource
    ): RegionsDBRepository {
        return RegionsDBRepositoryImpl(localDataSource)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(
        weatherApiDataSource: WeatherApiDataSource
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherApiDataSource)
    }
}