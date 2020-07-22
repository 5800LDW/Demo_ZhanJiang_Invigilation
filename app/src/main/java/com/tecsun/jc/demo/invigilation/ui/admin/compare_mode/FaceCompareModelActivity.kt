package com.tecsun.jc.demo.invigilation.ui.admin.compare_mode

import android.os.Bundle
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.utils.PreferenceUtil
import com.tecsun.jc.base.widget.CheckSwitchButton
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.register.util.constant.FaceRecognitionConst.FACE_COMPARE_MODE
import com.tecsun.jc.register.util.constant.FaceRecognitionConst.MODE_0
import com.tecsun.jc.register.util.constant.FaceRecognitionConst.MODE_1

/**
 * 切换认证比对的模式
 * @author liudongwen
 * @date 2019/11/04
 */
class FaceCompareModelActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.app_activity_face_compare_mode
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle(R.string.app_face_compare_mode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //默认是离线
        if (PreferenceUtil.get(this, FACE_COMPARE_MODE).isNullOrBlank()) {
            PreferenceUtil.set(this, FACE_COMPARE_MODE, MODE_0)
        }

        var csbMode = findViewById<CheckSwitchButton>(R.id.csbMode)

        csbMode.isChecked =
            if (PreferenceUtil.get(this, FACE_COMPARE_MODE) == MODE_0) {
                true
            } else {
                false
            }

        csbMode.setOnCheckedChangeListener { v, isChecked ->
            if (isChecked) {
                PreferenceUtil.set(this, FACE_COMPARE_MODE, MODE_0)
                showToast("当前离线模式")
            } else {
                PreferenceUtil.set(this, FACE_COMPARE_MODE, MODE_1)
                showToast("当前在线模式")
            }
        }
    }

}


































