package com.nhnace.contentprovidertest

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log

class ContentProviderTest : ContentProvider() {
    override fun onCreate(): Boolean {
        Log.d("jhlee", "ContentProviderTest onCreate")
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        Log.d("jhlee", "query")
        return null
    }

    override fun getType(uri: Uri): String? {
        Log.d("jhlee", "getType")
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d("jhlee", "insert")
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.d("jhlee", "delete")
        return 0
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?
    ): Int {
        Log.d("jhlee", "update")
        return 0
    }
}