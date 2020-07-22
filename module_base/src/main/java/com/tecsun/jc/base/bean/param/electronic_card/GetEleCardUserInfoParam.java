package com.tecsun.jc.base.bean.param.electronic_card;

import com.tecsun.jc.base.JinLinApp;
import com.tecsun.jc.base.common.BaseConstant;

/**
 * 查询电子社保卡信息 getEleCardUserInfo
 *
 * @author liudongwen
 * @date 2019/6/24
 */
public class GetEleCardUserInfoParam {

    private String channelcode = BaseConstant.CHANNEL_CODE;
    private String deviceid = JinLinApp.Companion.getMDeviceId();
    private String tokenid = JinLinApp.Companion.getMTokenId();
    /**
     * 发卡地区行政区划代码
     */
    private String regionCode = BaseConstant.REGION_CODE;
    /**
     * 社会保障号/身份证号
     */
    private String esscNo = "";


    public String getChannelcode() {
        return channelcode;
    }


    public String getDeviceid() {
        return deviceid;
    }


    public String getTokenid() {
        return tokenid;
    }


    public String getRegionCode() {
        return regionCode;
    }


    public String getEsscNo() {
        return esscNo;
    }

    public void setEsscNo(String esscNo) {
        this.esscNo = esscNo;
    }

    @Override
    public String toString() {
        return "GetEleCardUserInfoParam{" +
                "channelcode='" + channelcode + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", tokenid='" + tokenid + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", esscNo='" + esscNo + '\'' +
                '}';
    }
}
