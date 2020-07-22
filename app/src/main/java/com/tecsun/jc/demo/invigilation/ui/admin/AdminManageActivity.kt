package com.tecsun.jc.demo.invigilation.ui.admin

import android.os.Bundle
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.builder.BasicDataBuilder
import com.tecsun.jc.demo.invigilation.ui.admin.add.AddNewActivity
import com.tecsun.jc.demo.invigilation.ui.admin.compare_mode.FaceCompareModelActivity
import com.tecsun.jc.demo.invigilation.ui.admin.download.DownloadInfoActivity
import com.tecsun.jc.demo.invigilation.ui.admin.history.HistoryProctorActivity
import com.tecsun.jc.demo.invigilation.ui.admin.initialize.InitializeActivity
import kotlinx.android.synthetic.main.activity_admin_manage.*

class AdminManageActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_admin_manage
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle("管理")
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        ll01.setOnClickListener{
            myStartActivity(DownloadInfoActivity::class.java)
        }

        ll02.setOnClickListener{
            myStartActivity(HistoryProctorActivity::class.java)
        }

        ll03.setOnClickListener{
            myStartActivity(InitializeActivity::class.java)

        }

        ll04.setOnClickListener{
            myStartActivity(AddNewActivity::class.java)
        }

        ll05.setOnClickListener{
            myStartActivity(FaceCompareModelActivity::class.java)
        }

    }

    override fun onStart() {
        super.onStart()
        //加入基础的考试信息
        BasicDataBuilder.addStudentNormalData()
    }
}
