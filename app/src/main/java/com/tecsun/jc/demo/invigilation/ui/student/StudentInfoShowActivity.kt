package com.tecsun.jc.demo.invigilation.ui.student

import android.os.Bundle
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.StudentDetailsBean
import com.tecsun.jc.base.builder.StudentOwnImageBuilder
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import kotlinx.android.synthetic.main.activity_student_info_show.*

class StudentInfoShowActivity : BaseActivity() {


    var studentDetailsBean: StudentDetailsBean? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_student_info_show
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle("考生资料")
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        var bean = intent.getSerializableExtra(BaseConstant.FILTER_SELECT)
        bean?.let {
            studentDetailsBean = bean as StudentDetailsBean
        }


        tv_show_name.text = studentDetailsBean?.name ?: ""


        var sex = ""

        studentDetailsBean?.sfzh?.let {


                //显示图片
                var bitmap = StudentOwnImageBuilder.getSFZBitmap(studentDetailsBean?.sfzh!!)
                bitmap?.let {
                    img_show_card_selfie_bitmap.setImageBitmap(bitmap)
                }




            if (studentDetailsBean?.sfzh?.length?:0 > 15) {
                if (Integer.parseInt(
                        studentDetailsBean?.sfzh?.substring(16)?.substring(
                            0,
                            1
                        )
                    ) % 2 == 0
                ) {// 判断性别
                    sex = "女"
                } else {
                    sex = "男"
                }
            }

        }

        tv_show_sex.text = sex
        tv_show_cardid.text = studentDetailsBean?.sfzh?:""
        tvSchoolName.text = studentDetailsBean?.schoolName?:""
        tvExaminationNO.text = studentDetailsBean?.examinationNO?:""
        tvApplyingMajor.text = studentDetailsBean?.applyingMajor?:""
        tvExamTime.text  = studentDetailsBean?.testTime?:""

        tvExamRoomNo.text = studentDetailsBean?.numberOfExams?:""
        tvSeatNo.text = studentDetailsBean?.seatNumber?:""
        tvSubject.text = studentDetailsBean?.subject?:""

    }


}


















