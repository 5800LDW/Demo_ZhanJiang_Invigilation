package com.tecsun.jc.demo.invigilation.ui.pic

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.builder.StudentOwnImageBuilder
import com.tecsun.jc.base.builder.StudentOwnSFZImageBuilder
import com.tecsun.jc.base.collector.BaseActivityCollector
import com.tecsun.jc.base.dialog.DialogUtils
import com.tecsun.jc.base.listener.IEvents
import com.tecsun.jc.base.listener.OkGoRequestCallback
import com.tecsun.jc.base.utils.BitmapUtils
import com.tecsun.jc.base.utils.OkGoManager
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.builder.compare.CompareBuilderFactory
import com.tecsun.jc.demo.invigilation.builder.compare.ICompare
import com.tecsun.jc.demo.invigilation.util.MyFileUtil
import com.tecsun.jc.demo.invigilation.util.constant.Constants
import com.tecsun.jc.demo.invigilation.zhanjiang.bean.UploadPicEntity
import com.tecsun.jc.demo.invigilation.zhanjiang.bean.UploadPicParam
import com.tecsun.jc.demo.invigilation.zhanjiang.bean.UploadResultParam
import com.tecsun.jc.register.util.constant.Const
import kotlinx.android.synthetic.main.activity_register_photo_confirm.*
import java.io.File
import java.io.FileNotFoundException

/**
 * 照片确认
 */
@Route(path = Const.ROUTER_REGISTER_PHOTO_CONFIRM)
class PhotoConfirmActivity : MyBaseActivity() {

    private val TAG: String = "TAG"


    private var MHandler: Handler? = null

