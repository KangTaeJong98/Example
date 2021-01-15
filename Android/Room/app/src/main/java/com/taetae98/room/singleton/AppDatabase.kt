package com.taetae98.room.singleton

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.taetae98.room.DATABASE_NAME
import com.taetae98.room.data.Drawer
import com.taetae98.room.data.DrawerDao

@Database(entities = [Drawer::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
            }.also {
                instance = it
            }
        }
    }

    abstract fun drawer(): DrawerDao
}