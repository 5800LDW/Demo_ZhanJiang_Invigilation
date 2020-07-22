//package com.tecsun.jc.demo.invigilation.builder.compare
//
//import android.graphics.Bitmap
//import com.tecsun.jc.base.base.BaseActivity
//import com.tecsun.jc.base.bean.db.invigilation.bean.StudentDetailsBean
//import com.tecsun.jc.demo.invigilation.builder.ComparePhotoOnlineBuilder
//
//object CompareBuilderFactory {
//
//    private var studentDetailsBean: StudentDetailsBean? = null
//    private var selectPosition: Int? = null
//    fun setStudentDetailsBean(studentDetailsBean: StudentDetailsBean?) {
//        this.studentDetailsBean = studentDetailsBean
//    }
//
//    fun getStudentDetailsBean(): StudentDetailsBean? {
//        return studentDetailsBean
//    }
//    fun setSelectPosition(selectPosition: Int?) {
//        this.selectPosition = selectPosition
//    }
//
//
//    fun getBuilder(
//        a: BaseActivity,
//        localBitmap: Bitmap?,
//        selfPic: Bitmap?
//    ): ICompare? {
//        if (studentDetailsBean == null) {
//            return null
//        }
//
//        var builder = ComparePhotoOnlineBuilder
//        builder.setActivity(a)
//        builder.setPic(selfPic)
//        builder.setLocalBitmap(localBitmap)
//        builder.setStudentDetailsBean(studentDetailsBean)
//        builder.setSelectPosition(selectPosition)
//        return builder
//    }
//
//    fun release() {
//        studentDetailsBean = null
//        selectPosition = null
//    }
//
//}
//
//interface ICompare {
//    fun startCompare()
//    fun release()
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
