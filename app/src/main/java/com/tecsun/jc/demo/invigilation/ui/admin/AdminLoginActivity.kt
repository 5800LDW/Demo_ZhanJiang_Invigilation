package com.tecsun.jc.demo.invigilation.ui.admin

import android.os.Bundle
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.collector.BaseActivityCollector
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import kotlinx.android.synthetic.main.activity_admin_login.*

class AdminLoginActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_admin_login
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle(R.string.app_admin_login)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        btn_change_company_confirm.setOnClickListener{
            if(etRegisterName.text.toString() == "admin" &&
                etRegisterNumber.text.toString() == "admin"
                    ){
                myStartActivity(AdminManageActivity::class.java)
                myFinish()
                BaseActivityCollector.finishAllActivity()
            }
            else{
                showToast("账号或密码错误")
            }
        }
    }
}





























