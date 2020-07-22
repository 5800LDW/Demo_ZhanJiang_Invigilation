package com.tecsun.jc.base.utils

import android.content.Context


object CommonTools {
    /**
     * 将Drawable StringArray转为资源ID数组
     * @param ctx 上下文
     * @param arrIconId 图标资源ArrayID
     * @return 资源ID数组
     */
    fun getArrayDrawable(ctx: Context, arrIconId: Int): IntArray {
        val ar = ctx.resources.obtainTypedArray(arrIconId)
        val len = ar.length()
        val resIds = IntArray(len)
        for (i in 0 until len) {
            resIds[i] = ar.getResourceId(i, 0)
        }
        ar.recycle()

        return resIds
    }
}