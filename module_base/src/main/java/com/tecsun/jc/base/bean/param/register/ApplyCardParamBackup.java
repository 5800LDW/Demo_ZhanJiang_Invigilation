package com.tecsun.jc.base.bean.param.register;//package com.tecsun.jc.base.bean.param.register;
//
//import android.graphics.Bitmap;
//
///**
// * 申请制卡参数
// * Created by _Smile on 2017/5/20.
// */
//public class ApplyCardParamBackup {
//
//    public String channelcode = "App";
//
//    public String tokenid ;
//
//    /**
//     * 身份证号码
//     */
//    public String sfzh;
//
//    /**
//     * 用户姓名
//     */
//    public String xm;
//
//    /**
//     * 性别01男性;02女性;03未知
//     */
//    public String sex;
//
//    /**
//     * 人员类别 1：城镇职工 2：城镇居民 3：农村居民 9：其他
//     */
//    public String personType;
//
//    /**
//     * 民族（中文，如：汉）
//     */
//    public String nation;
//
//    /**
//     * 国籍
//     */
//    public String guoji;
//
//    /**
//     * 手机号码
//     */
//    public String mobile;
//
//    /**
//     * 户籍地址
//     */
//    public String domicile;
//
//    /**
//     * 签发机关
//     */
//    public String certIssuingOrg;
//
//    /**
//     * 联系地址
//     */
//    public String address;
//
//    /**
//     * 参保人相片ID（2.34上传图片uploadPicture返回picId，该相片无需处理）
//     */
//    public String photoBuzId;
//
//    /**
//     * 身份证正面相片ID（2.34上传图片uploadPicture返回picId）
//     */
//    public String picupId;
//
//    /**
//     * 身份证反面相片ID（2.34上传图片uploadPicture返回picId）
//     */
//    public String picdownId;
//
//    /**
//     * 证件有效期 yyyyMMdd-yyyyMMdd/长期
//     */
//    public String certValidity;
//
//    /**
//     * 银行编码
//     */
//    public String bankCode;
//
//    /**
//     * 银行名称
//     */
//    public String bankName;
//
//    /**
//     * 人员状态 1:就业 2:退休 3: 离休 4:待业 5:无业 6:从未就业 7:务农 8:退职 9:失业 10:就读 11:外出务工 99:其他
//     */
//    public String personStatus;
//
//    /**
//     * 户口性质 1-本地非农业户口 2-外地非农业户口 3-本地农业户口 4-外地农业户口
//     */
//    public String accountProties;
//
//    /**
//     * 出生日期 yyyy-MM-dd
//     */
//    public String birthday;
//
//
//    /**
//     * 地址简称或网点
//     */
//    public String cardAddress;
//
//    /**
//     * 领卡地址（邮寄地址、网点地址）
//     */
//    public String cardAddressShort;
//    /**
//     * 领卡方式：1：快递邮寄；2：到网点领取；3：德生宝所在地领取
//     */
//    public String addrType;
//    /**
//     * 领卡网点编码
//     */
//    public String addrOrgCode;
//    /**
//     * 收件人手机号
//     */
//    public String addrPhone;
//    /**
//     * 收件人姓名
//     */
//    public String addressee;
//
//    /*************************社保卡申领****************************/
//    /**
//     * 证件类型
//     * */
//    public String certType;
//    /**
//     * 所属区县code
//     * */
//    public String distinctCode;
//
//    /**
//     * 卡类别
//     * */
//    public String cardType;
//
//    /**
//     * 网点编码
//     * */
//    public String orgCode;
//
//    /**
//     * 联系电话
//     * */
//    public String phone;
//
//    /**
//     * 行业
//     * */
//    public String trade;
//
//    /**
//     * 职业
//     * */
//    public String occupation;
//    /**
//     * 电话类型
//     * */
//    public String phoneType;
//
//    /**
//     * 申领方式 0:本人办理, 1:代办
//     * */
//    public String applyWay;
//
//    /**
//     * 民族code
//     * */
//    public String nationcode;
//
//    /**
//     * 性别code
//     * */
//    public String sexcode;
//
//    /**
//     * 国籍id（默认CHN）
//     * */
//    public String guojicode;
//    /***********************社保卡申领预览界面***************************/
//
//    /**
//     * 姓名
//     * */
//    public String show_name;
//
//    /**
//     * 性别
//     * */
//    public String show_sex;
//
//    /**
//     * 国籍
//     * */
//    public String show_country;
//
//    /**
//     * 民族
//     * */
//    public String show_nation;
//
//    /**
//     * 身份证号码
//     * */
//    public String show_cardid;
//
//    /**
//     * 出生日期
//     * */
//    public String show_birthday;
//    /**
//     * 证件类型
//     * */
//    public String show_certType;
//    /**
//     * 身份证有效期
//     * */
//    public String show_certValidity;
//
//    /**
//     * 联系手机
//     * */
//    public String show_phone;
//
//    /**
//     * 户籍地址
//     * */
//    public String show_domicile;
//
//    /**
//     *通讯地址
//     * */
//    public String show_address;
//
//    /**
//     * 人员状态
//     * */
//    public String show_people_state;
//
//    /**
//     * 户口性质
//     * */
//    public String show_number_quality;
//
//    /**
//     * 电话类型
//     * */
//    public String show_phone_state;
//
//    /**
//     * 联系电话
//     * */
//    public String show_link_phone;
//
//    /**
//     * 行业
//     * */
//    public String show_trade;
//
//    /**
//     * 职业
//     * */
//    public String show_occupation;
//
//    /**
//     * 所属区域
//     * */
//    public String show_distinctCode;
//
//    /**
//     * 卡类别
//     * */
//    public String show_card_category;
//
//    /**
//     *服务银行
//     * */
//    public String show_service_bank;
//
//    /**
//     * 取卡网点
//     * */
//    public String show_cardAddress;
//
//    /**
//     *证件身份证反面
//     * */
//    public Bitmap show_card_back_bitmap;
//
//    /**
//     * 证件身份证正面
//     * */
//    public Bitmap show_card_right_bitmap;
//
//    /**
//     * 证件自拍照
//     * */
//    public Bitmap show_card_selfie_bitmap;
//
//
//}
