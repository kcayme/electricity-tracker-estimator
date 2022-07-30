package com.example.electricitips

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class ApplianceDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
    fun insertAppliance(appliance: Appliance): Boolean {
        // gets data repository in write mode
        val db = writableDatabase

        // create new map of values where column names are the keys
        val values = ContentValues()
        values.put(DBSchema.ApplianceEntity.COLUMN_MODELCODE, appliance.modelCode)
        values.put(DBSchema.ApplianceEntity.COLUMN_IMGID, appliance.imgId)
        values.put(DBSchema.ApplianceEntity.COLUMN_NAME, appliance.name)
        values.put(DBSchema.ApplianceEntity.COLUMN_TYPE, appliance.type)
        values.put(DBSchema.ApplianceEntity.COLUMN_RATING, appliance.rating)
        values.put(DBSchema.ApplianceEntity.COLUMN_DURATION, appliance.duration)
        values.put(DBSchema.ApplianceEntity.COLUMN_FREQUENCY, appliance.frequency)

        // insert new row, returning the primary key value of new row
        val success = db.insert(DBSchema.ApplianceEntity.TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$success")!=-1)
    }
    @Throws(SQLiteConstraintException::class)
    fun deleteAppliance(code: String): Boolean {
        // gets the data repository in write mode
        val db = writableDatabase
        // define 'where' part of query
        val selection = DBSchema.ApplianceEntity.COLUMN_MODELCODE + " LIKE ?"
        // specify arguments in placeholder order.
        val selectionArgs = arrayOf(code)
        // issue sql statement
        val success = db.delete(DBSchema.ApplianceEntity.TABLE_NAME, selection, selectionArgs)

        db.close()
        return (Integer.parseInt("$success")!= 0)
    }

    fun readAppliance(nameQuery: String): ArrayList<Appliance> {
        val appliances = ArrayList<Appliance>()
        val db = readableDatabase
        var cursor: Cursor?
        try {
            cursor = db.rawQuery("select * from " + DBSchema.ApplianceEntity.TABLE_NAME + " WHERE " + DBSchema.ApplianceEntity.COLUMN_NAME + "='" + nameQuery + "'", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            db.close()
            return ArrayList()
        }

        var id: Int
        var code : String
        var name : String
        var type: String
        var rating: Float
        var duration: Float
        var frequency: String

        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                id = cursor.getInt(cursor.getColumnIndex(DBSchema.ApplianceEntity.COLUMN_IMGID))
                name = cursor.getString(cursor.getColumnIndex(DBSchema.ApplianceEntity.COLUMN_NAME))
                code = cursor.getString(cursor.getColumnIndex(DBSchema.ApplianceEntity.COLUMN_MODELCODE))
                type = cursor.getString(cursor.getColumnIndex(DBSchema.ApplianceEntity.COLUMN_TYPE))
                rating = cursor.getFloat(cursor.getColumnIndex(DBSchema.ApplianceEntity.COLUMN_RATING))
                duration = cursor.getFloat(cursor.getColumnIndex(DBSchema.ApplianceEntity.COLUMN_DURATION))
                frequency = cursor.getString(cursor.getColumnIndex(DBSchema.ApplianceEntity.COLUMN_FREQUENCY))

                appliances.add(Appliance(id, name, code, type, rating, duration, frequency))
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
    fun readAllAppliances(): ArrayList<Appliance> {
        var appliances = ArrayList<Appliance>()
        val db = readableDatabase
        var cursor: Cursor?
        try {
            cursor = db.rawQuery("select * from " + DBSchema.ApplianceEntity.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        var id: Int
        var code : String
        var name : String
        var type: String
        var rating: Float
        var duration: Float
        var frequency: String

        if (cursor!!.moveToFirst()) {
            while(!cursor.isAfterLast) {
                id = cursor.getInt(cursor.getColumnIndex(DBSchema.ApplianceEntity.COLUMN_IMGID))
                name = cursor.getString(cursor.getColumnIndex(DBSchema.ApplianceEntity.COLUMN_NAME))
                code = cursor.getString(cursor.getColumnIndex(DBSchema.ApplianceEntity.COLUMN_MODELCODE))
                type = cursor.getString(cursor.getColumnIndex(DBSchema.ApplianceEntity.COLUMN_TYPE))
                rating = cursor.getFloat(cursor.getColumnIndex(DBSchema.ApplianceEntity.COLUMN_RATING))
                duration = cursor.getFloat(cursor.getColumnIndex(DBSchema.ApplianceEntity.COLUMN_DURATION))
                frequency = cursor.getString(cursor.getColumnIndex(DBSchema.ApplianceEntity.COLUMN_FREQUENCY))
                val app = Appliance(id, name, code, type, rating, duration, frequency)
                appliances.add(app)
                // move to next row
                cursor.moveToNext()
            }
        }
        db.close()
        return appliances
    }

    companion object {
        // if you change the database schema, you must increment database version
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Electricitips_appliances.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBSchema.ApplianceEntity.TABLE_NAME + " (" +
                    DBSchema.ApplianceEntity.COLUMN_IMGID + " INT," +
                    DBSchema.ApplianceEntity.COLUMN_MODELCODE + " STRING PRIMARY KEY," +
                    DBSchema.ApplianceEntity.COLUMN_NAME + " TEXT," + DBSchema.ApplianceEntity.COLUMN_TYPE + " TEXT," +
                    DBSchema.ApplianceEntity.COLUMN_RATING + " FLOAT," + DBSchema.ApplianceEntity.COLUMN_DURATION + " FLOAT," +
                    DBSchema.ApplianceEntity.COLUMN_FREQUENCY + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBSchema.ApplianceEntity.TABLE_NAME
    }
}