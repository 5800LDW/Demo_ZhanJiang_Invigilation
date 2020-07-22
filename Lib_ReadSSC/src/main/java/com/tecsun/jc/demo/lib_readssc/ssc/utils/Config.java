package com.tecsun.jc.demo.lib_readssc.ssc.utils;

public class Config {
    public static String CardActive = "002";
    public static String CardOther = "003";
    public static final String TQ_NAME = "name";
    public static final String TQ_SEX = "tq_sex";
    public static final String TQ_ID_NUM = "idCard";
    public static final String TQ_NATION = "tq_nation";
    public static final String OPTION_ID = "option_id";
    public static final String OPTION_NAME = "option_name";
    public static final boolean isShouji = false;

    public static final int SIGN_IN_INTERVAL_TIME = 25 * 60 * 1000;

    public enum SYSDiaLogType {
        DefaultTpye,//默認的樣式，系統樣式
        IosType,//ios風格的
        HorizontalWithNumberProgressBar,  // 進度條
        RoundWidthNumberProgressBar,     // 圓形進度條
        ;
    }

}
