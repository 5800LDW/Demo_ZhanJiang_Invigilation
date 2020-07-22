package com.tecsun.jc.base.bean.old_age;

import com.tecsun.jc.base.bean.BaseResultEntity;

public class OldAgeObjectEntity extends BaseResultEntity {
    /**
     * data : {"code":"0","msg":"success","data":{"busSeqFlag":"0"},"page":null}
     * total : 0
     */

    private DataBeanX data;
    private int total;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class DataBeanX {
        /**
         * code : 0
         * msg : success
         * data : {"busSeqFlag":"0"}
         * page : null
         */

        private String code;
        private String msg;
        private DataBean data;
        private Object page;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public Object getPage() {
            return page;
        }

        public void setPage(Object page) {
            this.page = page;
        }

        public static class DataBean {
            /**
             * busSeqFlag : 0
             */

            private String busSeqFlag;

            public String getBusSeqFlag() {
                return busSeqFlag;
            }

            public void setBusSeqFlag(String busSeqFlag) {
                this.busSeqFlag = busSeqFlag;
            }
        }
    }


//    private String data;
//    private int total;
//
//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
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
//    @Override
//    public String toString() {
//        return "OldAgeEntity{" +
//                "data='" + data + '\'' +
//                ", total=" + total +
//                '}';
//    }


}
