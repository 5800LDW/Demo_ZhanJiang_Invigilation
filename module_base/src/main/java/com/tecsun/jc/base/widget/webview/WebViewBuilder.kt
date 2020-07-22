package com.tecsun.jc.base.widget.webview

import android.content.Context
import android.os.Build
import android.view.View
import android.webkit.WebViewClient
import com.tecsun.jc.base.utils.WebViewUtil
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.ProgressWebView
import ren.yale.android.cachewebviewlib.WebViewCacheInterceptorInst

class WebViewBuilder(c: Context) : IWebView {


    private var progressWebView: ProgressWebView? = null
    private val context = c

    init {
        progressWebView = ProgressWebView(context)
    }

    override fun loadData(data: String?, mimeType: String, encoding: String) {

        LogUtil.e(">>>>>>>>>>>>>>>>  loadData")
        LogUtil.e(data)

        var newData = data
        if (data != null) {
            newData = WebViewUtil.fitImageSize(data)
        }

        progressWebView?.loadData(newData ?: "", mimeType, encoding)

//        var css = "<style type=\"text/css\"> </style>";
//        var html = "<html><header><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no>"+css+"</header>"+"<body>"+data+"</body>"+"</html>";
//        progressWebView?.loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
    }

    override fun setWebViewClient(o: Any?) {
        if (o != null && o is WebViewClient) {
            progressWebView?.webViewClient = o
        }
    }

    override fun loadUrl(url: String?) {
        if (progressWebView != null && url != null && !url.endsWith("html")) {
            WebViewCacheInterceptorInst.getInstance().loadUrl(progressWebView, url)
        } else if (progressWebView != null && url != null && url.endsWith("html")) {
            progressWebView?.loadUrl(url)
        }
    }

    override fun canGoBack(): Boolean {
        return progressWebView?.canGoBack() ?: false
    }

    override fun goBack() {
        progressWebView?.goBack()
    }

    override fun getWebViewClient(): Any? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return progressWebView?.WebChromeClient()
        } else {
            return null
        }
    }

    override fun setBlockNetworkImage(b: Boolean?) {
        progressWebView?.settings?.blockNetworkImage = b ?: true
    }

    override fun getWebView(): View? {
        return progressWebView
    }

    override fun setParam() {
        WebViewUtil.set(progressWebView)
    }

    override fun setGeolocationParam() {
        WebViewUtil.setGeolocation(progressWebView)
    }

    override fun setParam(url: String?) {
        WebViewUtil.set(progressWebView, url)
    }

    override fun release() {
        WebViewUtil.release(progressWebView)
    }

}
