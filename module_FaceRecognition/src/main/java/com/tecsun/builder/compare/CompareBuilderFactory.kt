package com.tecsun.jc.demo.invigilation.builder.compare

import android.graphics.Bitmap
import com.tecsun.builder.ComparePhotoOfflineBuilder
import com.tecsun.builder.ComparePhotoOnlineBuilder
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.StudentDetailsBean
import com.tecsun.jc.base.utils.PreferenceUtil
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.register.util.constant.FaceRecognitionConst.FACE_COMPARE_MODE
import com.tecsun.jc.register.util.constant.FaceRecognitionConst.MODE_0
import com.tecsun.jc.register.util.constant.FaceRecognitionConst.MODE_1

/**
 * 对比方式处理
 * @author liuDongWen
 * @date 2019/11/4
 */
object CompareBuilderFactory {

    private var studentDetailsBean: StudentDetailsBean? = null
    private var selectPosition: Int? = null

    fun getStudentDetailsBean(): StudentDetailsBean? {
        return studentDetailsBean
    }

    fun setSelectPosition(selectPosition: Int?) {
        this.selectPosition = selectPosition
    }

    fun initICompare(studentDetailsBean: StudentDetailsBean?) {
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> CompareBuilderFactory  studentDetailsBean: $studentDetailsBean")
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> CompareBuilderFactory  initICompare")
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> CompareBuilderFactory  PreferenceUtil.get(JinLinApp.context, FACE_COMPARE_MODE) :${PreferenceUtil.get(JinLinApp.context, FACE_COMPARE_MODE)}")
        this.studentDetailsBean = studentDetailsBean
        when (PreferenceUtil.get(JinLinApp.context, FACE_COMPARE_MODE)) {
            MODE_0 -> ComparePhotoOnlineBuilder.init()
            MODE_1 -> ComparePhotoOfflineBuilder.init()
        }
    }

        fun getBuilder(
        a: BaseActivity,
        localBitmap: Bitmap,
        selfPic: Bitmap,
        localBitmapPath: String,
        selfPicPath: String
    ): ICompare? {
        if (studentDetailsBean == null) {
            return null
        }
        return when (PreferenceUtil.get(JinLinApp.context, FACE_COMPARE_MODE)) {
            MODE_0 -> startOfflineCompare(a, localBitmapPath, selfPicPath)
            MODE_1 -> startOnLineCompare(a, localBitmap, selfPic)
            else -> null
        }
    }

    fun release() {
        studentDetailsBean = null
        selectPosition = null
    }

    fun doQuitApp(){
        release()
        ComparePhotoOnlineBuilder.release()
        ComparePhotoOfflineBuilder.getSyKeanHelper()?.dispose()
    }



    private fun startOnLineCompare(
        a: BaseActivity,
        localBitmap: Bitmap,
        selfPic: Bitmap
    ): ICompare {
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>> startOnLineCompare")
        var builder = ComparePhotoOnlineBuilder
        builder.setActivity(a)
        builder.setPic(selfPic)
        builder.setLocalBitmap(localBitmap)
        builder.setStudentDetailsBean(studentDetailsBean)
        builder.setSelectPosition(selectPosition)
        return builder
    }

    private fun startOfflineCompare(
        a: BaseActivity,
        localBitmapPath: String,
        selfPicPath: String
    ): ICompare {
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>> startOfflineCompare")
        var builder = ComparePhotoOfflineBuilder
        builder.setActivity(a)
        builder.setLocalPicPath(localBitmapPath)
        builder.setSelfPicPath(selfPicPath)
        builder.setSelectPosition(selectPosition)
        return builder
    }
}

interface ICompare {
    fun init()
    fun initState(): Boolean
    fun startCompare()
    fun release()
}
