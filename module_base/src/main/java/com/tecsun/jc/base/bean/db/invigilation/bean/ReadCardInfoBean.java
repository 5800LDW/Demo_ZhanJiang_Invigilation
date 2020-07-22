package com.tecsun.jc.base.bean.db.invigilation.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;


/**
 *
 * 读取到的监考官信息
 *
 */
public class ReadCardInfoBean extends LitePalSupport implements Serializable {

    /**姓名*/
    private String name;
    /**性别*/
    private String sex;
    /**生日*/
    private String birthday;
    /**民族*/
    private String nation;
    /**地址*/
    private String address;
    /**身份证号*/
    private String number;
    /**签发机关*/
    private String qianfa;
    /**有效日期*/
    private String effdate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    @Override
    public String toString() {
        return "ReadCardInfoBean{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", nation='" + nation + '\'' +
                ", address='" + address + '\'' +
                ", number='" + number + '\'' +
                ", qianfa='" + qianfa + '\'' +
                ", effdate='" + effdate + '\'' +
                '}';
    }
}
