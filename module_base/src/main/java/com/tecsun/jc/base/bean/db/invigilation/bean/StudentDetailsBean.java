package com.tecsun.jc.base.bean.db.invigilation.bean;

import com.tecsun.jc.base.bean.filter.IFilter;

import org.jetbrains.annotations.NotNull;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class StudentDetailsBean extends LitePalSupport implements IFilter , Serializable {

    private String name = null;
    private String sfzh = null;
    /**准考证号*/
    private String examinationNO = null;
    /**报考专业*/
    private String ApplyingMajor = null;
    /**照片*/
    private String pic = null;
    /**考试时间*/
    private String testTime = null;

    /**考点*/
    private String schoolName = null;

    /**监考老师姓名*/
    private String invigilator = null;

    /**是否已经审核,0是未审核*/
    private String isCheck = "0";

    /**是否已经拍了两张照片,0是未拍*/
    private String isTake2PicAR = "0";
    /**是否已经上传了照片,0是未上传*/
    private String isUploadAR = "0";


    /**考场号*/
    private String numberOfExams = null;
    /**座位号*/
    private String seatNumber = null;
    /**科目*/
    private String subject = null;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getExaminationNO() {
        return examinationNO;
    }

    public void setExaminationNO(String examinationNO) {
        this.examinationNO = examinationNO;
    }

    public String getApplyingMajor() {
        return ApplyingMajor;
    }

    public void setApplyingMajor(String applyingMajor) {
        ApplyingMajor = applyingMajor;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }


    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getIsTake2PicAR() {
        return isTake2PicAR;
    }

    public void setIsTake2PicAR(String isTake2PicAR) {
        this.isTake2PicAR = isTake2PicAR;
    }

    public String getIsUploadAR() {
        return isUploadAR;
    }

    public void setIsUploadAR(String isUploadAR) {
        this.isUploadAR = isUploadAR;
    }

    public String getInvigilator() {
        return invigilator;
    }

    public void setInvigilator(String invigilator) {
        this.invigilator = invigilator;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getNumberOfExams() {
        return numberOfExams;
    }

    public void setNumberOfExams(String numberOfExams) {
        this.numberOfExams = numberOfExams;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "StudentDetailsBean{" +
                "name='" + name + '\'' +
                ", sfzh='" + sfzh + '\'' +
                ", examinationNO='" + examinationNO + '\'' +
                ", ApplyingMajor='" + ApplyingMajor + '\'' +
                ", pic='" + pic + '\'' +
                ", testTime='" + testTime + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", invigilator='" + invigilator + '\'' +
                ", isCheck='" + isCheck + '\'' +
                ", isTake2PicAR='" + isTake2PicAR + '\'' +
                ", isUploadAR='" + isUploadAR + '\'' +
                ", numberOfExams='" + numberOfExams + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }

    @NotNull
    @Override
    public String getInfo() {
        return getName();
    }
}
