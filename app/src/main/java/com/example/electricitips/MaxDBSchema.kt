package com.example.electricitips

import android.provider.BaseColumns

object MaxDBSchema {
    /* inner class that defines the table contents */
    class MaxEntity : BaseColumns {
        companion object {
            val TABLE_NAME = "electricity_max"
            val COLUMN_MAX = "max"
        }
    }
}