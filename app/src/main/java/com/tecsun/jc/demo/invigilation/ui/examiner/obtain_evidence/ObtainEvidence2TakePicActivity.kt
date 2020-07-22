package com.tecsun.jc.demo.invigilation.ui.examiner.obtain_evidence

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.StudentDetailsBean
import com.tecsun.jc.base.builder.StudentOwnImageBuilder
import com.tecsun.jc.base.builder.StudentTeacherTakeAdmissionTicketImageBuilder
import com.tecsun.jc.base.builder.StudentTeacherTakeImageBuilder
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.dialog.DialogUtils
import com.tecsun.jc.base.listener.IEvents
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.ui.pic.TakeIdCardActivity
import com.tecsun.jc.demo.invigilation.ui.pic.TakePhotoForAddStudentActivity
import com.tecsun.jc.demo.invigilation.util.constant.Constants
import com.tecsun.jc.register.util.constant.Const
import kotlinx.android.synthetic.main.activity_student_info_obtain_evidence_take_pic.*
import kotlinx.android.synthetic.main.activity_student_info_obtain_evidence_take_pic.tvExamTime
import kotlinx.android.synthetic.main.activity_student_info_show.*
import kotlinx.android.synthetic.main.activity_student_info_show.img_show_card_selfie_bitmap
import kotlinx.android.synthetic.main.activity_student_info_show.tvApplyingMajor
import kotlinx.android.synthetic.main.activity_student_info_show.tvExaminationNO
import kotlinx.android.synthetic.main.activity_student_info_show.tvSchoolName
import kotlinx.android.synthetic.main.activity_student_info_show.tv_show_cardid
import kotlinx.android.synthetic.main.activity_student_info_show.tv_show_name
import kotlinx.android.synthetic.main.activity_student_info_show.tv_show_sex


/***考场内取证*/
class ObtainEvidence2TakePicActivity : BaseActivity() {


    var studentDetailsBean: StudentDetailsBean? = null

    private var selectPosition = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_student_info_obtain_evidence_take_pic
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle(getString(R.string.app_student_obtain_evidence))
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        selectPosition = intent.getIntExtra(BaseConstant.FILTER_SELECT_POSITION, -1)

        var bean = intent.getSerializableExtra(BaseConstant.FILTER_SELECT)
        bean?.let {
            studentDetailsBean = bean as StudentDetailsBean
        }


        tv_show_name.text = studentDetailsBean?.name ?: ""
        tvExamTime.text = studentDetailsBean?.testTime?:""


        var sex = ""

