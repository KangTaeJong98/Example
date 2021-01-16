package com.taetae98.room.singleton

import android.content.Context
import androidx.room.CoroutinesRoom
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.taetae98.room.DATABASE_NAME
import com.taetae98.room.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Drawer::class, ToDo::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                getInstance(context).drawer().insert(
                                    Drawer(name = "ToDo")
                                )
                            }
                        }
                    })
                    .addMigrations(MIGRATION_1_2)
                    .build()
            }.also {
                instance = it
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ToDo ADD COLUMN isFinished INTEGER DEFAULT 0 NOT NULL")
            }
        }
    }

    abstract fun drawer(): DrawerDao
    abstract fun todo(): ToDoDao
}