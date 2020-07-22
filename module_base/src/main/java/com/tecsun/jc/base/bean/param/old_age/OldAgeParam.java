package com.tecsun.jc.base.bean.param.old_age;

import com.tecsun.jc.base.JinLinApp;
import com.tecsun.jc.base.common.BaseConstant;

/**
 * 电子社保卡养老查询
 *
 * @author liudongwen
 * @date 2019/6/20
 */
public class OldAgeParam  {
    /**手机号码*/
    private String aac067 = "";
    /**签发号*/
    private String signNo = "";
    /**操作串*/
    private String busiSeq = "";
//    /**资源id(3.3应用资源清单)*/
//    private Integer[] ids ;

    /**资源id(3.3应用资源清单)*/
    private String ids ;

    private String channelcode = BaseConstant.CHANNEL_CODE;
    private String deviceid = JinLinApp.Companion.getMDeviceId();
    private String tokenid = JinLinApp.Companion.getMTokenId();
    private String xm = "";
    private String sfzh = "";

    public String getAac067() {
        return aac067;
    }

    public void setAac067(String aac067) {
        this.aac067 = aac067;
    }

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

    public String getBusiSeq() {
        return busiSeq;
    }

    public void setBusiSeq(String busiSeq) {
        this.busiSeq = busiSeq;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

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

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    @Override
    public String toString() {
        return "OldAgeParam{" +
                "aac067='" + aac067 + '\'' +
                ", signNo='" + signNo + '\'' +
                ", busiSeq='" + busiSeq + '\'' +
                ", ids='" + ids + '\'' +
                ", channelcode='" + channelcode + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", tokenid='" + tokenid + '\'' +
                ", xm='" + xm + '\'' +
                ", sfzh='" + sfzh + '\'' +
                '}';
    }
}
