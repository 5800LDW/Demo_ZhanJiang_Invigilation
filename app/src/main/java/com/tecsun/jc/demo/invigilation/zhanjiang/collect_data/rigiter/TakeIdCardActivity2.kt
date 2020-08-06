package com.tecsun.jc.demo.invigilation.zhanjiang.collect_data.rigiter

//import com.tecsun.jc.register.util.extension.setInsertPicture
import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.Camera
import android.media.AudioManager
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.utils.BitmapUtils
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.ui.pic.MyBaseActivity
import com.tecsun.jc.demo.invigilation.util.BitmapUtils2
import com.tecsun.jc.demo.invigilation.util.PermissionUtils
import com.tecsun.jc.demo.invigilation.util.RotateImage
import com.tecsun.jc.demo.invigilation.util.myToastUtils
import com.tecsun.jc.demo.invigilation.widget.surfaceview.CameraPreview
import com.tecsun.jc.demo.invigilation.widget.surfaceview.CameraUtils
import com.tecsun.jc.register.util.constant.Const


class TakeIdCardActivity2 : MyBaseActivity(), View.OnTouchListener {

    private var TAG: String = "TAG"

    private var mCamera: Camera? = null
    private var parameters: Camera.Parameters? = null
    private var mSurfaceView: CameraPreview? = null
    private var mTvTakeIdTip: TextView? = null
    var mIdSource: Int = Const.SOURCE_ID_POSITIVE
    private var mBitmap: Bitmap? = null

