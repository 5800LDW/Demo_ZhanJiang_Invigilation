//package com.tecsun.jc.base.widget
//
//import android.util.Log
//import android.view.View
//
//
///**
// *
// * 间隔时间内只允许单次点击
// *
// * @author liudongwen
// * @version
// * @date 2019/05/29
// */
// class SingleClickListener3 (l:MyListener?): View.OnClickListener {
//
//    var listener:MyListener? = null
//
//    init {
//
//        listener = l
//    }
//
//
//
//    private var mLastClickTime: Long = 0
//    /**
//     * 间隔的点击时间
//     */
//    var timeInterval = 300L
//
//    override fun onClick(v: View) {
//        val nowTime = System.currentTimeMillis()
//        if (nowTime - mLastClickTime < timeInterval) {
//            Log.d("TAG", "连续点击间隔太短被过滤")
//            return
//        }
//        mLastClickTime = nowTime
//        listener?.onSingleClick(v)
//    }
//
//
//    private fun set(){
//        var ii = SingleClickListener3(MyListener(){
//
//        })
//    }
//
//
//
//}
//
//
//
//interface MyListener{
//    /**
//     * 间隔时间内只允许单次点击
//     * @param v
//     */
//    fun onSingleClick(v: View)
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
