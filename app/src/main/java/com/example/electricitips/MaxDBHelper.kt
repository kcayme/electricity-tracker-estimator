package com.example.electricitips

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class MaxDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // this database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertMax(max: Float): Boolean {
        // gets data repository in write mode
        val db = writableDatabase

        // create new map of values where column names are the keys
        val values = ContentValues()
        values.put(MaxDBSchema.MaxEntity.COLUMN_MAX, max)

        // insert new row, returning the primary key value of new row
        val success = db.insert(MaxDBSchema.MaxEntity.TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$success")!=-1)
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteMax(): Boolean {
        // gets the data repository in write mode
        val db = writableDatabase

        // issue sql statement
        val success = db.delete(MaxDBSchema.MaxEntity.TABLE_NAME, null, null)
        db.close()
        return (Integer.parseInt("$success")!= 0)
    }

    fun readMax(): Float {
        var max = 0.0f
        val db = readableDatabase
        var cursor: Cursor?
        try {
            cursor = db.rawQuery("select * from " + MaxDBSchema.MaxEntity.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return 0.0f
        }

        if (cursor!!.moveToFirst()) {
            max = cursor.getFloat(cursor.getColumnIndex(MaxDBSchema.MaxEntity.COLUMN_MAX))
        }
        db.close()
        return max
    }

    companion object {
        // if you change the database schema, you must increment database version
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Electricitips_max.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MaxDBSchema.MaxEntity.TABLE_NAME + " (" +
                    MaxDBSchema.MaxEntity.COLUMN_MAX+ " FLOAT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MaxDBSchema.MaxEntity.TABLE_NAME
    }
}