    /**
     * 判断手机是否静音模式
     */
    private// 下面这句代码可以根据系统音量的状态来开关拍照声音
    val isSilentMode: Boolean
        get() {
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION)
            return if (0 == currentVolume) {
                true
            } else false
        }


    override fun getLayoutId(): Int {
        return R.layout.activity_register_take_id_card2
    }

    override fun setTitleBar(titleBar: TitleBar) {
//        titleBar.setTitle(R.string.register_upload_id_card_pic)
        titleBar.setTitle("拍摄")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /*动态请求需要的权限*/
        val checkPermissionFirst = PermissionUtils.checkPermissionFirst(
            this, PermissionUtils.PERMISSION_CODE_FIRST,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        )
        if (checkPermissionFirst) {

            mSurfaceView = findViewById(R.id.mSurfaceView)
            mTvTakeIdTip = findViewById(R.id.mTvTakeIdTip)

            myInitData()

            /*增加0.5秒过渡界面，解决个别手机首次申请权限导致预览界面启动慢的问题*/
            Handler().postDelayed({
                runOnUiThread {
                    mSurfaceView!!.setVisibility(View.VISIBLE)

                    mSurfaceView!!.setEnabled(true)
                    mSurfaceView!!.addCallback()
                    mSurfaceView!!.startPreview()

                    mCamera = CameraUtils.camera

                }
            }, 500)

            Handler().postDelayed({
                mSurfaceView!!.focus()
            }, 1000)
        }


    }

    private fun myInitData() {
        if (intent != null) {
            mIdSource = intent.getIntExtra(Const.TAKE_ID_SOURCE, Const.SOURCE_ID_POSITIVE)
        }
        if (mIdSource == Const.SOURCE_ID_NEGATIVE) {

            mSurfaceView!!.setBackgroundResource(R.drawable.bg_id_card_negative_new_copy)

            mTvTakeIdTip?.setText(R.string.take_id_negative_tip)
        }
        else{
            mSurfaceView!!.setBackgroundResource(R.drawable.bg_id_card_positive_new_copy)

            mTvTakeIdTip?.setText(R.string.take_id_positive_tip)
        }

        addListeners()

    }

    fun addListeners() {
        mSurfaceView!!.setOnClickListener(View.OnClickListener {
            // 拍照前需要对焦 获取清析的图片
            if (null == mCamera) {
                return@OnClickListener
            }
            mCamera!!.autoFocus { success, camera -> }
        })
    }


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_BUTTON_PRESS) {
            // 拍照前需要对焦 获取清析的图片
            mCamera!!.autoFocus { success, camera -> myToastUtils.showToast(mActivity, "聚焦成功") }

        }
        if (event.action == MotionEvent.ACTION_DOWN) {//按下时自动对焦
            mCamera!!.autoFocus { success, camera ->
                myToastUtils.showToast(mActivity, "聚焦成功")
            }
        }
        return false
    }


    private var internalTime = 0L

    /**
     * 拍照
     */
    fun takeIdCard(view: View) {

        if (System.currentTimeMillis() - internalTime < 300) {
            return
        }
        internalTime = System.currentTimeMillis()


        if (mSurfaceView == null) {
            return
        }

        mSurfaceView!!.setEnabled(false)


        if (null == CameraUtils.getCamera()) {
            return
        }

//      mSurfaceView!!.focus()

//        val handler = Handler()
//        handler.postDelayed({
//            if (isSilentMode) {
//                CameraUtils.getCamera()!!.takePicture(null, null, this@TakeIdCardActivity)
//            } else {
//
//                CameraUtils.getCamera()!!.takePicture(Camera.ShutterCallback { }, null, this@TakeIdCardActivity)
//            }
//        }, 100)


        val handler = Handler()
        handler.postDelayed({
            CameraUtils.getCamera().setOneShotPreviewCallback { bytes, camera ->
                onPictureTaken(bytes,camera)
            }


        }, 100)


    }


     fun onPictureTaken(data: ByteArray?, camera: Camera) {
        if (data != null) {
//            mBitmap = BitmapFactory.decodeByteArray(data, 0, data.size)

            val size = camera.parameters.previewSize //获取预览大小
            camera.stopPreview()
            val w = size.width
            val h = size.height
            mBitmap= BitmapUtils2.getBitmapFromByte(data, w, h)


            LogUtil.e(TAG, "1 mBitmap!!.width")
            LogUtil.e(TAG, mBitmap!!.width)
            LogUtil.e(TAG, "1 mBitmap!!.height")
            LogUtil.e(TAG, mBitmap!!.height)
//            if (Const.IS_TEST2) {
//                //保存本地
//                var filePath = BitmapUtils2.saveImage(mBitmap)
//                LogUtil.e(TAG, "保存到本地的路径:$filePath")
//            }
            //部分相机拍出来方向不对,需要旋转下;
            if (mBitmap!!.width < mBitmap!!.height) {
                mBitmap = RotateImage.rotateBitmap(mBitmap, -90.toFloat())
            }
            LogUtil.e(TAG, "2 mBitmap!!.width")
            LogUtil.e(TAG, mBitmap!!.width)
            LogUtil.e(TAG, "2 mBitmap!!.height")
            LogUtil.e(TAG, mBitmap!!.height)

//            if (Const.IS_TEST2) {
//                //保存本地
//                var filePath = BitmapUtils2.saveImage(mBitmap)
//                LogUtil.e(TAG, "保存到本地的路径:$filePath")
//            }


            //截取矩形内照片
            val width = mBitmap!!.width  // 原图宽
            val height = mBitmap!!.height  // 原图高
            val widthNew = width * 900 / 1708  // 裁剪图宽
            val heightNew = height * 720 / 1080  // 裁剪图高
            mBitmap = Bitmap.createBitmap(
                mBitmap!!, (width - widthNew) / 2, (height - heightNew) / 2, widthNew,
                heightNew
            )
            mBitmap = BitmapUtils.zoomImage(mBitmap!!, 720.0, (720 * mBitmap!!.height / mBitmap!!.width).toDouble())
            JinLinApp.instance.setInsertPicture(mBitmap!!)
            val intent = Intent()
            this.setResult(mIdSource, intent)
            this.finish()
        }
    }


    override fun onStart() {
        super.onStart()
        if (mSurfaceView != null) {
            mSurfaceView!!.onStart()

        }
    }

    override fun onStop() {
        super.onStop()
        if (mSurfaceView != null) {
            mSurfaceView!!.onStop()
        }
    }


    override fun onDestroy() {
        if (mBitmap != null && mBitmap!!.isRecycled) {
            mBitmap!!.recycle()
            mBitmap = null
        }
        super.onDestroy()
    }
}












