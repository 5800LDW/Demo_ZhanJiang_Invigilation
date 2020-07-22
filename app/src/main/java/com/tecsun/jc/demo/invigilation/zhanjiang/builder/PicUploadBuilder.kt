package com.tecsun.jc.demo.invigilation.zhanjiang.builder

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Handler
import android.provider.Settings
import com.tecsun.builder.ComparePhotoOnlineBuilder
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.PictureBean
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.listener.OkGoRequestCallback
import com.tecsun.jc.base.utils.BitmapUtils
import com.tecsun.jc.base.utils.OkGoManager
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.demo.invigilation.ui.pic.MyBaseActivity
import com.tecsun.jc.demo.invigilation.zhanjiang.bean.UploadPicEntity
import com.tecsun.jc.demo.invigilation.zhanjiang.bean.UploadPicParam
import com.tecsun.jc.demo.invigilation.zhanjiang.constant.Constants


object PicUploadBuilder {
    fun uploadPic(activity: MyBaseActivity? , param: UploadPicParam) {


        OkGoManager.instance.okGoRequestManage(
            Constants.URL_UPLOAD_PICTURE, param
            , UploadPicEntity::class.java, object : OkGoRequestCallback<UploadPicEntity> {
                override fun onSuccess(t: UploadPicEntity) {
                    activity?.dismissLoadingDialog()
                    if(t!=null && t.statusCode == "200"){




                    }









                    LogUtil.e(">>>>>>>>>>>>>>>>> onSuccess ${t}")
                }

                override fun onError(throwable: Throwable?) {
                    activity?.dismissLoadingDialog()
                    LogUtil.e(">>>>>>>>>>>>>>>>> throwable $throwable")
                    activity?.showErrorMessageDialog("$throwable")
                }
            })

    }

}
















