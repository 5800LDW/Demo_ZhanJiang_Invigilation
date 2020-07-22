package com.tecsun.jc.base.builder

import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.builder.base.BaseBuilder

/**
 *
 * @author liudongwen
 * @date 2019/11/07
 */
object TakeExamRegistrationNOImageBuilder : BaseBuilder() {
    override fun getFilePath(): String {
        return JinLinApp.context!!.getExternalFilesDir("student_exam_registration_pic").absolutePath
    }
}
















