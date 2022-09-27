package com.example.domain

import com.example.datalayer.local.datasource.RegionsRoomDataSource
import com.example.datalayer.remote.datasource.WeatherApiDataSource
import com.example.domain.repository.RegionsDBRepository
import com.example.domain.repository.RegionsDBRepositoryImpl
import com.example.domain.repository.WeatherRepository
import com.example.domain.repository.WeatherRepositoryImpl
import com.example.domain.usecase.GetDateTimeInfoUseCase
import com.example.domain.usecase.map.CheckRegionsDbDataUseCase
import com.example.domain.usecase.map.GetSearchedRegionsUseCase
import com.example.domain.usecase.map.GetWeatherInfoUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsecaseModule {

    @Provides
    fun provideCheckRegionsDbDataUsecase(
        regionsDBRepository: RegionsDBRepository
    ): CheckRegionsDbDataUseCase {
        return CheckRegionsDbDataUseCase(regionsDBRepository)
    }

    @Provides
    fun provideGetWeatherInfoUsecase(
        weatherRepository : WeatherRepository,
        dateTimeInfoUseCase: GetDateTimeInfoUseCase
    ): GetWeatherInfoUsecase {
        return GetWeatherInfoUsecase(weatherRepository, dateTimeInfoUseCase)
    }

    @Provides
    fun provideGetDateTimeInfoUseCase() : GetDateTimeInfoUseCase = GetDateTimeInfoUseCase()

    @Provides
    fun provideGetSearchedRegionsUseCase(
        regionsDBRepository: RegionsDBRepository
    ) : GetSearchedRegionsUseCase {
        return GetSearchedRegionsUseCase(regionsDBRepository)
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