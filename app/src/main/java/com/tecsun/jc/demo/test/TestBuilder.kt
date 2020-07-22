package com.tecsun.jc.demo.test

import com.tecsun.jc.base.utils.log.LogUtil
import org.json.JSONArray
import org.json.JSONObject



object TestBuilder {
    fun test() {

        val X1 = JSONArray()
        val X2 = JSONObject("{}")

        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject("{\"usnam\":\"SAPSZ\",\"ltrans\":\"ZFGCK\"}")
            jsonObject.put("usnam", "登录成功")
            jsonObject.put("X1", X1)
            jsonObject.put("X2", X2)
            jsonObject.put("X3", "")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        LogUtil.e(">>>>>>>>>>>>>>>>打印 ${jsonObject.toString()}")
    }
}
















