package com.tecsun.jc.demo.invigilation.zhanjiang.bean;

public class UploadPicParam {

    /**
     * channelcode : string
     * name : string
     * picBase64 : string
     * picId : 0
     * picName : string
     * picPath : string
     * picType : string
     * sfzh : string
     */

    private String channelcode = "";
    private String name = "";
    private String picBase64 = "";
    private int picId;
    private String picName = "";
    private String picPath = "";
    private String picType = "";
    private String sfzh = "";

    public String getChannelcode() {
        return channelcode;
    }

    public void setChannelcode(String channelcode) {
        this.channelcode = channelcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicBase64() {
        return picBase64;
    }

    public void setPicBase64(String picBase64) {
        this.picBase64 = picBase64;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    @Override
    public String toString() {
        return "UploadPicParam{" +
                "channelcode='" + channelcode + '\'' +
                ", name='" + name + '\'' +
//                ", picBase64='" + picBase64 + '\'' +
                ", picId=" + picId +
                ", picName='" + picName + '\'' +
                ", picPath='" + picPath + '\'' +
                ", picType='" + picType + '\'' +
                ", sfzh='" + sfzh + '\'' +
                '}';
    }
}
