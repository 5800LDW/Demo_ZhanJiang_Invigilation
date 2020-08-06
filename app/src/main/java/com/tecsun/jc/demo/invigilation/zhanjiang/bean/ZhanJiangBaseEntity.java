package com.tecsun.jc.demo.invigilation.zhanjiang.bean;

public class ZhanJiangBaseEntity {
    private String message;
    private String statusCode;

    public Boolean isSuccess() {
        if (statusCode != null && statusCode.equals("200")) {
            return true;
        }
        return false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "ZhanJiangBaseEntity{" +
                "message='" + message + '\'' +
                ", statusCode='" + statusCode + '\'' +
                '}';
    }
}
