package com.tecsun.jc.base.common

object RouterHub {

    const val ROUTER_MAIN = "/app/MainActivity"

    const val ROUTER_WEB_VIEW = "/base/WebViewActivity"
    const val ROUTER_USER_LOGIN = "/login/UserLoginActivity"

    const val ROUTER_MY_PROFILE = "/user/MyProfileActivity"
    const val ROUTER_PERSONAL_INFO_CHANGE = "/user/personalinfochangeactivity"
    const val ROUTER_USER_CHANGE_COMPANY = "/user/ChangeCompanyActivity"
    const val ROUTER_USER_CHANGE_COMMUNICATION = "/user/ChangeCommunicationActivity"
    const val ROUTER_CHANGE_PWD = "/user/ChangePwdActivity"
    const val ROUTER_REGISTER_OPERATIONGUIDE = "/user/OperationGuideListActivity"//操作指南、常见问题
    const val ROUTER_MY_NEWS="/user/MyNewsActivity"//我的消息

    const val ROUTER_GOVERNMENT_INFO_DETAIL = "/government/GovernmentDetailActivity"
    const val ROUTER_GOVERNMENT_INFO_DETAIL_NOT_SINGLE_TOP = "/government/GovernmentDetailActivityNotSingleTop"
    const val ROUTER_INFO_DETAIL = "/info/InfoDetailActivity"

    const val ROUTER_INSURANCE_MEDICAL_BALANCE = "/insurance/MedicalBalanceActivity"
    const val ROUTER_INSURANCE_DRUGLIST_BALANCE = "/insurance/DruglistActivity"//药品目录查询
    const val ROUTER_INSURANCE_THREEHOSPTITAL = "/insurance/ThreeHospitalsActivity";//三甲医院查询
    const val ROUTER_INSURANCE_MEDICAL_CONSUMPTION="/insurance/MedicalConsumptionActivity"//医疗消费
    const val ROUTER_INSURANCE_MEDICAL_DESIGNATED_HOSPITAL="/insurance/DesignatedHospitalActivity"//定点医院
    const val ROUTER_INSURANCE_MEDICAL_DESIGNATE_HOSPITAL="/insurance/DesignatedHospitalActivity"//定点医院查询
    const val ROUTER_INSURANCE_MEDICAL_MEDICAL_INSURANCE="/insurance/MedicalInsuranceActivity"//医疗保险
    const val ROUTER_INSURANCE_MEDICAL_INSURED_CERTIFICATE="/insurance/InsuredCertificateActivity"//参保凭证
    const val ROUTRT_INSURANCE_SOCIALSECURITYADDRESS="/insurance/SocialSecurityAddressActivity"//医保局地址

    const val ROUTE_CHECK_PWD_SERVICE1="CheckPwd_Card";//社保卡业务
    const val ROUTE_CHECK_PWD_SERVICE2="CheckPwd_Electronic";//电子社保卡
    const val ROUTE_CHECK_PWD_SERVICE3="CheckPwd_SS";//人社业务
    const val ROUTE_CHECK_PWD_SERVICE4="CheckPwd_Convenient";//便民服务
    const val ROUTE_CHECK_PWD_SERVICE5="CheckPwd_Government";//政府部门

    const val ROUTER_CHECK_LOGIN_CARDAPPLY = "/card/TransparentActivity"//社保卡申领
    const val ROUTER_CHECK_LOGIN_PWD = "/card/CheckPwdActivity"
    const val ROUTER_REPORT_LOSS = "/card/ReportLossActivity"
    const val ROUTER_RELEASE_LOSS = "/card/ReleaseLossActivity"
    const val ROUTER_CARD_ACTIVATION = "/card/CardActivationActivity"
    const val ROUTER_BANK_ADDRESS = "/card/BankAddressActivity"
    const val ROUTER_SCHEDULE_QUERY = "/card/CardScheduleQueryActivity"


    const val ROUTER_CARD_INFO_AFFIRM = "/card/AffirmInfoActivity"


