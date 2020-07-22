package com.tecsun.jc.base.interceptor

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.utils.Preference
import com.tecsun.jc.base.utils.TokenUtil

@Interceptor(priority = 1)
class LoginInterceptor : IInterceptor {

    private var context: Context? = null

    private val userLoginStatus by Preference(BaseConstant.USER_LOGIN_STATUS, false)
    private val userCertificationStatus by Preference(BaseConstant.USER_CERTIFICATION_STATUS, false)

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {


        if (userLoginStatus ) {
            if (!userCertificationStatus && postcard?.priority != 1) {//需要实名认证
                ARouter.getInstance().build(RouterHub.ROUTER_REGISTER_IDENTITY_REAL).greenChannel().navigation(context)
                callback?.onInterrupt(null)
            } else {
                callback?.onContinue(postcard)
            }
        } else {
            ARouter.getInstance().build(RouterHub.ROUTER_USER_LOGIN).greenChannel().navigation(context)
            callback?.onInterrupt(null)
        }
    }

    override fun init(context: Context?) {
        this.context = context
    }
}