//package com.tecsun.jc.demo.invigilation.builder
//
//import com.tecsun.jc.demo.invigilation.builder.compare.ICompare
//
///**
// * 图片比对
// * @author liudongwen
// * @date 2019/10/28
// */
//object ComparePhotoOfflineBuilder : ICompare {
//    override fun release() {
//        // TODO
//    }
//
//
//    override fun startCompare(){
//       //TODO
//    }
//
//
//
//
//
////    private var localBitmap: Bitmap? = null
//
////    private var activity:BaseActivity? = null
////    private var localPicID = 0
////    private var takePicID = 0
////    /**自拍照片 */
////    private var pic: Bitmap? = null
////
////
////    fun set(a: BaseActivity,localBitmap: Bitmap?){
////        activity = a
////        activity?.showLoadingDialog(tipContent = "正在上传本地图片数据...")
////        var base64 = BitmapUtils.bitmapToBase64(localBitmap)
////        uploadGetMessageResultPictureForLocalPicID(base64)
////    }
////    private fun uploadGetMessageResultPictureForLocalPicID(BBase64: String?) {
////        val param = PictureParam()
////        param.picType = "001"
//////        param.picType = "101"
////        param.channelcode = "App"
////        param.picBase64 = BBase64
////        LogUtil.e("TAG", "uploadGetMessageResultPicture")
//////        LogUtil.e("TAG", param)
////
////        OkGoManager.instance.okGoRequestManage(
////            Const.UPLOAD_PICTURE, param
////            , PictureBean::class.java, object : OkGoRequestCallback<PictureBean> {
////
////                override fun onSuccess(t: PictureBean) {
////
////                    if (t != null && t.isSuccess() && t.data != null && t.data!!.picId != null) {
////                        localPicID = t.data!!.picId!!.toInt()
////
////                        activity?.dismissLoadingDialog()
////                        pic?.let {
////                            //获取自拍照的picID
////                            //TODO
////                            uploadGetMessageResultPictureForTakePicID(BitmapUtils.bitmapToBase64(pic))
//////                            uploadGetMessageResultPictureForTakePicID(BitmapUtils.bitmapToBase64(localBitmap))
////                        }
////
////
////                    } else {
////                        activity?.dismissLoadingDialog()
////                        t?.let {
////                            activity?.showErrorMessageDialog2(t?.message ?: "")
////                        }
////
////                    }
////                    LogUtil.e(">>>>>>>>>>>>>>>>> onSuccess ${t}")
////                }
////
////                override fun onError(throwable: Throwable?) {
////                    activity?.dismissLoadingDialog()
////                    LogUtil.e(">>>>>>>>>>>>>>>>> throwable ${throwable}")
////                    activity?.showErrorMessageDialog2("$throwable")
////                }
////            })
////    }
////
////
////    private fun uploadGetMessageResultPictureForTakePicID(BBase64: String?) {
////        val param = PictureParam()
////        param.picType = "101"//
//////        param.picType = "101"
////        param.channelcode = "App"
////        param.picBase64 = BBase64
////        LogUtil.e("TAG", "uploadGetMessageResultPicture")
//////        LogUtil.e("TAG", param)
////
////
////        activity?.showLoadingDialog(tipContent = "正在上传自拍数据...")
////
////        OkGoManager.instance.okGoRequestManage(
////            Const.UPLOAD_PICTURE, param
////            , PictureBean::class.java, object : OkGoRequestCallback<PictureBean> {
////
////                override fun onSuccess(t: PictureBean) {
////
////                    if (t != null && t.isSuccess() && t.data != null && t.data!!.picId != null) {
////                        takePicID = t.data!!.picId!!.toInt()
////
////                        //比对;
////                        comparePhoto()
////
////                    } else {
////                        activity?.dismissLoadingDialog()
////                        t?.let {
////                            activity?.showErrorMessageDialog2(t?.message ?: "")
////                        }
////
////                    }
////                    LogUtil.e(">>>>>>>>>>>>>>>>> onSuccess ${t}")
////                }
////
////                override fun onError(throwable: Throwable?) {
////                    activity?.dismissLoadingDialog()
////                    LogUtil.e(">>>>>>>>>>>>>>>>> throwable ${throwable}")
////                    activity?.showErrorMessageDialog2("$throwable")
////                }
////            })
////    }
////
////
////    /**
////     * 人脸对比(两张照片)comparePhoto接口
////     */
////    private fun comparePhoto() {
////        val param = ComparePhotoParam()
////        param.sfzh = studentDetailsBean?.sfzh
////        param.xm = studentDetailsBean?.name
////        param.picType = "101"
////        param.verifyAddress = ""
////        param.verifyBus = "002"
////        param.verifyChannel = "App"
////        param.verifyTime = myTimeUtils.getCurrentTime4()
//////        param.comparisonData = secondPic
//////        param.comparisonData = uploadPicID!!.toLong()
////        param.comparisonData = localPicID?.toLong() ?: 1//卡管 //todo
////        param.verifyType = "01"
//////        param.verifyData = firstPic
//////        param.verifyData = cardGetMessagePicID!!.toLong()
////        param.verifyData = takePicID?.toLong() ?: 1
////        param.deviceid = studentDetailsBean?.sfzh
////        param.tokenid = JinLinApp.mTokenId
////
////        showLoadingDialog(tipContent = "正在进行比对...")
////
////        OkGoManager.instance.okGoRequestManage(
////            Const.COMPARE_PHOTO, param
////            , ComparePhotoBean::class.java, object : OkGoRequestCallback<ComparePhotoBean> {
////
////                override fun onSuccess(t: ComparePhotoBean) {
////                    dismissLoadingDialog()
////                    if (t != null && t.isSuccess()) {
////
////                        showSuccessMessageDialog(t.message ?: "对比成功!", iEvents = IEvents {
////                            val intent = Intent()
////                            intent.putExtra(BaseConstant.FILTER_SELECT_POSITION, selectPosition)
////                            setResult(Const.EXAMINATION_ROOM_INFO_CODE, intent)
////                            finish()
////
////                        })
////
////                    } else {
////
////                        t?.let {
////                            showErrorMessageDialog2(t?.message ?: "")
////                        }
////                    }
////                    LogUtil.e(">>>>>>>>>>>>>>>>> onSuccess ${t}")
////                }
////
////                override fun onError(throwable: Throwable?) {
////                    dismissLoadingDialog()
////                    showErrorMessageDialog2("$throwable")
////                }
////            })
////
////
////    }
////
//
//
//
//
//
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
