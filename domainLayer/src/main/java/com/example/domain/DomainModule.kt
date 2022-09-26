package com.example.domain

import com.example.datalayer.local.datasource.RegionsRoomDataSource
import com.example.datalayer.local.datasource.TranslateRoomDataSource
import com.example.datalayer.remote.datasource.WeatherApiDataSource
import com.example.domain.repository.RegionsDBRepository
import com.example.domain.repository.RegionsDBRepositoryImpl
import com.example.domain.repository.WeatherRepository
import com.example.domain.repository.WeatherRepositoryImpl
import com.example.domain.repository.translate.LanguageRepository
import com.example.domain.repository.translate.LanguageRepositoryImpl
import com.example.domain.usecase.GetDateTimeInfoUseCase
import com.example.domain.usecase.map.CheckRegionsDbDataUseCase
import com.example.domain.usecase.map.GetWeatherInfoUsecase
import com.example.domain.usecase.translate.GetLanguageCodeUseCase
import com.example.domain.usecase.translate.GetLanguageTargetUseCase
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
    fun provideGetLanguageCodeUseCase(
        repository : LanguageRepository
    ): GetLanguageCodeUseCase = GetLanguageCodeUseCase(repository)

    @Provides
    fun provideGetLanguageTargetUseCase(
        repository : LanguageRepository
    ): GetLanguageTargetUseCase = GetLanguageTargetUseCase(repository)

    @Provides
    fun provideGetDateTimeInfoUseCase() : GetDateTimeInfoUseCase = GetDateTimeInfoUseCase()
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

    @Singleton
    @Provides
    fun provideLanguageRepository(
        dataSource: TranslateRoomDataSource
    ): LanguageRepository = LanguageRepositoryImpl(dataSource)
}