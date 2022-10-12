package com.example.datalayer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatchersModule {

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class DefaultDispatcher

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class IoDispatcher

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class MainDispatcher

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class MainImmediateDispatcher

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatchers() = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatchers() = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatchers() = Dispatchers.Main

    @MainImmediateDispatcher
    @Provides
    fun providesMainImmediateDispatchers() = Dispatchers.Main.immediate
}