        studentDetailsBean?.sfzh?.let {
            //显示图片
            var bitmap = StudentOwnImageBuilder.getSFZBitmap(studentDetailsBean?.sfzh!!)
            bitmap?.let {
                img_show_card_selfie_bitmap.setImageBitmap(bitmap)
            }
            if (studentDetailsBean?.sfzh?.length ?: 0 > 15) {
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
        tv_show_cardid.text = studentDetailsBean?.sfzh ?: ""
        tvSchoolName.text = studentDetailsBean?.schoolName ?: ""
        tvExaminationNO.text = studentDetailsBean?.examinationNO ?: ""
        tvApplyingMajor.text = studentDetailsBean?.applyingMajor ?: ""
        tvExamRoomNo1.text = studentDetailsBean?.numberOfExams?:""
        tvSeatNo1.text = studentDetailsBean?.seatNumber?:""
        tvSubject1.text = studentDetailsBean?.subject?:""

        takePic1()

        btCancel.setOnClickListener{
            finish()
        }

        flAdmissionTicket.setOnClickListener{
            startActivityResult(Const.SOURCE_ID_POSITIVE)
        }

        btObtainConfirm.setOnClickListener{
            if(picFace == null){
                showToast("请拍人像照")
                return@setOnClickListener
            }
            if(picExamination == null){
                showToast("请拍准考证照片")
                return@setOnClickListener
            }

            DialogUtils.showDialog(
                this, "", resources.getColor(R.color.c_e60012),
                "确定进行添加保存吗?",
                R.string.app_sure,R.string.app_cancel,
                DialogInterface.OnClickListener { _, _ ->

                    showLoadingDialog(tipContent = "正在处理...")

                    Handler().postDelayed({
                        StudentTeacherTakeImageBuilder.savePic(picFace,studentDetailsBean?.sfzh ?: "")
                        StudentTeacherTakeAdmissionTicketImageBuilder.savePic(picExamination,studentDetailsBean?.sfzh ?: "")
                    },300)


                    Handler().postDelayed({
                        dismissLoadingDialog()
                        showSuccessMessageDialog("添加成功!",iEvents = IEvents {

                            intent.putExtra(BaseConstant.FILTER_SELECT_POSITION, selectPosition)
                            setResult(Const.EXAMINATION_ROOM_INFO_CODE, intent)
                            finish()

                        })
                    },1500)

                },
                DialogInterface.OnClickListener { _, which -> })

        }

        studentDetailsBean?.sfzh?.let {
            var bitmap1 = StudentTeacherTakeImageBuilder.getSFZBitmap(studentDetailsBean!!.sfzh!!)
            if(bitmap1 !=null){
                ivTakePhotoPic.setImageBitmap(bitmap1)
            }
            var bitmap2 = StudentTeacherTakeAdmissionTicketImageBuilder.getSFZBitmap(studentDetailsBean!!.sfzh!!)
            if(bitmap2 !=null){
                ivAdmissionTicket.setImageBitmap(bitmap2)
            }
        }

        set()
    }

    private fun set(){
        val foregroundColorSpan = ForegroundColorSpan(Color.parseColor("#e60012"))
        val text = getString(R.string.register_photo_tips2)
        val index = text.indexOf("此处") + 2
        val end = text.indexOf(",再")
        var spannable = SpannableStringBuilder(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View?) {
//                showToast("点击了")
                if (picExamination != null) {
                    rotateBitmap(picExamination!!)
                }
                else{
                    showToast("请拍照准考证")
                }

            }

            override fun updateDrawState(ds: TextPaint?) {
//                ds!!.color = Color.parseColor("#e60012")
            }
        }
        spannable.setSpan(clickableSpan, index, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(foregroundColorSpan, index, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        tvRotateTips!!.movementMethod = LinkMovementMethod.getInstance()
        tvRotateTips!!.text = spannable
        tvRotateTips!!.setHighlightColor(getResources().getColor(android.R.color.transparent))
    }

    private var internalTime = 0L
    private fun rotateBitmap(bitmap: Bitmap) {
        if (System.currentTimeMillis() - internalTime < 300) {
            return
        }
        internalTime = System.currentTimeMillis()
        runOnUiThread {
            picExamination = com.tecsun.jc.demo.invigilation.util.RotateImage.rotateBitmap(bitmap, 90.toFloat())
            ivAdmissionTicket!!.setImageBitmap(picExamination)
        }
    }








    private fun takePic1() {
        flTakePic.setOnClickListener {
            /**
             * 拍照
             */
            TakePhotoForAddStudentActivity.isThis = true
            val intent = Intent(this, TakePhotoForAddStudentActivity::class.java)
            intent.putExtra(BaseConstant.FILTER_SELECT, Const.EXAMINATION_ROOM_INFO_CODE)
            intent.putExtra(Constants.LAST_ACTIVITY_PHOTO, Constants.APPLY_PHOTO)
            startActivityForResult(intent, Const.EXAMINATION_ROOM_INFO_CODE)
        }
    }

    /**自拍照片 */
    private var picFace: Bitmap? = null
    /**准考证照片*/
    private var picExamination:Bitmap? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        LogUtil.e("requestCode :$requestCode")
        LogUtil.e("resultCode :$resultCode")
        when (resultCode) {
            Const.EXAMINATION_ROOM_INFO_CODE -> {
                var picPath = data?.getStringExtra(BaseConstant.FILTER_SELECT_DATA)
                var mBitmap = BitmapFactory.decodeFile(picPath)
                picFace = mBitmap
                picFace?.let {
                    LogUtil.e("高:${picFace!!.height},宽:${picFace!!.width}")
                    ivTakePhotoPic.setImageBitmap(picFace)
                }

            }

            Const.SOURCE_ID_POSITIVE ->{
                var picPath = data?.getStringExtra(BaseConstant.FILTER_SELECT_DATA)
                var mBitmap = BitmapFactory.decodeFile(picPath)
                if(mBitmap!=null){
                    picExamination = mBitmap
                }
                if (picExamination != null) {
                    ivAdmissionTicket!!.setImageBitmap(picExamination)
                    LogUtil.e("TAG","---------------- mIvIdPositive ------------------")
                }


//                picExamination = JinLinApp.instance.getInsertPicture()
//                if (picExamination != null) {
//                    ivAdmissionTicket!!.setImageBitmap(picExamination)
//                    LogUtil.e("TAG","---------------- mIvIdPositive ------------------")
//                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }







    /**
     * 页面跳转
     * @param takeIdSource 拍照来源
     */
    private fun startActivityResult(takeIdSource: Int) {
        var intent = Intent(this, TakeIdCardActivity::class.java)
        intent.putExtra(Const.TAKE_ID_SOURCE, takeIdSource)
        startActivityForResult(intent, 0x0001)
    }
}

















