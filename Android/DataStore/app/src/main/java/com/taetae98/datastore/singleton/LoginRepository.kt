package com.taetae98.datastore.singleton

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.taetae98.datastore.di.LoginDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor() {
    private val idKey by lazy { stringPreferencesKey("id") }
    private val passwordKey by lazy { stringPreferencesKey("password") }

    @Inject
    @LoginDataStore
    lateinit var dataStore: DataStore<Preferences>

    var id: String
        get() {
            return runBlocking(Dispatchers.IO) {
                dataStore.data.map {
                    it[idKey] ?: ""
                }.first()
            }
        }
        set(value) {
            runBlocking(Dispatchers.IO) {
                dataStore.edit {
                    it[idKey] = value
                }
            }
        }

    var password: String
        get() {
            return runBlocking(Dispatchers.IO) {
                dataStore.data.map {
                    it[passwordKey] ?: ""
                }.first()
            }
        }
        set(value) {
            runBlocking(Dispatchers.IO) {
                dataStore.edit {
                    it[passwordKey] = value
                }
            }
        }
}