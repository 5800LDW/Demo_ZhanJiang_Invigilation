package com.tecsun.jc.base.utils

import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.tecsun.jc.base.listener.HandlerCallback
import java.lang.ref.WeakReference

class SafeHandler(activity: AppCompatActivity, private val callback: HandlerCallback) : Handler() {

    private var ref: WeakReference<AppCompatActivity>? = null

    init {
        this.ref = WeakReference(activity)
    }

    override fun handleMessage(msg: Message?) {
        val activity = ref?.get()
        if (activity != null) {
            callback.handleMessage(msg)
        }
        super.handleMessage(msg)
    }
}