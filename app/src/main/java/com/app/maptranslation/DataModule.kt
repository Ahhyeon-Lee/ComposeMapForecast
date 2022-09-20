package com.app.maptranslation

import android.content.Context
import com.example.datalayer.local.repository.RegionsDBRepository
import com.example.datalayer.local.database.RegionsDatabase
import com.example.datalayer.local.datasource.RegionsRoomDataSource
import com.example.domain.usecases.RegionsDBRepositoryImpl
import dagger.Module
import androidx.room.Room
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalRegionsRoomDataSource

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRegionsDBRepository(
        @LocalRegionsRoomDataSource localDataSource: RegionsRoomDataSource
    ): RegionsDBRepository {
        return RegionsDBRepositoryImpl(localDataSource)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @LocalRegionsRoomDataSource
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
    fun provideRegionsDataBase(@ApplicationContext context: Context): RegionsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            RegionsDatabase::class.java,
            "regionsDb"
        ).build()
    }
}