package com.tecsun.jc.demo.invigilation.builder

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Handler
import android.provider.Settings
import com.tecsun.jc.base.base.BaseActivity

/**
 * @author liudongwen
 * @date 2019/10/14
 */
object NFCBuilder {
    fun isNFCReady(activity: BaseActivity?): Boolean {
        var nfcAdapter = NfcAdapter.getDefaultAdapter(activity)
        if (nfcAdapter == null) {
            activity?.showErrorMessageDialog2("该设备不支持NFC功能!")
            return false
        }

        if (nfcAdapter != null && !nfcAdapter.isEnabled()) {
            activity?.showToast("请打开NFC!")
            Handler().postDelayed({
                activity?.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
            },500)

            return false
        }

        return true
    }

}
















