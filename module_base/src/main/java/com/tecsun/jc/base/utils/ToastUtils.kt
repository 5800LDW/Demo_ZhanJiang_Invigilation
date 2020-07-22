package com.tecsun.jc.base.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast

object ToastUtils {

    fun showGravityLongToast(context: Context, toastText: String?) {
        showToast(context, Gravity.CENTER, toastText, Toast.LENGTH_LONG)
    }

    fun showGravityShortToast(context: Context, toastText: String?) {
        showToast(context, Gravity.CENTER, toastText, Toast.LENGTH_SHORT)
    }

    fun showTopLongToast(context: Context, toastText: String?) {
        showToast(context, Gravity.TOP, toastText, Toast.LENGTH_LONG)
    }

    fun showTopShortToast(context: Context, toastText: String?) {
        showToast(context, Gravity.TOP, toastText, Toast.LENGTH_SHORT)
    }

    fun showBottomLongToast(context: Context, toastText: String?) {
        showToast(context, Gravity.BOTTOM, toastText, Toast.LENGTH_LONG)
    }

    fun showBottomShortToast(context: Context, toastText: String?) {
        showToast(context, Gravity.BOTTOM, toastText, Toast.LENGTH_SHORT)
    }

    @JvmStatic
    var toast :Toast? = null
    private fun showToast(context: Context, gravity: Int, toastText: String?, duration: Int) {
        if(toast == null){
            //context.getApplicationContext() 能解决内存泄露的问题
            toast = Toast.makeText(context.getApplicationContext(), toastText, duration)
        }else{
            toast!!.cancel()
            toast = Toast.makeText(context.getApplicationContext(), toastText, duration)
            toast!!.setText(toastText)
        }
        if (toast != null) {
            toast!!.setGravity(gravity, 0, 0)
            toast!!.show()
        }
    }


}