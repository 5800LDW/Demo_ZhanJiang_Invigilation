package com.tecsun.jc.demo.test

import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONObject

class HandleErrorInterceptor : ResponseBodyInterceptor() {
    override fun intercept(response: Response, url: String, body: String): Response {
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(body)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (jsonObject != null) {
            if (jsonObject.optInt("code", -1) != 200 && jsonObject.has("msg")) {
                throw ApiException(jsonObject.getString("msg"))
            }
        }
        return response
    }

//    override fun intercept(response: Response, body: String): Response {
//        var jsonObject: JSONObject? = null
//        try {
//            jsonObject = JSONObject(body)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        if (jsonObject != null) {
//            if (jsonObject.optInt("code", -1) != 200 && jsonObject.has("msg")) {
//                throw ApiException(jsonObject.getString("msg"))
//            }
//        }
//        return response
//    }


    fun test(){
        val okHttpClient = OkHttpClient.Builder()
            // 其它配置
            .addInterceptor(HandleErrorInterceptor())
            .build()
    }
}

