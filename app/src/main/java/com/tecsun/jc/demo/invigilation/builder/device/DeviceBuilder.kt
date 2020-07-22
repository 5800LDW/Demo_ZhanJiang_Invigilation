package com.tecsun.jc.demo.invigilation.builder.device

import com.alibaba.android.arouter.launcher.ARouter
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.common.BaseConstant.STUDENT_SFZH
import com.tecsun.jc.base.common.BaseConstant.TITLE_NAME
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.utils.DeviceUtil
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.demo.invigilation.builder.NFCBuilder
import com.tecsun.jc.demo.invigilation.ui.examiner.ExaminerLoginActivity

/**
 * 判断机器来跳转的Builder
 *
 * @author liudongwen
 * @date 2019/10/28
 */
object DeviceBuilder {

    fun skip(activity: BaseActivity,title:String , sfzh:String? = ""){
        var deviceName = DeviceUtil.getDeviceModel()
        if (deviceName != null && deviceName.toUpperCase() == "TPS900") {
            //天波机器
            ARouter.getInstance().build(RouterHub.ROUTER_LIB_READCARD_IDCARDACTIVITY_COMBINE)
                .withString(
                    TITLE_NAME,
                    title
                )
                .withString(
                    STUDENT_SFZH,
                    sfzh
                )
                .greenChannel().navigation()
            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> DeviceBuilder skip ")


        } else if (deviceName != null && deviceName.toUpperCase() == "T1.2") {
            //深圳厂家机器
            if (NFCBuilder.isNFCReady(activity)) {
                activity.myStartActivity(ExaminerLoginActivity::class.java)
            }

        } else {
            //深圳厂家机器
            if (NFCBuilder.isNFCReady(activity)) {
                activity.myStartActivity(ExaminerLoginActivity::class.java)
            }
        }
    }

    /**跳转去读取社保卡*/
    fun skipReadSSC(activity: BaseActivity,title:String, studentSFZH:String){
        var deviceName = DeviceUtil.getDeviceModel()
        if (deviceName != null && deviceName.toUpperCase() == "TPS900") {
            //天波机器
            ARouter.getInstance().build(RouterHub.ROUTER_LIB_READCARD_SSC)
                .withString(
                    TITLE_NAME,
                    title
                )
                .withString(
                    STUDENT_SFZH,
                    studentSFZH
                )
                .greenChannel().navigation()


        } else if (deviceName != null && deviceName.toUpperCase() == "T1.2") {
//            //深圳厂家机器
//            if (NFCBuilder.isNFCReady(activity)) {
//                activity.myStartActivity(ExaminerLoginActivity::class.java)
//            }

        } else {
//            //深圳厂家机器
//            if (NFCBuilder.isNFCReady(activity)) {
//                activity.myStartActivity(ExaminerLoginActivity::class.java)
//            }
        }
    }





}
















