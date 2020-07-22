package com.tecsun.jc.base.bean.db.invigilation.bean;

import com.tecsun.jc.base.bean.filter.IFilter;

import org.jetbrains.annotations.NotNull;
import org.litepal.crud.LitePalSupport;

/***
 * 考点信息
 *
 */
public class TestingDetailsBean extends LitePalSupport implements IFilter {

    private String schoolName = null;
    private String address = null;
    /**楼栋*/
    private String buildingName = null;


    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    @Override
    public String toString() {
        return "TestingDetailsBean{" +
                "schoolName='" + schoolName + '\'' +
                ", address='" + address + '\'' +
                ", buildingName='" + buildingName + '\'' +
                '}';
    }

    @NotNull
    @Override
    public String getInfo() {
        return getSchoolName();
    }
}
