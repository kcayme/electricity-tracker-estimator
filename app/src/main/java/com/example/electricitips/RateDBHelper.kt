package com.example.electricitips

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class RateDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
    fun insertRate(rate: Float): Boolean {
        // gets data repository in write mode
        val db = writableDatabase

        // create new map of values where column names are the keys
        val values = ContentValues()
        values.put(RateDBSchema.RateEntity.COLUMN_RATE, rate)

        // insert new row, returning the primary key value of new row
        val success = db.insert(RateDBSchema.RateEntity.TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$success")!=-1)
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteCost(): Boolean {
        // gets the data repository in write mode
        val db = writableDatabase

        // issue sql statement
        val success = db.delete(RateDBSchema.RateEntity.TABLE_NAME, null, null)
        db.close()
        return (Integer.parseInt("$success")!= 0)
    }

    /*fun checkEmptyTable(): Int {
        val db = writableDatabase
        var cursor: Cursor?

        cursor = db.rawQuery("select * from " + DBSchema.UserEntity.TABLE_NAME, null)
        cursor.moveToFirst()

        return cursor.count
    }*/
    fun readCost(): Float {
        var cost = 0.0f
        val db = readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + RateDBSchema.RateEntity.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return 0.0f
        }

        if (cursor!!.moveToFirst()) {
            cost = cursor.getFloat(cursor.getColumnIndex(RateDBSchema.RateEntity.COLUMN_RATE))
        }
        db.close()
        return cost
    }

    companion object {
        // if you change the database schema, you must increment database version
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Electricitips_rate.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + RateDBSchema.RateEntity.TABLE_NAME + " (" +
                    RateDBSchema.RateEntity.COLUMN_RATE + " FLOAT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + RateDBSchema.RateEntity.TABLE_NAME
    }
}