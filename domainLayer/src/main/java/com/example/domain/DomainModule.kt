package com.example.domain

import android.content.Context
import com.example.domain.repository.RegionsDBRepository
import com.example.datalayer.local.database.RegionsDatabase
import com.example.datalayer.local.datasource.RegionsRoomDataSource
import com.example.domain.repository.RegionsDBRepositoryImpl
import dagger.Module
import androidx.room.Room
import com.example.domain.usecase.map.CheckRegionsDbDataUsecase
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
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
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideRegionsRoomDataSource(
        database: RegionsDatabase
    ): RegionsRoomDataSource {
        return RegionsRoomDataSource(database.regionDao())
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRegionsDataBase(
        @ApplicationContext context: Context
    ): RegionsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            RegionsDatabase::class.java,
            "regionsDb"
        ).build()
    }
}