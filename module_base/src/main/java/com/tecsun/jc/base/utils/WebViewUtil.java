package com.tecsun.jc.base.utils;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.tecsun.jc.base.JinLinApp;
import com.tecsun.jc.base.utils.log.LogUtil;
import com.tecsun.jc.base.widget.webview.x5.BaseX5WebView;
import com.tencent.smtt.sdk.CookieSyncManager;

import static java.lang.Long.MAX_VALUE;


final public class WebViewUtil {


    final public static void set(WebView webView) {
        if (webView != null) {
            WebSettings settings = webView.getSettings();

            //设置缓存模式,这里使用的默认,
//            settings.setCacheMode(WebSettings.LOAD_DEFAULT);

            //提高网页渲染的优先级
            settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //当涉及到混合式内容时，WebView 会尝试去兼容最新Web浏览器的风格。
                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                //设置是否允许通过 file url 加载的 Js代码读取其他的本地文件
                settings.setAllowFileAccessFromFileURLs(true);
                //设置是否允许通过 file url 加载的 Javascript 可以访问其他的源(包括http、https等源)
                settings.setAllowUniversalAccessFromFileURLs(true);
            }


            //Web SQL Database 存储机制：
            //虽然已经不推荐使用了，但是为了兼容性
            settings.setDatabaseEnabled(true);
            settings.setDatabasePath(JinLinApp.getContext().getDir("db", Context.MODE_PRIVATE).getPath());


            //启用H5缓存
            settings.setAppCacheEnabled(true);
            final String cachePath = JinLinApp.getContext().getDir("cache", Context.MODE_PRIVATE).getPath();
            settings.setAppCachePath(cachePath);
            //根据官方文档，AppCache 已经不推荐使用了，标准也不会再支持。现在主流的浏览器都是还支持 AppCache的，以后就不太确定了
            settings.setAppCacheMaxSize(MAX_VALUE);
//            settings.setAppCacheMaxSize(8*1024*1024);


            settings.setSupportMultipleWindows(false);





            //设置支持javascript脚本
            settings.setJavaScriptEnabled(true);

            //DOM存储API是否可用，默认false
            settings.setDomStorageEnabled(true);

            //是否禁止从网络（通过http和https URI schemes访问的资源）下载图片资源，默认值为false
            settings.setBlockNetworkImage(false);

            //支持通过JS打开新窗口
            settings.setJavaScriptCanOpenWindowsAutomatically(true);

            //支持自动加载图片
            settings.setLoadsImagesAutomatically(true);

            //使其不能加载本地的 html 文件
            settings.setAllowFileAccess(true);

//            //支持缩放
            settings.setSupportZoom(true);
//            //支持缩放,必须加
            settings.setBuiltInZoomControls(true);
//            //不显示缩放按钮
            settings.setDisplayZoomControls(false);

            //页面自适应手机屏幕(不推荐)
            settings.setUseWideViewPort(false);
            //页面自适应手机屏幕(推荐)
            settings.setLoadWithOverviewMode(true);

            //所有的视图内容都会移动到该宽度的View中,并形成一列.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            } else {
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            }
            //意味着没有呈现变化,建议选择当前最大的安卓版本,为了在不同的平台的Android版本进行兼容.
//          settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


//            webView.setWebChromeClient(new WebChromeClient());

            if (webView.isHardwareAccelerated()) {
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }

//            int screenDensity = JinLinApp.getContext().getResources().getDisplayMetrics().densityDpi;
//            WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;
//            if (screenDensity == DisplayMetrics.DENSITY_LOW) {
//                zoomDensity = WebSettings.ZoomDensity.CLOSE;
//            } else if (screenDensity == DisplayMetrics.DENSITY_MEDIUM) {
//                zoomDensity = WebSettings.ZoomDensity.MEDIUM;
//            } else if (screenDensity == DisplayMetrics.DENSITY_HIGH) {
//                zoomDensity = WebSettings.ZoomDensity.FAR;
//            }
//            LogUtil.e("WebViewUtil", "1");
//            settings.setDefaultZoom(zoomDensity);




            ////4.4版本以上无法保存cookie，要是用代码去判断，开启第三方的cookie支持
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptThirdPartyCookies(webView,true);
            }
        }
    }


    final public static void set(WebView webView, String url) {
        set(webView);
        if (webView != null) {
            WebSettings settings = webView.getSettings();

            //允许加载本地file
            settings.setAllowFileAccess(true);

            // 使用 file 域加载的 js代码能够使用进行同源策略跨域访问，从而导致隐私信息泄露,所以禁止 file 协议加载 JavaScript
            if (url != null && url.startsWith("file://")) {
                settings.setJavaScriptEnabled(false);
            } else {
                settings.setJavaScriptEnabled(true);
            }
        }
    }

    final public static void setGeolocation(WebView webView) {
        if (webView != null) {
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setDatabaseEnabled(true);
            //设置定位的数据库路径
            String dir = JinLinApp.getContext().getDir("geolocation", Context.MODE_PRIVATE).getPath();
            settings.setGeolocationDatabasePath(dir);
            //启用地理定位
            settings.setGeolocationEnabled(true);
            //开启DomStorage缓存
            settings.setDomStorageEnabled(true);
        }
    }


    final public static void release(WebView webView) {
        if (webView != null) {


            ViewGroup parent = (ViewGroup) webView.getParent();
            //父容器中移除webView
            parent.removeView(webView);
            webView.stopLoading();
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.getSettings().setJavaScriptEnabled(false);
            //清除缓存,解决可能出现的白屏
//            webView.clearCache(true);
            webView.clearHistory();
            //大多数时间会有bug,所有废弃;
//            webView.clearView();
            webView.clearFormData();
            webView.loadUrl("about:blank");
            webView.removeAllViews();
            try {
                webView.destroy();
            } catch (Throwable t) {
                LogUtil.e("WebViewUtil", t);
            }
        }
    }


    final public static void x5Set(BaseX5WebView mWebView) {

        LogUtil.e("x5Set>>>>>>>>>>");

        com.tencent.smtt.sdk.WebSettings webSetting = mWebView.getSettings();
        //是否允许访问文件
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //是否支持缩放
        webSetting.setSupportZoom(true);
        //是否显示缩放控制栏
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(JinLinApp.getContext().getDir("cache", 0).getPath());
        webSetting.setDatabasePath(JinLinApp.getContext().getDir("db", 0).getPath());
        webSetting.setGeolocationDatabasePath(JinLinApp.getContext().getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(com.tencent.smtt.sdk.WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);


        webSetting.setJavaScriptEnabled(true);
        webSetting.setSupportZoom(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setDefaultTextEncodingName("UTF-8");
        webSetting.setLoadsImagesAutomatically(true);
        webSetting.setBlockNetworkImage(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(false);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);


        CookieSyncManager.createInstance(JinLinApp.getContext());
        CookieSyncManager.getInstance().sync();
    }


    final public static void x5Release(BaseX5WebView mWebView) {
        if (mWebView != null) {
            mWebView.destroy();
        }
    }


    /***处理下图片大小*/
    final public static String fitImageSize(String str){
        if(str == null){
            return str;
        }
        else{

//            int width = ScreenUtil.getScreenWidth(BaseActivityCollector.getTopActivity());
//            int margin = (int) (width*0.04);
            return str.replace("<img", "<img style='max-width:90%;height:auto;vertical-align:middle;margin-Left:5%;margin-Right:5%'");
        }
    }

}
