package com.tecsun.jc.base.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import com.bigkoo.pickerview.TimePickerView;
import com.tecsun.jc.base.R;

import java.util.Calendar;

/**
 * 滚动选择控件工具类
 *
 * @author pangshulian
 * @version v1.0.0
 * @date 2017/11/22 9:16
 */

public class PickerViewUtils {

    /**
     * 私有构造函数
     */
    private PickerViewUtils() {
    }

    /**
     * 创建时间选择器
     *
     * @param context  上下文
     * @param listener  点击确定监听
     * @param isShowYear  是否显示年
     * @param isShowMonth  是否显示月
     * @param isShowDay  是否显示日
     * @param isShowHour  是否显示时
     * @param isShowMins  是否显示分
     * @param isShowSecond  是否显示秒
     * @return
     */
    public static TimePickerView createTimePickerView(@NonNull Context context, TimePickerView.OnTimeSelectListener listener,
                                                      boolean isShowYear, boolean isShowMonth, boolean isShowDay,
                                                      boolean isShowHour, boolean isShowMins, boolean isShowSecond) {
        //时间选择器
        return createTimePickerView(context, listener, null, null, isShowYear, isShowMonth, isShowDay, isShowHour,
                isShowMins, isShowSecond);
    }

    /**
     * 创建时间选择器
     *
     * @param context  上下文
     * @param listener  点击确定监听
     * @param isShowYear  是否显示年
     * @param isShowMonth  是否显示月
     * @param isShowDay  是否显示日
     * @param isShowHour  是否显示时
     * @param isShowMins  是否显示分
     * @param isShowSecond  是否显示秒
     * @return
     */
    public static TimePickerView createTimePickerView(@NonNull Context context, TimePickerView.OnTimeSelectListener listener,
                                                      Calendar startDate, Calendar endDate,
                                                      boolean isShowYear, boolean isShowMonth, boolean isShowDay,
                                                      boolean isShowHour, boolean isShowMins, boolean isShowSecond) {
        //时间选择器
        TimePickerView.Builder builder = new TimePickerView.Builder(context, listener)
                .setType(new boolean[]{isShowYear, isShowMonth, isShowDay, isShowHour, isShowMins, isShowSecond})//
                // 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setSubmitColor(context.getResources().getColor(R.color.c_2358ff))//确定按钮文字颜色
                .setCancelColor(context.getResources().getColor(R.color.c_black_03))//取消按钮文字颜色
                .setTextColorCenter(context.getResources().getColor(R.color.c_1c1c1c))  //设置分割线之间的文字颜色
                .setTitleBgColor(context.getResources().getColor(R.color.c_white))//标题背景颜色 Night mode
                .setBgColor(context.getResources().getColor(R.color.c_white))//滚轮背景颜色 Night mode
                .setLabel("", "", "", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false);  //是否显示为对话框样式

        // 设置时间范围
        if (startDate != null && endDate != null) {
            builder.setRangDate(startDate, endDate);
        }

        return builder.build();
    }

}
