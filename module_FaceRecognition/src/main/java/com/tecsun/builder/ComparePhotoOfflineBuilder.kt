package com.tecsun.builder

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import com.tecsun.face.SyKeanHelper
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.builder.StudentOwnSFZImageBuilder
import com.tecsun.jc.base.builder.sound.SoundBuilder
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.listener.IEvents
import com.tecsun.jc.base.utils.BitmapUtils
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.demo.invigilation.builder.compare.ICompare


/**
 * 图片离线比对
 * @author liudongwen
 * @date 2019/10/28
 */
object ComparePhotoOfflineBuilder : ICompare {

    private var helper: SyKeanHelper? = null
    private var localPicPath: String? = null
    private var selfPicPath: String? = null
    private var activity: BaseActivity? = null
    private var selectPosition: Int? = null

    fun getSyKeanHelper(): SyKeanHelper? {
        return helper
    }


    fun setActivity(activity: BaseActivity?) {
        this.activity = activity
    }

    fun setLocalPicPath(localPicPath: String?) {
        this.localPicPath = localPicPath
    }

    fun setSelfPicPath(selfPicPath: String?) {
        this.selfPicPath = selfPicPath
    }

    fun setSelectPosition(selectPosition: Int?) {
        this.selectPosition = selectPosition
    }

    @JvmStatic
    fun myStaticInit(context: Context) {
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> ComparePhotoOfflineBuilder init()")
        if (!initState()) {
            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> ComparePhotoOfflineBuilder 开始初始化...")
//            Thread().run {
            helper = SyKeanHelper()
            helper?.init(context)
//            }
        } else {
            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> ComparePhotoOfflineBuilder  无需初始化")
        }
    }

    override fun init() {
        JinLinApp.context?.let {
            myStaticInit(JinLinApp.context!!)
        }

//        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> ComparePhotoOfflineBuilder init()")
//        if (!initState()) {
//            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> ComparePhotoOfflineBuilder 开始初始化...")
////            Thread().run {
//                helper = SyKeanHelper()
//                helper?.init(JinLinApp.context!!)
////            }
//        }
//        else{
//            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> ComparePhotoOfflineBuilder  无需初始化")
//        }
    }


    override fun initState(): Boolean {
        return helper?.myIsHasInit() ?: false
    }

    override fun release() {
        //不释放这个资源,免得反复初始化;
//        helper?.dispose()
//        helper = null

        localPicPath = null
        selfPicPath = null
        if (activity != null) {
            if (!activity!!.isFinishing) {
                activity?.finish()
            }
            activity = null
        }
    }


