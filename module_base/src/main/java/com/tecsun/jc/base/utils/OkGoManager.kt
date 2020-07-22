package com.tecsun.jc.base.utils

import android.provider.SyncStateContract
import android.util.Log
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.AbsCallback
import com.lzy.okgo.convert.StringConvert
import com.lzy.okgo.https.HttpsUtils
import com.lzy.okgo.model.Response
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.collector.BaseActivityCollector
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.common.CommonApi
import com.tecsun.jc.base.interceptor.network.TokenInterceptor
import com.tecsun.jc.base.listener.IEvents
import com.tecsun.jc.base.listener.OkGoRequestCallback
import com.tecsun.jc.base.utils.log.LogUtil
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkGoManager {

    companion object {
        val instance: OkGoManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            OkGoManager()
        }
    }

    fun <T> okGoRequestManage(roadUrl: String, requestParam: Any, clz: Class<T>, callback: OkGoRequestCallback<T>) {
        okGoRequestManage(roadUrl, Gson().toJson(requestParam), clz, callback)
    }

    fun <T> okGoRequestManage(roadUrl: String, requestParam: String, clz: Class<T>, callback: OkGoRequestCallback<T>) {
//        val requestUrl = CommonApi.BASE_URL_ADDRESS + roadUrl
        val requestUrl = CommonApi.IP + roadUrl


        //start 20190517 ldw 0次重连
        var okHttpClient = OkGo.getInstance().setRetryCount(0).okHttpClient
        var builder = okHttpClient.newBuilder() as OkHttpClient.Builder
//        val loggingInterceptor = HttpLoggingInterceptor("OkGo")
//        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
//        loggingInterceptor.setColorLevel(Level.INFO)
        builder.addInterceptor(TokenInterceptor())
        builder.readTimeout(BaseConstant.DEFAULT_READ_MILLISECONDS.toLong(), TimeUnit.MILLISECONDS)
        builder.writeTimeout(BaseConstant.DEFAULT_WRITE_MILLISECONDS.toLong(), TimeUnit.MILLISECONDS)
        builder.connectTimeout(BaseConstant.DEFAULT_CONNECTION_MILLISECONDS.toLong(), TimeUnit.MILLISECONDS)
        val sslParams = HttpsUtils.getSslSocketFactory()//https://blog.csdn.net/chaoyu168/article/details/87995109
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
        builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier)
        var okHttpClientNew = builder.build()//新的okhttpclient
//        OkGo.getInstance().setOkHttpClient(okHttpClientNew)
        //end


        OkGo.post<T>(requestUrl)
            .tag(roadUrl)
            .client(okHttpClientNew)
            .isSpliceUrl(true)
            .params("tokenid", JinLinApp.mTokenId)
            .upJson(requestParam)
            .execute(object : AbsCallback<T>() {
                override fun onSuccess(response: Response<T>?) {
                    val body = response?.body()
                    if (body != null) {
                        try {

                            myRunOnUIThread(IEvents {
                                callback.onSuccess(Gson().fromJson(body.toString(), clz))
                            })

                        } catch (e: Exception) {

                            myRunOnUIThread(IEvents {
                                callback.onError(response?.exception)
                            })

                            Log.e("OkGoManager", "发生错误:$e.message")
                            Log.e("OkGoManager", body?.toString() ?: "")

                        }

                    }
                }

                override fun onError(response: Response<T>?) {
                    super.onError(response)
                    try {

                        myRunOnUIThread(IEvents {
//                            response?.exception?.let {
//                                if(response?.exception is ConnectException){
//                                    ToastUtils.showGravityShortToast(JinLinApp.context!!,"连接服务器异常")
//                                }
//                            }

                            callback.onError(response?.exception)
                        })


                    } catch (e: java.lang.Exception) {
                        Log.e("OkGoManager", e?.toString() ?: "")
                    }


                }

                override fun convertResponse(response: okhttp3.Response?): T {
                    val s = StringConvert().convertResponse(response)
                    response?.close()
                    return s as T
                }
            })
    }


    private fun myRunOnUIThread(l: IEvents) {
        var activity = BaseActivityCollector.getTopActivity()
        if (l != null) {
            if (activity != null) {
                activity.runOnUiThread {
                    l.biz()
                }
            } else {
                l.biz()
            }
        }
    }
