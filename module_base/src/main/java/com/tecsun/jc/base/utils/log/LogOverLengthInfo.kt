package com.tecsun.jc.base.utils.log

import android.util.Log

internal object LogOverLengthInfo {


    fun e(tag: String?, msg: Any?) {
        if (tag == null || msg == null) {
            Log.e("TAG", "tag 或 msg 为null")
            return
        }

        try {

            var const = 2000
            var start: Int = 0
            var end: Int = const
            if (msg.toString().length > end) {
                while (true) {
                    Log.e(tag, "--------- ${msg.toString().substring(start, end)} ------------")
                    start = end
                    end += const

                    if (end >= msg.toString().length) {
                        Log.e(
                            tag,
                            "--------- ${msg.toString().substring(
                                start,
                                msg.toString().length
                            )} ------------"
                        )
                        return
                    }
                }
            } else {
                Log.e(tag, "--------- ${msg.toString()} ------------")
            }


        } catch (e: Exception) {
            Log.e("TAG", "------------------- LogOverLengthInfo 发生错误 -----------------")
            e.printStackTrace()
        }


    }


}
