package com.codex.hackernews.di

import android.app.Application
import com.codex.hackernews.AssesmentApp
import com.codex.hackernews.data.source.local.AppDatabase
import com.codex.hackernews.data.source.local.PreferencesManager
import com.codex.hackernews.data.source.local.StoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun providesCatDao(database: AppDatabase):StoryDao= database.storyDao()

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase = AppDatabase.getDatabase(application)

    @Provides
    @Singleton
    fun providePreferenceManager(): PreferencesManager = PreferencesManager(AssesmentApp.applicationContext())
}