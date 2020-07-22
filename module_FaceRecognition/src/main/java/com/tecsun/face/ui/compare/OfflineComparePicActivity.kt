package com.tecsun.face.ui.compare

import android.os.Bundle
import android.view.ViewGroup
import com.example.module_facerecognition.R
import com.tecsun.builder.ComparePhotoOfflineBuilder
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.common.BaseConstant.PIC_PATH_1
import com.tecsun.jc.base.common.BaseConstant.PIC_PATH_2
import com.tecsun.jc.base.utils.log.LogUtil


/**
 * 离线图片比对
 * @author liuDongWen
 * @date 2019/10/31
 */
//@Route(path = RouterHub.ROUTER_LIB_FACE_RECOGNITION_OFFLINE)
class OfflineComparePicActivity : BaseActivity() {

    private var localPicPath = ""
    private var selfPicPath = ""

    override fun getLayoutId(): Int {
        return R.layout.face_activity_offline_compare
    }

    override fun initView(savedInstanceState: Bundle?) {
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        localPicPath = intent.getStringExtra(PIC_PATH_1) ?: ""
        selfPicPath = intent.getStringExtra(PIC_PATH_2) ?: ""

        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>> localPicPath = $localPicPath")
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>> selfPicPath = $selfPicPath")

        if (!localPicPath.isNullOrBlank() || !selfPicPath.isNullOrBlank()) {
            ComparePhotoOfflineBuilder.setLocalPicPath(localPicPath)
            ComparePhotoOfflineBuilder.setSelfPicPath(selfPicPath)
        }
    }


    override fun finish() {
        super.finish()
        //注释掉activity本身的过渡动画
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
    }



















//    override fun getLayoutId(): Int {
//        return R.layout.face_activity_offline_compare
//    }
//
//    private var localPicPath = ""
//    private var selfPicPath = ""
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        initView()
//    }
//
//    var helper: SyKeanHelper? = null
//
//    private var mHandler = Handler()
//    fun initView() {
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//
////        localPicPath = intent.getStringExtra(PIC_PATH_1) ?: ""
////        selfPicPath = intent.getStringExtra(PIC_PATH_2) ?: ""
////
////        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>> localPicPath = $localPicPath")
////        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>> selfPicPath = $selfPicPath")
////
////        if (!localPicPath.isNullOrBlank() || !selfPicPath.isNullOrBlank()) {
//
//        //TODO 比对
//        helper = SyKeanHelper()
//        helper?.init(this)
//
////        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        helper?.registerPhotoForSFZPic(BitmapFactory.decodeFile(localPicPath))
//        var result = helper?.registerPhotoForSFZPic(BitmapFactory.decodeResource(getResources(), R.raw.face_sfz_ldw1))
//        Log.e("TAG",">>>>>>>>>>>>>>>>>>>>>>>>>>>> result: $result")
//
////        var selfPicBitmap = BitmapFactory.decodeFile(selfPicPath)
//        var selfPicBitmap = BitmapFactory.decodeResource(getResources(), R.raw.face_pic_ldw)
//        var b = helper?.canFindFace(selfPicBitmap)?:false
//        if (b ) {
//            var comparisonResult = helper?.comparisonGetScore(selfPicBitmap)
//            Log.e("TAG",">>>>>>>>>>>>>>>>>>>>>>>>>>>> 离线图片比对结果: $comparisonResult")
//            Toast.makeText(this,"离线图片比对结果: $comparisonResult", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    override fun finish() {
//        super.finish()
//        //注释掉activity本身的过渡动画
////        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
//    }
}



































