package com.tecsun.jc.demo.invigilation.ui.admin.add

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.utils.DeviceUtil
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.builder.NFCBuilder
import com.tecsun.jc.demo.invigilation.builder.device.DeviceBuilder
import kotlinx.android.synthetic.main.activity_add_new.*

class AddNewActivity : BaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_add_new
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle(R.string.app_add_data)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

//        btAddStudent.setOnClickListener {
//            myStartActivity(AddNewStudentActivity::class.java)
//            myFinish()
//        }

        btBatchAddStudent.setOnClickListener{
            //批量新增学生
            ARouter.getInstance().build(RouterHub.ROUTER_LIB_IMPORT_DATA)
                .greenChannel().navigation()
        }

        btAddStudent.setOnClickListener{
            DeviceBuilder.skip(this,getString(R.string.app_add_new_student),"")
        }

        btAddInvigilator.setOnClickListener {

            var deviceName = DeviceUtil.getDeviceModel()

            //天波机器
            if (deviceName != null && deviceName.toUpperCase() == "TPS900") {
                ARouter.getInstance().build(RouterHub.ROUTER_LIB_READCARD_IDCARDACTIVITY_COMBINE)
                    .withString(
                        "titleName",
                        getString(R.string.app_add_new_invigilator_data)
                    ).greenChannel().navigation()
            }
            //深圳厂家机器
            else if (deviceName != null && deviceName.toUpperCase() == "T1.2") {

                if (NFCBuilder.isNFCReady(this)) {
                    myStartActivity(AddNewInvigilatorActivity::class.java)
                    myFinish()
                }
            }

            //其他机器
            else {
                //深圳厂家机器
                if (NFCBuilder.isNFCReady(this)) {
                    myStartActivity(AddNewInvigilatorActivity::class.java)
                    myFinish()
                }
            }
        }
    }
}



















