package com.tecsun.jc.base.bean.electronic_card;

import com.tecsun.jc.base.bean.BaseResultEntity;

public class GetEleCardUserInfoEntity extends BaseResultEntity {


    /**
     * data : {"id":10,"esscNo":"220202197207120000","name":"张一","mobile":"13600000000","regionCode":"220200","signNo":"1","signSeq":"11","signLevel":"3","signDate":"20190624","validDate":"20290624","actionType":"1","bindStatus":"1","createTime":"2019-06-24 16:09:11","updateTime":"2019-06-24 16:39:12"}
     * total : 0
     */

    private DataBean data;
    private int total;

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

    public static class DataBean {
        /**
         * id : 10
         * esscNo : 220202197207120000
         * name : 张一
         * mobile : 13600000000
         * regionCode : 220200
         * signNo : 1
         * signSeq : 11
         * signLevel : 3
         * signDate : 20190624
         * validDate : 20290624
         * actionType : 1
         * bindStatus : 1
         * createTime : 2019-06-24 16:09:11
         * updateTime : 2019-06-24 16:39:12
         */

        private int id;
        /**社会保障号/身份证号*/
        private String esscNo;
        private String name;
        private String mobile;
        private String regionCode;
        /**签发号*/
        private String signNo;
        private String signSeq;
        private String signLevel;
        private String signDate;
        private String validDate;
        private String actionType;
        private String bindStatus;
        private String createTime;
        private String updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEsscNo() {
            return esscNo;
        }

        public void setEsscNo(String esscNo) {
            this.esscNo = esscNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(String regionCode) {
            this.regionCode = regionCode;
        }

        public String getSignNo() {
            return signNo;
        }

        public void setSignNo(String signNo) {
            this.signNo = signNo;
        }

        public String getSignSeq() {
            return signSeq;
        }

        public void setSignSeq(String signSeq) {
            this.signSeq = signSeq;
        }

        public String getSignLevel() {
            return signLevel;
        }

        public void setSignLevel(String signLevel) {
            this.signLevel = signLevel;
        }

        public String getSignDate() {
            return signDate;
        }

        public void setSignDate(String signDate) {
            this.signDate = signDate;
        }

        public String getValidDate() {
            return validDate;
        }

        public void setValidDate(String validDate) {
            this.validDate = validDate;
        }

        public String getActionType() {
            return actionType;
        }

        public void setActionType(String actionType) {
            this.actionType = actionType;
        }

        public String getBindStatus() {
            return bindStatus;
        }

        public void setBindStatus(String bindStatus) {
            this.bindStatus = bindStatus;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
