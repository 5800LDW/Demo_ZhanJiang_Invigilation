package com.tecsun.jc.demo.lib_readssc.ssc.bean;


/**
 * 申请制卡参数
 * Created by _Smile on 2017/5/20.
 */
public class ApplyCardParam extends BaseParam {

    /**
     * 身份证号码
     */
    public String sfzh;

    /**
     * 用户姓名
     */
    public String xm;

    /**
     * 性别01男性;02女性;03未知
     */
    public String sex;
    public String sexName;

    /**
     * 人员类别 1：城镇职工 2：城镇居民 3：农村居民 9：其他
     */
    public String personType;
    /**
     * 单位名称
     */
    public String companyName;
    public String companyNo;

    /**
     * 民族（中文，如：汉）
     */
    public String nation;

    /**
     * 国籍
     */
    public String guoji;

    /**
     * 手机号码
     */
    public String mobile;

    /**
     * 户籍地址
     */
    public String domicile;

    /**
     * 签发机关
     */
    public String certIssuingOrg;
    /**
     * 证件类型
     */
    public String certificateType;
    /**
     * 证件类型
     */
    public String certType;
    /**
     * 电话类型
     */
    public String phoneType;
    public String phone;
    /**
     * 电话类型
     */
    public String dhlx;
    /**
     * 行政区
     */
    public String officeDistric;
    /**
     * 行政区
     */
    public String xzq;
    /**
     * 行业
     */
    public String industry ;
    /**
     * 行业
     */
    public String hy ;

    /**
     * 职业
     */
    public String job ;
    public String zy ;

    /**
     * 联系地址
     */
    public String address;

    /**
     * 参保人相片ID（2.34上传图片uploadPicture返回picId，该相片无需处理）
     */
    public String photoBuzId;

    /**
     * 身份证正面相片ID（2.34上传图片uploadPicture返回picId）
     */
    public String picupId;

    /**
     * 身份证反面相片ID（2.34上传图片uploadPicture返回picId）
     */
    public String picdownId;

    /**
     * 证件有效期 yyyyMMdd-yyyyMMdd/长期
     */
    public String certValidity;

    /**
     * 银行编码
     */
    public String bankCode;

    /**
     * 银行名称
     */
    public String bankName;

    /**
     * 人员状态 1:就业 2:退休 3: 离休 4:待业 5:无业 6:从未就业 7:务农 8:退职 9:失业 10:就读 11:外出务工 99:其他
     */
    public String personStatus;
    public String personStatusName;

    /**
     * 户口性质 1-本地非农业户口 2-外地非农业户口 3-本地农业户口 4-外地农业户口
     */
    public String accountProties;
    public String accountProtiesName;

    /**
     * 出生日期 yyyy-MM-dd
     */
    public String birthday;


    /**
     * 地址简称或网点
     */
    public String cardAddress;

    /**
     * 领卡地址（邮寄地址、网点地址）
     */
    public String cardAddressShort;
    /**
     * 领卡方式：1：快递邮寄；2：到网点领取；3：德生宝所在地领取
     */
    public String addrType;
    /**
     * 领卡网点编码
     */
    public String addrOrgCode;
    /**
     * 收件人手机号
     */
    public String addrPhone;
    /**
     * 收件人姓名
     */
    public String addressee;
    /**
     * 收件人身份证号
     */
    public String addressSfzh;
    /**
     * 卡类型
     * */
    public String cardType;
    /**
     * 卡类型
     * */
    public String cardTypeName;
    /**
     * 电子邮箱
     */
    public String email;
    /**
     * 邮政编码
     */
    public String mailCode;
    public String zipCode;
    /**
     * 监护人姓名
     */
    public String jhrXm;
    /**
     * 监护人证件
     */
    public String jhrSfzh;
    /**
     * 监护人证件类型
     */
    public String jhrCertType;
    /**
     * 监护人电话
     */
    public String jhrMobile;
    /**
     * 经办人
     */
    public String jbr;
    /**
     * 经办机构
     */
    public String jbjg;
    /**
     * 领取方式
     */
    public String getCardType;

    public String pichoidId;

    public String autographId;
}
