package com.sample.wireviewer.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sample.wireviewer.services.AppDispatchers
import com.sample.wireviewer.services.DuckDuckGoService
import com.sample.wireviewer.services.ENDPOINT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideMediaType(): MediaType =MediaType.get("application/json; charset=utf-8")

    @Provides
    fun provideDispatchers(): AppDispatchers = AppDispatchers()

    @Provides
    @Singleton
    fun provideRetrofit(contentType: MediaType): Retrofit = Retrofit.Builder()
        .baseUrl(ENDPOINT)
        .client(OkHttpClient.Builder().also { client ->
            client.connectTimeout(15, TimeUnit.SECONDS)
            client.readTimeout(15, TimeUnit.SECONDS)
            client.writeTimeout(15, TimeUnit.SECONDS)
        }.build())
        .addConverterFactory(Json{ ignoreUnknownKeys = true }.asConverterFactory(contentType))
        .build()

    @Provides
    @Singleton
    fun provideDataService(retrofit: Retrofit): DuckDuckGoService =
        retrofit.create(DuckDuckGoService::class.java)
}