//    private inner class SafeTrustManager : X509TrustManager {
//        @Throws(CertificateException::class)
//        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
//            for (cert in chain) {
//                try {
//                    cert.checkValidity()
//                } catch (e: Exception) {
//                    throw CertificateException("Certificate not valid or trusted.")
//                }
//
//            }
//        }
//        @Throws(CertificateException::class)
//        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
//            try {
//                for (certificate in chain) {
//                    certificate.checkValidity() //检查证书是否过期，签名是否通过等
//                }
//            } catch (e: Exception) {
//                throw CertificateException(e)
//            }
//
//        }
//        override fun getAcceptedIssuers(): Array<X509Certificate?> {
//            return arrayOfNulls(0)
//        }
//    }

    fun <T> okGoRequestManageForGet2(roadUrl: String, clz: Class<T>, callback: OkGoRequestCallback<T>) {
        LogUtil.e(">>>>>>>>>>>>>>>>> roadUrl")
        LogUtil.e("${CommonApi.BASE_URL_ADDRESS}/$roadUrl")
        okGoRequestManageForGet("${CommonApi.BASE_URL_ADDRESS}/$roadUrl", clz, callback)
    }

    fun <T> okGoRequestManageForGet(roadUrl: String, clz: Class<T>, callback: OkGoRequestCallback<T>) {
        val requestUrl = roadUrl
        //超时重连0次;
        var okHttpClient = OkGo.getInstance().setRetryCount(0).okHttpClient
        var builder = okHttpClient.newBuilder() as OkHttpClient.Builder
        builder.addInterceptor(TokenInterceptor())
        builder.readTimeout(BaseConstant.DEFAULT_READ_MILLISECONDS.toLong(), TimeUnit.MILLISECONDS)
        builder.writeTimeout(BaseConstant.DEFAULT_WRITE_MILLISECONDS.toLong(), TimeUnit.MILLISECONDS)
        builder.connectTimeout(BaseConstant.DEFAULT_CONNECTION_MILLISECONDS.toLong(), TimeUnit.MILLISECONDS)
        val sslParams = HttpsUtils.getSslSocketFactory()
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
        builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier)
        var okHttpClientNew = builder.build()//新的okhttpclient
//        OkGo.getInstance().setOkHttpClient(okHttpClientNew)
        //end


        OkGo.get<T>(requestUrl)
            .tag(roadUrl)
            .client(okHttpClientNew)
            .params("tokenid", JinLinApp.mTokenId)
            .execute(object : AbsCallback<T>() {
                override fun onSuccess(response: Response<T>?) {
                    val body = response?.body()
                    if (body != null) {
                        try {
                            myRunOnUIThread(IEvents {
                                callback.onSuccess(Gson().fromJson(body.toString(), clz))
                            })

                        } catch (e: Exception) {

                            myRunOnUIThread(IEvents {
                                callback.onError(response?.exception)
                            })
                        }

                    }
                }

                override fun onError(response: Response<T>?) {
                    super.onError(response)
                    try {

                        myRunOnUIThread(IEvents {
                            callback.onError(response?.exception)
                        })


                    } catch (e: java.lang.Exception) {
                        Log.e("OkGoManager", e?.toString() ?: "")
                    }

                }

                override fun convertResponse(response: okhttp3.Response?): T {
                    val s = StringConvert().convertResponse(response)
                    response?.close()
                    return s as T
                }
            })
    }



    fun myCancelTag(obj:Any){
        //根据 Tag 取消请求
        obj?.let {
            LogUtil.e(">>>>>>>>>>>> myCancelTag($obj)")
            OkGo.getInstance().cancelTag(obj)
        }
    }
}













