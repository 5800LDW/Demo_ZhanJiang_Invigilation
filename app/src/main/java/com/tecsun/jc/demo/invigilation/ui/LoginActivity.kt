package com.tecsun.jc.demo.invigilation.ui

import android.os.Bundle
import android.view.KeyEvent
import com.alibaba.android.arouter.launcher.ARouter
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.ProctorDetailsBean
import com.tecsun.jc.base.builder.TakePhotoRecordBuilder
import com.tecsun.jc.base.builder.sound.SoundBuilder
import com.tecsun.jc.base.collector.BaseActivityCollector
import com.tecsun.jc.base.utils.*
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.builder.BasicDataBuilder
import com.tecsun.jc.demo.invigilation.builder.compare.CompareBuilderFactory
import com.tecsun.jc.demo.invigilation.builder.device.DeviceBuilder
import com.tecsun.jc.demo.invigilation.ui.admin.AdminLoginActivity
import com.tecsun.jc.demo.lib_readcard.util.PowerUtil
import com.tecsun.jc.register.util.constant.FaceRecognitionConst
import com.tecsun.jc.register.util.constant.FaceRecognitionConst.MODE_0
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus
import org.litepal.LitePal



class LoginActivity : BaseActivity() {

    override fun isCanAddToCollector(): Boolean {
        return false
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }


    var timeInternal = 0L

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        ShowPicUtil.load(R.drawable.login_background, iv_Background)

        btFirst.setOnClickListener {
            myStartActivity(AdminLoginActivity::class.java)
        }

        btSecond.setOnClickListener {
            if (System.currentTimeMillis() - timeInternal < 500) {
                return@setOnClickListener
            }
            timeInternal = System.currentTimeMillis()


            //            var count = LitePal.count(TestingDetailsBean::class.java)
            if (schoolCount == 0) {
                showErrorMessageDialog2(getString(R.string.app_require_admin_band_data))
                return@setOnClickListener
            }


            DeviceBuilder.skip(this, getString(R.string.app_examiner_login))


//            var deviceName = DeviceUtil.getDeviceModel()
//            if (deviceName != null && deviceName.toUpperCase() == "TPS900") {
//                //天波机器
//                ARouter.getInstance().build(RouterHub.ROUTER_LIB_READCARD_IDCARDACTIVITY_COMBINE)
//                    .withString(
//                        "titleName",
//                        getString(R.string.app_examiner_login)
//                    ).greenChannel().navigation()
//            } else if (deviceName != null && deviceName.toUpperCase() == "T1.2") {
//                //深圳厂家机器
//                if (NFCBuilder.isNFCReady(this)) {
//                    myStartActivity(ExaminerLoginActivity::class.java)
//                }
//            } else {
//                //深圳厂家机器
//                if (NFCBuilder.isNFCReady(this)) {
//                    myStartActivity(ExaminerLoginActivity::class.java)
//                }
//            }


        }


        tv_system_version.text = "V${PhoneInfoUtils.getVersionName(this)}"


        //系统厂商:topwise
        //系统厂商:unknown
        LogUtil.e(">>>>>>>>>>>>>>>> 系统厂商:${DeviceUtil.getDeviceManufacturer()}")
        //设备型号:T1.2
        //设备型号:TPS900
        LogUtil.e(">>>>>>>>>>>>>>>> 设备型号:${DeviceUtil.getDeviceModel()}")





//        var deviceName = DeviceUtil.getDeviceModel()
//        if (deviceName != null && deviceName.toUpperCase() != "TPS900") {
//            showErrorMessageDialog("该设备不具有读取身份证或社保卡的硬件!\n点击确定退出")
//        }





        initMode()

        //声音初始化
        SoundBuilder.myInit()

////        var soundPool = SoundPool(10, AudioManager.STREAM_SYSTEM, 5)
////        var id = soundPool.load(this, R.raw.sound_compare_success, 1)
//        llTestingCentre.setOnClickListener{
//            SoundBuilder.playDiscurnSuccess()
////            soundPool.play(id, 1f, 1f, 0, 0, 1f)
//        }



    }

    /***设置默认的人证认证模式*/
    private fun initMode(){
        if (PreferenceUtil.get(JinLinApp.context, FaceRecognitionConst.FACE_COMPARE_MODE).isNullOrBlank()) {
            PreferenceUtil.set(JinLinApp.context, FaceRecognitionConst.FACE_COMPARE_MODE,MODE_0)
        }
    }


//    override fun onResume() {
//        super.onResume()
//        var test = Test()
//        test.activity  = this
//        test.openUsbDevice()
//
//
//        mPermissionGranted = true;
//        finish();
//
//    }


//    var schoolCount = 0
//    override fun onStart() {
//        super.onStart()
//        var sb = StringBuffer()
//        schoolCount = 0
//        var list = LitePal.findAll(TestingDetailsBean::class.java)
//        if (list != null && list.size > 0) {
//            for ((index, item) in list.withIndex()) {
//                if (item is TestingDetailsBean) {
//                    if (index == 0) {
//                        sb.append(item.schoolName ?: "")
//                        schoolCount++
//                    } else {
//                        sb.append("、")
//                        sb.append(item.schoolName ?: "")
//                        schoolCount++
//                    }
//                }
//            }
//
//        }
//        tvSchoolName.text = sb?.toString()?:""
//    }

    var schoolCount = 0
    override fun onStart() {
        super.onStart()
        var hashSet = HashSet<String>()
        var sb = StringBuffer()
        schoolCount = 0
        var list = LitePal.findAll(ProctorDetailsBean::class.java)
        if (list != null && list.size > 0) {
            for (item in list) {
                if (!item.schoolName.isNullOrBlank()) {
                    hashSet.add(item.schoolName)
                }
            }

            var index = 0
            for (item in hashSet) {
                if (index == 0) {
                    sb.append(item)
                    schoolCount++
                    index++
                } else {
                    sb.append("、")
                    sb.append(item)
                    schoolCount++
                }
            }
        }
        tvSchoolName.text = sb?.toString() ?: ""

        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>> sb?.toString() ${sb.toString()}")

        //加入基础的考试信息
        BasicDataBuilder.addStudentNormalData()
    }


    override fun onDestroy() {
        ARouter.getInstance().destroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        CompareBuilderFactory.doQuitApp()
        BaseActivityCollector.finishAllActivity()
        //删除所有的验证人脸时候的"自拍照"
        TakePhotoRecordBuilder.deleteAllSFZImage()
        //关闭读取身份证模块的硬件的供电,减少能耗;
        PowerUtil.readSFZPowerOff()
        super.onDestroy()
        ExitUtils.exit()
    }

    // 用来计算返回键的点击间隔时间
    private var exitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                //弹出提示，可以有多种方式
                ToastUtils.showGravityShortToast(this, getString(R.string.drop_out))
                exitTime = System.currentTimeMillis()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}





























