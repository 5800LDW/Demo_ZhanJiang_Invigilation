package com.tecsun.jc.base.bean.db.invigilation.bean;

import com.tecsun.jc.base.bean.filter.IFilter;

import org.jetbrains.annotations.NotNull;
import org.litepal.crud.LitePalSupport;

/**
 *
 * 下载的监考官信息
 *
 */
public class ProctorDetailsBean extends LitePalSupport implements IFilter {

    private String name = null;
    private String sfzh = null;
    private String pic = null;


    /**考点*/
    private String schoolName = null;




    /**性别*/
    private String sex;
    /**生日*/
    private String birthday;
    /**民族*/
    private String nation;
    /**地址*/
    private String address;
    /**签发机关*/
    private String qianfa;
    /**有效日期*/
    private String effdate;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQianfa() {
        return qianfa;
    }

    public void setQianfa(String qianfa) {
        this.qianfa = qianfa;
    }

    public String getEffdate() {
        return effdate;
    }

    public void setEffdate(String effdate) {
        this.effdate = effdate;
    }

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Override
    public String toString() {
        return "ProctorDetailsBean{" +
                "name='" + name + '\'' +
                ", sfzh='" + sfzh + '\'' +
                ", pic='" + pic + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", nation='" + nation + '\'' +
                ", address='" + address + '\'' +
                ", qianfa='" + qianfa + '\'' +
                ", effdate='" + effdate + '\'' +
                '}';
    }

    @NotNull
    @Override
    public String getInfo() {
        return getName() ;
    }
}
