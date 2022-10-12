package com.example.datalayer.di

import com.example.datalayer.repositoryimpl.map.RegionsDBRepositoryImpl
import com.example.datalayer.repositoryimpl.map.WeatherRepositoryImpl
import com.example.datalayer.repositoryimpl.translate.LanguageRepositoryImpl
import com.example.datalayer.repositoryimpl.translate.TranslateRepositoryImpl
import com.example.domain.repository.map.RegionsDBRepository
import com.example.domain.repository.map.WeatherRepository
import com.example.domain.repository.translate.LanguageRepository
import com.example.domain.repository.translate.TranslateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRegionsDBRepository (
        regionsDBRepositoryImpl: RegionsDBRepositoryImpl
    ): RegionsDBRepository

    @Singleton
    @Binds
    abstract fun bindWeatherRepository (
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Singleton
    @Binds
    abstract fun bindLanguageRepository(
        languageRepositoryImpl: LanguageRepositoryImpl
    ): LanguageRepository

    @Singleton
    @Binds
    abstract fun bindTranslateRepository(
        translateRepositoryImpl: TranslateRepositoryImpl
    ): TranslateRepository
}
