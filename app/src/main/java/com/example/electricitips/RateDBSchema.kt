package com.example.electricitips

import android.provider.BaseColumns

object RateDBSchema {
    /* inner class that defines the table contents */
    class RateEntity : BaseColumns {
        companion object {
            val TABLE_NAME = "electricity_rate"
            val COLUMN_RATE = "cost"
        }
    }
}