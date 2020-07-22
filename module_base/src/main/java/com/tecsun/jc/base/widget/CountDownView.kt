package com.tecsun.jc.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.Button
import androidx.annotation.RequiresApi
import com.tecsun.jc.base.R

/**
 * 验证码倒计时
 * @author liudongwen
 */
@SuppressLint("AppCompatCustomView")
class CountDownView : Button, Runnable {

    private var canClickTextColor: Int? = null
    private var canNotClickTextColor: Int? = null
    // 倒计时秒数
    private var mTotalTime = 60
    // 当前秒数
    private var mCurrentTime: Int = 0
    // 记录原有的文本
    private var mRecordText: CharSequence? = null
    // 标记是否重置了倒计控件
    private var mFlag: Boolean = false

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
    }

    /**
     * 设置倒计时总秒数
     */
    fun setTotalTime(totalTime: Int) {
        this.mTotalTime = totalTime
    }

    /**
     * 重置倒计时控件 , 会恢复到开始的文字样式
     */
    fun resetState() {
        mFlag = true
    }

    /**开始倒计时*/
    fun startCountDonw() {
        //不能再点击了
        isClickable = false
        setTextColor(canNotClickTextColor ?: resources.getColor(R.color.c_2358ff))

        mRecordText = text
        mCurrentTime = mTotalTime
        post(this)
    }

    /**设置开始时的颜色*/
    fun setStartTextColor(i: Int?) {
        canClickTextColor = i
    }

    /**设置倒计时的时候的颜色*/
    fun setCountDownTextColor(i: Int?) {
        canNotClickTextColor = i
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //不能进行点击
        isClickable = true
    }

    override fun onDetachedFromWindow() {
        // 移除延迟任务，避免内存泄露
        removeCallbacks(this)
        super.onDetachedFromWindow()
    }

    override fun run() {
        if (mCurrentTime == 0 || mFlag) {
            //能进行点击
            isClickable = true
            setTextColor(canClickTextColor ?: resources.getColor(R.color.c_2358ff))

            text = mRecordText
            mFlag = false

        } else {
            mCurrentTime--
            text = "重新获取($mCurrentTime$TIME_UNIT)"
            postDelayed(this, 1000)
        }
    }

    companion object {
        private val TIME_UNIT = "秒" // 秒数单位文本
    }
}
