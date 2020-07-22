package com.tecsun.jc.base.widget

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView


class HideKeyBoardScrollView : NestedScrollView {

    private var scrollViewListener: ScrollViewListener? = null

    constructor(context: Context) : super(context)

    constructor(
        context: Context, attrs: AttributeSet,
        defStyle: Int
    ) : super(context, attrs, defStyle)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setScrollViewListener(scrollViewListener: ScrollViewListener) {
        this.scrollViewListener = scrollViewListener
    }

    override fun onScrollChanged(x: Int, y: Int, oldx: Int, oldy: Int) {
        super.onScrollChanged(x, y, oldx, oldy)
        if (scrollViewListener != null) {
            scrollViewListener!!.onScrollChanged(this, x, y, oldx, oldy)
        }
//
//        if(Math.abs(y - oldy) > 100 ){
//            val imm = getSystemService(JinLinApp.context!!,InputMethodManager::class.java)
//            imm?.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
//        }

    }

    interface ScrollViewListener {
        fun onScrollChanged(scrollView: HideKeyBoardScrollView, x: Int, y: Int, oldx: Int, oldy: Int)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        overScrollMode = OVER_SCROLL_NEVER
    }
}