package com.tecsun.jc.demo.invigilation.zhanjiang.bean;

public class DeclareResultEntity extends ZhanJiangBaseEntity {
    /**
     * data : {}
     * message : string
     * pages : 0
     * statusCode : string
     * total : 0
     */

    private DataBean data;
    private int pages;
    private int total;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class DataBean {
    }

    @Override
    public String toString() {
        return "DeclareResultEntity{" +
                "data=" + data +
                ", pages=" + pages +
                ", total=" + total +
                '}';
    }
}
