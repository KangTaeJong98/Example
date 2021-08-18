package com.taetae98.googleapi.manager

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.taetae98.googleapi.dto.Place
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject constructor() {

    suspend fun get(): List<Place> {
        val id = FirebaseAuth.getInstance().currentUser!!.uid
        return FirebaseDatabase.getInstance().getReference(id).get().await().getValue(
            object : GenericTypeIndicator<List<Place>>() {}
        ) ?: updateDefaultData()
    }

    suspend fun set(places: List<Place>) {
        val id = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference(id).setValue(places).await()
    }

    private suspend fun updateDefaultData(): List<Place> {
        val id = FirebaseAuth.getInstance().currentUser!!.uid
        return FirebaseDatabase.getInstance().getReference("default").get().await().getValue(
            object : GenericTypeIndicator<List<Place>>() {}
        )!!.also {
            FirebaseDatabase.getInstance().getReference(id).setValue(it)
        }
    }
}