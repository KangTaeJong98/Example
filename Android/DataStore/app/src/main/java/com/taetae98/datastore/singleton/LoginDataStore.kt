package com.taetae98.datastore.singleton

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.taetae98.datastore.loginStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginDataStore @Inject constructor(@ApplicationContext private val context: Context) {
    private val idKey by lazy { stringPreferencesKey("id") }
    private val passwordKey by lazy { stringPreferencesKey("password") }

    var id: String
        get() {
            return runBlocking(Dispatchers.IO) {
                context.loginStore.data.map {
                    it[idKey] ?: ""
                }.first()
            }
        }
        set(value) {
            runBlocking(Dispatchers.IO) {
                context.loginStore.edit {
                    it[idKey] = value
                }
            }
        }

    var password: String
        get() {
            return runBlocking(Dispatchers.IO) {
                context.loginStore.data.map {
                    it[passwordKey] ?: ""
                }.first()
            }
        }
        set(value) {
            runBlocking(Dispatchers.IO) {
                context.loginStore.edit {
                    it[passwordKey] = value
                }
            }
        }
}