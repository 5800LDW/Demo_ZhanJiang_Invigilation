package com.tecsun.jc.demo.invigilation.zhanjiang.bean;

import java.util.List;

public class DictionariesInfoEntity extends ZhanJiangBaseEntity {
    /**
     * data : [{"pageNo":0,"pageSize":0,"code":"E","id":"327","codedesc":"台湾证件","type":"1","typedesc":null,"status":"1","orderno":null,"lastupdacct":"408","note":null,"pid":"CERT_TYPE"},{"pageNo":0,"pageSize":0,"code":"F","id":"328","codedesc":"外国护照","type":"1","typedesc":null,"status":"1","orderno":null,"lastupdacct":"408","note":null,"pid":"CERT_TYPE"},{"pageNo":0,"pageSize":0,"code":"G","id":"329","codedesc":"其他","type":"1","typedesc":null,"status":"1","orderno":null,"lastupdacct":"408","note":null,"pid":"CERT_TYPE"},{"pageNo":0,"pageSize":0,"code":"A","id":"323","codedesc":"身份证","type":"1","typedesc":null,"status":"1","orderno":null,"lastupdacct":"408","note":null,"pid":"CERT_TYPE"},{"pageNo":0,"pageSize":0,"code":"B","id":"324","codedesc":"军官证","type":"1","typedesc":null,"status":"1","orderno":null,"lastupdacct":"408","note":null,"pid":"CERT_TYPE"},{"pageNo":0,"pageSize":0,"code":"C","id":"325","codedesc":"香港证件","type":"1","typedesc":null,"status":"1","orderno":null,"lastupdacct":"408","note":null,"pid":"CERT_TYPE"},{"pageNo":0,"pageSize":0,"code":"D","id":"326","codedesc":"澳门证件","type":"1","typedesc":null,"status":"1","orderno":null,"lastupdacct":"408","note":null,"pid":"CERT_TYPE"}]
     * total : 0
     * pages : 0
     */
    private int total;
    private int pages;
    private List<DataBean> data;

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

    public static class DataBean {
        /**
         * pageNo : 0
         * pageSize : 0
         * code : E
         * id : 327
         * codedesc : 台湾证件
         * type : 1
         * typedesc : null
         * status : 1
         * orderno : null
         * lastupdacct : 408
         * note : null
         * pid : CERT_TYPE
         */

        private int pageNo;
        private int pageSize;
        private String code;
        private String id;
        private String codedesc;
        private String type;
        private Object typedesc;
        private String status;
        private Object orderno;
        private String lastupdacct;
        private Object note;
        private String pid;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCodedesc() {
            return codedesc;
        }

        public void setCodedesc(String codedesc) {
            this.codedesc = codedesc;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getTypedesc() {
            return typedesc;
        }

        public void setTypedesc(Object typedesc) {
            this.typedesc = typedesc;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getOrderno() {
            return orderno;
        }

        public void setOrderno(Object orderno) {
            this.orderno = orderno;
        }

        public String getLastupdacct() {
            return lastupdacct;
        }

        public void setLastupdacct(String lastupdacct) {
            this.lastupdacct = lastupdacct;
        }

        public Object getNote() {
            return note;
        }

        public void setNote(Object note) {
            this.note = note;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "pageNo=" + pageNo +
                    ", pageSize=" + pageSize +
                    ", code='" + code + '\'' +
                    ", id='" + id + '\'' +
                    ", codedesc='" + codedesc + '\'' +
                    ", type='" + type + '\'' +
                    ", typedesc=" + typedesc +
                    ", status='" + status + '\'' +
                    ", orderno=" + orderno +
                    ", lastupdacct='" + lastupdacct + '\'' +
                    ", note=" + note +
                    ", pid='" + pid + '\'' +
                    '}';
        }
    }






}
