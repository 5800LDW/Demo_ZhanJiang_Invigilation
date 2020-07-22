package com.tecsun.jc.base.utils

import android.content.Context
import android.telephony.TelephonyManager

import android.content.Context.TELEPHONY_SERVICE
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.tecsun.jc.base.common.BaseConstant

/**
 * 手机信息工具类
 */
object PhoneInfoUtils {

    fun getPhoneDeviceId(context: Context): String {
        var id = (context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager).deviceId
        if(id.isEmpty()){
            id = BaseConstant.DEFAULT_DEVICE_ID
        }
        return id
    }

    /**
     * 获取当前应用包的版本名称
     *
     * @param context
     * @return
     */
    fun getVersionName(context: Context): String {
        val packageInfo = getPackageInfo(context)
        return if (packageInfo != null) packageInfo!!.versionName else ""
    }

    /**
     * 获取包信息
     *
     * @param context
     * @return
     */
    private fun getPackageInfo(context: Context): PackageInfo? {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = context.packageManager.getPackageInfo(
                context.packageName, 0
            )
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return packageInfo
    }

}
