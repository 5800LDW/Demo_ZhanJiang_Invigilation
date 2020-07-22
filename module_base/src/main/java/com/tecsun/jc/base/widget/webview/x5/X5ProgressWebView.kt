package com.tecsun.jc.base.widget.webview.x5

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.tecsun.jc.base.R
import com.tecsun.jc.base.collector.BaseActivityCollector
import com.tecsun.jc.base.utils.log.LogUtil
import com.tencent.smtt.sdk.*





class X5ProgressWebView : BaseX5WebView {

    private var mProgressBar: ProgressBar? = null

    constructor(arg0: Context) : super(arg0) {
        init()
    }

    @SuppressLint("SetJavaScriptEnabled")
    constructor(arg0: Context, arg1: AttributeSet) : super(arg0, arg1) {
        init()
    }

    private fun init() {
        mProgressBar = ProgressBar(
            context, null,
            android.R.attr.progressBarStyleHorizontal
        )
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 8)
        mProgressBar?.layoutParams = layoutParams

        val drawable = context.resources.getDrawable(R.drawable.base_web_progress_bar_states)
        mProgressBar?.progressDrawable = drawable
        addView(mProgressBar)

        initWebViewSettings()

        webViewClient = myWebViewClient
        webChromeClient = myWebChromeClient
        view.isClickable = true
    }


    private val myWebViewClient = object : WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            return doShouldOverrideUrlLoading2(view, url)
        }

    }

    private val myWebChromeClient = object : WebChromeClient() {

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            LogUtil.e("ProgressWebView", newProgress)
            if (newProgress == 100) {
                mProgressBar?.visibility = View.GONE
            } else {
                if (mProgressBar?.visibility == View.GONE) {
                    mProgressBar?.visibility = View.VISIBLE
                }
                mProgressBar?.progress = newProgress
            }
            super.onProgressChanged(view, newProgress)
        }
    }


    private fun doShouldOverrideUrlLoading2(view: WebView, url: String?): Boolean {
        LogUtil.e("doShouldOverrideUrlLoading2 = " + url!!)


        if (url == null) {
            return false
        }
        //携程的协议不跳转到它的app
        if (url.startsWith("ctrip://")) {
            return true
        }


        if (url.startsWith("http:") || url.startsWith("https:")) {
            view.loadUrl(url)
            return true
        }

        try {
            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
            intent.addCategory("android.intent.category.BROWSABLE")
            intent.component = null
            val activity = BaseActivityCollector.getTopActivity()
            activity?.startActivity(intent)
            return true
        } catch (e: Exception) {
            LogUtil.e("发生错误$e")

        }
        return true

    }

    private fun initWebViewSettings() {
        val webSetting = this.settings
        webSetting.javaScriptEnabled = true
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        webSetting.allowFileAccess = true
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSetting.setSupportZoom(true)
        webSetting.builtInZoomControls = true
        webSetting.useWideViewPort = true
        webSetting.setSupportMultipleWindows(true)
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true)
        // webSetting.setDatabaseEnabled(true);
        webSetting.domStorageEnabled = true
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(java.lang.Long.MAX_VALUE)
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webSetting.cacheMode = WebSettings.LOAD_NO_CACHE

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
    }

    override fun drawChild(canvas: Canvas, child: View, drawingTime: Long): Boolean {
        val ret = super.drawChild(canvas, child, drawingTime)
        canvas.save()
        val paint = Paint()
        paint.color = 0x7fff0000
        paint.textSize = 24f
        paint.isAntiAlias = true
        if (x5WebViewExtension != null) {
            canvas.drawText(
                this.context.packageName + "-pid:"
                        + android.os.Process.myPid(), 10f, 50f, paint
            )
            canvas.drawText(
                "X5  Core:" + QbSdk.getTbsVersion(this.context), 10f,
                100f, paint
            )
        } else {
            canvas.drawText(
                (this.context.packageName + "-pid:"
                        + android.os.Process.myPid()), 10f, 50f, paint
            )
            canvas.drawText("Sys Core", 10f, 100f, paint)
        }
        canvas.drawText(Build.MANUFACTURER, 10f, 150f, paint)
        canvas.drawText(Build.MODEL, 10f, 200f, paint)
        canvas.restore()
        return ret
    }

}
