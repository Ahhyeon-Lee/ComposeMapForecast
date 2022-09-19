package com.example.datalayer

import android.content.Context
import androidx.room.Room
import com.example.datalayer.local.RegionsDBRepository
import com.example.datalayer.local.RegionsDatabase
import com.example.datalayer.local.RoomDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteTasksDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalRoomDataSource

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTasksRepository(
        @LocalRoomDataSource localDataSource: RoomDataSource
    ): RegionsDBRepository {
        return RegionsDBRepository(localDataSource)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @LocalRoomDataSource
    @Provides
    fun provideRoomDataSource(
        database: RegionsDatabase
    ): RoomDataSource {
        return RoomDataSource(database.regionDao())
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): RegionsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            RegionsDatabase::class.java,
            "regionsDb"
        ).build()
    }
}