package com.napworks.screenlock.utilPackage;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.napworks.mohra.utilPackage.MyConstants;
import com.napworks.screenlock.modelPackage.AppDetailsModel;

import java.lang.reflect.Type;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BackgroundServices extends Service {

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;
    String packageName = "";
    String TAG = "BackgroundServices";
    public SharedPreferences sharedPreferences = null;
    List<AppDetailsModel> lockedAppName = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(TAG," OnCreate BackgroundServices");
        sharedPreferences = context.getSharedPreferences(MyConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();
        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
//               Log.e(TAG,"Service is still running");
                 String currentPackageName = retriveNewApp();
                for(int l=0; l<getList().size(); l++) {
                    if (getList().get(l).isSelect()) {
//                        Log.e(TAG,"Selected  = >  " + getList().get(l).getPackageName());
//                        Log.e(TAG,"Selected   =>  " + getList().get(l).getPackageName()  + " Current  => " + currentPackageName);
                        if (getList().get(l).getPackageName().equalsIgnoreCase(currentPackageName)) {
                            Intent intent = new Intent(currentPackageName);
                            intent.setClassName("com.napworks.screenlock", "com.napworks.screenlock.activityPackage.LockScreenActivity");
//                            intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                    else
                    {

                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

//        handler = new Handler();
//        runnable = new Runnable() {
//            public void run()
//            {
//                Log.e(TAG,"Service is still running");
//                 String currentPackageName = retriveNewApp();
//                for(int l=0; l<getList().size(); l++){
//                    if(getList().get(l).isSelect())
//                    {
//                        if(getList().get(l).getPackageName().equalsIgnoreCase(currentPackageName))
//                        {
//                        Intent intent = new Intent(currentPackageName);
//                        intent.setClassName("com.napworks.screenlock", "com.napworks.screenlock.activityPackage.LockScreenActivity");
//                        intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        }
//                    }
//                    else
//                    {
//
//                    }
//                }
//                handler.postDelayed(runnable, 5000);
//            }
//        };
//        handler.postDelayed(runnable, 5000);
    }

    public List<AppDetailsModel> getList()
    {
        List<AppDetailsModel> arrayItems = null;
        String serializedObject = sharedPreferences.getString(MyConstants.APP_LIST, null);

        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<AppDetailsModel>>() {
            }.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
        return arrayItems;
    }

    private String retriveNewApp() {
        if (Build.VERSION.SDK_INT >= 21)
        {
            String currentApp = null;
            UsageStatsManager usm = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> applist = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
            if (applist != null && applist.size() > 0)
            {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<>();
                for (UsageStats usageStats : applist) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
            return currentApp;
        }
        else
        {
            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            String mm=(manager.getRunningTasks(1).get(0)).topActivity.getPackageName();
            return mm;
        }
    }

    @Override
    public void onDestroy()
    {
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startid)
    {
        Toast.makeText(this, "Service started by user.", Toast.LENGTH_LONG).show();
    }
}
