package com.napworks.screenlock.databasePackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.napworks.screenlock.modelPackage.AppDetailsModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseMethods {
    DatabaseClass dataBase;
    SQLiteDatabase db;
    private String TAG = getClass().getSimpleName();
    Context context;

    public DataBaseMethods(Context context) {
        dataBase = new DatabaseClass(context);
        db = dataBase.getWritableDatabase();
        this.context = context;
    }

    public void insertPackage(   ArrayList<AppDetailsModel> appDetailsModel) {
        Log.e(TAG,"size  list  =  " + appDetailsModel.size());
        long result =0 ;
        for (int i = 0; i < appDetailsModel.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseClass.PACKAGE_NAME, appDetailsModel.get(i).getPackageName());
            values.put(DatabaseClass.PACKAGE_SELECTED, appDetailsModel.get(i).isSelect());
            result = db.insert(DatabaseClass.PACKAGE_NAME_TABLE, null, values);
        }
        Log.e(TAG,"result    insertPackage  " + result);
    }


    public void updateChannelText(int selected , String packageName) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseClass.PACKAGE_SELECTED, selected);
        int result = db.update(DatabaseClass.PACKAGE_NAME_TABLE, contentValues, "package_name = ?", new String[]{String.valueOf(packageName)});

        Log.e(TAG,"result  after  update  " + result);


    }


    public List<AppDetailsModel> getPackageName() {
        List<AppDetailsModel> list = new ArrayList<>();
        Cursor cursor = db.query(DatabaseClass.PACKAGE_NAME_TABLE, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            AppDetailsModel appDetailsModel = new AppDetailsModel();
            appDetailsModel.setId(cursor.getInt(cursor.getColumnIndex(DatabaseClass.PACKAGE_NAME_ID)));
            appDetailsModel.setPackageName(cursor.getString(cursor.getColumnIndex(DatabaseClass.PACKAGE_NAME)));
            appDetailsModel.setSelect(cursor.getInt(cursor.getColumnIndex(DatabaseClass.PACKAGE_SELECTED)));
            list.add(appDetailsModel);
        }
        cursor.close();
//        Log.e(TAG,"size of keyWord list  " + list.size());
//        for (int i = 0; i < list.size(); i++) {
//            Log.e(TAG, "Id " + list.get(i).getId());
//            Log.e(TAG, "Package Name " + list.get(i).getPackageName());
//            Log.e(TAG, "isSelected " + list.get(i).isSelect());
//
//        }
        return  list;
    }

    public void deletePackageNames() {
        db.delete(DatabaseClass.PACKAGE_NAME_TABLE, null, null);
        Log.e(TAG, "deleted done ");
    }

}
