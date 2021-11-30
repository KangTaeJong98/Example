package com.taetae98.widget.di

import com.taetae98.widget.api.CovidStatusApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    fun providesCovidStatusApi() = CovidStatusApi.getInstance()
}