package com.tecsun.jc.base.sign

import com.google.gson.Gson
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.bean.SignResultEntity
import com.tecsun.jc.base.bean.param.SignParam
import com.tecsun.jc.base.common.CommonApi
import com.tecsun.jc.base.listener.OkGoRequestCallback
import com.tecsun.jc.base.utils.OkGoManager

object APPSignUtils {

    fun signApp() {

        OkGoManager.instance.okGoRequestManage(CommonApi.ROAD_APP_SIGN,
            Gson().toJson(SignParam()), SignResultEntity::class.java, object : OkGoRequestCallback<SignResultEntity> {
                override fun onSuccess(t: SignResultEntity) {
                    if (t.isSuccess()) {
                        if (t.data != null) {
                            JinLinApp.mTokenId = t.data!!
                        }
                    }
                }

                override fun onError(throwable: Throwable?) {
//                    signApp()
                }
            })
    }

}