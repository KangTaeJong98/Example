package com.taetae98.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.taetae98.datastore.Account
import com.taetae98.datastore.accountStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AccountDataStoreModule {
    @Singleton
    @Provides
    fun providesAccountDataStore(@ApplicationContext context: Context): DataStore<Account> {
        return context.accountStore
    }
}