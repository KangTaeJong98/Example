package com.taetae98.datastore

import android.content.Context
import android.content.res.Resources
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.loginStore: DataStore<Preferences> by preferencesDataStore(name = "login")
val Context.accountStore: DataStore<Account> by dataStore(
    fileName = "user_prefs.pb",
    serializer = AccountSerializer
)

fun Int.toDp(): Int {
    return (this * Resources.getSystem().displayMetrics.density + 0.5F).toInt()
}