package com.tecsun.jc.base.builder

import android.content.Context
import ren.yale.android.cachewebviewlib.CacheType
import ren.yale.android.cachewebviewlib.WebViewCacheInterceptor
import ren.yale.android.cachewebviewlib.WebViewCacheInterceptorInst
import java.io.File

class WebViewCacheSettingBuilder {

    fun normalSet(context:Context) {
        val builder = WebViewCacheInterceptor.Builder(context)
        builder.setCachePath(File(context.cacheDir, "cache_path_name"))//设置缓存路径，默认getCacheDir，名称CacheWebViewCache
            .setCacheSize((1024 * 1024 * 300).toLong())//设置缓存大小，300M
            .setConnectTimeoutSecond(25)//设置http请求链接超时
            .setReadTimeoutSecond(25)//设置http请求链接读取超时
            .setCacheType(CacheType.FORCE)//强制缓存静态资源
            .setTrustAllHostname()//HostnameVerifier不验证，HostnameVerifier.verify()返回true，默认正常验证
        WebViewCacheInterceptorInst.getInstance().init(builder)
    }


}
