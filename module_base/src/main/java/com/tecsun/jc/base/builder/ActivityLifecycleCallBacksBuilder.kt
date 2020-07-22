package com.tecsun.jc.base.builder

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.WindowManager

/**
 * 生命周期检测
 *
 * @author liudongwen
 * @date 2019/8/10
 */
object ActivityLifecycleCallBacksBuilder{

     fun set(application: Application){
        application?.let {
            application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityPaused(activity: Activity?) {
                }

                override fun onActivityResumed(activity: Activity?) {
                }

                override fun onActivityStarted(activity: Activity?) {
                }

                override fun onActivityDestroyed(activity: Activity?) {
                }

                override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
                }

                override fun onActivityStopped(activity: Activity?) {
                }

                override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
//                    LogUtil.e("activity?.localClassName")
//                    LogUtil.e(activity?.localClassName)
                    activity?.let {
                        if(activity!!.localClassName == "com.nantian.facedetectlib.XSDetectFaceActivity"){
                            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                        }
                    }
                }
            })
        }
    }



}
