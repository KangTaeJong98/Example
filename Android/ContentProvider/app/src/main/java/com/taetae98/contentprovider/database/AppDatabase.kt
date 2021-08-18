package com.taetae98.contentprovider.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.taetae98.contentprovider.dao.SimpleDataDao
import com.taetae98.contentprovider.dto.SimpleData

@Database(entities = [SimpleData::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, AppDatabase::class.java, "SimpleDatabase.db")
                    .build()
            }
        }
    }

    abstract fun simpleData(): SimpleDataDao
}