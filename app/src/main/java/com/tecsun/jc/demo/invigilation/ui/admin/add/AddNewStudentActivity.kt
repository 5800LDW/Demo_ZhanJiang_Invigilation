package com.tecsun.jc.demo.invigilation.ui.admin.add

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import com.alibaba.android.arouter.facade.annotation.Route
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.ReadCardInfoBean
import com.tecsun.jc.base.bean.db.invigilation.bean.StudentDetailsBean
import com.tecsun.jc.base.bean.db.invigilation.bean.TestingDetailsBean
import com.tecsun.jc.base.bean.filter.IFilter
import com.tecsun.jc.base.builder.ReadCardImageBuilder
import com.tecsun.jc.base.builder.StudentOwnImageBuilder
import com.tecsun.jc.base.builder.StudentTeacherTakeAdmissionTicketImageBuilder
import com.tecsun.jc.base.builder.StudentTeacherTakeImageBuilder
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.common.BaseConstant.DEFAULT_FORMAT
import com.tecsun.jc.base.common.BaseConstant.DEFAULT_FORMAT_YYYY
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.utils.IdcardUtils
import com.tecsun.jc.base.utils.PermissionsUtils
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.utils.time.TimeUtil
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.base.widget.builder.TimePickerBuilder
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.builder.BasicDataBuilder
import com.tecsun.jc.demo.invigilation.ui.FilterItemActivity
import com.tecsun.jc.demo.invigilation.ui.pic.TakePhotoForAddStudentActivity
import com.tecsun.jc.demo.invigilation.util.constant.Constants
import com.tecsun.jc.register.util.constant.Const.EXAMINATION_ROOM_INFO_CODE_02
import com.tecsun.jc.register.util.constant.Const.ROOM_1
import com.tecsun.jc.register.util.constant.Const.ROOM_2
import kotlinx.android.synthetic.main.activity_add_new_student.*
import org.litepal.LitePal
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


@Route(path = RouterHub.ROUTER_APP_ADD_NEW_STUDENT)
class AddNewStudentActivity : BaseActivity() {

    //    private val EXAMINER_INFO_CODE = 0x000100
    private val EXAMINATION_ROOM_INFO_CODE = 0x000101
    var readCardInfoBean: ReadCardInfoBean? = null

    private var name: String? = null
    private var sfzh: String? = null
    /**准考证号 */
    private var examinationNO: String? = null
    /**报考专业 */
    private var ApplyingMajor: String? = null
    /**照片 */
    private var pic: Bitmap? = null
    /**考点 */
    private var schoolName: String? = null

    private var mBitmap: Bitmap? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_add_new_student
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle(getString(R.string.app_add_new_student))
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        setExaminationRoom()

        flTakePic.setOnClickListener {
            takePic()
        }

        rl02.setOnClickListener {
            getExaminationRoomInfo()
        }


        btnAddStudent.setOnClickListener {

            if (etRegisterName.text.isNullOrBlank()) {
                showToast("请填入学生姓名")
                return@setOnClickListener
            }

            if (tvVenue.text.isNullOrBlank()) {
                showToast("请选择考场")
                return@setOnClickListener
            }

            if (etSFZH.text.isNullOrBlank()) {
                showToast("请填入身份证号")
                return@setOnClickListener
            }

            //验证社保号
            if (!IdcardUtils.validateCard(etSFZH.text.toString())) {
                showToast("请输入正确的身份证号")
                return@setOnClickListener
            }

            if (pic == null) {
                showToast("请进行人像拍照")
                return@setOnClickListener
            }
            addStudent()
        }


        val yearStr = TimeUtil.getPastYear(DEFAULT_FORMAT_YYYY, 0)
        val startDate = "$yearStr-01"
        val endDate = "$yearStr-12"
        var startTimeBuilder: TimePickerBuilder? = null
        startTimeBuilder = TimePickerBuilder()
            .setActivity(this)
//            .setDefaultDate(startDate)
            //设置开始时间比结束时间少一年
            //.setDefaultDate(TimeUtil.getPastYear(OtherConstant.DEFAULT_FORMAT, 1))
            .setListener { date, v ->
                val b = isErrorDate(
                    SimpleDateFormat(
                        DEFAULT_FORMAT,
                        Locale.getDefault()
                    ).format(date), TimeUtil.getCurrentDateAccordingToFormat(DEFAULT_FORMAT)
                )


                LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> $b   startTimeBuilder")

                if(b){
                    startTimeBuilder?.setDefaultDate(
                        SimpleDateFormat(
                            DEFAULT_FORMAT,
                            Locale.getDefault()
                        ).format(date)
                    )
                    tvExamTime2.text = startTimeBuilder?.defaultDate
                }
                else{
                    showToast("考试时间不能小于当前时间")
                }


            }

