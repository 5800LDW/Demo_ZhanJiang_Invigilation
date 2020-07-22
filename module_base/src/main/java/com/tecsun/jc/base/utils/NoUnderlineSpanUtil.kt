package com.tecsun.jc.base.utils

import android.text.Spannable
import android.text.Spanned
import android.text.TextPaint
import android.text.style.UnderlineSpan
import android.widget.TextView
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.R

/**
 * 改变textView超链接样式
 *
 * @author liudongwen
 * @date 2019/8/06
 */
class NoUnderlineSpanUtil {

     class NoUnderlineSpan : UnderlineSpan() {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            //ds.setColor(Color.argb(255, 255, 164, 102)); // 设置字体颜色
            ds.color = JinLinApp.context!!.resources.getColor(R.color.c_2358ff)
            //去掉下划线
            ds.isUnderlineText = false
        }
    }

    companion object {
        fun changeTextStyle(textView:TextView?) {
            textView?.let {
                val mNoUnderlineSpan = NoUnderlineSpan()
                if (textView?.text is Spannable) {
                    val s = textView?.text as Spannable
                    s.setSpan(mNoUnderlineSpan, 0, s.length, Spanned.SPAN_MARK_MARK)
                }
            }
        }
    }
}
