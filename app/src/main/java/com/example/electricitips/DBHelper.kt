package com.example.electricitips

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // this database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQl_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    @Throws(SQLiteConstraintException::class)
    fun insertAppliance(appliance: ApplianceModel): Boolean {
        // gets data repository in write mode
        val db = writableDatabase

        // create new map of values where column names are the keys
        val values = ContentValues()
        values.put(DBSchema.UserEntity.COLUMN_ID, appliance.id)
        values.put(DBSchema.UserEntity.COLUMN_NAME, appliance.name)
        values.put(DBSchema.UserEntity.COLUMN_TYPE, appliance.type)
        values.put(DBSchema.UserEntity.COLUMN_RATING, appliance.rating)
        values.put(DBSchema.UserEntity.COLUMN_DURATION, appliance.duration)
        values.put(DBSchema.UserEntity.COLUMN_FREQUENCY, appliance.frequency)

        // insert new row, returning the primary key value of new row
        val success = db.insert(DBSchema.UserEntity.TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$success")!=-1)
    }
    @Throws(SQLiteConstraintException::class)
    fun deleteAppliance(name: String): Boolean {
        // gets the data repository in write mode
        val db = writableDatabase
        // define 'where' part of query
        val selection = DBSchema.UserEntity.COLUMN_NAME + " LIKE ?"
        // specify arguments in placeholder order.
        val selectionArgs = arrayOf(name)
        // issue sql statement
        val success = db.delete(DBSchema.UserEntity.TABLE_NAME, selection, selectionArgs)

        db.close()
        return (Integer.parseInt("$success")!= 0)
    }

    fun readAppliance(name: String): ArrayList<ApplianceModel> {
        val appliances = ArrayList<ApplianceModel>()
        val db = readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery("select * from " + DBSchema.UserEntity.TABLE_NAME + " WHERE " + DBSchema.UserEntity.COLUMN_NAME + "='" + name + "'", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            db.close()
            return ArrayList()
        }

        var id: Int
        var type: String
        var rating: String
        var duration: String
        var frequency: String

        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                id = cursor.getInt(cursor.getColumnIndex(DBSchema.UserEntity.COLUMN_ID))
                type = cursor.getString(cursor.getColumnIndex(DBSchema.UserEntity.COLUMN_TYPE))
                rating = cursor.getString(cursor.getColumnIndex(DBSchema.UserEntity.COLUMN_RATING))
                duration = cursor.getString(cursor.getColumnIndex(DBSchema.UserEntity.COLUMN_DURATION))
                frequency = cursor.getString(cursor.getColumnIndex(DBSchema.UserEntity.COLUMN_FREQUENCY))

                appliances.add(ApplianceModel(id, name, type, rating, duration, frequency))
                cursor.moveToNext()
            }
        }
        db.close()
        return appliances
    }

    /*fun checkEmptyTable(): Int {
        val db = writableDatabase
        var cursor: Cursor?

        cursor = db.rawQuery("select * from " + DBSchema.UserEntity.TABLE_NAME, null)
        cursor.moveToFirst()

        return cursor.count
    }*/
    fun readAllAppliances(): ArrayList<ApplianceModel> {
        val appliances : ArrayList<ApplianceModel> = ArrayList()
        val db = readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery("select * from " + DBSchema.UserEntity.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            e.printStackTrace()
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        var id: Int
        var name: String
        var type: String
        var rating: String
        var duration: String
        var frequency: String

        if (cursor!!.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(DBSchema.UserEntity.COLUMN_ID))
                name = cursor.getString(cursor.getColumnIndex(DBSchema.UserEntity.COLUMN_NAME))
                type = cursor.getString(cursor.getColumnIndex(DBSchema.UserEntity.COLUMN_TYPE))
                rating = cursor.getString(cursor.getColumnIndex(DBSchema.UserEntity.COLUMN_RATING))
                duration = cursor.getString(cursor.getColumnIndex(DBSchema.UserEntity.COLUMN_DURATION))
                frequency = cursor.getString(cursor.getColumnIndex(DBSchema.UserEntity.COLUMN_FREQUENCY))
                val app = ApplianceModel(id, name, type, rating, duration, frequency)
                appliances.add(app)
            }while(cursor.moveToNext())
        }
        db.close()
        return appliances
    }

    companion object {
        // if you change the database schema, you must increment database version
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FeedReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE" + DBSchema.UserEntity.TABLE_NAME + " (" +
                    DBSchema.UserEntity.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    DBSchema.UserEntity.COLUMN_NAME + " TEXT," + DBSchema.UserEntity.COLUMN_TYPE + " TEXT," +
                    DBSchema.UserEntity.COLUMN_RATING + " TEXT," + DBSchema.UserEntity.COLUMN_DURATION + " TEXT," +
                    DBSchema.UserEntity.COLUMN_FREQUENCY + " TEXT)"

        private val SQl_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBSchema.UserEntity.TABLE_NAME
    }
}