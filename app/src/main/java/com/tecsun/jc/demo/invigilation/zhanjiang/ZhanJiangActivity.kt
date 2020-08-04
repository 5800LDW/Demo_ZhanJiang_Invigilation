package com.tecsun.jc.demo.invigilation.zhanjiang

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.JinLinApp.Companion.courseId
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.ProctorDetailsBean
import com.tecsun.jc.base.bean.db.invigilation.bean.TestingDetailsBean
import com.tecsun.jc.base.builder.TakePhotoRecordBuilder
import com.tecsun.jc.base.builder.sound.SoundBuilder
import com.tecsun.jc.base.collector.BaseActivityCollector
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.dialog.DialogUtils
import com.tecsun.jc.base.listener.IEvents
import com.tecsun.jc.base.listener.OkGoRequestCallback
import com.tecsun.jc.base.utils.*
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.SingleClickListener
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.builder.BasicDataBuilder
import com.tecsun.jc.demo.invigilation.builder.compare.CompareBuilderFactory
import com.tecsun.jc.demo.invigilation.builder.device.DeviceBuilder
import com.tecsun.jc.demo.invigilation.ui.FilterItemActivity
import com.tecsun.jc.demo.invigilation.ui.admin.AdminLoginActivity
import com.tecsun.jc.demo.invigilation.zhanjiang.bean.ListBean
import com.tecsun.jc.demo.invigilation.zhanjiang.constant.Constants.IP_LIST
import com.tecsun.jc.demo.lib_readcard.util.PowerUtil
import com.tecsun.jc.register.util.constant.Const
import com.tecsun.jc.register.util.constant.FaceRecognitionConst
import com.tecsun.jc.register.util.constant.FaceRecognitionConst.MODE_0
import kotlinx.android.synthetic.main.activity_add_new_student.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btFirst
import kotlinx.android.synthetic.main.activity_login.btSecond
import kotlinx.android.synthetic.main.activity_login.iv_Background
import kotlinx.android.synthetic.main.activity_login.tvSchoolName
import kotlinx.android.synthetic.main.activity_login.tv_system_version
import kotlinx.android.synthetic.main.activity_login_zhanjiang.*
import org.greenrobot.eventbus.EventBus
import org.litepal.LitePal
import java.io.Serializable


class ZhanJiangActivity : BaseActivity() {

    private val TAG = ZhanJiangActivity::class.java.simpleName

    override fun isCanAddToCollector(): Boolean {
        return false
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login_zhanjiang
    }


    var timeInternal = 0L

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

//
        LogUtil.e(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> initView  ZhanJiangActivity")
//
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

        }


        tv_system_version.text = "V${PhoneInfoUtils.getVersionName(this)}"


        //系统厂商:topwise
        //系统厂商:unknown
        LogUtil.e(">>>>>>>>>>>>>>>> 系统厂商:${DeviceUtil.getDeviceManufacturer()}")
        //设备型号:T1.2
        //设备型号:TPS900
        LogUtil.e(">>>>>>>>>>>>>>>> 设备型号:${DeviceUtil.getDeviceModel()}")


        initMode()

        //声音初始化
        SoundBuilder.myInit()


        //////////////////////////////////////////////////////////

        getList(null)

        loginTV01.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                if (data.isNullOrEmpty()) {
                    showLoadingDialog(tipContent = "正在获取数据, 请稍后...")
                    getList(IEvents {
                        showListSelect()
                    })
                } else {
                    showListSelect()
                }
            }
        })
        loginIV01.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                loginTV01.performClick()
            }
        })

        loginBT01.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                if (selectDataBean == null) {
                    showToast("请选择培训课程")
                    return
                }

                courseId = selectDataBean?.id

                DeviceBuilder.skip(this@ZhanJiangActivity, getString(R.string.base_student_sign_in))
            }

        })
    }

    /***设置默认的人证认证模式*/
    private fun initMode() {
        if (PreferenceUtil.get(JinLinApp.context, FaceRecognitionConst.FACE_COMPARE_MODE)
                .isNullOrBlank()
        ) {
            PreferenceUtil.set(JinLinApp.context, FaceRecognitionConst.FACE_COMPARE_MODE, MODE_0)
        }
    }

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


//    override fun onDestroy() {
//        ARouter.getInstance().destroy()
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this)
//        }
//        CompareBuilderFactory.doQuitApp()
//        BaseActivityCollector.finishAllActivity()
//        //删除所有的验证人脸时候的"自拍照"
//        TakePhotoRecordBuilder.deleteAllSFZImage()
//        //关闭读取身份证模块的硬件的供电,减少能耗;
//        PowerUtil.readSFZPowerOff()
//        super.onDestroy()
//        ExitUtils.exit()
//    }

    private val data = ArrayList<ListBean.DataBean>()
    private fun getList(listener: IEvents?) {
        OkGoManager.instance.okGoRequestManageForGet(
            IP_LIST,
            ListBean::class.java,
            object : OkGoRequestCallback<ListBean> {
                override fun onSuccess(t: ListBean) {
                    dismissLoadingDialog()
                    if (t != null && t.statusCode == "200") {

                        if (t.data != null && t.data.size > 0) {
                            if(data.size == 0){
                                data.addAll(t.data)
                            }
                            listener?.biz()

                        } else {
                            showToast("培训课程数据为空")
                        }
                    } else {
                        showToast(t?.message ?: "")
                    }
                }

                override fun onError(throwable: Throwable?) {
                    dismissLoadingDialog()
                    showToast("获取数据失败!")
                    LogUtil.e(TAG, ">>>>>>>>>>>>>>>>>>>>>>>> getList() onError  = $throwable")
                }
            })
    }

    private fun showListSelect() {
        val intent = Intent(this, FilterItemActivity::class.java)
        intent.putExtra(BaseConstant.FILTER_SELECT, Const.EXAMINATION_ROOM_INFO_CODE_02)
        intent.putExtra(BaseConstant.FILTER_LIST_DATA, data as Serializable)
        startActivityForResult(intent, Const.EXAMINATION_ROOM_INFO_CODE_02)
    }


    private var selectDataBean: ListBean.DataBean? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        LogUtil.e("requestCode :$requestCode")
        LogUtil.e("resultCode :$resultCode")

        when (resultCode) {

            Const.EXAMINATION_ROOM_INFO_CODE_02 -> {
                var item = data?.getSerializableExtra(BaseConstant.FILTER_SELECT_DATA)
                val bean =
                    if (item != null) {
                        item as ListBean.DataBean
                    } else {
                        null
                    }


                bean?.let {
                    selectDataBean = bean
                    loginTV01.text = bean.courseName ?: ""
                    loginTV02.text = bean.signNum?.toString() ?: ""
                    loginTV03.text = bean.createTime ?: ""

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}





























