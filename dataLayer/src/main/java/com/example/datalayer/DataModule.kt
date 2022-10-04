package com.example.datalayer

import android.content.Context
import androidx.room.Room
import com.example.datalayer.local.database.AppDatabase
import com.example.datalayer.local.datasource.RegionsRoomDataSource
import com.example.datalayer.local.datasource.TranslateRoomDataSource
import com.example.datalayer.remote.datasource.TranslateApiDataSource
import com.example.datalayer.remote.datasource.WeatherApiDataSource
import com.example.datalayer.remote.service.TranslateApiService
import com.example.datalayer.remote.service.WeatherApiService
import com.example.datalayer.repository.LanguageRepositoryImpl
import com.example.datalayer.repository.RegionsDBRepositoryImpl
import com.example.datalayer.repository.TranslateRepositoryImpl
import com.example.datalayer.repository.WeatherRepositoryImpl
import com.example.domain.repository.map.RegionsDBRepository
import com.example.domain.repository.map.WeatherRepository
import com.example.domain.repository.translate.LanguageRepository
import com.example.domain.repository.translate.TranslateRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRegionsDBRepository (
        regionsDBRepositoryImpl: RegionsDBRepositoryImpl
    ): RegionsDBRepository

    @Binds
    abstract fun bindWeatherRepository (
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    abstract fun bindLanguageRepository(
        languageRepositoryImpl: LanguageRepositoryImpl
    ): LanguageRepository

    @Binds
    abstract fun bindTranslateRepository(
        translateRepositoryImpl: TranslateRepositoryImpl
    ): TranslateRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideRegionsRoomDataSource(
        database: AppDatabase
    ): RegionsRoomDataSource {
        return RegionsRoomDataSource(database.regionDao())
    }

    @Provides
    fun provideWeatherApiDataSource(
        @RetrofitNetworkModule.Weather apiService: WeatherApiService
    ): WeatherApiDataSource {
        return WeatherApiDataSource(apiService)
    }

    @Provides
    fun provideTranslateRoomDataSource(
        database: AppDatabase
    ): TranslateRoomDataSource = TranslateRoomDataSource(database.translateDao())

    @Provides
    fun provideTranslateApiDataSource(
        @RetrofitNetworkModule.Translation service: TranslateApiService
    ): TranslateApiDataSource = TranslateApiDataSource(service)
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

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

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Weather

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Translation

    @Weather
    @Provides
    fun provideWeatherApiBaseUrl() = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"

    @Translation
    @Provides
    fun provideTranslationApiBaseUrl() = "https://openapi.naver.com/"

    @Provides
    fun provideGson() : Gson = GsonBuilder().setLenient().create()

    @Provides
    fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ) : OkHttpClient {
        return OkHttpClient().newBuilder()
            .addNetworkInterceptor(interceptor)
            .build()
    }

    @Weather
    @Provides
    fun provideWeatherRetrofit(
        okHttpClient: OkHttpClient,
        @Weather BASE_URL: String // baseurl 추가되면 retrofit 객체 반환 함수와 baseurl 제공 함수 추가 해서 각각에 @Qualifier 추가해서 지정해주기.
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Translation
    @Provides
    fun provideTranslationRetrofit(
        okHttpClient: OkHttpClient,
        @Translation BASE_URL: String // baseurl 추가되면 retrofit 객체 반환 함수와 baseurl 제공 함수 추가 해서 각각에 @Qualifier 추가해서 지정해주기.
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Weather
    @Provides
    fun provideWeatherApiService(
        @Weather retrofit: Retrofit
    ) : WeatherApiService = retrofit.create(WeatherApiService::class.java)

    @Translation
    @Provides
    fun provideTranslateApiService(
        @Translation retrofit: Retrofit
    ) : TranslateApiService = retrofit.create(TranslateApiService::class.java)
}

