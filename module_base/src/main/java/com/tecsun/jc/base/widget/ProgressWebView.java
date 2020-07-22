package com.tecsun.jc.base.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tecsun.jc.base.R;
import com.tecsun.jc.base.collector.BaseActivityCollector;
import com.tecsun.jc.base.common.BaseConstant;
import com.tecsun.jc.base.common.CommonApi;
import com.tecsun.jc.base.common.RouterHub;
import com.tecsun.jc.base.utils.log.LogUtil;

import ren.yale.android.cachewebviewlib.WebViewCacheInterceptorInst;

public class ProgressWebView extends WebView {
    private ProgressBar mProgressBar;

    public ProgressWebView(Context context) {
        super(context);
        init(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mProgressBar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 8);
        mProgressBar.setLayoutParams(layoutParams);

        Drawable drawable = context.getResources().getDrawable(
                R.drawable.base_web_progress_bar_states);
        mProgressBar.setProgressDrawable(drawable);
        addView(mProgressBar);
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(myWebViewClient());


    }

    public class WebChromeClient extends android.webkit.WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            LogUtil.e("ProgressWebView", newProgress);
            if (newProgress == 100) {
                mProgressBar.setVisibility(GONE);
            } else {
                if (mProgressBar.getVisibility() == GONE) {
                    mProgressBar.setVisibility(VISIBLE);
                }
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    }

    private WebViewClient myWebViewClient() {
        return new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //加载完成后允许图片加载
                ProgressWebView.this.getSettings().setBlockNetworkImage(false);
                //加载完成后启动硬件渲染加速
                ProgressWebView.this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                ToastUtils.INSTANCE.showGravityShortToast(JinLinApp.getContext(), "加载失败");
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // handler.cancel();// 默认的处理方式
                handler.proceed();// 接受所有网站的证书
                // handleMessage(Message msg);// 进行其他处理
//                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                super.onScaleChanged(view, oldScale, newScale);
            }


            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return WebViewCacheInterceptorInst.getInstance().interceptRequest(request);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return WebViewCacheInterceptorInst.getInstance().interceptRequest(url);
            }


            //            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
