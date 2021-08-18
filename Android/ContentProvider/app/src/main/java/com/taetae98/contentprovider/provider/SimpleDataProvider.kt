package com.taetae98.contentprovider.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.room.OnConflictStrategy
import com.taetae98.contentprovider.database.AppDatabase

class SimpleDataProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val context = context ?: return null
        val database = AppDatabase.getInstance(context)

        val id = database.openHelper.writableDatabase.insert(
            uri.lastPathSegment, OnConflictStrategy.REPLACE, contentValues
        )

        return ContentUris.withAppendedId(uri, id).also {
            context.contentResolver.notifyChange(it, null)
        }
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }
}