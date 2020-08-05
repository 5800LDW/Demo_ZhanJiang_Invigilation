package com.tecsun.jc.base.bean.db.invigilation.bean;

import com.tecsun.jc.base.bean.filter.IFilter;

import org.jetbrains.annotations.NotNull;

public class ShowInfoBean implements IFilter {

    private String info;

    public ShowInfoBean setInfo(String info) {
        this.info = info;
        return this;
    }

    @NotNull
    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "ShowInfoBean{" +
                "info='" + info + '\'' +
                '}';
    }
}
