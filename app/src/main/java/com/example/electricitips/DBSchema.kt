package com.example.electricitips

import android.provider.BaseColumns

object DBSchema {
    /* inner class that defines the table contents */
    class UserEntity : BaseColumns {
        companion object {
            val TABLE_NAME = "appliances"
            val COLUMN_ID = "id"
            val COLUMN_NAME = "name"
            val COLUMN_TYPE = "type"
            val COLUMN_RATING = "rating"
            val COLUMN_DURATION = "duration"
            val COLUMN_FREQUENCY = "frequency"
        }
    }
}