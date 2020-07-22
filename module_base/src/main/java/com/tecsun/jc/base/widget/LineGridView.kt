package com.tecsun.jc.base.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.GridView
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.R
import com.tecsun.jc.base.utils.ScreenUtil

/**
 * 有分割线的GridView
 *
 * @author liudongwen
 * @date 2019/8/13
 */
class LineGridView : GridView {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val expandSpec = MeasureSpec.makeMeasureSpec(
            Integer.MAX_VALUE shr 2,
            MeasureSpec.AT_MOST
        )
        super.onMeasure(widthMeasureSpec, expandSpec)
    }

    private var lineInterval = 0f

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)

        lineInterval = ScreenUtil.dip2px(JinLinApp.context, 15f).toFloat()

        val localView1 = getChildAt(0)
        val column = width / localView1.width//列数
        val childCount = childCount
        val localPaint: Paint
        localPaint = Paint()
        localPaint.strokeWidth = ScreenUtil.dip2px(JinLinApp.context, 1f).toFloat()
        localPaint.style = Paint.Style.STROKE
        localPaint.color = context.resources.getColor(R.color.c_all_bg)//这个就是设置分割线的颜色

        for (i in 0 until childCount) {
            val cellView = getChildAt(i)
            //每一行最后一个画出横线
            if ((i + 1) % column == 0) {

                //倒数最后一个不用
                if (i == childCount - 1) {

                } else {
                    canvas.drawLine(
                        cellView.left.toFloat(),
                        cellView.bottom.toFloat(),
                        cellView.right.toFloat() - lineInterval,//X坐标减一下
                        cellView.bottom.toFloat(),
                        localPaint
                    )
                }

            }

            //最后一行的item, 画出竖线
            else if (i + 1 > childCount - childCount % column) {
                canvas.drawLine(
                    cellView.right.toFloat(),
                    cellView.top.toFloat(),
                    cellView.right.toFloat(),
                    cellView.bottom.toFloat() - lineInterval,//y坐标减一下
                    localPaint
                )
            } else {

                //画竖线
                if (i == 0) {
                    canvas.drawLine(
                        cellView.right.toFloat(),
                        cellView.top.toFloat() + lineInterval,//y坐标加一下,
                        cellView.right.toFloat(),
                        cellView.bottom.toFloat(),
                        localPaint
                    )
                } else {
                    canvas.drawLine(
                        cellView.right.toFloat(),
                        cellView.top.toFloat(),
                        cellView.right.toFloat(),
                        cellView.bottom.toFloat() - lineInterval,//y坐标减一下,
                        localPaint
                    )
                }

                //画横线
                //倒数第二个不用,画底部横线
                if (i == childCount - 2) {

                } else {
                    canvas.drawLine(
                        cellView.left.toFloat() + lineInterval,
                        cellView.bottom.toFloat(),
                        cellView.right.toFloat(),
                        cellView.bottom.toFloat(),
                        localPaint
                    )
                }

            }
        }
    }

}