package com.napworks.screenlock.activityPackage

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import android.view.View
import com.napworks.screenlock.R
import com.napworks.screenlock.adapterPackage.AppsAdapter
import android.content.pm.PackageManager
import android.provider.Settings
import com.google.gson.Gson
import com.napworks.mohra.utilPackage.MyConstants
import com.napworks.screenlock.interfacePackage.AppLockInterface
import com.napworks.screenlock.modelPackage.AppDetailsModel
import com.napworks.screenlock.utilPackage.CommonMethods
import android.app.AppOpsManager
import android.content.Context
import android.os.Process
import android.util.Log
import com.napworks.screenlock.databasePackage.DataBaseMethods
import com.napworks.screenlock.dialogPackage.AppUsageConfirmationDialog
import com.napworks.screenlock.interfacePackage.ConfirmationInterface
import com.napworks.screenlock.utilPackage.BackgroundServices
import com.napworks.screenlock.utilPackage.LoadingDialog


class AppsActivity : AppCompatActivity() , AppLockInterface, ConfirmationInterface {
    var TAG: String = javaClass.simpleName
    private val REQUEST_READ_PHONE_STATE = 999
    var recyclerView: RecyclerView? = null
    var adapter: RecyclerView.Adapter<*>? = null
    var recyclerViewLayoutManager: RecyclerView.LayoutManager? = null
    var appDetailsList: ArrayList<AppDetailsModel>? = null
    private var sharedPreferences: SharedPreferences? = null
    var appUsageConfirmationDialog: AppUsageConfirmationDialog? = null
    var loadingDialog: LoadingDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingDialog = LoadingDialog(this)
        appDetailsList = ArrayList<AppDetailsModel>()
        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        sharedPreferences = this!!.getSharedPreferences(MyConstants.SHARED_PREFERENCE, AppCompatActivity.MODE_PRIVATE)
        startService(Intent(this, BackgroundServices::class.java))
        if(DataBaseMethods(this).packageName.size <= 0)
        {
            DataBaseMethods(this).insertPackage(apkDataExtractor())
        }
        appDetailsList!!.addAll(DataBaseMethods(this).packageName)

        recyclerViewLayoutManager = GridLayoutManager(this@AppsActivity, 1)
        recyclerView!!.layoutManager = recyclerViewLayoutManager
        adapter = AppsAdapter(this, appDetailsList, this)
        recyclerView!!.adapter = adapter


        appUsageConfirmationDialog = AppUsageConfirmationDialog(this, this)
        requestPremissions(this)
    }

    override fun onResume()
    {
        super.onResume()
        startService(Intent(this, BackgroundServices::class.java))
    }

//    override fun onDestroy() {
//        //stopService(mServiceIntent);
//        val broadcastIntent = Intent()
//        broadcastIntent.action = "restartservice"
//        broadcastIntent.setClass(this, Restarter::class.java)
//        this.sendBroadcast(broadcastIntent)
//        super.onDestroy()
//    }

    private fun requestDialog()
    {
        appUsageConfirmationDialog!!.showDialog(
            MyConstants.GIVE_PERMISSION,
            getString(R.string.setKeyboard),
            getString(R.string.setKeyboardMessage)
        )
    }
    private fun requestPremissions(context : Context)
    {
        var granted = false
        val appOps = context.getSystemService(APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), context.packageName)
        if (mode == AppOpsManager.MODE_DEFAULT)
        {
//            CommonMethods.showLog(TAG,"if enter")
//            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
//            context.checkCallingOrSelfPermission(Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED
             requestDialog()
        }
        else
        {
            startService(Intent(this, BackgroundServices::class.java))
            CommonMethods.showLog(TAG,"else")
            mode == AppOpsManager.MODE_ALLOWED
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun apkDataExtractor () : ArrayList<AppDetailsModel>?
    {
        val flags = PackageManager.GET_META_DATA or
                PackageManager.GET_SHARED_LIBRARY_FILES or
                PackageManager.GET_UNINSTALLED_PACKAGES

        val pm = packageManager
        val applications = pm.getInstalledApplications(flags)
        for (appInfo in applications)
        {
            var packageName = appInfo.packageName
            appDetailsList!!.add(AppDetailsModel(0,packageName,0))
        }
        return appDetailsList
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onAppLockClick(status: String?, appDetailsModel: AppDetailsModel, position: Int)
    {
        CommonMethods.showLog(TAG,"position   => " + position)
        CommonMethods.showLog(TAG,"position  id => " + appDetailsModel.id)
        if(appDetailsList!![position].isSelect == 1)
        {
            appDetailsList!![position].isSelect = 0
            DataBaseMethods(this).updateChannelText(0,appDetailsModel.packageName)
        }
        else
        {
            appDetailsList!![position].isSelect = 1
            DataBaseMethods(this).updateChannelText(1,appDetailsModel.packageName)
        }

        adapter!!.notifyDataSetChanged()
    }

//    fun <AppDetailsModel> setList(key: String?, list: List<AppDetailsModel>?) {
//        val gson = Gson()
//        val json: String = gson.toJson(list)
//        set(key, json)
//    }

//    operator fun set(key: String?, value: String?) {
//        val editor = sharedPreferences!!.edit()
//        editor.putString(key, value)
//        editor.apply()
//    }

    override fun confirmationResponse(id: String?, confirmationValue: String?, context: Context)
    {
        if(id == MyConstants.GIVE_PERMISSION)
        {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
            context.checkCallingOrSelfPermission(Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED
        }
    }
}