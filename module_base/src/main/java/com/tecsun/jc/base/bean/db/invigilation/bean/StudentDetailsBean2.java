package com.tecsun.jc.base.bean.db.invigilation.bean;

public class StudentDetailsBean2 extends StudentDetailsBean {

    private String sex;

    private String nation = null;

    /**
     * 生日
     */
    private String born;


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }


    @Override
    public String toString() {
        return "StudentDetailsBean2{" +
                "sex='" + sex + '\'' +
                ", nation='" + nation + '\'' +
                ", born='" + born + '\'' +
                '}';
    }
}
