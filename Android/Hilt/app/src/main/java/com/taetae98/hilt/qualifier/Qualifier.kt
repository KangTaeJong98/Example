package com.taetae98.hilt.qualifier

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier

@Qualifier
annotation class LocalDatabase

@Qualifier
annotation class ServerDatabase

interface DataSource {
    fun select()
    fun update()
    fun delete()
}

class LocalDataSource @Inject constructor() : DataSource {
    override fun select() {

    }

    override fun update() {

    }

    override fun delete() {

    }
}

class ServerDataSource @Inject constructor() : DataSource {
    override fun select() {

    }

    override fun update() {

    }

    override fun delete() {

    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule() {
    @Binds
    @LocalDatabase
    abstract fun provideLocalDatabase(localDataSource: LocalDataSource): DataSource

    @Binds
    @ServerDatabase
    abstract fun provideServerDatabase(serverDataSource: ServerDataSource): DataSource
}
