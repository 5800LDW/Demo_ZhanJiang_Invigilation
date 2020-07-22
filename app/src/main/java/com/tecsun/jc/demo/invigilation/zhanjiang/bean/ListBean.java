package com.tecsun.jc.demo.invigilation.zhanjiang.bean;

import com.tecsun.jc.base.bean.filter.IFilter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListBean {

    /**
     * statusCode : 200
     * message : 查询成功
     * data : [{"id":2,"signNum":100,"courseName":"语文","createTime":"2020-07-20 18:05:28.0"},{"id":3,"signNum":50,"courseName":"数学","createTime":"2020-07-20 18:05:44.0"},{"id":4,"signNum":80,"courseName":"英语","createTime":"2020-07-20 18:05:51.0"},{"id":21,"signNum":80,"courseName":"体育","createTime":"2020-07-21 10:03:38.0"}]
     * total : 0
     * pages : 0
     */

    private String statusCode;
    private String message;
    private int total;
    private int pages;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements IFilter {
        /**
         * id : 2
         * signNum : 100
         * courseName : 语文
         * createTime : 2020-07-20 18:05:28.0
         */

        private int id;
        private int signNum;
        private String courseName;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSignNum() {
            return signNum;
        }

        public void setSignNum(int signNum) {
            this.signNum = signNum;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", signNum=" + signNum +
                    ", courseName='" + courseName + '\'' +
                    ", createTime='" + createTime + '\'' +
                    '}';
        }

        @NotNull
        @Override
        public String getInfo() {
            return courseName;
        }
    }

    @Override
    public String toString() {
        return "ListBean{" +
                "statusCode='" + statusCode + '\'' +
                ", message='" + message + '\'' +
                ", total=" + total +
                ", pages=" + pages +
                ", data=" + data +
                '}';
    }
}
