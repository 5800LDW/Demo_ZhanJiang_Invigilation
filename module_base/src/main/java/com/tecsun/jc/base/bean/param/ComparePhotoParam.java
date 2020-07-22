package com.tecsun.jc.base.bean.param;

/**
 * Created by psl on 2017/5/19.
 */

public class ComparePhotoParam {

    public String channelcode = "App";
    public String deviceid;
    public String tokenid;
    public String sfzh;
    public String xm;
    public String verifyAddress;
    public String verifyType;
    public long verifyData;
    public String picType;
    public long comparisonData;
    public String verifyChannel;
    public String verifyBus;
    public String verifyTime;

    @Override
    public String toString() {
        return "ComparePhotoParam{" +
                "channelcode='" + channelcode + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", tokenid='" + tokenid + '\'' +
                ", sfzh='" + sfzh + '\'' +
                ", xm='" + xm + '\'' +
                ", verifyAddress='" + verifyAddress + '\'' +
                ", verifyType='" + verifyType + '\'' +
                ", verifyData=" + verifyData +
                ", picType='" + picType + '\'' +
                ", comparisonData=" + comparisonData +
                ", verifyChannel='" + verifyChannel + '\'' +
                ", verifyBus='" + verifyBus + '\'' +
                ", verifyTime='" + verifyTime + '\'' +
                '}';
    }
}
