package com.tecsun.jc.base.builder

import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.builder.base.BaseBuilder

/**
 * 学生的身份证图片保存, 是通过机器读取 身份证 的, 人证对比的时候用的上;
 *
 * @author liudongwen
 * @date 2019/10/28
 */
object StudentOwnSFZImageBuilder : BaseBuilder() {
    override fun getFilePath(): String {
//        super.getFilePath()
        return JinLinApp.context!!.getExternalFilesDir("student_sfz_pic").absolutePath
    }
}
















