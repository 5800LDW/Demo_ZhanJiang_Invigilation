package com.tecsun.jc.demo.invigilation.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.listener.HandlerCallback
import com.tecsun.jc.base.listener.IEvents
import com.tecsun.jc.base.sign.APPSignUtils
import com.tecsun.jc.base.utils.PermissionsUtils
import com.tecsun.jc.base.utils.PhoneInfoUtils
import com.tecsun.jc.base.utils.SafeHandler
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.zhanjiang.ZhanJiangActivity
import com.tecsun.jc.demo.test.TestBuilder


class StartPageActivity : AppCompatActivity(), HandlerCallback {

    private val TAG = StartPageActivity::class.java.simpleName

    private val DELAY_MILLIS = 500
    lateinit var handler: SafeHandler

//    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        if (!isTaskRoot) {
            finish()
            return
        }

       openFullScreenModel()

//       setContentView(R.layout.app_activity_start_page)



        handler = SafeHandler(this, this)

        getPhoneState()
        APPSignUtils.signApp()

    TestBuilder.test()
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.e(TAG,">>>>>>>>>>>>>>>>>>>>>>>> StartPageActivity onDestroy ")
    }






    /**
     * 动态获取手机信息权限
     */
    private fun getPhoneState() {
        if (PermissionsUtils.isRequestPermission()) {
            if (PermissionsUtils.startRequestPermission(
                    this,
                    PermissionsUtils.MANY_STATE_PERMISSIONS,
                    0x001
                )
            ) {
                handler.sendEmptyMessageDelayed(1000, DELAY_MILLIS.toLong())
                JinLinApp.mDeviceId = PhoneInfoUtils.getPhoneDeviceId(this)
            }
        } else {
            handler.sendEmptyMessageDelayed(1000, DELAY_MILLIS.toLong())
            JinLinApp.mDeviceId = PhoneInfoUtils.getPhoneDeviceId(this)
        }
    }

    override fun handleMessage(msg: Message?) {
        when (msg?.what) {
            1000 -> {
                val intent = Intent(
                    this@StartPageActivity,
                    ZhanJiangActivity::class.java
                )
                startActivity(intent)
                finish()
            }
            else -> {
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (PermissionsUtils.requestPermissionResult(this, permissions, grantResults)) {
            JinLinApp.mDeviceId = PhoneInfoUtils.getPhoneDeviceId(this)
            handler.sendEmptyMessageDelayed(1000, DELAY_MILLIS.toLong())
        } else {

            val event = IEvents {
                finish()
                System.exit(0)
            }
//            PermissionTipsShowBuilder.show(
//                this,
//                PermissionsUtils.MANY_STATE_PERMISSIONS,
//                event,
//                event
//            )


        }
    }


    private fun openFullScreenModel() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val lp = window.attributes

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            //设置绘图区域可以进入刘海屏区域
            lp.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        window.attributes = lp
        val decorView = window.decorView
        val uiOptions = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        decorView.systemUiVisibility = uiOptions
    }

}