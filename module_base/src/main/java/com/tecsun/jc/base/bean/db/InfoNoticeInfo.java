package com.tecsun.jc.base.bean.db;

import org.litepal.crud.LitePalSupport;

public class InfoNoticeInfo extends LitePalSupport {

    private String noticeID;

    public String getNoticeID() {
        return noticeID;
    }

    public void setNoticeID(String noticeID) {
        this.noticeID = noticeID;
    }

    @Override
    public String toString() {
        return "InfoNoticeInfo{" +
                "noticeID='" + noticeID + '\'' +
                '}';
    }
}