    /**异地社保卡邮寄*/
    const val ROUTER_CARD_LONG_DISTANCE_MAILLING = "/card/LongDistanceMailingActivity"
    const val ROUTER_CARD_FIRST_SHOW_GET_STATUS_INFO= "/card/FirstShowGetStatusInfoActivity"
    /***申请邮寄须知*/
    const val ROUTER_CARD_MAIL_NOTICE = "/card/MailNoticeActivity"

    const val ROUTER_ELECTRONIC_CARD_ISSUE = "/electronic/ElectronicCardIssueActivity"
    const val ROUTER_ELECTRONIC_CARD_PAYMENT_CODE = "/electronic/PaymentCodeActivity"
    const val ROUTER_ELECTRONIC_CARD_QR_CODE = "/electronic/IdentityQRCodeActivity"





    /* module_register*/
    //输入名称,社保号界面
    const val ROUTER_REGISTER_USER = "/register/RegisterUserActivity"
    //实名认证第一步,输入姓名和社保号
    const val ROUTER_REGISTER_IDENTITY_REAL = "/register/IdentityRealNameActivity"
    //输入密码和手机号 界面
    const val ROUTER_REGISTER_USER_NUMBER = "/register/InputUserNumberActivity"
    //拍摄身份证界面
    const val ROUTER_REGISTER_TAKE_ID_CARD_PIC = "/register/TakeIdCardPicActivity"
    //进行拍摄界面
    const val ROUTER_REGISTER_TAKE_ID_CARD = "/register/TakeIdCardActivity"
    //获取用户已录入密保问题列表
    const val ROUTER_REGISTER_QUESTION_LOADING = "/register/FirstStepLoadingActivity"
    //修改密保问题
    const val ROUTER_REGISTER_MODIFY_QUESTION = "/register/ModifyQuestionActivity"
    //修改邮箱
    const val ROUTER_REGISTER_MODIFY_EMAIL = "/register/ModifyEmailActivity"
    //验证密保问题 或 登录密码
    const val ROUTER_REGISTER_VERIFY = "/register/VerifyActivity"
    //找回登录密码的方式
    const val ROUTER_REGISTER_SELECT_FIND_BACK_PWD_TYPE = "/register/SelectFindBackThePasswordTypeActivity"
    //忘记密码--->验证邮箱验证码
    const val ROUTER_REGISTER_FORGET_PWD_CHECK_EMAIL_CODE= "/register/ForgetPwdCheckEmailCodeActivity"
    //忘记密码--->验证密保
    const val ROUTER_REGISTER_FORGET_PWD_CHECK_QUESTION= "/register/ForgetPwdCheckQuestionActivity"
    //填写新的密码
    const val ROUTER_REGISTER_INPUT_NEW_PWD= "/register/InputNewPwdActivity"

    const val ROUTER_REGISTER_TAKE_PHOTO1= "/register/TakePhotoActivity"
    //TakePhotoActivity2
    const val ROUTER_REGISTER_TAKE_PHOTO2= "/register/TakePhotoActivity2"


    /* module_insurance*/
    //后台url 养老基本信息查询 和 养老缴费基数查询
    const val ROUTER_INSURANCE_PROVIDE_WEB = "/insurance/ProvideWebActivity"
    //养老保险
    const val ROUTER_INSURANCE_OLD_AGE = "/insurance/OldAgeActivity"
    //工伤保险
    const val ROUTER_INSURANCE_INJURE = "/insurance/InjureInsuranceActivity"
    //生育保险
    const val ROUTER_INSURANCE_BIRTH = "/insurance/BirthInsuranceActivity"
    //社保缴费明细查询
    const val ROUTER_INSURANCE_PAYMENT_DETAILS = "/insurance/SocialSecurityPaymentDetails"
    //
    const val ROUTER_INSURANCE_FIRST_OLD_AGE_SHOW = "/insurance/FirstStepOldAgeShowActivity"
    //
    const val ROUTER_INSURANCE_FIRST_PAYMENT_SHOW = "/insurance/FirstStepPaymentDetailsShowActivity"
    //
    const val ROUTER_INSURANCE_FIRST_STEP_QR_SHOW = "/insurance/FirstStepQRShowActivity"

