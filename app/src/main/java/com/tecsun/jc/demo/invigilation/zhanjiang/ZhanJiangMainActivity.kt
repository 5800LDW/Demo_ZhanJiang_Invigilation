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
import com.tecsun.jc.base.common.RouterHub
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
import kotlinx.android.synthetic.main.app_activity_zhanjiang_main.*
import org.greenrobot.eventbus.EventBus
import org.litepal.LitePal
import java.io.Serializable


class ZhanJiangMainActivity : BaseActivity() {

    private val TAG = ZhanJiangMainActivity::class.java.simpleName

    override fun isCanAddToCollector(): Boolean {
        return false
    }

    override fun getLayoutId(): Int {
        return R.layout.app_activity_zhanjiang_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ShowPicUtil.load(R.drawable.app_zhanjiang_bg, iv_Background)

        ll01.setOnClickListener(object: SingleClickListener() {
            override fun onSingleClick(v: View?) {
                DeviceBuilder.skip(this@ZhanJiangMainActivity, getString(R.string.base_person_declare))


//                ARouter.getInstance().build(RouterHub.ROUTER_APP_PERSON_DECLARE)
//                    .withString(
//                        BaseConstant.STUDENT_SFZH,
//                        ""
//                    )
//                    .withString(
//                        BaseConstant.TITLE_NAME,
//                        resources.getString(com.tecsun.jc.demo.lib_readcard.R.string.base_student_Certification)
//                    )
//                    .greenChannel().navigation()
            }
        })

        ll02.setOnClickListener(object: SingleClickListener() {
            override fun onSingleClick(v: View?) {
            }
        })

        ll03.setOnClickListener(object: SingleClickListener() {
            override fun onSingleClick(v: View?) {
                myStartActivity(ZhanJiangActivity::class.java)
            }
        })

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





























