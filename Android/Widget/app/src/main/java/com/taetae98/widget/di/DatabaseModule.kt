package com.taetae98.widget.di

import android.content.Context
import com.taetae98.widget.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ) = AppDatabase.getInstance(context)

    @Provides
    fun providesCovidWidgetDao(
        appDatabase: AppDatabase
    ) = appDatabase.covidWidgetDao()
}