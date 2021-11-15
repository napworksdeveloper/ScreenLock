package com.napworks.screenlock.interfacePackage

import com.napworks.screenlock.modelPackage.AppDetailsModel

interface AppLockInterface {
    fun onAppLockClick(
        status: String?,
        appDetailsModel: AppDetailsModel,
        position: Int
    )
}