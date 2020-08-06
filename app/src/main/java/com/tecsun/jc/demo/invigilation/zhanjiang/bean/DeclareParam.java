package com.tecsun.jc.demo.invigilation.zhanjiang.bean;

/**
 * 培训管理-新增个人申报信息
 */
public class DeclareParam {

    /**
     * address : string
     * birth : string
     * certNo : string
     * certType : string
     * companyName : string
     * cond : string
     * createTime : string
     * education : string
     * examType : string
     * gender : string
     * job : string
     * jobBeginTime : string
     * jobCert : string
     * jobCertLevel : string
     * jobCertNo : string
     * jobCertTime : string
     * jobEndTime : string
     * jobLevel : string
     * jobTime : string
     * mobile : string
     * personId : 0
     * pic : string
     * subject : string
     * train : string
     * trainBeginTime : string
     * trainEndTime : string
     * trainOrg : string
     * trainTime : string
     * updateTime : string
     * userName : string
     *
     *
     *
     * //下面的字段介绍说明:
     *
     *
     * {
     * address*	string
     *
     * 通讯地址
     * birth*	string
     *
     * 出生年月
     * certNo*	string
     *
     * 证件号码
     * certType*	string
     *
     * 证件类型，数据字典编码：CERT_TYPE
     * companyName*	string
     *
     * 单位名称
     * cond*	string
     *
     * 申报条件
     * createTime	string
     * education*	string
     *
     * 文化程度，数据字典编码：EDU
     * examType*	string
     *
     * 考试类型，1:正考 0:补考
     * gender*	string
     *
     * 性别，1:男 0:女
     * job*	string
     *
     * 申报职业
     * jobBeginTime*	string
     *
     * 工作开始时间，时间格式：yyyy年mm月dd日
     * jobCert*	string
     *
     * 职业资格证书
     * jobCertLevel*	string
     *
     * 职业资格等级
     * jobCertNo*	string
     *
     * 证书号码
     * jobCertTime*	string
     *
     * 获证日期，时间格式：yyyy年mm月dd日
     * jobEndTime*	string
     *
     * 工作结束时间，时间格式：yyyy年mm月dd日
     * jobLevel*	string
     *
     * 申报职业级别，数据字典编码：APPLY_LEVEL
     * jobTime*	string
     *
     * 从事本职业工作年限
     * mobile*	string
     *
     * 手机号码
     * personId	integer($int32)
     * pic*	string
     *
     * 调用上传照片接口返回的id
     * subject*	string
     *
     * 考核科目，数据字典编码：APPLY_SUBJECT
     * train*	string
     *
     * 是否参加本职业培训，1:已参加培训 0:未参加培训
     * trainBeginTime*	string
     *
     * 培训开始时间，时间格式：yyyy年mm月dd日
     * trainEndTime*	string
     *
     * 培训结束时间，时间格式：yyyy年mm月dd日
     * trainOrg*	string
     *
     * 培训机构
     * trainTime*	string
     *
     * 培训学时
     * updateTime	string
     * userName*	string
     *
     * 姓名
     * }
     */

    private String address;
    private String birth;
    private String certNo;
    private String certType;
    private String companyName;
    private String cond;
    private String createTime;
    private String education;
    private String examType;
    private String gender;
    private String job;
    private String jobBeginTime;
    private String jobCert;
    private String jobCertLevel;
    private String jobCertNo;
    private String jobCertTime;
    private String jobEndTime;
    private String jobLevel;
    private String jobTime;
    private String mobile;
    private int personId;
    private String pic;
    private String subject;
    private String train;
    private String trainBeginTime;
    private String trainEndTime;
    private String trainOrg;
    private String trainTime;
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

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJobBeginTime() {
        return jobBeginTime;
    }

    public void setJobBeginTime(String jobBeginTime) {
        this.jobBeginTime = jobBeginTime;
    }

    public String getJobCert() {
        return jobCert;
    }

    public void setJobCert(String jobCert) {
        this.jobCert = jobCert;
    }

    public String getJobCertLevel() {
        return jobCertLevel;
    }

    public void setJobCertLevel(String jobCertLevel) {
        this.jobCertLevel = jobCertLevel;
    }

    public String getJobCertNo() {
        return jobCertNo;
    }

    public void setJobCertNo(String jobCertNo) {
        this.jobCertNo = jobCertNo;
    }

    public String getJobCertTime() {
        return jobCertTime;
    }

    public void setJobCertTime(String jobCertTime) {
        this.jobCertTime = jobCertTime;
    }

    public String getJobEndTime() {
        return jobEndTime;
    }

    public void setJobEndTime(String jobEndTime) {
        this.jobEndTime = jobEndTime;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public String getJobTime() {
        return jobTime;
    }

    public void setJobTime(String jobTime) {
        this.jobTime = jobTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getTrainBeginTime() {
        return trainBeginTime;
    }

    public void setTrainBeginTime(String trainBeginTime) {
        this.trainBeginTime = trainBeginTime;
    }

    public String getTrainEndTime() {
        return trainEndTime;
    }

    public void setTrainEndTime(String trainEndTime) {
        this.trainEndTime = trainEndTime;
    }

    public String getTrainOrg() {
        return trainOrg;
    }

    public void setTrainOrg(String trainOrg) {
        this.trainOrg = trainOrg;
    }

    public String getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(String trainTime) {
        this.trainTime = trainTime;
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
        return "DeclareParam{" +
                "address='" + address + '\'' +
                ", birth='" + birth + '\'' +
                ", certNo='" + certNo + '\'' +
                ", certType='" + certType + '\'' +
                ", companyName='" + companyName + '\'' +
                ", cond='" + cond + '\'' +
                ", createTime='" + createTime + '\'' +
                ", education='" + education + '\'' +
                ", examType='" + examType + '\'' +
                ", gender='" + gender + '\'' +
                ", job='" + job + '\'' +
                ", jobBeginTime='" + jobBeginTime + '\'' +
                ", jobCert='" + jobCert + '\'' +
                ", jobCertLevel='" + jobCertLevel + '\'' +
                ", jobCertNo='" + jobCertNo + '\'' +
                ", jobCertTime='" + jobCertTime + '\'' +
                ", jobEndTime='" + jobEndTime + '\'' +
                ", jobLevel='" + jobLevel + '\'' +
                ", jobTime='" + jobTime + '\'' +
                ", mobile='" + mobile + '\'' +
                ", personId=" + personId +
                ", pic='" + pic + '\'' +
                ", subject='" + subject + '\'' +
                ", train='" + train + '\'' +
                ", trainBeginTime='" + trainBeginTime + '\'' +
                ", trainEndTime='" + trainEndTime + '\'' +
                ", trainOrg='" + trainOrg + '\'' +
                ", trainTime='" + trainTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
