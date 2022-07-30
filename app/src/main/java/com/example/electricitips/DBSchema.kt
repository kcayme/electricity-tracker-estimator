package com.example.electricitips

import android.provider.BaseColumns

object DBSchema {
    /* inner class that defines the table contents */
    class ApplianceEntity : BaseColumns {
        companion object {
            val TABLE_NAME = "appliances"
            val COLUMN_MODELCODE = "code"
            val COLUMN_IMGID = "imgID"
            val COLUMN_NAME = "name"
            val COLUMN_TYPE = "type"
            val COLUMN_RATING = "rating"
            val COLUMN_DURATION = "duration"
            val COLUMN_FREQUENCY = "frequency"
        }
    }
}