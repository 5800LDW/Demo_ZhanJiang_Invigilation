package com.tecsun.jc.base.utils

import android.content.Context
import android.os.Build
import android.util.Log

/**
 * @author liudongwen
 * @date 2019/7/12
 */
object ColorUtil {
    private const val TAG = "ColorUtil"

    /**
     * https://blog.csdn.net/xuqingfeng77/article/details/55101835
     * @param context
     * @param id 例如 R.color.RRGGBB
     * @return
     */
    fun colorToString(context: Context, id: Int): String {
        val ints = toRGB(context, id)
        return toHex(ints[0], ints[1], ints[2])
    }

    /**
     * 16进制转GRB颜色值方法
     *
     * @param hex 例如:#0CA9E3
     * @return
     */
    fun toRGB(hex: String): IntArray {
        val color = Integer.parseInt(hex.replace("#", ""), 16)
        val red = color and 0xff0000 shr 16
        val green = color and 0x00ff00 shr 8
        val blue = color and 0x0000ff
        Log.d(TAG, "red=$red--green=$green--blue=$blue")
        return intArrayOf(red, green, blue)
    }

    /**
     * 16进制转GRB颜色值方法
     *
     * @param context
     * @param id 例如:R.color.RRGGBB
     * @return
     */
    fun toRGB(context: Context, id: Int): IntArray {
        val color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getColor(id)
        } else {
            context.resources.getColor(id)
        }
        val red = color and 0xff0000 shr 16
        val green = color and 0x00ff00 shr 8
        val blue = color and 0x0000ff
        return intArrayOf(red, green, blue)
    }

    /**
     * GRB转16进制颜色值方法
     *
     * @param red
     * @param green
     * @param blue
     */
    fun toHex(red: Int, green: Int, blue: Int): String {
        var hr: String? = Integer.toHexString(red)
        var hg: String? = Integer.toHexString(green)
        var hb: String? = Integer.toHexString(blue)
        Log.d(TAG, "#$hr$hg$hb")

        hr = addZero(hr)
        hg = addZero(hg)
        hb = addZero(hb)

        val sb = StringBuffer()
        sb.append("#")
        sb.append(hr)
        sb.append(hg)
        sb.append(hb)
        return sb.toString()

    }

    private fun addZero(str1: String?): String? {
        var str = str1

        if (str != null && str.length < 2) {
            val stringBuffer = StringBuffer()
            stringBuffer.append("0")
            stringBuffer.append(str)
            str = addZero(stringBuffer.toString())
        }
        return str
    }

}

