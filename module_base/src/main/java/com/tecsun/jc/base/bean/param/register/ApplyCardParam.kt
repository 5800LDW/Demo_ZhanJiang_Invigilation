package com.tecsun.jc.base.bean.param.register

import android.graphics.Bitmap

/**
 * 申请制卡参数
 * Created by _Smile on 2017/5/20.
 */
class ApplyCardParam {

    var channelcode = "App"

    var tokenid: String? = null

    /**
     * 身份证号码
     */
    var sfzh: String? = null

    /**
     * 用户姓名
     */
    var xm: String? = null

    /**
     * 性别01男性;02女性;03未知
     */
    var sex: String? = null

    /**
     * 人员类别 1：城镇职工 2：城镇居民 3：农村居民 9：其他
     */
    var personType: String? = null

    /**
     * 民族（中文，如：汉）
     */
    var nation: String? = null

    /**
     * 国籍
     */
    var guoji: String? = null

    /**
     * 手机号码
     */
    var mobile: String? = null

    /**
     * 户籍地址
     */
    var domicile: String? = null

    /**
     * 签发机关
     */
    var certIssuingOrg: String? = null

    /**
     * 联系地址
     */
    var address: String? = null

    /**
     * 参保人相片ID（2.34上传图片uploadPicture返回picId，该相片无需处理）
     */
    var photoBuzId: String? = null

    /**
     * 身份证正面相片ID（2.34上传图片uploadPicture返回picId）
     */
    var picupId: String? = null

    /**
     * 身份证反面相片ID（2.34上传图片uploadPicture返回picId）
     */
    var picdownId: String? = null

    /**
     * 证件有效期 yyyyMMdd-yyyyMMdd/长期
     */
    var certValidity: String? = null

    /**
     * 银行编码
     */
    var bankCode: String? = null

    /**
     * 银行名称
     */
    var bankName: String? = null

    /**
     * 人员状态 1:就业 2:退休 3: 离休 4:待业 5:无业 6:从未就业 7:务农 8:退职 9:失业 10:就读 11:外出务工 99:其他
     */
    var personStatus: String? = null

    /**
     * 户口性质 1-本地非农业户口 2-外地非农业户口 3-本地农业户口 4-外地农业户口
     */
    var accountProties: String? = null

    /**
     * 出生日期 yyyy-MM-dd
     */
    var birthday: String? = null


    /**
     * 地址简称或网点
     */
    var cardAddress: String? = null

    /**
     * 领卡地址（邮寄地址、网点地址）
     */
    var cardAddressShort: String? = null
    /**
     * 领卡方式：1：快递邮寄；2：到网点领取；3：德生宝所在地领取
     */
    var addrType: String? = null
    /**
     * 领卡网点编码
     */
    var addrOrgCode: String? = null
    /**
     * 收件人手机号
     */
    var addrPhone: String? = null
    /**
     * 收件人姓名
     */
    var addressee: String? = null

    /*************************社保卡申领 */
    /**
     * 证件类型
     */
    var certType: String? = null
    /**
     * 所属区县code
     */
    var distinctCode: String? = null

    /**
     * 卡类别
     */
    var cardType: String? = null

    /**
     * 网点编码
     */
    var orgCode: String? = null

    /**
     * 联系电话
     */
    var phone: String? = null

    /**
     * 行业
     */
    var trade: String? = null

    /**
     * 职业
     */
    var occupation: String? = null
    /**
     * 电话类型
     */
    var phoneType: String? = null

    /**
     * 申领方式 0:本人办理, 1:代办
     */
    var applyWay: String? = null

    /**
     * 民族code
     */
    var nationcode: String? = null

    /**
     * 性别code
     */
    var sexcode: String? = null

    /**
     * 国籍id（默认CHN）
     */
    var guojicode: String? = null
    /***********************社保卡申领预览界面 */

    /**
     * 姓名
     */
    var show_name: String? = null

    /**
     * 性别
     */
    var show_sex: String? = null

    /**
     * 国籍
     */
    var show_country: String? = null

    /**
     * 民族
     */
    var show_nation: String? = null

    /**
     * 身份证号码
     */
    var show_cardid: String? = null

    /**
     * 出生日期
     */
    var show_birthday: String? = null
    /**
     * 证件类型
     */
    var show_certType: String? = null
    /**
     * 身份证有效期
     */
    var show_certValidity: String? = null

    /**
     * 联系手机
     */
    var show_phone: String? = null

    /**
     * 户籍地址
     */
    var show_domicile: String? = null

    /**
     * 通讯地址
     */
    var show_address: String? = null

    /**
     * 人员状态
     */
    var show_people_state: String? = null

    /**
     * 户口性质
     */
    var show_number_quality: String? = null

    /**
     * 电话类型
     */
    var show_phone_state: String? = null

    /**
     * 联系电话
     */
    var show_link_phone: String? = null

    /**
     * 行业
     */
    var show_trade: String? = null

    /**
     * 职业
     */
    var show_occupation: String? = null

    /**
     * 所属区域
     */
    var show_distinctCode: String? = null

    /**
     * 卡类别
     */
    var show_card_category: String? = null

    /**
     * 服务银行
     */
    var show_service_bank: String? = null

    /**
     * 取卡网点
     */
    var show_cardAddress: String? = null

    /**
     * 证件身份证反面
     */
    var show_card_back_bitmap: Bitmap? = null

    /**
     * 证件身份证正面
     */
    var show_card_right_bitmap: Bitmap? = null

    /**
     * 证件自拍照
     */
    var show_card_selfie_bitmap: Bitmap? = null


}
