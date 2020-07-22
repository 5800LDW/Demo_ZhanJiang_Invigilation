package com.tecsun.jc.base.utils

import android.app.ActivityManager
import android.content.Context
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.utils.log.LogUtil


object ExitUtils {

    fun exit(){
        var mActivityManager = JinLinApp.context?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        mActivityManager?.let {
            var mList = mActivityManager.getRunningAppProcesses()
            for ( runningAppProcessInfo in mList) {
                if (runningAppProcessInfo.pid != android.os.Process.myPid()) {
                    LogUtil.e("runningAppProcessInfo.pid = "+runningAppProcessInfo.pid)
                    android.os.Process.killProcess(runningAppProcessInfo.pid)
                }
            }
        }

        android.os.Process.killProcess(android.os.Process.myPid())
    }

}