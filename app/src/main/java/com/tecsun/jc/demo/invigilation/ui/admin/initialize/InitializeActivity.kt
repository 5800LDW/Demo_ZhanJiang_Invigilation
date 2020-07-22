package com.tecsun.jc.demo.invigilation.ui.admin.initialize

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.ProctorDetailsBean
import com.tecsun.jc.base.bean.db.invigilation.bean.ReadCardInfoBean
import com.tecsun.jc.base.bean.db.invigilation.bean.StudentDetailsBean
import com.tecsun.jc.base.bean.db.invigilation.bean.TestingDetailsBean
import com.tecsun.jc.base.builder.StudentTeacherTakeAdmissionTicketImageBuilder
import com.tecsun.jc.base.builder.StudentTeacherTakeImageBuilder
import com.tecsun.jc.base.builder.TestingRoomBuilder
import com.tecsun.jc.base.dialog.DialogUtils
import com.tecsun.jc.base.utils.DBFunctionUtil
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import kotlinx.android.synthetic.main.activity_initialize_data.*
import org.litepal.LitePal

/***初始化数据*/
class InitializeActivity : BaseActivity(){

    override fun getLayoutId(): Int {
        return R.layout.activity_initialize_data
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle(getString(R.string.app_initialize_data_title))
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        tvInitializeInvigilator.setOnClickListener{

            DialogUtils.showDialog(
                this@InitializeActivity, "", resources.getColor(R.color.c_e60012),
                "确定进行${getString(R.string.app_initialize_invigilator_school)}吗?",
                R.string.app_sure,R.string.app_cancel,
                DialogInterface.OnClickListener { _, _ ->

                    showLoadingDialog(tipContent = "正在初始化...")


                    LitePal.deleteAll(ProctorDetailsBean::class.java)
                    LitePal.deleteAll(TestingDetailsBean::class.java)
                    LitePal.deleteAll(ReadCardInfoBean::class.java)



                    Handler().postDelayed({
                        dismissLoadingDialog()
                        showToast("初始化成功!")
                    },1000)

                },
                DialogInterface.OnClickListener { _, which -> })
        }

        tvInitializeStudent.setOnClickListener {



            DialogUtils.showDialog(
                this@InitializeActivity, "", resources.getColor(R.color.c_e60012),
                "确定进行${getString(R.string.app_initialize_student)}吗?",
                R.string.app_sure,R.string.app_cancel,
                DialogInterface.OnClickListener { _, _ ->
                    showLoadingDialog(tipContent = "正在初始化...")
//                    LitePal.deleteAll(StudentDetailsBean::class.java)
//                    StudentTeacherTakeAdmissionTicketImageBuilder.deleteAllSFZImage()
//                    StudentTeacherTakeImageBuilder.deleteAllSFZImage()
//                    Handler().postDelayed({
//                        dismissLoadingDialog()
//                        showToast("初始化成功!")
//                    },1000)
                    deleteAllStudentData()
                },
                DialogInterface.OnClickListener { _, which -> })

        }
    }


    private fun deleteAllStudentData(){
        Thread{
            LitePal.deleteAll(StudentDetailsBean::class.java)
            StudentTeacherTakeAdmissionTicketImageBuilder.deleteAllSFZImage()
            StudentTeacherTakeImageBuilder.deleteAllSFZImage()

            var hashSet = TestingRoomBuilder.getAllRoomsName()
            for (item in hashSet){
                var count = DBFunctionUtil.countStudentBySchoolName(item)
                if(count == 0){
                    TestingRoomBuilder.deleteRoom(item)
                }
            }
            Handler().postDelayed({
                dismissLoadingDialog()
                showToast("初始化成功!")
            },1000)

        }.run()
    }


}



















