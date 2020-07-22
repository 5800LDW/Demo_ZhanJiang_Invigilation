package com.tecsun.jc.base.collector;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * activity处理类
 *
 *  * @author liudongwen
 *  * @date 2019/6/11
 */
public class BaseActivityCollector {

    private static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static Activity getTopActivity() {
        if (activityList.isEmpty()) {
            return null;
        } else {
            return activityList.get(activityList.size() - 1);
        }
    }

    public static void finishAllActivity() {
        Log.i("TAG","-------------------finishAllActivity()------------------");

        for (int i = 0, size = activityList.size(); i < size; i++) {
            if (activityList.get(i) != null) {
                Activity activity = activityList.get(i);
                Log.i("TAG",activity.toString());
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
        activityList.clear();

    }


}

























