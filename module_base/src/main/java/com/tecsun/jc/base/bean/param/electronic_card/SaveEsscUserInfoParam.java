package com.tecsun.jc.base.bean.param.electronic_card;

import com.tecsun.jc.base.JinLinApp;
import com.tecsun.jc.base.common.BaseConstant;

/**
 * 保存电子社保卡申领用户信息 saveESSCUserInfo
 *
 * @author liudongwen
 * @date 2019/6/24
 */
public class SaveEsscUserInfoParam {

    private String channelcode = BaseConstant.CHANNEL_CODE;
    private String deviceid = JinLinApp.Companion.getMDeviceId();
    private String tokenid = JinLinApp.Companion.getMTokenId();
    /**社会保障号/身份证号*/
    private String esscNo = "";
    /**手机号*/
    private String mobile = "";
    /**发卡地区行政区划代码*/
    private String regionCode = BaseConstant.REGION_CODE;
    /**签发号*/
    private String signNo = "";
    /**领卡业务流水号*/
    private String signSeq = "";
    /**签发级别*/
    private String signLevel = "";
    /**签发日期*/
    private String signDate = "";
    /**合法日期*/
    private String validDate = "";
    /**触发功能号*/
    private String actionType = "";
    /**姓名*/
    private String name = "";

    public String getChannelcode() {
        return channelcode;
    }

    public void setChannelcode(String channelcode) {
        this.channelcode = channelcode;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }

    public String getEsscNo() {
        return esscNo;
    }

    public void setEsscNo(String esscNo) {
        this.esscNo = esscNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

    public String getSignSeq() {
        return signSeq;
    }

    public void setSignSeq(String signSeq) {
        this.signSeq = signSeq;
    }

    public String getSignLevel() {
        return signLevel;
    }

    public void setSignLevel(String signLevel) {
        this.signLevel = signLevel;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SaveEsscUserInfoParam{" +
                "channelcode='" + channelcode + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", tokenid='" + tokenid + '\'' +
                ", esscNo='" + esscNo + '\'' +
                ", mobile='" + mobile + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", signNo='" + signNo + '\'' +
                ", signSeq='" + signSeq + '\'' +
                ", signLevel='" + signLevel + '\'' +
                ", signDate='" + signDate + '\'' +
                ", validDate='" + validDate + '\'' +
                ", actionType='" + actionType + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
