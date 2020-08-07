package com.tecsun.jc.demo.invigilation.zhanjiang.test

import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

internal class Test : AppCompatActivity() {
    fun ma(et: Array<EditText>) {
        for (i in et.indices) {
            et[i].isClickable = false
            et[i].isFocusable = false
        }
    }
}