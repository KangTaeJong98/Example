package com.taetae98.hilt.di

import com.taetae98.hilt.api.RiotLeagueAPI
import com.taetae98.hilt.api.RiotSpectatorAPI
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
    fun provideRiotSpectatorAPI(): RiotSpectatorAPI {
        return RiotSpectatorAPI.create()
    }

    @Singleton
    @Provides
    fun provideRiotLeagueAPI(): RiotLeagueAPI {
        return RiotLeagueAPI.create()
    }
}