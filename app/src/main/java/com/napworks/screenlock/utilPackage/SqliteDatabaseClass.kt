package com.napworks.mohra.utilPackage


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.napworks.screenlock.utilPackage.CommonMethods


class SqliteDatabaseClass(context: Context) :
    SQLiteOpenHelper(context, context.packageName, null, DATABASE_VERSION) {
    private val TAG = javaClass.simpleName
    override fun onCreate(db: SQLiteDatabase) {
        try {
            val countryTable = ("CREATE TABLE IF NOT EXISTS " + MyConstants.COUNTRY_TABLE
                    + " ( " + MyConstants.COUNTRY_ID + " TEXT, "
                    + MyConstants.COUNTRY_NAME + " TEXT, "
                    + MyConstants.COUNTRY_SHORT_CODE + " TEXT, "
                    + MyConstants.COUNTRY_LONG_CODE + " TEXT, "
                    + MyConstants.COUNTRY_CALLING_CODE + " TEXT)")
            db.execSQL(countryTable)
        } catch (e: Exception) {
            CommonMethods.showLog(TAG, "onCreate Exception " + e.message)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        try {
            onCreate(db)
        } catch (e: Exception) {
            CommonMethods.showLog(TAG, "onUpgrade Exception " + e.message)
        }
    }

    companion object {
        private const val DATABASE_VERSION = 1
    }
}
