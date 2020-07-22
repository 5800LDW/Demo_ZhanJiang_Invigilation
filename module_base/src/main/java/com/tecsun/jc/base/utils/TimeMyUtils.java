package com.tecsun.jc.base.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeMyUtils {
    /**
     * 时间对比
     */
    public static boolean compareData(String startDate, String endDate) {
//		LogUtils.d("xiaoliang", "startDate=" + startDate + "endDate=" + endDate);
        DateFormat df = new SimpleDateFormat("yyyy-MM");
        try {
            Date d1 = df.parse(startDate);
            Date d2 = df.parse(endDate);
            long diff = d1.getTime() - d2.getTime();
//			LogUtils.d("xiaoliang", "diff=" + diff);
            if (diff > 0) {//startDate大于endDate
                return true;
            } else {//startDate小于endDate
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
