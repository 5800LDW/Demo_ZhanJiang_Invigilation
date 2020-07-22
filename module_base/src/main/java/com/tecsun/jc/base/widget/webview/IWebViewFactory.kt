package com.tecsun.jc.base.widget.webview

import android.content.Context

object IWebViewFactory {

    open fun get(context :Context): IWebView {
        return WebViewBuilder(context)
//        return X5WebViewBuilder(context)
    }


}
