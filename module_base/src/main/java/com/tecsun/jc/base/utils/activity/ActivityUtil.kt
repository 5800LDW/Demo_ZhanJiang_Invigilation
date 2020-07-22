package com.tecsun.jc.base.utils.activity

import android.app.Activity
import android.content.Context
import android.content.Intent

//import com.ldw.xyz.R

object ActivityUtil {

    /***
     * 无需带intent  或 带
     *
     * @param context
     * @param cls
     * @param i
     */
    fun startActivity(context: Context, cls: Class<*>, i: Intent?) {
        if (i == null) {
            val intent = Intent(context, cls)
            context.startActivity(intent)
        } else if (i != null) {
            context.startActivity(i)

        }
//        if (context is Activity)
//            context.overridePendingTransition(
//                R.anim.push_left_in,
//                R.anim.push_left_out
//            )
    }

    /**
     *
     * @param activity
     * @param cls
     * @param i
     * @param enter
     * 0:default animation
     * @param exit
     * 0:default animation
     */
    fun startActivity(activity: Activity, cls: Class<*>, i: Intent?, enter: Int, exit: Int) {
        if (i == null) {
            val intent = Intent(activity, cls)
            activity.startActivity(intent)
        } else if (i != null) {
            activity.startActivity(i)
        }

        if (enter == 0 || exit == 0) {
            animation(activity)
        } else {
            activity.overridePendingTransition(enter, exit)
        }
    }

    private fun animation(activity: Activity) {
//        activity.overridePendingTransition(
//            R.anim.slide_buttom_to_top_02enter,
//            R.anim.slide_top_to_bottom_02exit
//        )
    }

    /***
     * 启动activity 的时候 带过去String
     *
     * @param activity
     * @param cls
     * @param key
     * @param value
     * @param ani 动画, 两个动画的int 值 例如:R.anim.slide_
     */
    fun startActivityWithString(
        activity: Activity,
        cls: Class<*>,
        key: String,
        value: String,
        vararg ani: Int
    ) {
        val intent = Intent()
        intent.setClass(activity, cls)
        intent.putExtra(key, value)
        activity.startActivity(intent)
        if (ani.size == 0) {
            animation(activity)
        } else {
            val enter = ani[0]
            val exit: Int
            if (ani.size >= 2) {
                exit = ani[1]
                activity.overridePendingTransition(enter, exit)
            } else {
//                activity.overridePendingTransition(
//                    enter,
//                    R.anim.slide_top_to_bottom_02exit
//                )//后面的这个是随意了;
            }
        }
    }


}
