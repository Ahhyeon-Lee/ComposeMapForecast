package com.example.domain

import com.example.domain.repository.RegionsDBRepository
import com.example.domain.repository.WeatherRepository
import com.example.domain.repository.translate.LanguageRepository
import com.example.domain.repository.translate.TranslateRepository
import com.example.domain.usecase.GetDateTimeInfoUseCase
import com.example.domain.usecase.map.CheckRegionsDbDataUseCase
import com.example.domain.usecase.map.GetClosesRegionInDbUseCase
import com.example.domain.usecase.map.GetSearchedRegionsUseCase
import com.example.domain.usecase.map.GetWeatherInfoUsecase
import com.example.domain.usecase.translate.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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

    @Provides
    fun provideGetClosesRegionInDbUseCase(
        regionsDBRepository: RegionsDBRepository
    ) : GetClosesRegionInDbUseCase {
        return GetClosesRegionInDbUseCase(regionsDBRepository)
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
    fun provideTranslateUseCase(
        repository: TranslateRepository
    ) : TranslateUseCase = TranslateUseCase(repository)

    @Provides
    fun provideInsertTranslateHistoryUseCase(
        repository: LanguageRepository
    ) : InsertTranslateHistoryUseCase = InsertTranslateHistoryUseCase(repository)

    @Provides
    fun provideGetTranslateHistoryUseCase(
        repository: LanguageRepository
    ) : GetTranslateHistoryUseCase = GetTranslateHistoryUseCase(repository)
}