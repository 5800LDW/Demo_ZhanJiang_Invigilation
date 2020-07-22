package com.tecsun.jc.base.widget;

import android.util.Log;
import android.view.View;


/**
 *
 * 间隔时间内只允许单次点击
 *
 * @author liudongwen
 * @version
 * @date 2019/05/29
 */
public abstract class SingleClickListener implements View.OnClickListener {
    private long mLastClickTime;
    /**
     * 间隔的点击时间
     */
    private long timeInterval = 300L;

    public SingleClickListener() {

    }

    public long getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(long timeInterval) {
        this.timeInterval = timeInterval;
    }

    @Override
    public void onClick(View v) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime < timeInterval) {
            Log.d("TAG", "连续点击间隔太短被过滤");
            return;
        }
        mLastClickTime = nowTime;
        onSingleClick(v);
    }

    /**
     * 间隔时间内只允许单次点击
     * @param v
     */
    public abstract void onSingleClick(View v);

}
























