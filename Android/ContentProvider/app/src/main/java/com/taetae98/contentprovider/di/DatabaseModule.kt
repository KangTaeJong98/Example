package com.taetae98.contentprovider.di

import android.content.Context
import com.taetae98.contentprovider.dao.SimpleDataDao
import com.taetae98.contentprovider.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun providesSimpleDataDao(appDatabase: AppDatabase): SimpleDataDao {
        return appDatabase.simpleData()
    }
}