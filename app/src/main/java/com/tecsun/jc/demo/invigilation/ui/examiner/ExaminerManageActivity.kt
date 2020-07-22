package com.tecsun.jc.demo.invigilation.ui.examiner

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.ProctorDetailsBean
import com.tecsun.jc.base.builder.ReadCardImageBuilder
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.utils.JavaStringTool
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.ui.admin.AdminLoginActivity
import com.tecsun.jc.demo.invigilation.ui.examiner.compare.CompareStudentPicActivity
import com.tecsun.jc.demo.invigilation.ui.examiner.obtain_evidence.ObtainEvidenceActivity
import kotlinx.android.synthetic.main.activity_examiner_manage.*
import org.litepal.LitePal


@Route(path = RouterHub.ROUTER_APP_EXAMINER_MANAGE)
class ExaminerManageActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_examiner_manage
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle("监考信息管理")
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)


        if (JinLinApp.loginExaminer?.number.isNullOrBlank()) {
            finish()
        }

        var bean = LitePal.where("sfzh = ?", JinLinApp.loginExaminer?.number ?: "")
            .findFirst(ProctorDetailsBean::class.java)


        if (JinLinApp.loginExaminer != null && JinLinApp.loginExaminer?.number != null) {
            tv_mine_user_sfzh.text = JavaStringTool.replaceIdNum(JinLinApp.loginExaminer?.number)

            var bitmap = ReadCardImageBuilder.getSFZBitmap(JinLinApp.loginExaminer!!.number)
            bitmap?.let {
                iv_mine_avatar.setImageBitmap(bitmap)
            }
        }


        if (JinLinApp.loginExaminer != null && !JinLinApp.loginExaminer?.name.isNullOrBlank()) {

            if (JinLinApp.loginExaminer?.name?.length ?: 0 < 4) {
                tv_mine_user_name.text = "${JinLinApp.loginExaminer?.name!!.substring(0, 1)}老师"
            } else {
                tv_mine_user_name.text = "${JinLinApp.loginExaminer?.name!!.substring(0, 2)}老师"
            }

        }

//        var testingDetailsBean = DBFunctionUtil.findFirstTestingRoom()
//        testingDetailsBean?.let {
            tv_mine_user_card.text = bean?.schoolName ?: ""
//        }

        rlChange.setOnClickListener {
            myStartActivity(AdminLoginActivity::class.java)
//            myFinish()
        }

        ll01.setOnClickListener{
            myStartActivity(CompareStudentPicActivity::class.java)
        }

        ll02.setOnClickListener{
            myStartActivity(ObtainEvidenceActivity::class.java)
        }


    }
}























