package com.taetae98.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.taetae98.datastore.loginStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LoginDataStoreModule {

    @Provides
    @LoginDataStore
    fun providesLoginDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.loginStore
    }
}