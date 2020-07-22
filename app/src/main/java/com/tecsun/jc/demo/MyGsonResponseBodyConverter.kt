//package com.tecsun.jc.demo
//
////import android.util.JsonToken
////import com.bumptech.glide.load.model.ByteArrayLoader
////import com.google.gson.Gson
////import com.google.gson.JsonIOException
////import com.google.gson.TypeAdapter
////import okhttp3.ResponseBody
////import okhttp3.ResponseBody
//import android.util.JsonToken
//import com.bumptech.glide.load.model.ByteArrayLoader
//import com.google.gson.Gson
//import com.google.gson.JsonIOException
//import com.google.gson.TypeAdapter
//import okhttp3.ResponseBody
//import java.io.IOException
//
//class MyGsonResponseBodyConverter<T>(
//    private val gson: Gson,
//    private val adapter: TypeAdapter<T>
//) : ByteArrayLoader.Converter<ResponseBody, T> {
//
//    @Throws(IOException::class)
//    override fun convert(value: ResponseBody): T {
//
//        // 在这里通过 value 拿到 json 字符串进行解析
//        // 判断状态码是失败的情况，就抛出异常
//
//        val jsonReader = gson.newJsonReader(value.charStream())
//        value.use {
//            val result = adapter.read(jsonReader)
//            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
//                throw JsonIOException("JSON document was not fully consumed.")
//            }
//            return result
//        }
//    }
//}