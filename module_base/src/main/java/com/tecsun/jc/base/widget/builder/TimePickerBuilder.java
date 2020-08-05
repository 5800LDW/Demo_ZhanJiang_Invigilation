package com.tecsun.jc.base.widget.builder;

import android.app.Activity;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.tecsun.jc.base.utils.PickerViewUtils;
import com.tecsun.jc.base.utils.log.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * TimePickerBuilder
 *
 * @author liudongwen
 * @date 2019/5/28
 */
public class TimePickerBuilder {

    private String titleText;

    private Activity activity;
    private ITimeSelectListener listener;

    /**
     * 例如2019-05 这个对象只处理 横岗的时间
     */
    private String defaultDate;
    /**
     * 时间选择器
     */
    private TimePickerView mTimePickerView;

    public String getTitleText() {
        return titleText;

    }

    public TimePickerBuilder setTitleText(String titleText) {
        this.titleText = titleText;
        return this;
    }

    public Activity getActivity() {
        return activity;
    }

    public TimePickerBuilder setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public ITimeSelectListener getListener() {
        return listener;
    }

    public TimePickerBuilder setListener(ITimeSelectListener listener) {
        this.listener = listener;
        return this;
    }

    public String getDefaultDate() {
        return defaultDate;
    }

    public TimePickerBuilder setDefaultDate(String defaultDate) {
        this.defaultDate = defaultDate;
        return this;
    }

    public interface ITimeSelectListener {
        /**
         * 回调选择的结果
         *
         * @param date
         * @param v
         */
        void selectResult(Date date, View v);
    }

    /**
     * 显示 时间选择控件;
     */
    public void showView() {

        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    show();
                }
            });
        }
    }


    private void show() {
        if (mTimePickerView == null) {

            Calendar startDate, endDate;
            endDate = Calendar.getInstance();
            startDate = Calendar.getInstance();
//            startDate.set(1950, 0, 1);
            startDate.set(1960, 0, 1);
            endDate.set(Calendar.getInstance().get(Calendar.YEAR), 11, 31);

            mTimePickerView = PickerViewUtils.createTimePickerView(activity,
                    new TimePickerView.OnTimeSelectListener() {

                        @Override
                        public void onTimeSelect(Date date, View v) {
                            //更新显示的时间
                            defaultDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);

                            if (listener != null) {
                                listener.selectResult(date, v);
                            }
                        }
                    }, startDate, endDate, true, true, true, false, false, false, titleText + "");
        }

        if (defaultDate != null) {
            try {
                String[] dates = defaultDate.split("-");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1, 1);
                //时间设置
                mTimePickerView.setDate(calendar);
            } catch (Exception e) {
                //时间设置
                mTimePickerView.setDate(Calendar.getInstance());
                LogUtil.e(e);
            }

        } else {
            //时间设置
            mTimePickerView.setDate(Calendar.getInstance());
        }
        //显示
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            mTimePickerView.show();
        }

    }


}
