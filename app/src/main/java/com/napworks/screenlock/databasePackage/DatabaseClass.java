package com.napworks.screenlock.databasePackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.napworks.mohra.utilPackage.MyConstants;
import com.napworks.screenlock.utilPackage.CommonMethods;


public class DatabaseClass extends SQLiteOpenHelper {
    String TAG = getClass().getSimpleName();
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = MyConstants.DATABASE_NAME;

    public static String PACKAGE_NAME_TABLE = "package_name_table";

    public static String PACKAGE_NAME_ID = "package_name_id";
    public static String PACKAGE_NAME = "package_name";
    public static String PACKAGE_SELECTED = "PACKAGE_SELECTED";


    private static final String CREATE_CALL_LOG_TABLE = "CREATE TABLE IF NOT EXISTS " + PACKAGE_NAME_TABLE + " (" +
            PACKAGE_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PACKAGE_SELECTED + " TEXT, " +
            PACKAGE_NAME + " TEXT)";

    public DatabaseClass(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try
        {
            sqLiteDatabase.execSQL(CREATE_CALL_LOG_TABLE);
        }
        catch (Exception e)
        {
            Log.e(TAG, "tableCreate " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}