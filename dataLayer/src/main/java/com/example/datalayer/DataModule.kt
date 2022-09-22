package com.example.datalayer

import android.content.Context
import androidx.room.Room
import com.example.datalayer.local.database.AppDatabase
import com.example.datalayer.local.datasource.RegionsRoomDataSource
import com.example.datalayer.remote.datasource.WeatherApiDataSource
import com.example.datalayer.remote.service.WeatherApiService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideRegionsRoomDataSource(
        database: AppDatabase
    ): RegionsRoomDataSource {
        return RegionsRoomDataSource(database.regionDao())
    }

    @Singleton
    @Provides
    fun provideWeatherApiDataSource(
        apiService: WeatherApiService
    ): WeatherApiDataSource {
        return WeatherApiDataSource(apiService)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
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

@Module
@InstallIn(SingletonComponent::class)
object RetrofitNetworkModule {

    @Provides
    fun provideWeatherApiBaseUrl() = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"

    @Singleton
    @Provides
    fun provideGson() : Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ) : OkHttpClient {
        return OkHttpClient().newBuilder()
            .addNetworkInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String // baseurl 추가되면 retrofit 객체 반환 함수와 baseurl 제공 함수 추가 해서 각각에 @Qualifier 추가해서 지정해주기.
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherApiService(
        retrofit: Retrofit
    ) : WeatherApiService = retrofit.create(WeatherApiService::class.java)
}

