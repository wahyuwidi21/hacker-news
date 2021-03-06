package com.codex.hackernews.di


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import com.codex.hackernews.AssesmentApp
import com.codex.hackernews.data.source.remote.ApiServiceNews
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

object NetworkModule {
    @Module
    @InstallIn(AssesmentApp::class)
    object NetworkModule {

        @Provides
        @Singleton
        fun provideOkHttpClient() = run {
            val okHttpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(provideHttpLoggingInterceptor())
                .addInterceptor(provideCacheInterceptor())
//                .addInterceptor(ChuckerInterceptor(AssesmentApp.applicationContext()))
                .addInterceptor { chain ->
                    val request = chain.request()
                    val requestBuilder = request.newBuilder()
                        .addHeader("accept","application/vnd.github.v3+json")
                        .build()
                    chain.proceed(requestBuilder)
                }
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
            okHttpClientBuilder.build()
        }

        @Provides
        @Singleton
        fun provideCacheInterceptor() = run {
            Interceptor { chain ->
                val response = chain.proceed(chain.request())
                val maxAge =
                    60 // read from cache for 60 seconds even if there is internet connection
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .removeHeader("Pragma")
                    .build()
            }
        }

        @Provides
        @Singleton
        fun provideHttpLoggingInterceptor() = run {
            HttpLoggingInterceptor().apply {
                apply { level = HttpLoggingInterceptor.Level.BODY }
            }
        }

        @Provides
        @Singleton
        fun provideMoshiConverter(): Moshi = run {
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        @Provides
        @Singleton
        fun provideClientNews(
            okHttpClient: OkHttpClient,
            baseUrl: String,
            moshiConverter: Moshi
        ): ApiServiceNews {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshiConverter))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(ApiServiceNews::class.java)
        }

    }
}