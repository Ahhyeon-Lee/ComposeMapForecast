package com.example.datalayer.di

import android.content.Context
import androidx.room.Room
import com.example.datalayer.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Provides
    fun provideRegionsDataBase(
        @ApplicationContext applicationContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "AppDataBase"
        ).build()
    }
}