        tvExamTime2.setOnClickListener {
            startTimeBuilder.showView()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var bean = intent.getSerializableExtra(BaseConstant.FILTER_SELECT_DATA)
        if (bean != null && bean is ReadCardInfoBean) {
            readCardInfoBean = bean
//            person_name.text = bean.name?.trim()
//            person_gender.text = bean.sex?.trim()
//            person_nation.text = bean.nation?.trim()
//            person_birth.text = bean.birthday
//            person_address.text = bean.address
//            person_idCard.text = bean.number
//            person_authority.text = bean.qianfa
//            person_durationValidity.text = bean.effdate

            if (!bean.number.isNullOrBlank()) {
                //显示图片
                var bitmap = ReadCardImageBuilder.getSFZBitmap(bean.number)
                bitmap?.let {
                    img_show_card_selfie_bitmap.setImageBitmap(bitmap)
                    pic = bitmap
                }
            }
            etRegisterName.setText(bean.name?.trim() ?: "")
            etSFZH.setText(bean.number ?: "")
        }
    }


    private fun addStudent() {
        //删除历史记录
        var deleteCount =
            LitePal.deleteAll(StudentDetailsBean::class.java, "sfzh = ? ", etSFZH.text.toString())
        LogUtil.e(">>>>>>>>>>>>>>>>> 删除的学生(${etSFZH.text.toString()})的数量:$deleteCount")

        showLoadingDialog(tipContent = "正在处理...")

        var bean = StudentDetailsBean()
        bean.name = etRegisterName.text.toString()
        bean.sfzh = etSFZH.text.toString().toUpperCase()
        bean.schoolName = tvVenue.text.toString()
        bean.examinationNO = etExaminationNO.text.toString()
        bean.applyingMajor = etApplyingMajor.text.toString()
        bean.testTime = tvExamTime2.text.toString()

        bean.numberOfExams = etExamRoomNo.text.toString()
        bean.seatNumber = etSeatNo.text.toString()
        bean.subject = etSubject.text.toString()




        StudentOwnImageBuilder.savePic(pic, bean.sfzh)
        var b = bean.save()

        if (b) {
            Handler().postDelayed({
                dismissLoadingDialog()
//                showToast("保存成功!")

                StudentTeacherTakeImageBuilder.deleteFileBySFZH(bean.sfzh)
                StudentTeacherTakeAdmissionTicketImageBuilder.deleteFileBySFZH(bean.sfzh)

                showSuccessMessageDialog("保存成功!")

                var r2List =
                    LitePal.where("schoolName = ? ", ROOM_2).find(StudentDetailsBean::class.java)
                r2List?.let {
                    for (item in r2List) {
                        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> r2List :$item")
                    }
                }
            }, 1000)

        } else {
            Handler().postDelayed({
                dismissLoadingDialog()
                showToast("保存失败!")
            }, 1000)
        }
    }


    /**
     * 拍照
     */
    fun takePic() {
        TakePhotoForAddStudentActivity.isThis = true
        val intent = Intent(this, TakePhotoForAddStudentActivity::class.java)
        intent.putExtra(BaseConstant.FILTER_SELECT, EXAMINATION_ROOM_INFO_CODE)
        intent.putExtra(Constants.LAST_ACTIVITY_PHOTO, Constants.APPLY_PHOTO)
        startActivityForResult(intent, EXAMINATION_ROOM_INFO_CODE)

//        TakePhotoForAddStudentActivity.isThis = true
//        val intent = Intent(this, TakePhotoForAddStudentActivity::class.java)
//        intent.putExtra(BaseConstant.FILTER_SELECT, EXAMINATION_ROOM_INFO_CODE)
//        intent.putExtra(Constants.LAST_ACTIVITY_PHOTO, Constants.TAKE_PHOTO_FOR_ADD_STUDENT)
//        startActivityForResult(intent, EXAMINATION_ROOM_INFO_CODE)


    }

