package com.napworks.screenlock.utilPackage

import android.app.Activity
import android.content.pm.PackageManager
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*

import android.os.Environment
import java.io.File


 class CommonMethods {
    companion object {

        val TAG: String = "CommonMethods"

         fun showLog(tag: String?, message: String?)
        {
            Log.e("$tag", "$message")
        }

        fun dateMonthYearSignUp(timeInMillis: Long): String? {
            val myFormat = "MM/dd/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            return sdf.format(Date(timeInMillis))
        }

        fun getCurrentDate(timeInMillis: Long): String? {
            val myFormat = "dd-MM-yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            return sdf.format(Date(timeInMillis))
        }

        fun getFullDate(timeInMillis: Long): String? {
            val myFormat = "dd MMMM, yyyy hh:mm a"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            return sdf.format(Date(timeInMillis))
        }

        fun getDateForOroscope(timeInMillis: Long): String? {
            val myFormat = "EEEE MMM dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            return sdf.format(Date(timeInMillis))
        }

        fun getFolderPath(activity: Activity): String? {
            val path = Environment.getExternalStorageDirectory().absolutePath+File.separator + "mohra"
            showLog(TAG, "path : $path")
            val direct = File(path)
            val result: Boolean = direct.mkdirs()
            showLog(TAG, "getImagesFolderPath result : $result")
            return path
        }

        fun getHashKey(activity: Activity) {
            try {
                val info = activity.packageManager.getPackageInfo(
                    activity.packageName,
                    PackageManager.GET_SIGNATURES
                )
                for (signature in info.signatures) {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
        }


//        fun loginWithFacebook(
//            callbackManager: CallbackManager?,
//            facebookInterfaceCallBack: FacebookInterfaceCallBack,
//        ) {
//            LoginManager.getInstance().registerCallback(callbackManager,
//                object : FacebookCallback<LoginResult> {
//                    override fun onSuccess(loginResult: LoginResult) {
//                        showLog(TAG, "onSuccess" + loginResult.accessToken.userId)
//                        val graphRequest = GraphRequest.newMeRequest(
//                            AccessToken.getCurrentAccessToken()
//                        ) { jsonObject, graphResponse ->
//                            showLog(TAG, "Success")
//                            if (graphResponse != null) {
//                                try {
//                                    val facebookDataModel = FacebookDataModel()
//                                    facebookDataModel.accountId = jsonObject.getString("id")
//                                    if (jsonObject.has("first_name")) {
//                                        showLog(
//                                            TAG,
//                                            "name " + jsonObject.getString("first_name")
//                                        )
//                                        facebookDataModel.firstName =
//                                            jsonObject.getString("first_name")
//                                    }
//                                    if (jsonObject.has("last_name")) {
//                                        facebookDataModel.lastName =
//                                            jsonObject.getString("last_name")
//                                        showLog(
//                                            TAG,
//                                            "last_name " + jsonObject.getString("last_name")
//                                        )
//                                    }
//                                    if (jsonObject.has("name")) {
//                                        facebookDataModel.userName = jsonObject.getString("name")
//                                        showLog(
//                                            TAG,
//                                            "fullName " + jsonObject.getString("name")
//                                        )
//                                    }
//                                    if (jsonObject.has("email")) {
//                                        facebookDataModel.email = jsonObject.getString("email")
//                                        showLog(
//                                            TAG,
//                                            "email " + jsonObject.getString("email")
//                                        )
//                                    } else {
//                                        showLog(TAG, "Email heni aayiii")
//                                    }
//                                    val urlImage =
//                                        "https://graph.facebook.com/" + jsonObject.getString("id") + "/picture?width=480&height=480"
//                                    facebookDataModel.profileImage = urlImage
//                                    facebookInterfaceCallBack.getFacebookCall(
//                                        "1",
//                                        facebookDataModel
//                                    )
//                                    showLog(TAG, "url image$urlImage")
//                                } catch (e: Exception) {
//                                    facebookInterfaceCallBack.getFacebookCall("0", null)
//                                }
//                            } else {
//                                facebookInterfaceCallBack.getFacebookCall("0", null)
//                            }
//                        }
//                        val bundle = Bundle()
//                        bundle.putString(
//                            "fields",
//                            "id,email,gender,birthday,name,first_name,last_name,timezone,location"
//                        )
//                        graphRequest.parameters = bundle
//                        graphRequest.executeAsync()
//                    }
//
//                    override fun onCancel() {
//                        showLog(TAG, "Cancel")
//                        facebookInterfaceCallBack.getFacebookCall("0", null)
//                        showLog(CommonMethods.TAG, "cancel")
//                    }
//
//                    override fun onError(error: FacebookException) {
//                        facebookInterfaceCallBack.getFacebookCall("0", null)
//                        showLog(CommonMethods.TAG, "error " + error.message)
//                    }
//                })
//        }

        fun getWidth(activity: Activity): Int {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.widthPixels
        }

        fun getHeight(context: Activity): Int {
            val displayMetrics = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.heightPixels
        }

//        fun callLogout(activity: Activity) {
////            Toast.makeText(
////                activity,
////                activity.getString(R.string.),
////                Toast.LENGTH_SHORT
////            ).show()
//
//            val sharedPreferences =
//                activity.getSharedPreferences(MyConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE)
//            val editor = sharedPreferences.edit()
//            editor.clear()
//            editor.apply()
//            activity.finishAffinity()
//            activity.startActivity(Intent(activity, SplashScreenActivity::class.java))
//        }

        fun timeAgoDisplay(secondsAgo : Long) : String {

            if (secondsAgo < 60) {
                return "$secondsAgo seconds ago"
            }

            else if (secondsAgo < 60 * 60) {
                return ""+secondsAgo/60 +" minutes ago"
            }

            else if (secondsAgo < 60 * 60 * 24 )
            {
                return ""+secondsAgo / 60 / 60 + " hours ago"
            }
            else if (secondsAgo < 60 * 60 * 24 * 7){
                return ""+secondsAgo / 60 / 60 / 24 +" days ago"
            }
            return ""+secondsAgo / 60 / 60 / 24 / 7+" weeks ago"
        }

        fun getScreenDensity(activity: Activity): Float {
            val metrics = activity.resources.displayMetrics
            return metrics.density
        }
    }
}