////                return super.shouldOverrideUrlLoading(view, request);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    //避免出现net::ERR_UNKNOWN_URL_SCHEME错误
////                    if(request.getUrl().toString().startsWith("http:") || request.getUrl().toString().startsWith("https:") ) {
//                        view.loadUrl(request.getUrl().toString());
////                    }
//                } else {
//                    if(request.toString().startsWith("http:") || request.toString().startsWith("https:") ) {
//                        view.loadUrl(request.toString());
//                    }
//                }
//                return true;
//            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                LogUtil.e(">>>>>>>>>>>>>>>>>> shouldOverrideUrlLoading(WebView view, WebResourceRequest request)");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    WebViewCacheInterceptorInst.getInstance().loadUrl(view, request.getUrl().toString());
                    return doShouldOverrideUrlLoading2(view, request.getUrl().toString());
                } else {
//                    WebViewCacheInterceptorInst.getInstance().loadUrl(view, request.toString());
                    return doShouldOverrideUrlLoading2(view, request.toString());
                }
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView wv, String url) {
                LogUtil.e(">>>>>>>>>>>>>>>>>> shouldOverrideUrlLoading(WebView wv, String url)");
                return doShouldOverrideUrlLoading2(wv, url);
            }
        };
    }


    private Long timeInternal = 0L;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean doShouldOverrideUrlLoadingForNewsUrlInWebView(@NonNull String url) {
        int index = url.indexOf("govDetail?id=");
        if (index != -1) {
            String id = url.substring(index + "govDetail?id=".length());
            LogUtil.e(">>>>>>> id");
            LogUtil.e(id);
            if (id != null) {
                if (System.currentTimeMillis() - timeInternal > BaseConstant.TIME_INTERVAL_500) {
                    timeInternal = System.currentTimeMillis();
                    LogUtil.e(">>>>>>> ProgressWebView 点击没有被过滤");
                    ARouter.getInstance().build(RouterHub.ROUTER_GOVERNMENT_INFO_DETAIL_NOT_SINGLE_TOP)
                            .withString("id", id).greenChannel().navigation();
                }
                //下面的写法不行,因为后台返回很多没用的数据,我们只取"contentHasTitle"这个来显示
//              String str = "{\"channelcode\":\"App\",\"id\":\""+id+"\"}";
//              postUrl("http://222.162.179.46/sisp/gov/notice/getGovernmentDetail",Base64.encode(str.getBytes(), Base64.DEFAULT));
                return true;
            }
        }
        return false;
    }


    private boolean doShouldOverrideUrlLoading2(WebView wv, String url) {
        LogUtil.e("doShouldOverrideUrlLoading2 = " + url);


        //TODO  http://222.162.179.46/GovManagement-html/index.html#/index/govDetail?id=406
        /***下面是针对现在后台的链接需要登录的处理,后续改进后下面的代码可以注释掉*/
        if (url != null
                && url.startsWith(CommonApi.INSTANCE.getIpPrefix15())
                && url.contains(BaseConstant.NEWS_URL_FEATURE_PREFIX)) {
            if (doShouldOverrideUrlLoadingForNewsUrlInWebView(url)) {
                LogUtil.e(">>>>>>>>>>>>>>>>>> doShouldOverrideUrlLoading return true");
                return true;
            }
        }


        if (url == null) {
            return false;
        }
        //携程的协议不跳转到它的app
        if (url.startsWith("ctrip://")) {
            return true;
        }

        try {
            //QQ登录
            if (url.startsWith("wtloginmqq://ptlogin/qlogin")) {

                String urlNew = url.substring(0, url.indexOf("googlechrome"));

                url = urlNew + "ldw://";

                LogUtil.e("doShouldOverrideUrlLoading2 urlNew >>>>>>>>>>= " + url);


                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                Activity activity = BaseActivityCollector.getTopActivity();
                intent.addCategory(Intent.CATEGORY_BROWSABLE);


                if (activity != null) {
                    activity.startActivity(intent);
                }
                return true;
            }
        } catch (Exception e) {
            LogUtil.e(e.toString());
            return true;
        }


        if (url.startsWith("http:") || url.startsWith("https:")) {
            WebViewCacheInterceptorInst.getInstance().loadUrl(wv, url);
            return true;
        }

        try {

            Intent intent;
            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.setComponent(null);
            Activity activity = BaseActivityCollector.getTopActivity();
            if (activity != null) {
                activity.startActivity(intent);
            }
            return true;

        } catch (Exception e) {
            LogUtil.e("发生错误" + e.toString());
            return true;
        }


    }


    private boolean doShouldOverrideUrlLoading(WebView wv, String url) {
        LogUtil.e(url + "");

        if (url == null) {
            return false;
        }
        //携程的协议不跳转到它的app
        if (url.startsWith("ctrip://")) {
            return true;
        }


        try {
            //微信
            if (url.startsWith("weixin://")
                    ////QQ
//                  ||url.startsWith("wtloginmqq://ptlogin/qlogin")
                    //支付宝
                    || url.startsWith("alipays://")
                    //邮件
                    || url.startsWith("mailto://")
                    //电话
                    || url.startsWith("tel://")
                    || url.startsWith("tel:")
                //大众点评
//                  || url.startsWith("dianping://")
                //携程
//                  || url.startsWith("ctrip://")
                //其他自定义的scheme

            ) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                Activity activity = BaseActivityCollector.getTopActivity();
                if (activity != null) {
                    activity.startActivity(intent);
                }

                return true;
            }
            //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
        } catch (Exception e) {
            LogUtil.e(e.toString());
            //没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出错误页面
            return true;
        }

        //避免出现net::ERR_UNKNOWN_URL_SCHEME错误
        if (url.startsWith("http:") || url.startsWith("https:")) {
//          wv.loadUrl(url);
            WebViewCacheInterceptorInst.getInstance().loadUrl(wv, url);
        }

        return true;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) mProgressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        mProgressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }


//    //重写onScrollChanged 方法
//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//        scrollTo(l,0);
//    }


    int startX;
    int startY;
//@Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    startX = (int) event.getX();
//                    starty = (int) event.getY();
//                    break;
//                case MotionEvent.ACTION_UP:
//                    int endX = (int) event.getX();
//                    int startY = (int) event.getY();
//
//
//
//
//                    if(endX>startX && endX-startX>scrollSize){
//                        webview.goBack();
//                    }else if(endX<startX &&webview.canGoForward() && startX-endX>scrollSize){
//                        webview.goForward();
//                    }
//                    break;
//                default:
//                    break;
//                }
//                return false;
//            }
//        });
//        return super.dispatchTouchEvent(ev);
//    }


    private boolean isScrollX = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEventCompat.getPointerCount(event) == 1) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isScrollX = false;
                    //事件由webview处理
                    getParent().getParent()
                            .requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    //嵌套Viewpager时
                    getParent().getParent()
                            .requestDisallowInterceptTouchEvent(!isScrollX);
                    break;
                default:
                    getParent().getParent()
                            .requestDisallowInterceptTouchEvent(false);
            }
        } else {
            //使webview可以双指缩放（前提是webview必须开启缩放功能，并且加载的网页也支持缩放）
            getParent().getParent().
                    requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(event);
    }

    //当webview滚动到边界时执行
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        isScrollX = clampedX;
//        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>  isScrollX");
//        LogUtil.e(isScrollX);
    }
}












