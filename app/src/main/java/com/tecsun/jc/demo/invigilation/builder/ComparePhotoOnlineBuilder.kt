//package com.tecsun.jc.demo.invigilation.builder
//
//import android.content.Intent
//import android.graphics.Bitmap
//import com.tecsun.jc.base.JinLinApp
//import com.tecsun.jc.base.base.BaseActivity
//import com.tecsun.jc.base.bean.ComparePhotoBean
//import com.tecsun.jc.base.bean.PictureBean
//import com.tecsun.jc.base.bean.db.invigilation.bean.StudentDetailsBean
//import com.tecsun.jc.base.bean.param.ComparePhotoParam
//import com.tecsun.jc.base.bean.param.PictureParam
//import com.tecsun.jc.base.common.BaseConstant
//import com.tecsun.jc.base.listener.IEvents
//import com.tecsun.jc.base.listener.OkGoRequestCallback
//import com.tecsun.jc.base.utils.BitmapUtils
//import com.tecsun.jc.base.utils.OkGoManager
//import com.tecsun.jc.base.utils.log.LogUtil
//import com.tecsun.jc.base.utils.myTimeUtils
//import com.tecsun.jc.demo.invigilation.builder.compare.ICompare
//import com.tecsun.jc.register.util.constant.Const
//import com.tecsun.jc.register.util.constant.Const.EXAMINATION_ROOM_INFO_CODE
//
///**
// * 在线图片比对
// * @author liudongwen
// * @date 2019/10/28
// */
//object ComparePhotoOnlineBuilder : ICompare {
//
////    private var localBitmap: Bitmap? = null
//
//    private var activity: BaseActivity? = null
//    private var localPicID = 0
//    private var takePicID = 0
//    /**自拍照片 */
//    private var pic: Bitmap? = null
//    /**本地的图片,录入的时候的图片, 或身份证图片, 社保卡图片 */
//    private var localBitmap: Bitmap? = null
//    private var studentDetailsBean: StudentDetailsBean? = null
//
//    private var selectPosition:Int? = null
//
//    fun setActivity(activity: BaseActivity?) {
//        this.activity = activity
//    }
//
//    fun setPic(selfPic: Bitmap?) {
//        this.pic = selfPic
//    }
//
//    fun setLocalBitmap(localBitmap: Bitmap?) {
//        this.localBitmap = localBitmap
//    }
//
//    fun setStudentDetailsBean(studentDetailsBean: StudentDetailsBean?) {
//        this.studentDetailsBean = studentDetailsBean
//    }
//
//    fun setSelectPosition(selectPosition: Int?) {
//        this.selectPosition = selectPosition
//    }
//
//
//
//
//    /***
//     *
//     * @param a
//     * @param localBitmap 本地的图片,录入的时候的图片, 或身份证图片, 社保卡图片;
//     * @param selfPic 个人自拍照
//     * @param studentDetailsBean
//     */
//    override fun startCompare() {
//        activity?.showLoadingDialog(tipContent = "正在上传本地图片数据...")
//        var base64 = BitmapUtils.bitmapToBase64(localBitmap)
//
//        if(studentDetailsBean !=null){
//            uploadGetMessageResultPictureForLocalPicID(base64, studentDetailsBean!!)
//        }
//        else{
//            activity?.dismissLoadingDialog()
//        }
//    }
//
//    /**
//     * 释放资源;
//     */
//    override fun release() {
//        if (localBitmap != null) {
//            localBitmap = null
//        }
//        if (pic != null) {
//            pic = null
//        }
//        if (studentDetailsBean != null) {
//            studentDetailsBean = null
//        }
//        if (activity != null) {
//            if (!activity!!.isFinishing) {
//                activity?.finish()
//            }
//            activity = null
//        }
//    }
//
//
//    private fun uploadGetMessageResultPictureForLocalPicID(
//        BBase64: String?,
//        studentDetailsBean: StudentDetailsBean
//    ) {
//        val param = PictureParam()
//        param.picType = "001"
////        param.picType = "101"
//        param.channelcode = "App"
//        param.picBase64 = BBase64
//        LogUtil.e("TAG", "uploadGetMessageResultPicture")
////        LogUtil.e("TAG", param)
//
//        OkGoManager.instance.okGoRequestManage(
//            Const.UPLOAD_PICTURE, param
//            , PictureBean::class.java, object : OkGoRequestCallback<PictureBean> {
//
//                override fun onSuccess(t: PictureBean) {
//
//                    if (t != null && t.isSuccess() && t.data != null && t.data!!.picId != null) {
//                        localPicID = t.data!!.picId!!.toInt()
//
//                        activity?.dismissLoadingDialog()
//                        pic?.let {
//                            //获取自拍照的picID
//                            uploadGetMessageResultPictureForTakePicID(
//                                BitmapUtils.bitmapToBase64(pic),
//                                studentDetailsBean
//                            )
////                            uploadGetMessageResultPictureForTakePicID(BitmapUtils.bitmapToBase64(localBitmap))
//                        }
//
//
//                    } else {
//                        activity?.dismissLoadingDialog()
//                        t?.let {
//                            activity?.showErrorMessageDialog(t?.message ?: "")
//                        }
//
//                    }
//                    LogUtil.e(">>>>>>>>>>>>>>>>> onSuccess ${t}")
//                }
//
//                override fun onError(throwable: Throwable?) {
//                    activity?.dismissLoadingDialog()
//                    LogUtil.e(">>>>>>>>>>>>>>>>> throwable ${throwable}")
//                    activity?.showErrorMessageDialog("$throwable")
//                }
//            })
//    }
//
//
//    private fun uploadGetMessageResultPictureForTakePicID(
//        BBase64: String?,
//        studentDetailsBean: StudentDetailsBean
//    ) {
//        val param = PictureParam()
//        param.picType = "101"//
////        param.picType = "101"
//        param.channelcode = "App"
//        param.picBase64 = BBase64
//        LogUtil.e("TAG", "uploadGetMessageResultPicture")
////        LogUtil.e("TAG", param)
//
//
//        activity?.showLoadingDialog(tipContent = "正在上传自拍数据...")
//
//        OkGoManager.instance.okGoRequestManage(
//            Const.UPLOAD_PICTURE, param
//            , PictureBean::class.java, object : OkGoRequestCallback<PictureBean> {
//
//                override fun onSuccess(t: PictureBean) {
//
//                    if (t != null && t.isSuccess() && t.data != null && t.data!!.picId != null) {
//                        takePicID = t.data!!.picId!!.toInt()
//
//                        //比对;
//                        comparePhoto(studentDetailsBean)
//
//                    } else {
//                        activity?.dismissLoadingDialog()
//                        t?.let {
//                            activity?.showErrorMessageDialog(t?.message ?: "")
//                        }
//
//                    }
//                    LogUtil.e(">>>>>>>>>>>>>>>>> onSuccess ${t}")
//                }
//
//                override fun onError(throwable: Throwable?) {
//                    activity?.dismissLoadingDialog()
//                    LogUtil.e(">>>>>>>>>>>>>>>>> throwable ${throwable}")
//                    activity?.showErrorMessageDialog("$throwable")
//                }
//            })
//    }
//
//
//    /**
//     * 人脸对比(两张照片)comparePhoto接口
//     */
//    private fun comparePhoto(studentDetailsBean: StudentDetailsBean) {
//        val param = ComparePhotoParam()
//        param.sfzh = studentDetailsBean?.sfzh
//        param.xm = studentDetailsBean?.name
//        param.picType = "101"
//        param.verifyAddress = ""
//        param.verifyBus = "002"
//        param.verifyChannel = "App"
//        param.verifyTime = myTimeUtils.getCurrentTime4()
////        param.comparisonData = secondPic
////        param.comparisonData = uploadPicID!!.toLong()
//        param.comparisonData = localPicID?.toLong() ?: 1//卡管 //todo
//        param.verifyType = "01"
////        param.verifyData = firstPic
////        param.verifyData = cardGetMessagePicID!!.toLong()
//        param.verifyData = takePicID?.toLong() ?: 1
//        param.deviceid = studentDetailsBean?.sfzh
//        param.tokenid = JinLinApp.mTokenId
//
//        activity?.showLoadingDialog(tipContent = "正在进行比对...")
//
//        OkGoManager.instance.okGoRequestManage(
//            Const.COMPARE_PHOTO, param
//            , ComparePhotoBean::class.java, object : OkGoRequestCallback<ComparePhotoBean> {
//
//                override fun onSuccess(t: ComparePhotoBean) {
//                    activity?.dismissLoadingDialog()
//                    if (t != null && t.isSuccess()) {
//
//                        activity?.showSuccessMessageDialog(t.message ?: "对比成功!", iEvents = IEvents {
//                            val intent = Intent()
//                            intent.putExtra(BaseConstant.FILTER_SELECT_POSITION, selectPosition)
//                            activity?.setResult(EXAMINATION_ROOM_INFO_CODE, intent)
//                            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> EXAMINATION_ROOM_INFO_CODE : $EXAMINATION_ROOM_INFO_CODE")
//                            activity?.finish()
//                        })
//
//                    } else {
//
//                        t?.let {
//                            activity?.showErrorMessageDialog(t?.message ?: "")
//                        }
//                    }
//                    LogUtil.e(">>>>>>>>>>>>>>>>> onSuccess ${t}")
//                }
//
//                override fun onError(throwable: Throwable?) {
//                    activity?.dismissLoadingDialog()
//                    activity?.showErrorMessageDialog("$throwable")
//                }
//            })
//    }
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
