package com.codex.hackernews.di


import com.codex.hackernews.data.source.remote.ApiServiceNews
import com.codex.hackernews.di.GlobalVariable.getApiBaseUrl
import com.codex.hackernews.di.NetworkModule.NetworkModule.provideClientNews
import com.codex.hackernews.di.NetworkModule.NetworkModule.provideMoshiConverter
import com.codex.hackernews.di.NetworkModule.NetworkModule.provideOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object DataSourceModule {

    @Provides
    fun provideClientNews() : ApiServiceNews {
        return provideClientNews(provideOkHttpClient(), getApiBaseUrl() ,provideMoshiConverter())
    }

}