    // 用户权限 申请 的回调方法
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val intent = Intent(this@AddNewStudentActivity, TakePhotoForAddStudentActivity::class.java)
        intent.putExtra(Constants.LAST_ACTIVITY_PHOTO, Constants.APPLY_PHOTO)
        if (PermissionsUtils.requestPermissionResult(this, permissions, grantResults)) {
            if (requestCode == 321) {
                LogUtil.e("从这里去，3")
                startActivity(intent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == 0x01 && resultCode == Activity.RESULT_OK && data != null) {
//            //选择图片
//            val uri = data.data
//            val cr = this.contentResolver
//            try {
//                if (mBitmap != null) {
//                    //如果不释放的话，不断取图片，将会内存不够
//                    mBitmap!!.recycle()
//                }
//                mBitmap = BitmapFactory.decodeStream(cr.openInputStream(uri!!))
//            } catch (e: FileNotFoundException) {
//                e.printStackTrace()
//            }
//
//            mBitmap = BitmapUtils.zoomImage(mBitmap!!, 800.0, (800 * mBitmap!!.height / mBitmap!!.width).toDouble())
//            mBitmap = BitmapUtils.compressImage(mBitmap!!, 50)
//
//            pic = mBitmap
//            pic?.let{
//                LogUtil.e("高:${pic!!.height},宽:${pic!!.width}")
//                img_show_card_selfie_bitmap.setImageBitmap(pic)
//            }
//
//        } else {
//            showToast( "请重新选择图片")
//        }
//        super.onActivityResult(requestCode, resultCode, data)

        LogUtil.e("requestCode :$requestCode")
        LogUtil.e("resultCode :$resultCode")

        when (resultCode) {
            EXAMINATION_ROOM_INFO_CODE -> {
                var picPath = data?.getStringExtra(BaseConstant.FILTER_SELECT_DATA)
                var mBitmap = BitmapFactory.decodeFile(picPath)
                pic = mBitmap
                pic?.let {
                    LogUtil.e("高:${pic!!.height},宽:${pic!!.width}")
                    img_show_card_selfie_bitmap.setImageBitmap(pic)
                }
            }

            EXAMINATION_ROOM_INFO_CODE_02 -> {
                var item = data?.getSerializableExtra(BaseConstant.FILTER_SELECT_DATA)
                val district =
                    if (item != null) {
                        item as TestingDetailsBean
                    } else {
                        null
                    }

                district?.let {
                    tvVenue.text = district.schoolName

                    if (district.schoolName == ROOM_1) {
                        tvVenue.text = ROOM_1
                    } else if (district.schoolName == ROOM_2) {
                        tvVenue.text = ROOM_2
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun getExaminationRoomInfo() {
        val intent = Intent(this, FilterItemActivity::class.java)
        intent.putExtra(BaseConstant.FILTER_SELECT, EXAMINATION_ROOM_INFO_CODE_02)
        intent.putExtra(BaseConstant.FILTER_LIST_DATA, examinationRoomInfoList as Serializable)
        startActivityForResult(intent, EXAMINATION_ROOM_INFO_CODE_02)
    }

    private val examinationRoomInfoList: LinkedList<IFilter> = LinkedList()
    private fun setExaminationRoom() {
        if (BasicDataBuilder.getExaminationRoom().size != 0) {
            examinationRoomInfoList.clear()
            examinationRoomInfoList.addAll(BasicDataBuilder.getExaminationRoom())
        }
    }


    /**
     * 时间对比
     */
    private fun isErrorDate(startDate: String, endDate: String): Boolean {
        val df = SimpleDateFormat(DEFAULT_FORMAT)
        try {
            val d1 = df.parse(startDate)
            val d2 = df.parse(endDate)
            val diff = d1.time - d2.time
            return if (diff > 0) {
                true
            } else {
                false
            }
        } catch (e: Exception) {
            LogUtil.e(e)
        }

        return false
    }
}



















