package com.tecsun.jc.base.widget.webview

import android.content.Context
import android.view.View
import com.tecsun.jc.base.utils.WebViewUtil
import com.tecsun.jc.base.widget.webview.x5.X5ProgressWebView
import com.tencent.smtt.sdk.WebViewClient

class X5WebViewBuilder(c: Context) : IWebView {
    var x5ProgressWebView: X5ProgressWebView? = null
    private val context = c

    init {
        x5ProgressWebView = X5ProgressWebView(context)
    }

    override fun loadData(data: String?, mimeType: String, encoding: String) {
        x5ProgressWebView?.loadData(data, mimeType, encoding)
    }

    override fun setWebViewClient(o: Any?) {
        if (o != null && o is WebViewClient) {
            x5ProgressWebView?.webViewClient = o
        }
    }

    override fun loadUrl(url: String?) {
        x5ProgressWebView?.loadUrl(url)
    }

    override fun canGoBack(): Boolean {
        return x5ProgressWebView?.canGoBack() ?: false
    }

    override fun goBack() {
        x5ProgressWebView?.goBack()
    }

    override fun getWebViewClient(): Any? {
        return x5ProgressWebView?.webViewClient
    }

    override fun setBlockNetworkImage(b: Boolean?) {
        //x5 没有这个方法;
    }

    override fun getWebView(): View? {
        return x5ProgressWebView
    }

    override fun setParam() {
        WebViewUtil.x5Set(x5ProgressWebView)
    }

    override fun setGeolocationParam() {
        // TODO
    }

    override fun setParam(url: String?) {
        WebViewUtil.x5Set(x5ProgressWebView)
    }

    override fun release() {
        WebViewUtil.x5Release(x5ProgressWebView)
    }


}
