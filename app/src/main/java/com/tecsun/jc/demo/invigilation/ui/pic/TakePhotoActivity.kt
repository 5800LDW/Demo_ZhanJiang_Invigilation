package com.tecsun.jc.demo.invigilation.ui.pic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.PixelFormat
import android.hardware.Camera
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaScannerConnection
import android.os.*
import android.view.*
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.builder.TakePhotoRecordBuilder
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.listener.HandlerCallback
import com.tecsun.jc.base.utils.*
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.builder.device.DeviceBuilder
import com.tecsun.jc.demo.invigilation.ui.admin.add.AddNewStudentActivity
import com.tecsun.jc.demo.invigilation.util.MyFileUtil
import com.tecsun.jc.demo.invigilation.util.StatusBarUtils
import com.tecsun.jc.demo.invigilation.util.constant.ActivityConstant
import com.tecsun.jc.demo.invigilation.util.constant.Constants
import com.tecsun.jc.register.util.constant.Const.EXAMINATION_ROOM_INFO_CODE
import kotlinx.android.synthetic.main.activity_register_take_photo.*
import java.io.File
import java.io.IOException

/**
 * 认证照片拍摄
 * Created by psl on 2017/5/11.
 */
@Route(path = RouterHub.ROUTER_APP_TAKE_PHOTO)
class TakePhotoActivity : MyBaseActivity() {


    private var resultCode: Int = 0

    private var titleName = "";

    var isopen_camara = false
    private var mHandler: Handler? = null

    //    private var bind: ActivityTakePhotoBinding? = null
    private val camera: Camera? = null
    private var mholder: SurfaceHolder? = null
    private val mSurfaceCallback: SurfaceCallback? = null
    private val parameters: Camera.Parameters? = null

    //启动摄像机
    private var mCamera: Camera? = null
    private var previewCallBack: SurfaceCallback? = null

    //    private int cameraPosition = 1;//0代表前置摄像头，1代表后置摄像头
    private var isTakingPhoto: Boolean = false//是否正在拍照
    private var cameraPosition = 0//0代表前置摄像头，1代表后置摄像头

    /** 相机是否正常打开 */
    private var mIsCameraOpenNormally = true

    private var lastActivity: String? = null


    private var xm: String? = null
    private var sfzh: String? = null

    private val shootMP: MediaPlayer? = null

    private var svTakePhoto: SurfaceView? = null
    private var tvTakePhoto: TextView? = null
    private var tvTip: TextView? = null

    private var camearaFacing1: Int? = 1

    private

