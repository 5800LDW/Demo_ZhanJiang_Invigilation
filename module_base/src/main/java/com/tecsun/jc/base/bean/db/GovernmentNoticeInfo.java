package com.tecsun.jc.base.bean.db;

import org.litepal.crud.LitePalSupport;

public class GovernmentNoticeInfo extends LitePalSupport {

    private String noticeID;

    public String getNoticeID() {
        return noticeID;
    }

    public void setNoticeID(String noticeID) {
        this.noticeID = noticeID;
    }

    @Override
    public String toString() {
        return "GovernmentNoticeInfo{" +
                "noticeID='" + noticeID + '\'' +
                '}';
    }
}
