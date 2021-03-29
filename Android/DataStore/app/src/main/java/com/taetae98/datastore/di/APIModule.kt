package com.taetae98.datastore.di

import com.taetae98.datastore.api.SmartGachonAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APIModule {
    @Singleton
    @Provides
    fun provideSmartGachonAPI(): SmartGachonAPI {
        return SmartGachonAPI.getInstance()
    }
}