    private var mBitmap: Bitmap? = null
    private var mShowBitmap: Bitmap? = null
    private var mIvConfirmPhoto: ImageView? = null
    private var mBitmapBase64: String? = null
    private var btnCancel: Button? = null
    private var btnString: String? = null
    private var picId: String? = null
    private var sfzh: String? = null
    private var xm: String? = null
    private var tvRotateTips: TextView? = null
    private val timer = object : CountDownTimer(3000, 1000) {

        override fun onTick(millisUntilFinished: Long) {

        }

        override fun onFinish() {

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_register_photo_confirm
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle(R.string.photo_confirm)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //todo
        if (JinLinApp.studentDetailsBean != null) {
            CompareBuilderFactory.initICompare(JinLinApp.studentDetailsBean)
        }





        MHandler = Handler()



        btnString = intent.getStringExtra(Constants.TAKE_PHOTO_TIP)
        mIvConfirmPhoto = findViewById(R.id.iv_confirm_photo)
        btnCancel = findViewById(R.id.btn_cancel)
        tvRotateTips = findViewById(R.id.tvRotateTips)
        btnCancel!!.text = btnString

        initData()
        initTvRotateTips()

        mActivity = BaseActivityCollector.getTopActivity()


        btn_cancel.setOnClickListener {
            //删除
            deleteFile()
            finish()
        }
        btConfirm.setOnClickListener {

            //删除
            deleteFile()
            //保存
            var filePath = BitmapUtils.saveImage(mShowBitmap, path)
            LogUtil.e(TAG, "保存到本地的路径:$filePath")
            //通知上个acitivity
            val intent = Intent()
            setResult(10012, intent)
            finish()
        }


        startCompare()
    }


    var path = ""
    fun initData() {

        if (TakePhotoActivity.isThis) {

            Log.e("TAG", "1")

            val intent = intent
            val cameraPosition = intent.getIntExtra("cameraposition", 0)
            path = intent.getStringExtra(Constants.ACTIVITY_PIC_APPLY)
            mShowBitmap = BitmapFactory.decodeFile(path)
            Log.d("拍摄返回的图片路径", path + "");


//            PictureUtil.getInstance(applicationContext)!!.compressImage(path,30)
//            var file:File;
//            file=File(path)
//            TakePhotoUtils.instance.mImageFile=file
//            TakePhotoUtils.instance.cropImageUri(
//                this, Uri.fromFile(TakePhotoUtils.instance.mImageFile),
//                358, 441, TakePhotoUtils.CROP_BIG_PICTURE
//            )
//            var bitmap: Bitmap? = null
//            // 剪大图用uri
//            bitmap=TakePhotoUtils.instance.decodeUriAsBitmap(JinLinApp.context!!, Uri.fromFile(TakePhotoUtils.instance.mImageFile))
//            mShowBitmap=bitmap;
//            Log.d("输出裁剪后的图片",""+TakePhotoUtils.instance.mImageFile.path);
            val matrix = Matrix()
            if (0 == cameraPosition) {

                Log.e("TAG", "2")

                // 前置摄像头
                if ("mx4pro" == android.os.Build.BOARD || "MSM8916" == android.os.Build.BOARD
                    || "2014502" == android.os.Build.BOARD
                ) {
                    matrix.setRotate(90f)
                } else {
                    matrix.setRotate(270f)
                }
            } else {

                Log.e("TAG", "3")

                if ("mx4pro" == android.os.Build.BOARD || "MSM8916" == android.os.Build.BOARD || "2014502" == android.os.Build.BOARD) {
                    //                if (!AndroidSystemUtils.isMeizuFlymeOS()) {
                    matrix.setRotate(270f)
                } else {
                    matrix.postRotate(90f)
                }
                // 水平反转
                matrix.postScale(-1f, 1f)
            }
            mShowBitmap =
                Bitmap.createBitmap(
                    mShowBitmap!!,
                    0,
                    0,
                    mShowBitmap!!.width,
                    mShowBitmap!!.height,
                    matrix,
                    true
                )


//            if (mShowBitmap != null) {
//                //旋转图片
//                if (0 == cameraPosition) {
//                    mShowBitmap = RotateImage.rotateBitmap(mShowBitmap, 180.toFloat())
//                }
//            }


        } else {

            Log.e("TAG", "4")

            LogUtil.e(TAG, "重新选择照片")
            if ("2014502" == android.os.Build.BOARD) {
                if (JinLinApp.instance.getInsertPicture() != null) {
                    mShowBitmap = JinLinApp.instance.getInsertPicture()
                }

                val matrix = Matrix()
                matrix.postRotate(180f)
                mShowBitmap =
                    Bitmap.createBitmap(
                        mShowBitmap!!,
                        0,
                        0,
                        mShowBitmap!!.width,
                        mShowBitmap!!.height,
                        matrix,
                        true
                    )
            } else {
                if (JinLinApp.instance.getInsertPicture() != null) {
                    mShowBitmap = JinLinApp.instance.getInsertPicture()
                }
            }
        }

//        mIvConfirmPhoto!!.setImageBitmap(mShowBitmap)//BitmapUtils.rotateBitmapByDegree(bitmap,270)
        mShowBitmap = BitmapUtils.rotateBitmapByDegree(mShowBitmap, 90)
        mIvConfirmPhoto!!.setImageBitmap(mShowBitmap)
        val applyCardParam = Const.mApplyCardParam
        xm = applyCardParam?.xm ?: ""
        sfzh = applyCardParam?.sfzh ?: ""
        applyCardParam!!.show_card_selfie_bitmap = mShowBitmap


    }

    private fun initTvRotateTips() {
        val foregroundColorSpan = ForegroundColorSpan(Color.parseColor("#e60012"))
        val text = getString(R.string.register_photo_tips)
        val index = text.indexOf("此处") + 2
        val end = text.indexOf(",再")
        var spannable = SpannableStringBuilder(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View?) {
//                showToast("点击了")
                if (mShowBitmap != null) {
                    rotateBitmap(mShowBitmap!!)
                }

            }

            override fun updateDrawState(ds: TextPaint?) {
//                ds!!.color = Color.parseColor("#e60012")
            }
        }
        spannable.setSpan(clickableSpan, index, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(foregroundColorSpan, index, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        tvRotateTips!!.movementMethod = LinkMovementMethod.getInstance()
        tvRotateTips!!.text = spannable
        tvRotateTips!!.setHighlightColor(getResources().getColor(android.R.color.transparent))

    }

    private var internalTime = 0L
    private fun rotateBitmap(bitmap: Bitmap) {
        if (System.currentTimeMillis() - internalTime < 300) {
            return
        }
        internalTime = System.currentTimeMillis()
        runOnUiThread {
            mShowBitmap =
                com.tecsun.jc.demo.invigilation.util.RotateImage.rotateBitmap(bitmap, 180.toFloat())
            mIvConfirmPhoto!!.setImageBitmap(mShowBitmap)
        }
    }


    /**
     * 从手机选择
     */
    fun phoneImages() {
        //        TakePhotoActivity.isThis = false;
        //调用相册
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "选择图片"), 0x01)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0x01 && resultCode == Activity.RESULT_OK && data != null) {
            //选择图片
            val uri = data.data
            val cr = this.contentResolver
            try {
                if (mBitmap != null) {
                    //如果不释放的话，不断取图片，将会内存不够
                    mBitmap!!.recycle()
                }
                mBitmap = BitmapFactory.decodeStream(cr.openInputStream(uri!!))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

            val intent = Intent(this, PhotoConfirmActivity::class.java)
            mBitmap = BitmapUtils.compressImage(mBitmap!!, 80)
            JinLinApp.instance.setInsertPicture(mBitmap!!)
            intent.putExtra(Constants.TAKE_PHOTO_TIP, "重新选择")
            TakePhotoActivity.isThis = false
            this.finish()
            startActivity(intent)
        } else {
            showToast("请重新选择图片")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        if (mShowBitmap != null && !mShowBitmap!!.isRecycled) {
            mShowBitmap!!.recycle()
            mShowBitmap = null
        }
        mBitmapBase64 = null
//        ((BaseApplication) getApplication()).recyclePicture();
        if (MHandler != null) {
            MHandler!!.removeCallbacksAndMessages(null)
        }

        LogUtil.e(">>>>>>>>>>>>>>>>>>>>> onDestroy()  PhotoConfirm")

        //释放资源
        compare?.release()

        super.onDestroy()
    }


    private fun deleteFile() {
        // 删除
        val deletelastPicName = File(path)
        if (deletelastPicName != null && MyFileUtil.isFileExists(deletelastPicName.toString())) {
            val file = File(deletelastPicName.toString())
            file.delete()
            LogUtil.e(">>>>>>>>>>>>>>> 删除成功 $deletelastPicName")
        }
    }


    private var compare: ICompare? = null
    private fun startCompare() {


//        var bean = CompareBuilderFactory.getStudentDetailsBean()
//        ARouter.getInstance().build(RouterHub.ROUTER_LIB_FACE_RECOGNITION_OFFLINE)
//            .withString(
//                BaseConstant.PIC_PATH_1,
//                StudentOwnSFZImageBuilder.getBitmapAbsolutePath(bean!!.sfzh)
//            )
//            .withString(
//                BaseConstant.PIC_PATH_2,
//                StudentOwnImageBuilder.getBitmapAbsolutePath(bean!!.sfzh)
//            )
//            .greenChannel().navigation()
//
//
//
//        if(true){
//            return
//        }


        var localBitmapPath: String? = null
        var selfPicPath: String? = path

        var localBitmap: Bitmap? = null
        var selfBitmap: Bitmap? = null


        var studentDetailsBean = CompareBuilderFactory.getStudentDetailsBean()
        if (studentDetailsBean != null && !studentDetailsBean!!.sfzh.isNullOrBlank()) {
            //拿之前录入学生信息的图片做比对
            var bitmap: Bitmap? = null

            //提取身份证图片
            bitmap = StudentOwnSFZImageBuilder.getSFZBitmap(studentDetailsBean!!.sfzh)
            localBitmapPath =
                StudentOwnSFZImageBuilder.getBitmapAbsolutePath(studentDetailsBean!!.sfzh)

            //提取录入学生信息的时候自拍的图片
            if (bitmap == null) {
                bitmap = StudentOwnImageBuilder.getSFZBitmap(studentDetailsBean!!.sfzh)
                localBitmapPath =
                    StudentOwnImageBuilder.getBitmapAbsolutePath(studentDetailsBean!!.sfzh)
            }
            if (bitmap != null) {
                localBitmap = bitmap
            }
        }

        if (localBitmap == null) {
            showErrorMessageDialog(getString(R.string.app_student_nonentity_local_or_sfz_pic))
            return
        }

        selfBitmap = BitmapFactory.decodeFile(path)
        if (selfBitmap == null) {
            showErrorMessageDialog2("自拍图片不存在!")
            return
        }

        //TEST
        //自拍的照片
//        localBitmap = StudentOwnImageBuilder.getSFZBitmap(studentDetailsBean!!.sfzh)
//        var sfzBitmap = StudentOwnSFZImageBuilder.getSFZBitmap(studentDetailsBean!!.sfzh)
//        BitmapFactory.decodeFile(path)
        //testEnd


        compare = CompareBuilderFactory.getBuilder(
            this,
            //这个首先是拿身份证的照片,没了才拿本地录入时候的照片;
            localBitmap,
            //自拍的图片路径
            selfBitmap,
            localBitmapPath!!,
            selfPicPath!!

        )
        compare?.startCompare()


//        LogUtil.e(
//            "TAG",
//            "localBitmap 宽为：" + localBitmap?.width + "高为：" + localBitmap?.height + "内存大小为：" + localBitmap?.byteCount
//        )
//        LogUtil.e(
//            "TAG",
//            "sfzBitmap 宽为：" + sfzBitmap?.width + "高为：" + sfzBitmap?.height + "内存大小为：" + sfzBitmap?.byteCount
//        )

    }


    private var sfzhPic: String? = ""
    private var diyPic: String? = ""

    fun uploadSFZHPic(activity: MyBaseActivity?, param: UploadPicParam) {
        OkGoManager.instance.okGoRequestManage(
            com.tecsun.jc.demo.invigilation.zhanjiang.constant.Constants.URL_UPLOAD_PICTURE, param
            , UploadPicEntity::class.java, object : OkGoRequestCallback<UploadPicEntity> {
                override fun onSuccess(t: UploadPicEntity) {
                    activity?.dismissLoadingDialog()
                    if (t != null && t.statusCode == "200" && t.data != null && t.data.picId != null) {
                        sfzhPic = t.data.picId.toString()

                    } else {
                        //TODO 提示错误, 和询问是否重新上传
                    }

                    LogUtil.e(">>>>>>>>>>>>>>>>> onSuccess $t")
                }

                override fun onError(throwable: Throwable?) {
                    activity?.dismissLoadingDialog()
                    //TODO 提示错误, 和询问是否重新上传
                    LogUtil.e(">>>>>>>>>>>>>>>>> throwable $throwable")
                    activity?.showErrorMessageDialog("$throwable")
                }
            })

    }


    fun uploadDiyPic(activity: MyBaseActivity?, param: UploadPicParam) {
        OkGoManager.instance.okGoRequestManage(
            com.tecsun.jc.demo.invigilation.zhanjiang.constant.Constants.URL_UPLOAD_PICTURE, param
            , UploadPicEntity::class.java, object : OkGoRequestCallback<UploadPicEntity> {
                override fun onSuccess(t: UploadPicEntity) {
                    activity?.dismissLoadingDialog()
                    if (t != null && t.statusCode == "200" && t.data != null && t.data.picId != null) {
                        diyPic = t.data.picId.toString()
                    } else {
                        //TODO 提示错误, 和询问是否重新上传
                    }

                    LogUtil.e(">>>>>>>>>>>>>>>>> onSuccess $t")
                }

                override fun onError(throwable: Throwable?) {
                    activity?.dismissLoadingDialog()
                    //TODO 提示错误, 和询问是否重新上传
                    LogUtil.e(">>>>>>>>>>>>>>>>> throwable $throwable")
                    activity?.showErrorMessageDialog("$throwable")
                }
            })
    }

    fun uploadConfirmResult(sfzh: String, cid: String, name: String) {
        var param = UploadResultParam()
        param.certNo = sfzh
        param.picCert = sfzhPic
        param.picPerson = diyPic
        param.userName = name
        // "verify": "1:人脸认证成功 0:人脸认证不成功" //
        param.verify = "1"
        try {
            param.courseId = cid.toInt()
        } catch (e: Exception) {
            LogUtil.e(TAG, ">>>>>>>>>>>>>>>>>>>> uploadConfirmResult  Exception = $e")
        }

        OkGoManager.instance.okGoRequestManage(
            com.tecsun.jc.demo.invigilation.zhanjiang.constant.Constants.URL_SAVE_INFO, param
            , UploadPicEntity::class.java, object : OkGoRequestCallback<UploadPicEntity> {
                override fun onSuccess(t: UploadPicEntity) {
                    dismissLoadingDialog()
                    if (t != null && t.statusCode == "200") {
                        showSuccessMessageDialog(msg = t.message ?: "")
                    } else {
                        //TODO 提示错误, 和询问是否重新上传
                    }

                    LogUtil.e(">>>>>>>>>>>>>>>>> onSuccess $t")
                }

                override fun onError(throwable: Throwable?) {
                    dismissLoadingDialog()
                    //TODO 提示错误, 和询问是否重新上传
                    LogUtil.e(">>>>>>>>>>>>>>>>> throwable $throwable")
                    showErrorMessageDialog("$throwable")
                }
            })


    }


    private fun showFailDialog(msg: String,event:IEvents) {
        DialogUtils.showDialog2(
            R.drawable.ic_failed, "上传数据失败",
            msg + "", R.string.base_confirm_upload, R.string.base_cancel,
            { dialog, which -> event?.biz() },
            { dialog, which -> dialog.dismiss() },
            this
        )
    }


}


























