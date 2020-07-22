package com.tecsun.jc.demo.invigilation.util.constant;


import com.tecsun.jc.base.JinLinApp;

/**
 * 常量
 */
public class Constants {

    // 本地保存用户信息
    public static final String LOCAL_USER_INFO = "user_info";

    // bitmap对象传递参数
    public static final String BITMAP = "bitmap";

    // 选择服务银行传递对象
    public static final String SELECT_SERVICE_BANK = "select_service_bank";

    // 调用身份证拍照来源
    public static final String TAKE_ID_SOURCE = "take_id_source";
    public static final int SOURCE_ID_POSITIVE = 0x01; // 身份证正面
    public static final int SOURCE_ID_NEGATIVE = 0x02; // 身份证反面

    public static final String ACCOUNT_ID = "accountId";
    public static final String ACCOUNT_MPHONE = "account_phone";
    public static final String ACCOUNT_ADDRESS = "account_address";
    public static final String ACCOUNT_COMPANY = "account_company";
    public static final String ACCOUNT_SEX = "account_sex";
    public static final String ACCOUNT_NATION = "account_nation";

    public static final String ACCOUNT_PHONE = "accountPhone";

    public static final String ACCOUNT_xm = "accountXm";

    public static final String ACCOUNT_NUM = "accountNum";

    public static final String ACCOUNT_PASSWORD = "accountPassword";

    public static final String ACTIVITY_PIC_USER = "apu";

    public static final String ACTIVITY_PIC_APPLY = "apa";

    public static final String ACTIVITY_PIC_WORKER = "apw";

    public static final String APPLY_FAILED_CONTENT = "apply_failed_content";

//    public static final String ACCOUNT_ID = "accountId";

    public static final String PHONE_NUM = "phone";

    public static final String COLLECT_NAME="collect_name";
    public static final String COLLECT_SFZH="collect_sfzh";
    public static final String COLLECT_PHOTO="collect_photo";
    public static final String CERITIFICATION_PHOTO="ceritification_photo";
    public static final String CERITIFICATION_PHOTO_DEMO="ceritification_photo_demo";
    public static final String LAST_ACTIVITY_PHOTO="last_activity_photo";
    public static final String APPLY_PHOTO="apply_photo";
    public static final String AUTHENTICATION="AUTHENTICATION";
    public static final String Loss_FAIL_REASON="loss_fail_reason";//挂失失败原因
    public static final String Loss_succeed="loss_succeed";//挂失成功
    public static final String CANCEL_LOSS_SUCCEED="cancel_loss_succeed";//解除挂失成功
    public static final String CANCEL_LOSS_FAIL="cancel_loss_fail";//解除挂失失败
    public static final String IS_LOGIN="is_login";//登陆入口
    public static final String TAKE_PHOTO_TIP="take_photo_tip";//登陆入口
    public static final String INSURE_POSITION="insure_position";//社保查询position
    public static final String INSURE_PATAM="insure_param";//社保查询参数
    public static final String INSURE_TITLE="insure_title";//社保查询参数
    public static final String INSURE_URL="insure_url";//社保查询url
    public static final String LOGIN = "login";//社保查询url
    public static final String ISWORK_LOGIN = "iswork_login";//社保查询url
    public static final String INSURE_PAY_TITLE = "insure_pay_title";//社保缴费标题
    public static final String PAY_TIPE = "pay_type";//缴费类型
    public static final String PAY_AMOUNT = "pay_amount";//缴费金额
    public static final String FLOWNUM = "flowNum";//缴费金额
    /**搜索内容保存文件**/
    public static final String FILE_SERCH = "serchfile.obj";
    public static final String FILE_LOCATION = "serchlocationfile.obj";
    public static final String FILE_POSITION = "serchpositionfile.obj";
    /**搜索医院名称**/
    public static final String HOSPITAL_NAME = "hospital_name";
    public static final String HOSPITAL_ID= "hospital_id";
    public static final String  DEPTCODE= "deptCode";
    public static final String  DOCTORNO= "doctorNo";
    public static final String  DOCTOR_DETAIL= "doctor_detail";
    public static final String  HOSPITAL_BASE= "hospitalBase64";
    public static final String  MYLOCATION= "myLocation";
    public static final String  LONGITUDE= "longitude";
    public static final String  LATITUDE= "latitude";
    public static final String  SEARCHLOCATION= "search_location";
    public static final String  isIntelligence= "isIntelligence";
    public static final String  WECHARPAY_ID= "wxf42fcdd09abec10a";
    public static final String  QUERY_TYPE= "queryType";//个人用工类型
    public static final String  CERI_TYPE= "ceri_Type";//个人用工类型
    public static final String  AREA= "area";//
    public static final String  AREACODE= "areaCode";//
    public static final String  CITYCODE= "cityCode";//
    public static final String  CITY= "city";//
    public static final String  DISTRIC= "distric";//
    public static final String  USERPIC= "userPic";//
    public static final String  USERPHONE= "userPhone";//
    public static final String  UPDATR_ACCOUNT= "update_account";//切换账号

    public static final String  COMMUNICATION= "communication";//
    public static final String  COMPANY= "company";//
    public static final String  HEAD_U_D= "head_up_down";//低头抬头
    public static final String  HEAD_R_L= "head_right_left";//左右摇头
    public static final String  MOUTH= "mouth";//嘴巴
    public static final String  EYE= "eye";//眼睛
    public static final String  ACTION_WAY= "action_way";//随机2顺序1
    public static final String  VOICE_COLLECR_ID= "voice_collect_id";//随机2顺序1

    /**微信分享appid**/
    public static final String WX_APP_ID = "wxf42fcdd09abec10a";


    public static boolean IS_REGISTER_CERTIFICATION = false;


    public static final String TAKE_PHOTO_FOR_ADD_STUDENT = "TAKE_PHOTO_FOR_ADD_STUDENT";


    //LDW
    public static final String TAG = "TAG";

//    private static RetrofitAPIImpl mRetrofitAPI;
//    private static Retrofit mRetrofit;
//    public static RetrofitAPIImpl getRetrofitAPI() {
//        if(mRetrofitAPI == null){
//            mRetrofitAPI = new RetrofitAPIImpl();
//        }
//        return mRetrofitAPI;
//    }
//
//    public static Retrofit getRetrofit() {
//        if(mRetrofit == null){
//            mRetrofit = getRetrofitAPI().initRetrofit(JinLinApp.Companion.getInstance(), Const.BASE_URL_ADDRESS);
//        }
//        return mRetrofit;
//    }

    public static String name = JinLinApp.getAccountName();
    public static String numberText = JinLinApp.getSfzh();

    /**
     * 身份证正面照片ID 人像面 2188
     */
    public static String idcardPicUp = JinLinApp.Companion.getInstance().getMApplyCardParam().getPicupId();

    /**
     * 身份证反面照片ID 2189
     */
    public static String idcardPicDown = JinLinApp.Companion.getInstance().getMApplyCardParam().getPicdownId();

}




























