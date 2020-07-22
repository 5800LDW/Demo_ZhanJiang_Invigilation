package com.tecsun.jc.base.ui.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tecsun.jc.base.R
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.utils.WebViewUtil
import com.tecsun.jc.base.widget.TitleBar
import kotlinx.android.synthetic.main.activity_webview.*

@Route(path = RouterHub.ROUTER_WEB_VIEW)
class WebViewActivity : BaseActivity() {

    @JvmField
    @Autowired(name = BaseConstant.WEB_VIEW_URL)
    var webUrl: String? = null

    @JvmField
    @Autowired(name = BaseConstant.ACTIVITY_TITLE)
    var title: String? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_webview
    }

    override fun initView(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        setupSetting()

    }

    override fun onResume() {
        super.onResume()
        wv_web_view.loadUrl(webUrl)
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle(title ?: "江城人社")
    }

    private fun setupSetting() {
        wv_web_view.scrollBarStyle = WebView.SCROLLBARS_INSIDE_OVERLAY
        wv_web_view.isHorizontalScrollBarEnabled = false
        wv_web_view.overScrollMode = WebView.OVER_SCROLL_NEVER

        val settings = wv_web_view.settings
        settings.setSupportZoom(true)
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.defaultTextEncodingName = "UTF-8"
        settings.loadsImagesAutomatically = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        settings.blockNetworkImage = true
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.allowFileAccess = true
        settings.databaseEnabled = true
        settings.domStorageEnabled = true
        settings.setAppCacheEnabled(true)

        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        if (info != null && info.isConnected) {
            settings.cacheMode = WebSettings.LOAD_DEFAULT
        } else {
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }

        if (Build.VERSION.SDK_INT >= 16) {
            settings.allowFileAccessFromFileURLs = true
            settings.allowUniversalAccessFromFileURLs = true
        }
        settings.allowContentAccess = true

        wv_web_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view?.loadUrl(request?.url.toString())
                    return true
                }
                return false
            }
        }

        wv_web_view.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress < 100) {
                    pb_web_progress.visibility = View.VISIBLE
                    pb_web_progress.progress = newProgress
                } else {
                    pb_web_progress.visibility = View.GONE
                }
                super.onProgressChanged(view, newProgress)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && wv_web_view.canGoBack()) {
            wv_web_view.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (wv_web_view != null) {
//            val parent = wv_web_view.parent
//            if (parent != null) {
//                (parent as ViewGroup).removeView(wv_web_view)
//            }
//
//            wv_web_view.stopLoading()
//            wv_web_view.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
//            wv_web_view.settings.javaScriptEnabled = false
//            wv_web_view.clearHistory()
//            wv_web_view.clearView()
//            wv_web_view.removeAllViews()
//            wv_web_view.destroy()
            WebViewUtil.release(wv_web_view)
        }
    }

}