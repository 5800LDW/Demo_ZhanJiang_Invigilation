package com.tecsun.jc.demo.invigilation.zhanjiang.bean;

public class UploadResultParam {

    /**
     * certNo : string
     * courseId : 0
     * createTime : string
     * id : 0
     * picCert : 调用上传照片接口返回值
     * picPerson : 调用上传照片接口返回值
     * signTime : string
     * userName : string
     * verify : 1:人脸认证成功 0:人脸认证不成功
     */

    private String certNo;
    private int courseId;
    private String createTime;
    private int id;
    private String picCert;
    private String picPerson;
    private String signTime;
    private String userName;
    private String verify;

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicCert() {
        return picCert;
    }

    public void setPicCert(String picCert) {
        this.picCert = picCert;
    }

    public String getPicPerson() {
        return picPerson;
    }

    public void setPicPerson(String picPerson) {
        this.picPerson = picPerson;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    @Override
    public String toString() {
        return "UploadResultParam{" +
                "certNo='" + certNo + '\'' +
                ", courseId=" + courseId +
                ", createTime='" + createTime + '\'' +
                ", id=" + id +
                ", picCert='" + picCert + '\'' +
                ", picPerson='" + picPerson + '\'' +
                ", signTime='" + signTime + '\'' +
                ", userName='" + userName + '\'' +
                ", verify='" + verify + '\'' +
                '}';
    }
}
