package com.tecsun.jc.base.builder

import android.content.Context
import android.util.Log
import com.tencent.smtt.sdk.QbSdk

class X5WebViewSettingBuilder {

    fun set(context: Context) {
        val cb = object : QbSdk.PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("TAG", " onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {}
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(context.applicationContext, cb)
    }
}
