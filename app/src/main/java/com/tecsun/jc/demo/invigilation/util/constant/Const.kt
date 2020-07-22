package com.tecsun.jc.register.util.constant

import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.bean.param.register.ApplyCardParam
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.common.CommonApi

internal object Const {

    const val IS_TEST = false
    const val IS_TEST2 = false


    open var mTokenId = JinLinApp.mTokenId

    open var mDeviceId = JinLinApp.mDeviceId


    // 常量
    const val USER_NAME = "USER_NAME"
    const val USER_SOCIAL_SECURITY_NUMBER = "USER_SOCIAL_SECURITY_NUMBER"
    const val CODE_1001 = 1001

    const val CHANNEL_CODE = "App"


    // 调用身份证拍照来源
    const val TAKE_ID_SOURCE = "take_id_source"
    const val SOURCE_ID_POSITIVE = 0x01// 身份证正面
    const val SOURCE_ID_NEGATIVE = 0x02// 身份证反面


    //URL
    const val BASE_URL_ADDRESS = CommonApi.BASE_URL_ADDRESS
    const val URL_REGISTER = "$BASE_URL_ADDRESS/sisp/iface/account/registerAccount"

    const val URL_AGREEMENT = "$BASE_URL_ADDRESS/sisp/agreement/agreement.html"


    //路由 确认身份证信息界面
    const val ROUTER_REGISTER_APPLY_ONE = "/register/ApplyOneActivity"
    const val ROUTER_REGISTER_PHOTO_STANDARD = "/register/PhotoStandardActivity"
    const val ROUTER_REGISTER_PHOTO_CONFIRM = "/register/PhotoConfirmActivity"
    const val ROUTER_REGISTER_PHOTO_CONFIRM_FOR_ADD_STUDENT = "/register/PhotoConfirmActivityForAddStudent"


    var mApplyCardParam: ApplyCardParam? = JinLinApp.instance.mApplyCardParam



    /**注册账号-验证邮箱**/
    const val URL_VALIDATE_EMAIL_REGISTER ="/sisp/iface/account/findPwd/validateEmail4Register"
    /**账号管理-注册账号（个人版）**/
    const val URL_REGISTER_NEW ="/sisp/iface/account/registerAccount"
    /**获取所有密保问题*/
    const val URL_ALL_SECRET_QUESTION ="/sisp/iface/account/findPwd/getAllSecretQuestion"
    /***获取密保问题列表(修改密保问题,包括答案) */
    const val URL_GET_SECRET_QUESTION_ANSWER="/sisp/iface/account/getSecretQuestionWithAnswer"
    /***更新密保问题*/
    const val URL_SECRET_QUESTION_ANSWER_MODIFY="/sisp/iface/account/updateSecretQuestion"
    /***账号管理-修改个人信息*/
    const val URL_EMAIL_MODIFY="/sisp/iface/account/saveAccountInfo"
    /***账号管理-校验密码*/
    const val URL_CHECK_ACCOUNT_PWD="/sisp/iface/account/checkAccountPwd"
    /**找回密码-校验账户 1 , 判断用户存不存在*/
    const val URL_VALIDATE_ACCOUNT_CHECK="/sisp/iface/account/findPwd/validateAccount"
    /**找回密码-校验账户 2 , 选择找回密码方式*/
    const val URL_VALIDATE_ACCOUNT="/sisp/iface/account/findPwd/findPassword"
    /**找回密码-验证邮箱验证码*/
    const val URL_VALIDATE_CAPTCHA="/sisp/iface/account/findPwd/validateCaptcha"
    /**找回密码-验证密保问题*/
    const val URL_VALIDATE_QUESTION="/sisp/iface/account/findPwd/validateSecretQuestion"
    /**找回密码-重置密码*/
    const val URL_RESET_PWD="/sisp/iface/account/findPwd/resetPwd"


    const val ROOM_1 = "河北师范大学"
    const val ROOM_2 = "石家庄学院南区"
    const val EXAMINER_INFO_CODE = BaseConstant.EXAMINER_INFO_CODE
    const val EXAMINATION_ROOM_INFO_CODE = BaseConstant.EXAMINATION_ROOM_INFO_CODE
    const val EXAMINATION_ROOM_INFO_CODE_02 = BaseConstant.EXAMINATION_ROOM_INFO_CODE_02




    const val FORM_COMPARE_STUDENT_PIC = "FORM_COMPARE_STUDENT_PIC"

    val UPLOAD_PICTURE = BaseConstant.UPLOAD_PICTURE
    val COMPARE_PHOTO = BaseConstant.COMPARE_PHOTO




}





