    //线下医保结算
    const val ROUTER_ELECTRONIC_INSURANCE_OFFLINE_MEDICAL_SETTLEMENT = "/insurance/OfflineMedicalInsuranceSettlementActivity"
    //线下扫码购药
    const val ROUTER_ELECTRONIC_INSURANCE_OFFLINE_SCAN_CODE_SHOPPING = "/insurance/OfflineScanCodeShoppingActivity"
    //调用显示二维码的最后一步页面
    const val ROUTER_ELECTRONIC_CARD_QR = "/electronic/ElectronicCardCodeQRActivity"

    const val ROUTER_ELECTRONIC_VALIDATE_PWD = "/electronic/ElectronicValidatePwdActivity"
    const val ROUTER_ELECTRONIC_VALIDATE_PWD_FOR_OLD_AGE = "/electronic/ElectronicValidatePwdForOldAgeActivity"
    const val ROUTER_ELECTRONIC_VALIDATE_PWD_FOR_SOCIAL_SECURITY = "/electronic/ElectronicValidatePwdForSocialSecurityActivity"
    const val ROUTER_ELECTRONIC_VALIDATE_PWD_FOR_QR = "/electronic/ElectronicValidatePwdForQRActivity"
    const val ROUTER_ELECTRONIC_LOADING_QR= "/electronic/LoadingQRActivity"


    /**便民服务*/
    /**吉林美食*/
    const val ROUTER_CONVENIENCE_SERVICE_FINE_FOOD = "/ConvenienceService/FineFoodActivity"
    /**吉林旅游*/
    const val ROUTER_CONVENIENCE_SERVICE_TRAVEL_GUIDE = "/ConvenienceService/TravelGuideActivity"
    /**天气预报*/
    const val ROUTER_CONVENIENCE_SERVICE_WEATHER = "/ConvenienceService/WeatherActivity"
    /**实名农户*/
    const val ROUTER_CONVENIENCE_SERVICE_FARMERS = "/ConvenienceService/FarmersActivity"
    /**吉林公交路线*/
    const val ROUTER_CONVENIENCE_SERVICE_TRANSPORT_LINE = "/ConvenienceService/TransportLineActivity"


    /**用在下面的很大块头的读身份证模块*/
    const val ROUTER_LIB_READCARD_IDCARDACTIVITY_COMBINE = "/Lib_ReadCard/IdcardActivity_Combine"
    /**天波的读取社保卡模块*/
    const val ROUTER_LIB_READCARD_SSC = "/Lib_ReadCard/ReadSSCActivity"
    /**离线图片比对*/
    const val ROUTER_LIB_FACE_RECOGNITION_OFFLINE = "/module_FaceRecognition/OfflineComparePicActivity"
    /**考官签到登录*/
    const val ROUTER_APP_EXAMINER_MANAGE = "/MyAPP/ExaminerManageActivity"
    /**显示将要新增监考老师的信息*/
    const val ROUTER_APP_Show_New_Invigilator_Info = "/MyAPP/ShowNewInvigilatorInfoActivity"
    /**拍照*/
    const val ROUTER_APP_TAKE_PHOTO = "/MyAPP/TakePhotoActivity"
    /**新增学生*/
    const val ROUTER_APP_ADD_NEW_STUDENT = "/MyAPP/AddNewStudentActivity"


    /**导入数据*/
    const val ROUTER_LIB_IMPORT_DATA = "/module_ImportData/ImportDtaActivity"



    /**个人申报*/
    const val ROUTER_APP_PERSON_DECLARE = "/MyAPP/PersonDeclareActivity"

    /**学员登记*/
    const val ROUTER_APP_STUDENT_REGISTER = "/MyAPP/StudentRegisterActivity"
}


























