package com.tecsun.jc.demo.invigilation.zhanjiang.bean;

public class TrainRegisterParam {


    /**
     * address : string
     * birth : string
     * certNo : string
     * certificate : string
     * certificateType : string
     * createTime : string
     * domicile : string
     * education : string
     * employmentIntent : string
     * employmentStatus : string
     * employmentWish : string
     * gender : string
     * jobTrainLevel : string
     * jobTrainType : string
     * mobile : string
     * nation : string
     * picDown : string
     * picUp : string
     * trainId : 0
     * trainOrg : string
     * updateTime : string
     * userName : string
     *
     *
     *
     *
     *
     *
     * {
     * address*	string
     *
     * 现住地址
     * birth*	string
     *
     * 出生年月
     * certNo*	string
     *
     * 证件号码
     * certificate*	string
     *
     * 现有技术等级、职业资格证书,数据字典编码：TELV
     * certificateType*	string
     *
     * 拥有证书的工种
     * createTime	string
     * domicile*	string
     *
     * 户籍地址
     * education*	string
     *
     * 学历,数据字典编码：EDU
     * employmentIntent*	string
     *
     * 就业意向,1:是 0:否
     * employmentStatus*	string
     *
     * 目前状态,数据字典编码：TRAIN_JOB_STATUS
     * employmentWish*	string
     *
     * 就业意愿,1:是 0:否
     * gender*	string
     *
     * 性别,1:男 0:女
     * jobTrainLevel*	string
     *
     * 报名参加培训级别,数据字典编码：TELV_TYPE
     * jobTrainType*	string
     *
     * 报名参加培训工种
     * mobile*	string
     *
     * 联系电话
     * nation*	string
     *
     * 民族
     * picDown*	string
     *
     * 调用上传照片接口返回的id
     * picUp*	string
     *
     * 调用上传照片接口返回的id
     * trainId	integer($int32)
     * trainOrg*	string
     *
     * 培训机构
     * updateTime	string
     * userName*	string
     *
     * 姓名
     * }
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */

    private String address;
    private String birth;
    private String certNo;
    private String certificate;
    private String certificateType;
    private String createTime;
    private String domicile;
    private String education;
    private String employmentIntent;
    private String employmentStatus;
    private String employmentWish;
    private String gender;
    private String jobTrainLevel;
    private String jobTrainType;
    private String mobile;
    private String nation;
    private String picDown;
    private String picUp;
    private int trainId;
    private String trainOrg;
    private String updateTime;
    private String userName;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEmploymentIntent() {
        return employmentIntent;
    }

    public void setEmploymentIntent(String employmentIntent) {
        this.employmentIntent = employmentIntent;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getEmploymentWish() {
        return employmentWish;
    }

    public void setEmploymentWish(String employmentWish) {
        this.employmentWish = employmentWish;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJobTrainLevel() {
        return jobTrainLevel;
    }

    public void setJobTrainLevel(String jobTrainLevel) {
        this.jobTrainLevel = jobTrainLevel;
    }

    public String getJobTrainType() {
        return jobTrainType;
    }

    public void setJobTrainType(String jobTrainType) {
        this.jobTrainType = jobTrainType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPicDown() {
        return picDown;
    }

    public void setPicDown(String picDown) {
        this.picDown = picDown;
    }

    public String getPicUp() {
        return picUp;
    }

    public void setPicUp(String picUp) {
        this.picUp = picUp;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public String getTrainOrg() {
        return trainOrg;
    }

    public void setTrainOrg(String trainOrg) {
        this.trainOrg = trainOrg;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "TrainRegisterParam{" +
                "address='" + address + '\'' +
                ", birth='" + birth + '\'' +
                ", certNo='" + certNo + '\'' +
                ", certificate='" + certificate + '\'' +
                ", certificateType='" + certificateType + '\'' +
                ", createTime='" + createTime + '\'' +
                ", domicile='" + domicile + '\'' +
                ", education='" + education + '\'' +
                ", employmentIntent='" + employmentIntent + '\'' +
                ", employmentStatus='" + employmentStatus + '\'' +
                ", employmentWish='" + employmentWish + '\'' +
                ", gender='" + gender + '\'' +
                ", jobTrainLevel='" + jobTrainLevel + '\'' +
                ", jobTrainType='" + jobTrainType + '\'' +
                ", mobile='" + mobile + '\'' +
                ", nation='" + nation + '\'' +
                ", picDown='" + picDown + '\'' +
                ", picUp='" + picUp + '\'' +
                ", trainId=" + trainId +
                ", trainOrg='" + trainOrg + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
