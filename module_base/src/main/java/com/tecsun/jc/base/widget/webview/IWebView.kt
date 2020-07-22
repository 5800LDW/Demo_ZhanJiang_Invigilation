package com.tecsun.jc.base.widget.webview

import android.view.View

interface IWebView{

    fun setWebViewClient(o:Any?)

    fun loadUrl(url:String?)

    fun canGoBack():Boolean

    fun goBack()

    fun getWebViewClient():Any?

    fun setBlockNetworkImage(b:Boolean?)

    fun getWebView(): View?

    fun setParam()

    fun setGeolocationParam()

    fun setParam(url:String?)

    fun release()

    fun loadData( data:String?,  mimeType:String,  encoding:String)


//    fun getSetting():Any?

}
