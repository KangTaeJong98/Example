package com.taetae98.hilt.di

import android.content.Context
import com.taetae98.hilt.database.AppDatabase
import com.taetae98.hilt.database.SummonerEntityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideSummonerEntityDao(appDatabase: AppDatabase): SummonerEntityDao {
        return appDatabase.summonerEntityDao()
    }
}