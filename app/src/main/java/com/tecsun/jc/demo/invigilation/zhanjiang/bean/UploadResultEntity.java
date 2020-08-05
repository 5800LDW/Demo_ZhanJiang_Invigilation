package com.tecsun.jc.demo.invigilation.zhanjiang.bean;

public class UploadResultEntity {


    /**
     * statusCode : 200
     * message : 新增成功
     * data : 1
     * total : 0
     * pages : 0
     */

    private String statusCode;
    private String message;
//    private int data;
//    private int total;
//    private int pages;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public int getData() {
//        return data;
//    }
//
//    public void setData(int data) {
//        this.data = data;
//    }
//
//    public int getTotal() {
//        return total;
//    }
//
//    public void setTotal(int total) {
//        this.total = total;
//    }
//
//    public int getPages() {
//        return pages;
//    }
//
//    public void setPages(int pages) {
//        this.pages = pages;
//    }


    @Override
    public String toString() {
        return "UploadResultEntity{" +
                "statusCode='" + statusCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
