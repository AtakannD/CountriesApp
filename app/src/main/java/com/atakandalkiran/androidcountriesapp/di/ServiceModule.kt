package com.atakandalkiran.androidcountriesapp.di

import android.content.Context
import com.atakandalkiran.androidcountriesapp.data.api.CountriesService
import com.atakandalkiran.androidcountriesapp.data.local.CountriesDao
import com.atakandalkiran.androidcountriesapp.data.local.CountriesRoomDatabase
import com.atakandalkiran.androidcountriesapp.data.remote.CountriesRemoteDataSource
import com.atakandalkiran.androidcountriesapp.data.repository.CountriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @BaseUrl
    @Provides
    @Singleton
    fun provideBaseUrl() = "https://restcountries.com/v3.1/"

    @Provides
    @Singleton
    fun provideRetrofit(@BaseUrl baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

    @Provides
    fun provideCountriesService(retrofit: Retrofit): CountriesService =
        retrofit.create(CountriesService::class.java)

    @Provides
    @Singleton
    fun provideCountriesRemoteDataSource(
        countriesService: CountriesService
    ): CountriesRemoteDataSource = CountriesRemoteDataSource(countriesService)

    @Provides
    @Singleton
    fun provideCountriesRepository(
        remoteDataSource: CountriesRemoteDataSource,
        countriesDao: CountriesDao,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): CountriesRepository = CountriesRepository(remoteDataSource, countriesDao, dispatcher)

    @Provides
    @Singleton
    fun provideCountriesDatabase(
        @ApplicationContext context: Context
    ): CountriesRoomDatabase = CountriesRoomDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun provideCountriesDao(database: CountriesRoomDatabase): CountriesDao = database.countriesDao()

    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}