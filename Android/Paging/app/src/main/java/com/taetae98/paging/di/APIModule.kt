package com.taetae98.paging.di

import com.taetae98.paging.api.WebToonAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APIModule {
    @Provides
    @Singleton
    fun provideWebToonAPI(): WebToonAPI {
        return WebToonAPI.getInstance()
    }
}