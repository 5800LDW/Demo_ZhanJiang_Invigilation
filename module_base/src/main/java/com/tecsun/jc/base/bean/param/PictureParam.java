package com.tecsun.jc.base.bean.param;

/**
 * Created by psl on 2017/5/17.
 */

public class PictureParam {

    public String picId;

    public String picType;

    public String picBase64;

    public String channelcode = "App";
    public String deviceid;
    public String tokenid;

    @Override
    public String toString() {
        return "PictureParam{" +
                "picId='" + picId + '\'' +
                ", picType='" + picType + '\'' +
                ", picBase64='" + picBase64 + '\'' +
                ", channelcode='" + channelcode + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", tokenid='" + tokenid + '\'' +
                '}';
    }
}