            /**
             * 判断手机是否静音模式
             * // 下面这句代码可以根据系统音量的状态来开关拍照声音
             */
    val isSilentMode: Boolean
        get() {
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION)
            return if (currentVolume == 0) {
                true
            } else false
        }


    private var myMoDel: String? = "1"

    override fun getLayoutId(): Int {
        return R.layout.activity_register_take_photo
    }

    override fun setTitleBar(titleBar: TitleBar) {
        //        Drawable rightDrawable = getResources().getDrawable(R.drawable.ic_change_camera);


        intent?.let {
            var title = intent.getStringExtra(BaseConstant.TITLE_NAME)
            if (!title.isNullOrBlank()) {
                titleName = title
                btTakePhoto.text = "开始认证"
            }
        }

        titleBar.setTitle("拍照")
        if (!titleName.isNullOrBlank()) {
            titleBar.setTitle(titleName)
        }



        if (lastActivity == Constants.COLLECT_PHOTO) {
            titleBar.setTitleColor(resources.getColor(R.color.c_black))
            titleBar.setBackgroundColor(resources.getColor(R.color.white))
            titleBar.setLeftImageResource(R.drawable.ic_back_work)
            titleBar.setDividerColor(resources.getColor(R.color.c_gray_01))
            titleBar.addAction(object : TitleBar.ImageAction(R.drawable.ic_work_change_camere) {
                override fun performAction(view: View) {
                    switchCamara()
                }
            })

        } else {

            //不添加切换摄像头的功能了;
//            titleBar.addAction(object : TitleBar.ImageAction(R.drawable.ic_change_camera) {
//                override fun performAction(view: View) {
//                    switchCamara()
//                }
//            })
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        svTakePhoto = findViewById(R.id.svTakePhoto)
        tvTakePhoto = findViewById(R.id.tvTakePhoto)
        tvTip = findViewById(R.id.tvTip)

        mHandler = Handler()

        initUI()
        addListeners()
        isNeedImmer()

        registerListener()

        resultCode = intent.getIntExtra(BaseConstant.FILTER_SELECT, 0)

    }

    private fun initUI() {
        val intent = intent
        if (getIntent() != null) {
            lastActivity = intent.getStringExtra(Constants.LAST_ACTIVITY_PHOTO)
            if (lastActivity == Constants.COLLECT_PHOTO) {
                xm = getIntent().getStringExtra(Constants.COLLECT_NAME)
                sfzh = getIntent().getStringExtra(Constants.COLLECT_SFZH)

            }
        }

        if (lastActivity == Constants.APPLY_PHOTO) {
            tvTip!!.visibility = View.VISIBLE
        }
        mholder = svTakePhoto!!.holder
        // 设置参数
        mholder!!.setKeepScreenOn(true)
        mholder!!.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }


    fun addListeners() {
        tvTakePhoto!!.setOnClickListener { takePhoto() }

        var testID = findViewById<View>(R.id.btTakePhoto)

        if (testID == null) {
            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>> testID == null")
        } else {
            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>> testID != null")
        }

        btTakePhoto.setOnClickListener {
            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>> flTakePhoto 点击拍照")
            takePhoto()
        }

    }


    fun isNeedImmer(): Boolean {
        return if (lastActivity == Constants.COLLECT_PHOTO) {
            StatusBarUtils.StatusBarLightMode(this) != 0
        } else true
    }


    override fun onRestart() {
        super.onRestart()
        cameraBizInOnRestart()
    }


    private fun takePhoto() {

        if (!isopen_camara) {

            LogUtil.e("takePhoto 1")

            runOnUiThread {

                previewCallBack = SurfaceCallback()
                mholder!!.addCallback(previewCallBack)

                LogUtil.e("takePhoto 2")
            }
        } else {

            LogUtil.e("takePhoto 3")
            autoTakePhoto()
        }
    }

    override fun onResume() {
        super.onResume()
        cameraBizInOnResume()
    }


    private fun cameraBizInOnResume() {
        if (mCamera == null) {

            previewCallBack = SurfaceCallback()
            mholder!!.addCallback(previewCallBack)
        } else {
            if (!isopen_camara) {
                mCamera!!.startPreview()
            }
        }
    }

    private fun cameraBizInOnRestart() {
        if (mCamera == null) {
            if (!isopen_camara) {
                previewCallBack = SurfaceCallback()
                mholder!!.addCallback(previewCallBack)
            }
        }

    }


    override fun onPause() {
        super.onPause()
        cameraBizInOnPause()
    }

    private fun cameraBizInOnPause() {
        isopen_camara = false
        if (mCamera != null && mIsCameraOpenNormally) {
            if (svTakePhoto != null && mholder != null && previewCallBack != null) {
                mholder!!.removeCallback(previewCallBack)
            }
            mCamera!!.setPreviewCallback(null)
            mCamera!!.stopPreview()
            mCamera!!.lock()
            mCamera!!.release()
            mCamera = null
        }
    }


    fun reset() {
        val cameraInfo = Camera.CameraInfo()
        if (mCamera != null && mIsCameraOpenNormally) {
            if (svTakePhoto != null && mholder != null && previewCallBack != null) {
                mholder!!.removeCallback(previewCallBack)
            }
            mCamera!!.setPreviewCallback(null)
            mCamera!!.stopPreview()
            mCamera!!.lock()
            mCamera!!.release()
            mCamera = null

            mCamera = Camera.open(cameraPosition)
            if (null != mholder)
                setParams(mholder!!, cameraInfo.facing)
        }

    }

    override fun onDestroy() {
        //取消注册
        unregisterReceiver(lockScreenReceiver)

        super.onDestroy()
        if (mCamera != null && mIsCameraOpenNormally) {
            if (svTakePhoto != null && mholder != null && previewCallBack != null) {
                mholder!!.removeCallback(previewCallBack)
            }
            mCamera!!.setPreviewCallback(null)
            mCamera!!.stopPreview()
            mCamera!!.lock()
            mCamera!!.release()
            mCamera = null
        }
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>> TakePhotoActivity onDestroy()")


    }

    private fun setParams(mySurfaceView: SurfaceHolder, postion: Int) {
        try {
            var PreviewWidth = 0
            var PreviewHeight = 0
            val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager//获取窗口的管理器
            /*PreviewWidth = display.();
            PreviewHeight = display.getHeight();*/

            val parameters = mCamera!!.parameters

            // 选择合适的预览尺寸
            val sizeList = parameters.supportedPreviewSizes

            // 如果sizeList只有一个我们也没有必要做什么了，因为就他一个别无选择
            if (sizeList.size > 1) {
                val itor = sizeList.iterator()
                var diff = java.lang.Double.MAX_VALUE
                val tempWidth = mySurfaceView.surfaceFrame.height()
                val tempHeight = mySurfaceView.surfaceFrame.width()
                while (itor.hasNext()) {
                    val cur = itor.next()
                    /*if (cur.width >= PreviewWidth
                            && cur.height >= PreviewHeight) {*/
                    //                    if (cur.width >= PreviewWidth
                    //                            && cur.height >= PreviewHeight) {
                    //                        PreviewWidth = cur.width;
                    //                        PreviewHeight = cur.height;
                    ////                        break;
                    //                    }
                    if (!(cur.width > tempWidth && cur.height > tempHeight)) {
                        if (cur.width == tempWidth && cur.height == tempHeight) {
                            PreviewWidth = cur.width
                            PreviewHeight = cur.height
                            break
                        }

                        val newDiff =
                            Math.abs(cur.width.toDouble() / cur.height - tempWidth.toDouble() / tempHeight)
                        if (newDiff < diff) {
                            diff = newDiff
                            PreviewWidth = cur.width
                            PreviewHeight = cur.height
                        }
                    }

                }
            } else {
                PreviewHeight = sizeList[0].height
                PreviewWidth = sizeList[0].width
            }
            parameters.setPreviewSize(PreviewWidth, PreviewHeight) //获得摄像区域的大小
            //parameters.setPreviewFrameRate(3);//每秒3帧  每秒从摄像头里面获得3个画面
            //parameters.setPreviewFpsRange(3,);
            val list = parameters.supportedPreviewFpsRange
            var v: IntArray? = null
            var index = 0
            var min = 0
            for (i in list.indices) {
                v = list[i]
                if (v!![0] > min) {
                    min = v[0]
                    index = i
                }
            }
            parameters.setPreviewFpsRange(list[index][0], list[index][1])
            parameters.pictureFormat = PixelFormat.JPEG//设置照片输出的格式
            parameters.set("jpeg-quality", 85)//设置照片质量

            val pictureSizes = parameters.supportedPictureSizes
            val size = pictureSizes[pictureSizes.size / 2]
            parameters.setPictureSize(size.width, size.height)
            //            if (!AndroidSystemUtils.isMeizuFlymeOS() && !AndroidSystemUtils.isSangsun() && !AndroidSystemUtils
            //                    .isHuaweiEmuiOS()) {
            //                parameters.setPictureSize(PreviewWidth, PreviewHeight);//设置拍出来的屏幕大小
            //            }

            parameters.setRotation(180) //Java部分

            mCamera!!.setDisplayOrientation(90)


//            try {
//                //下面的针对后置摄像头的
//                if(postion == Camera.CameraInfo.CAMERA_FACING_BACK){
//                    LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>  Camera.CameraInfo.CAMERA_FACING_BACK")
//                    var l = parameters.supportedFocusModes
//                    l?.let {
//                        for (item in l) {
//                            if (item.contains("continuous-picture")) {
//                                parameters.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
//                                break
//                            }
//                            LogUtil.e(item.toString())
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                LogUtil.e(">>>>>>>>>>>>>>>>>>>>>> 设置focusMode 失败!")
//                LogUtil.e(e)
//            }


            mCamera!!.parameters = parameters//把上面的设置 赋给摄像头
            mCamera!!.setPreviewDisplay(mySurfaceView)//把摄像头获得画面显示在SurfaceView控件里面
            mholder = mySurfaceView
            mCamera!!.setPreviewCallback { data, camera -> }
            mCamera!!.startPreview()//开始预览
            mCamera!!.autoFocus(null)
            //   mPreviewRunning = true;
        } catch (e: IOException) {
            LogUtil.e(">>>>>>>>>>>>>>>>>> e: IOException")
            LogUtil.e(e)
        }

    }


    var timeInternal = 0L
    private fun autoTakePhoto() {
        if (System.currentTimeMillis() - timeInternal < 300L) {
            return
        }
        timeInternal = System.currentTimeMillis()

//        // 拍照前需要对焦 获取清析的图片
//        if (null == mCamera) return
//
//        mCamera!!.parameters.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO
//
//        mCamera!!.autoFocus { success, camera ->
//            if (isopen_camara) {
//                if (!isTakingPhoto) {
//                    isTakingPhoto = true
//
//                    LogUtil.e("autoTakePhoto 1")
//
//                    mHandler = Handler()
//                    mHandler!!.postDelayed({
//                        if (isSilentMode) {
//                            mCamera!!.takePicture(null, null, MyPictureCallback())
//
//                            LogUtil.e("autoTakePhoto 2")
//
//                        } else {
//                            mCamera!!.takePicture(Camera.ShutterCallback { }, null, MyPictureCallback())
//
//
//                            LogUtil.e("autoTakePhoto 3")
//                        }
//                    },1)
//                }
//            }
//        }


        // 拍照前需要对焦 获取清析的图片
        if (null == mCamera) return



        try {
            mCamera!!.autoFocus(Camera.AutoFocusCallback { success, camera ->
                LogUtil.e(">>>>>>>>>>>>>>>>>>> success")
                LogUtil.e(success)

                if (isopen_camara) {
                    if (!isTakingPhoto) {
                        isTakingPhoto = true
                        if (handler == null) {
                            handler = SafeHandler(this@TakePhotoActivity, object : HandlerCallback {
                                override fun handleMessage(msg: Message?) {
                                }

                            })
                        }

                        handler.post {
                            if (isSilentMode) {
                                mCamera!!.takePicture(null, null, MyPictureCallback())
                            } else {
                                mCamera!!.takePicture(
                                    Camera.ShutterCallback { },
                                    null,
                                    MyPictureCallback()
                                )
                            }
                        }
                    }
                }
            })

        } catch (e: java.lang.Exception) {
            LogUtil.e(">>>>>>>>>>>>>>>>>>> TakePhotoActivity 发生报错")
            LogUtil.e(e)
        }


    }


    var internalTime = 0L
    private fun switchCamara() {
        if ((System.currentTimeMillis() - internalTime) < 500) {
            return
        }
        internalTime = System.currentTimeMillis()

        //        切换前后摄像头
        var cameraCount = 0
        val cameraInfo = Camera.CameraInfo()
        cameraCount = Camera.getNumberOfCameras()//得到摄像头的个数

        for (i in 0 until cameraCount) {
            Camera.getCameraInfo(i, cameraInfo)//得到每一个摄像头的信息
            if (cameraPosition == 1) {
                //现在是后置，变更为前置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置


                    myMoDel = "0"

                    if (svTakePhoto != null && mholder != null && previewCallBack != null) {
                        mholder?.removeCallback(previewCallBack)
                    }
                    mCamera?.setPreviewCallback(null)
                    mCamera?.stopPreview()//停掉原来摄像头的预览
                    mCamera?.lock()
                    mCamera?.release()//释放资源
                    mCamera = null//取消原来摄像头
                    mCamera = Camera.open(i)//打开当前选中的摄像头
                    if (null != mholder)
                        setParams(mholder!!, Camera.CameraInfo.CAMERA_FACING_BACK)
                    cameraPosition = 0



                    break
                }
            } else {
                //现在是前置， 变更为后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置

                    myMoDel = "1"

                    if (svTakePhoto != null && mholder != null && previewCallBack != null) {
                        mholder?.removeCallback(previewCallBack)
                    }
                    mCamera?.setPreviewCallback(null)
                    mCamera?.stopPreview()//停掉原来摄像头的预览
                    mCamera?.lock()
                    mCamera?.release()//释放资源
                    mCamera = null//取消原来摄像头
                    mCamera = Camera.open(i)//打开当前选中的摄像头
                    /*try {
                        if (null != mholder)
                            mCamera.setPreviewDisplay(mholder);//通过surfaceview显示取景画面
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    mCamera.startPreview();//开始预览*/
                    if (null != mholder)
                        setParams(mholder!!, Camera.CameraInfo.CAMERA_FACING_FRONT)
                    cameraPosition = 1


                    break
                }
            }

        }
    }

    // 预览界面回调
    private inner class SurfaceCallback : SurfaceHolder.Callback {
        // 预览界面被创建
        override fun surfaceCreated(holder: SurfaceHolder) {
            if (mCamera == null) {
                try {
                    //1代表打开后置摄像头,0代表打开前置摄像头.
                    mCamera = Camera.open(cameraPosition)// 打开摄像头
                    setParams(holder, cameraPosition)
                    mIsCameraOpenNormally = true
                } catch (e: Exception) {
                    e.printStackTrace()
                    mIsCameraOpenNormally = false
                    LogUtil.e(">>>>>>>>>>>>>>>> surfaceCreated ")
                    finish()
//                    Toast.makeText(
//                        applicationContext,
//                        getString(R.string.request_camera_permissions_faild),
//                        Toast.LENGTH_LONG
//                    ).show()
                    ToastUtils.showGravityShortToast(
                        applicationContext,
                        resources.getString(R.string.request_camera_permissions_faild)
                    )

                }

            }
        }

        override fun surfaceChanged(
            holder: SurfaceHolder, format: Int, width: Int,
            height: Int
        ) {
            println("surfaceChanged")
            isopen_camara = true
            //            autoTakePhoto();
        }

        // 预览界面被销毁
        override fun surfaceDestroyed(holder: SurfaceHolder) {
            println("surfaceDestroyed")
            if (!isopen_camara)
                return
            if (mCamera != null && mIsCameraOpenNormally) {
                holder.removeCallback(this)
                mCamera!!.setPreviewCallback(null)
                mCamera!!.stopPreview()
                mCamera!!.lock()
                mCamera!!.release()
                mCamera = null
            }
        }

    }

    var lastPicName = ""
    var jpgFile1: File? = null

    // 照片回调
    private inner class MyPictureCallback : Camera.PictureCallback {
        // 照片生成后
        override fun onPictureTaken(data: ByteArray, camera: Camera) {

            LogUtil.e("takePhoto 4")

//            LogUtil.e("TAG","myMoDel=$myMoDel")

            try {
                var bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)


//                var info = android.hardware.Camera.CameraInfo()
//                if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                    LogUtil.e("TAG","旋转图片")
//                    //旋转图片
//                    bitmap = RotateImage.rotateBitmap(bitmap, 180.toFloat())
//                }

//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
//                    LogUtil.e("TAG","旋转图片")
//                    //旋转图片
//                    bitmap = RotateImage.rotateBitmap(bitmap, 180.toFloat())
//                }

                TakePhotoRecordBuilder.getFilePath()

//                val jpgFile = File(Environment.getExternalStorageDirectory().toString() + "/DCIM/camera")
                val jpgFile = File(TakePhotoRecordBuilder.getFilePath())



                if (!jpgFile.exists()) {
                    jpgFile.mkdir()
                }

//                val jpgFile1 = File(jpgFile.absoluteFile, lastActivity!! + ".jpg")
                var picName = System.currentTimeMillis().toString() + ".jpg"
                jpgFile1 = File(jpgFile.absoluteFile, picName)

                /********************社保卡申领*******************/
                if (JinLinApp.istype == 2) {

                    LogUtil.e("TAG", "照片储存地址为：$jpgFile1")
                    LogUtil.e("TAG", "初始化宽：" + bitmap.width + "初始化高：" + bitmap.height);
                    //自拍：初始化宽：1200初始化高：1200
                    //拍照：初始化宽：1280初始化高：960
                    if (bitmap.width <= bitmap.height) {
                        bitmap =
                            com.tecsun.jc.base.utils.BitmapUtils.ImageCrop(bitmap, 358, 441, true)

                    } else {
                        bitmap =
                            com.tecsun.jc.base.utils.BitmapUtils.ImageCrop(bitmap, 441, 358, true)

                    }
                    LogUtil.e("TAG", "宽：" + bitmap.width + "高：" + bitmap.height);
                    bitmap = BitmapUtils.zoomImage(bitmap, 441.0, 358.0)
//                    if(bitmap.width<=bitmap.height){
////                        bitmap=com.tecsun.jc.base.utils.BitmapUtils.ImageCrop(bitmap,441,358,true)
//                        bitmap=com.tecsun.jc.base.utils.BitmapUtils.ImageCrop(bitmap,441,358,true)
////                        bitmap = BitmapUtils.zoomImage(bitmap, 358.0,(358 * bitmap.height / bitmap.width).toDouble())
//
//                    }
                    //小：宽为：441高为：245内存大小为：432180
                    //大: 宽为：441高为：441内存大小为：432180

                    //删除
                    deleteFile(jpgFile)
                    lastPicName = picName
                    //更新mtp


                    try {
                        ImageFactory.compressAndGenImage(bitmap, jpgFile1.toString(), 30)

                        // 更新mtp
                        updateMTPOneFile(jpgFile1.toString())

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }//小米
                //441-358:宽为：441高为：290内存大小为：511560

                else {
                    val jpgFile =
                        File(Environment.getExternalStorageDirectory().toString() + "/DCIM/camera")
                    if (!jpgFile.exists()) {
                        jpgFile.mkdir()
                    }

                    LogUtil.e("TAG", "照片储存地址为：$jpgFile1")
                    bitmap = BitmapUtils.zoomImage(
                        bitmap,
                        800.0,
                        (800 * bitmap.height / bitmap.width).toDouble()
                    )

                    bitmap = BitmapUtils.rotateBitmapByDegree(bitmap, 270)


                    try {
                        ImageFactory.compressAndGenImage(bitmap, jpgFile1.toString(), 80)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }

                LogUtil.e(
                    "TAG",
                    "宽为：" + bitmap.width + "高为：" + bitmap.height + "内存大小为：" + bitmap.byteCount
                )
                val bundle = Bundle()
                bundle.putInt("cameraposition", cameraPosition)
                if (!bitmap.isRecycled) {
                    bitmap.recycle()
                }

                LogUtil.e(">>>>>>>>>>>>>>>$lastActivity")


//                if (lastActivity == Constants.COLLECT_PHOTO) {
////                    val intent = Intent(
////                        this@TakePhotoActivity,
////                        com.tecsun.mobileintegration.activity.worker.PhotoConfirActivity::class.java
////                    )
////                    bundle.putString(Constants.COLLECT_NAME, xm)
////                    bundle.putString(Constants.COLLECT_SFZH, sfzh)
////                    intent.putExtra(Constants.ACTIVITY_PIC_WORKER, jpgFile1.toString())
////                    intent.putExtras(bundle)
////                    startActivity(intent)
////                    saveFilePath(jpgFile1.toString())
//                } else if (lastActivity == Constants.CERITIFICATION_PHOTO) {
////                    val intent = Intent(this@TakePhotoActivity, PhotoConfirActivity::class.java)
////                    intent.putExtra(Constants.ACTIVITY_PIC_USER, jpgFile1.toString())
////                    intent.putExtras(bundle)
////                    startActivity(intent)
////                    saveFilePath(jpgFile1.toString())
//
//                } else if (lastActivity == Constants.APPLY_PHOTO) {
                val intent = Intent(this@TakePhotoActivity, PhotoConfirmActivity::class.java)
                intent.putExtra(Constants.ACTIVITY_PIC_APPLY, jpgFile1.toString())
                intent.putExtra(Constants.TAKE_PHOTO_TIP, "重新拍摄")
                intent.putExtras(bundle)
                startActivityForResult(intent, EXAMINATION_ROOM_INFO_CODE)
                saveFilePath(jpgFile1.toString())
                LogUtil.e("takePhoto 5")

//                }


//  s


            } catch (e: Exception) {
                LogUtil.e("takePhoto 6")
                e.printStackTrace()
            } finally {
                if (Build.VERSION.SDK_INT >= 24) {
                    reset()
                }
                isTakingPhoto = false
            }
        }
    }

    companion object {

        var isThis = true
    }

    //为了减少不必要的bug,尤其在三星手机上--->当锁屏的时候关闭当前activity
    private inner class LockScreenReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            this@TakePhotoActivity.finish()
        }
    }

    private var lockScreenReceiver: LockScreenReceiver? = null
    private fun registerListener() {
        lockScreenReceiver = LockScreenReceiver()
        var filter = IntentFilter()
//        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
//        filter.addAction(Intent.ACTION_USER_PRESENT)
        registerReceiver(lockScreenReceiver, filter)
    }


    fun updateMTPOneFile(path: String) {
        MediaScannerConnection.scanFile(
            this, arrayOf(path), null
        ) { _, _ ->

        }
    }

    private fun deleteFile(jpgFile: File) {
        // 删除
        val deletelastPicName = File(jpgFile.absoluteFile, lastPicName + ".jpg")
        if (deletelastPicName != null && MyFileUtil.isFileExists(deletelastPicName.toString())) {
            updateMTPOneFile(deletelastPicName.toString())
            val file = File(deletelastPicName.toString())
            file.delete()
            LogUtil.e(">>>>>>>>>>>>>>> 删除成功 $deletelastPicName")
        }
    }

    private fun saveFilePath(path: String?) {
        //TODO
//        ApplyPreviewBuilder.setPicPeopleP(path)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>  TakePhotoActivity")
        LogUtil.e("requestCode :$requestCode")
        LogUtil.e("resultCode :$resultCode")

        when (resultCode) {
            EXAMINATION_ROOM_INFO_CODE -> {
                val intent = Intent(this@TakePhotoActivity, AddNewStudentActivity::class.java)
                intent.putExtra(BaseConstant.FILTER_SELECT_DATA, jpgFile1.toString())
                setResult(EXAMINATION_ROOM_INFO_CODE, intent)

                finish()

                //由于是使用了Arouter跳转到验证身份证的页面,然后从身份证页面跳转到当前页面, 所以使用了下面的方法跳转到showStudentPicActivity
                ActivityConstant.showStudentPicActivity?.finishForResult()

                //启动刷身份证界面
                DeviceBuilder.skip(this@TakePhotoActivity, getString(R.string.base_student_sign_in))


            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun initTitleView() {
        super.initTitleView()
        val titleBar = findViewById<TitleBar>(com.tecsun.jc.base.R.id.title_bar)
        titleBar?.setLeftClickListener {
            KeyboardUtils.hideSoftKeyboard(this)
            // 处理返回按钮事件
            this.finish()
            //启动刷身份证界面
            DeviceBuilder.skip(this@TakePhotoActivity, getString(R.string.base_student_sign_in))
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            this.finish()
            //启动刷身份证界面
            DeviceBuilder.skip(this@TakePhotoActivity, getString(R.string.base_student_sign_in))
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}




































