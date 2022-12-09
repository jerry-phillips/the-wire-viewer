package com.sample.wireviewer.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sample.wireviewer.services.AppDispatchers
import com.sample.wireviewer.services.DuckDuckGoService
import com.sample.wireviewer.services.ENDPOINT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    fun provideDispatchers(): AppDispatchers = AppDispatchers()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(ENDPOINT)
        .client(OkHttpClient.Builder().also { client ->
            client.connectTimeout(15, TimeUnit.SECONDS)
            client.readTimeout(15, TimeUnit.SECONDS)
            client.writeTimeout(15, TimeUnit.SECONDS)
        }.build())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideDataService(retrofit: Retrofit): DuckDuckGoService =
        retrofit.create(DuckDuckGoService::class.java)
}
