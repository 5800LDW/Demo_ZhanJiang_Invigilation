package com.tecsun.jc.base.widget.diy

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.R
import com.tecsun.jc.base.utils.ScreenUtil
import com.tecsun.jc.base.utils.log.LogUtil

/**
 *
 * 组合的Item View
 * @author liudongwen
 * @date 2019/11/05
 */
class OnClickItemView : LinearLayout {

    private var imageView: ImageView? = null
    private var textView: TextView? = null
    private var llBackground: LinearLayout? = null

    private var bg_background: Drawable? = null
    private var iv_pic: Drawable? = null
    private var text_color = Color.BLACK
    private var text: String? = ""
    private var text_size: Float = 0.toFloat()

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initTypeValue(context, attrs)
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initTypeValue(context, attrs)
        initView(context)
    }

    fun initTypeValue(context: Context, attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.OnClickItemView)
        text_size = a.getDimension(
            R.styleable.OnClickItemView_text_size,
            ScreenUtil.dip2px(JinLinApp.context!!, 9f).toFloat()
        )

        text = a.getString(R.styleable.OnClickItemView_text)
        text_color = a.getColor(R.styleable.OnClickItemView_text_color, Color.BLACK)
        bg_background = a.getDrawable(R.styleable.OnClickItemView_bg_background)
        iv_pic = a.getDrawable(R.styleable.OnClickItemView_iv_pic)
        a.recycle()
    }

    fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.base_item_on_click_item_view, this, true)
        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.textView)
        llBackground = findViewById(R.id.llBackground)

        llBackground!!.background =
            JinLinApp.context!!.resources.getDrawable(R.drawable.white_base_white_round_selector)
        if (bg_background != null) {
            llBackground!!.background = bg_background
        } else {
            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>> bg_background 是null")
        }
        if (iv_pic != null) {
            imageView!!.background = iv_pic
        }

        textView!!.setTextColor(text_color)
        textView!!.text = text?:""
        textView!!.textSize = text_size
    }

}

















