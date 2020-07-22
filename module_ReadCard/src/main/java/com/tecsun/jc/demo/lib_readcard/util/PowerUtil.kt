package com.tecsun.jc.demo.lib_readcard.util

import com.common.pos.api.util.posutil.PosUtil
import com.tecsun.jc.base.JinLinApp
import com.telpo.tps550.api.fingerprint.FingerPrint
import com.telpo.tps550.api.util.ShellUtils

object PowerUtil{

    fun readSFZPowerOff(){
        if (android.os.Build.MODEL.contains("TPS350")) {
            PosUtil.setFingerPrintPower(PosUtil.FINGERPRINT_POWER_OFF)
            // PosUtil.setFingerPrintPower(PosUtil.FINGERPRINT_POWER_ON);
        } else if (android.os.Build.MODEL.contains("TPS616")) {
            ShellUtils.execCommand(
                "echo 4 >/sys/class/telpoio/power_status",
                true
            )// usb

        } else if (android.os.Build.MODEL.contains("TPS520A")) {
            FingerPrint.fingerPrintPower(0)
        } else {
            PosUtil.setIdCardPower(PosUtil.IDCARD_POWER_OFF)
        }

        JinLinApp.isIDCardModulePowerOff = true

        try {
            Thread.sleep(2000)
        } catch (e1: InterruptedException) {
            e1.printStackTrace()
        }
    }





}
