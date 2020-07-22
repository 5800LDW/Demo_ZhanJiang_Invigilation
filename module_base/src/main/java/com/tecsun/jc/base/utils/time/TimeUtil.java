package com.tecsun.jc.base.utils.time;


import android.text.TextUtils;
import com.tecsun.jc.base.utils.log.LogUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间处理工具类
 *
 * @author liudongwen
 * @date 2019/05/29
 */
public class TimeUtil {


    public static long getCurrentTimeMillisFromInternet() {
        URL url;//取得资源对象
        long ld = 0;
        try {
            url = new URL("http://www.baidu.com");
            //生成连接对象
            URLConnection uc = url.openConnection();
            //发出连接
            uc.connect();
            //取得网站日期时间
            ld = uc.getDate();
            System.out.println(ld);

        } catch (MalformedURLException e) {
            LogUtil.e(e);
        } catch (IOException e) {
            LogUtil.e(e);
        }
        return ld;
    }

    /**
     * 获取24小时制时间yyyy-MM-dd HH-mm-ss
     * 获取12小时制时间yyyy-MM-dd hh-mm-ss
     *
     * @param dateFormat
     * @return
     */
    public static String getCurrentDateAccordingToFormat(String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }


    /**
     * 几天前的日期
     * <p>
     * 具体思路如下：
     * 1、先使用Calendar获取当前的年份、当前的月份、当前的日期。
     * 2、在用Calendar的set函数设定当前时间。
     * 3、通过Calendar的add函数来寻找前或者后几天的日期。
     * 4、最后用Calendar的get函数获取当时的具体日期。
     * 本方法将日期转化为String并保存在List<String>中了，其中有一些细节需要大家仔细注意。
     */
    public static List<String> getDateAFewDaysAgo(int daysAgo) {

        LinkedList<String> titles = new LinkedList<>();
        String mYear; // 当前年
        String mMonth; // 月
        String mDay;
        int current_day;
        int current_month;
        int current_year;

        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        current_day = c.get(Calendar.DAY_OF_MONTH);
        current_month = c.get(Calendar.MONTH);
        current_year = c.get(Calendar.YEAR);
        for (int i = 0; i < daysAgo; i++) {
            c.clear();//记住一定要clear一次
            c.set(Calendar.MONTH, current_month);
            c.set(Calendar.DAY_OF_MONTH, current_day);
            c.set(Calendar.YEAR, current_year);
            //j记住是DATE
            c.add(Calendar.DATE, -i);
            // 获取当前月份
            mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);
            // 获取当前日份的日期号码
            mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
            // 获取当前年份
            mYear = String.valueOf(c.get(Calendar.YEAR));
            String date = mYear + "-" + mMonth + "-" + mDay;
            titles.addFirst(date);
        }
        return titles;
    }

    /**
     * @param dateFormat 例如:yyyy-MM-dd
     * @param dateStr    例如2012-2-1
     * @return
     */
    public static Date string2Date(String dateFormat, String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.parse(dateStr);

    }

//    /**
//     *
//     * @param dateStr 例如2012-2-1
//     * @return
//     */
//    public static Date string2Date2(String dateStr){
//        return new Date(dateStr);
//    }


    /**
     * @param dateFormat 例如:yyyy-MM-dd
     * @param date
     * @return
     */
    public static String date2String(String dateFormat, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    public static String getPastYear(String dateFormat, int year) {
        int yearNew = -year;
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.YEAR, yearNew);
        Date pastTime = ca.getTime();
        return date2String(dateFormat, pastTime);
    }

    public static String getPastMonth(String dateFormat, int m) {
        int month = -m;
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.MONTH, month);
        Date pastTime = ca.getTime();
        return date2String(dateFormat, pastTime);
    }

    public static String getPastMonth(String dateFormat, int m, String dateStr) {
        Date date = new Date();
        try {
            date = string2Date(dateFormat, dateStr);
        } catch (Exception e) {
            LogUtil.e(e);
        }

        int month = -m;
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MONTH, month);
        Date pastTime = ca.getTime();
        return date2String(dateFormat, pastTime);
    }


    /**
     * 获取当前日期是星期几
     *
     * @param date
     * @return 当前日期是星期几
     */
    public String getWeekOfDate(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 获取当前日期是星期几
     *
     * @param date
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate2(Date date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }


    /**
     * 获取当前农历
     *
     * @return
     */
    public static Lunar getNowLunar() {
        return new Lunar();
    }


    public static String changeFormat(String strDate) {
        String str = strDate;
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            String format = "yyyy-MM-dd";
            Date date = TimeUtil.string2Date(format, strDate);
            if (date != null) {
                str = TimeUtil.date2String(format, date);
            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return str;
    }
}
