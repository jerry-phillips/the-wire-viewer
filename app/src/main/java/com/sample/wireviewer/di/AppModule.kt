package com.sample.wireviewer.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sample.wireviewer.services.AppDispatchers
import com.sample.wireviewer.services.DuckDuckGoService
import com.sample.wireviewer.services.ENDPOINT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.sample.wireviewer.model.Character

@OptIn(ExperimentalSerializationApi::class)
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val json = Json { ignoreUnknownKeys = true }

    @Provides
    fun provideMediaType(): MediaType = "application/json; charset=utf-8".toMediaType()

    @Provides
    fun provideDispatchers(): AppDispatchers = AppDispatchers()

    @Provides
    fun providesCharacterCache(): MutableList<Character> = mutableListOf()

    @Provides
    @Singleton
    fun provideRetrofit(contentType: MediaType): Retrofit = Retrofit.Builder()
        .baseUrl(ENDPOINT)
        .client(OkHttpClient.Builder().also { client ->
            client.connectTimeout(15, TimeUnit.SECONDS)
            client.readTimeout(15, TimeUnit.SECONDS)
            client.writeTimeout(15, TimeUnit.SECONDS)
        }.build())
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    @Provides
    @Singleton
    fun provideDataService(retrofit: Retrofit): DuckDuckGoService =
        retrofit.create(DuckDuckGoService::class.java)
}