    override fun startCompare() {
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>> 打印属性 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>> localPicPath : $localPicPath")
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>> selfPicPath : $selfPicPath")


        if (!initState()) {
            show(iEvent = IEvents {
                activity!!.showErrorMessageDialog("离线比对功能初始化失败！请重启设备！")
            })
            return
        }


        show(iEvent = IEvents {
            activity!!.showLoadingDialog(tipContent = "正在处理...")
        })

        Thread(Runnable {

            var bitmap1 = BitmapFactory.decodeFile(localPicPath)
            LogUtil.e("TAG", "宽：" + bitmap1?.width + "高：" + bitmap1?.height)

//            StudentOwnSFZImageBuilder.savePic(bitmap1, "处理前")
////            if (bitmap1 != null && (bitmap1.width > 350 || bitmap1.height > 400)) {
//            if (bitmap1 != null && bitmap1.width <= bitmap1.height) {
//                bitmap1 = BitmapUtils.ImageCrop(bitmap1, 350, 400, true)
//                bitmap1 = BitmapUtils.zoomImage(bitmap1, 400.0, 350.0)
//                StudentOwnSFZImageBuilder.savePic(bitmap1, "我是测试")
//            } else if (bitmap1 != null) {
//                bitmap1 = BitmapUtils.ImageCrop(bitmap1, 400, 350, true)
//                bitmap1 = BitmapUtils.zoomImage(bitmap1, 400.0, 350.0)
//            }
//            StudentOwnSFZImageBuilder.savePic(bitmap1, "处理后")


            //为了兼容性,下面使用三种注册方式,都失败了才提示失败!
            //本地图片注册1
            LogUtil.e("TAG", "第一次注册");
            var registerResult = helper?.registerPhotoForSFZPic(bitmap1)

            //本地图片注册2
            if (registerResult == null || registerResult == false) {
                StudentOwnSFZImageBuilder.savePic(bitmap1, "处理前")
//            if (bitmap1 != null && (bitmap1.width > 350 || bitmap1.height > 400)) {
                if (bitmap1 != null && bitmap1.width <= bitmap1.height) {
                    bitmap1 = BitmapUtils.ImageCrop(bitmap1, 350, 400, true)
                    bitmap1 = BitmapUtils.zoomImage(bitmap1, 400.0, 350.0)
                    StudentOwnSFZImageBuilder.savePic(bitmap1, "我是测试")
                } else if (bitmap1 != null) {
                    bitmap1 = BitmapUtils.ImageCrop(bitmap1, 400, 350, true)
                    bitmap1 = BitmapUtils.zoomImage(bitmap1, 400.0, 350.0)
                }
                StudentOwnSFZImageBuilder.savePic(bitmap1, "处理后")

                registerResult = helper?.registerPhotoForSFZPic(bitmap1)
                LogUtil.e("TAG", "第二次注册")
            }

            //本地图片注册3
            if (registerResult == null || registerResult == false) {
                registerResult = helper?.registerPhotoForSFZPic(helper?.ldwDoWith(bitmap1))
                LogUtil.e("TAG", "第三次注册")
            }


//            var registerResult = helper?.registerPhotoForSFZPic(BitmapFactory.decodeResource(
//                activity!!.getResources(), R.raw.face_sfz_hyx))

            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 注册结果: $registerResult")

            if (registerResult == null || registerResult == false) {
                show(iEvent = IEvents {
                    activity!!.showErrorMessageDialog("注册证件照失败！")
                })
                return@Runnable
            }

            //获取要比对的图片的
            var selfPicBitmap = BitmapFactory.decodeFile(selfPicPath)
            //验证是否能照片人脸
            var b = helper?.canFindFace(selfPicBitmap) ?: false

            if (b) {
                var comparisonResult = helper?.comparisonGetScore(selfPicBitmap)
                LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 离线图片比对结果: $comparisonResult")

                show(iEvent = IEvents {
                    activity!!.dismissLoadingDialog()
                    if (comparisonResult == null) {
                        activity!!.showErrorMessageDialog("比对失败!")
                    } else if (comparisonResult.toInt() < 85) {
                        activity!!.showErrorMessageDialog("相似度过低，人证比对失败！")
                    } else {
                        //改成在弹出框后发出声音
//                        SoundBuilder.playCompareSuccess()

                        activity?.runOnUiThread{
                            activity?.successCompareBiz()
                        }




//                        activity!!.showSuccessMessageDialog("人证比对成功!", iEvents = IEvents {
//                            val intent = Intent()
//                            intent.putExtra(
//                                BaseConstant.FILTER_SELECT_POSITION,
//                                selectPosition
//                            )
//                            activity?.setResult(BaseConstant.EXAMINATION_ROOM_INFO_CODE, intent)
//                            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> EXAMINATION_ROOM_INFO_CODE : ${BaseConstant.EXAMINATION_ROOM_INFO_CODE}")
//                            activity?.finish()
//                        })

                    }
                })

            } else {
                show(iEvent = IEvents {
                    activity!!.dismissLoadingDialog()
                    activity!!.showErrorMessageDialog("没有检测到人脸")
                })
            }

        }).start()

//        ARouter.getInstance().build(RouterHub.ROUTER_LIB_FACE_RECOGNITION_OFFLINE)
//            .withString(
//                BaseConstant.PIC_PATH_1,
//                localPicPath
//            )
//            .withString(
//                BaseConstant.PIC_PATH_2,
//                selfPicPath
//            )
//            .greenChannel().navigation()
    }

    private fun show(iEvent: IEvents?) {
        if (activity != null && !activity!!.isFinishing) {
            activity!!.runOnUiThread {
                iEvent?.biz()
            }
        }
    }
}
















