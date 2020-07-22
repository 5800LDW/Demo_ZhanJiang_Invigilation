package com.tecsun.jc.demo.invigilation.zhanjiang.bean;

public class UploadPicEntity {


    /**
     * statusCode : 200
     * message : 上传成功!
     * data : {"picId":1285391521520136192}
     * total : 0
     * pages : 0
     */

    private String statusCode;
    private String message;
    private DataBean data;
    private int total;
    private int pages;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public static class DataBean {
        /**
         * picId : 1285391521520136192
         */

        private long picId;

        public long getPicId() {
            return picId;
        }

        public void setPicId(long picId) {
            this.picId = picId;
        }
    }

    @Override
    public String toString() {
        return "UploadEntity{" +
                "statusCode='" + statusCode + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", total=" + total +
                ", pages=" + pages +
                '}';
    }
}
