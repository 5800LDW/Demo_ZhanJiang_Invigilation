package com.tecsun.jc.base.builder

import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.builder.base.BaseBuilder


object ZhanJiangRegisterSFZImageBuilder : BaseBuilder() {

    val CONST_POSITIVE_PIC_ID = "CONST_POSITIVE_PIC_ID"

    val CONST_NEGATIVE_PIC_ID = "CONST_NEGATIVE_PIC_ID"


    override fun getFilePath(): String {
//        super.getFilePath()
        return JinLinApp.context!!.getExternalFilesDir("registerPic").absolutePath
    }
}
















