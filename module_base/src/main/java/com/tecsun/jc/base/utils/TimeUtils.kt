package com.tecsun.jc.base.utils

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    fun strToDate(strDate: String?): Date? {
        if (strDate == null || strDate.isEmpty()) {
            return null
        }
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val pos = ParsePosition(0)
        return formatter.parse(strDate, pos)
    }

    fun dateToStr(dateDate: Date?): String? {
        if (dateDate == null) {
            return ""
        }
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(dateDate)
    }

    fun strToStr(strDate: String?): String? {
        return dateToStr(strToDate(strDate))
    }

    fun getCurrentTime(): String {
        val date = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(date)
    }
}