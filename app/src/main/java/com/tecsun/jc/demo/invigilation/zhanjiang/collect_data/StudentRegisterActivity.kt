package com.tecsun.jc.demo.invigilation.zhanjiang.collect_data

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.ShowInfoBean
import com.tecsun.jc.base.bean.filter.IFilter
import com.tecsun.jc.base.builder.StudentOwnSFZImageBuilder
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.common.BaseConstant.KEY_PARAM
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.listener.OkGoRequestCallback
import com.tecsun.jc.base.utils.BaseRegexUtil
import com.tecsun.jc.base.utils.BuildUtils
import com.tecsun.jc.base.utils.KeyboardUtils
import com.tecsun.jc.base.utils.OkGoManager
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.SingleClickListener
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.ui.FilterItemActivity
import com.tecsun.jc.demo.invigilation.util.BitmapUtils2
import com.tecsun.jc.demo.invigilation.zhanjiang.bean.TrainRegisterParam
import com.tecsun.jc.demo.invigilation.zhanjiang.bean.UploadPicEntity
import com.tecsun.jc.demo.invigilation.zhanjiang.bean.UploadPicParam
import kotlinx.android.synthetic.main.app_activity_person_declare.*
import kotlinx.android.synthetic.main.app_activity_person_declare.etDeclarePhone
import kotlinx.android.synthetic.main.app_activity_person_declare.tvCulture
import kotlinx.android.synthetic.main.app_activity_person_declare.tvDeclareBirth
import kotlinx.android.synthetic.main.app_activity_person_declare.tvDeclareSex
import kotlinx.android.synthetic.main.app_activity_person_declare.tvDeclareSfzh
import kotlinx.android.synthetic.main.app_activity_person_declare.tvRank
import kotlinx.android.synthetic.main.app_activity_student_regiser.*
import java.io.Serializable

/**
 * 学员登记
 */
@Route(path = RouterHub.ROUTER_APP_STUDENT_REGISTER)
class StudentRegisterActivity: BaseActivity() {

    private val TAG = StudentRegisterActivity::class.java.simpleName

    private val CONST_CULTURE = 0x0001

    private val CONST_EMPLOYMENT_INTENTION = 0x0002

    private val CONST_PURPOSE = 0x0003

    private val CONST_STATE = 0x0004

    private val CONST_CERTIFICATE = 0x0005

    private val CONST_RANK = 0x0006

    private var positivePicId = ""

    private var negativePicId = ""

    /** 文化程度 */
    private val dataCulture: ArrayList<IFilter> by lazy {
        getArr(R.array.arr_dataCulture)
    }

    /** 就业意愿 就业意向 */
    private val dataEmploymentIntention: ArrayList<IFilter> by lazy {
        getArr(R.array.arr_dataEmploymentIntention)
    }

    /** 目前状态 */
    private val dataState: ArrayList<IFilter> by lazy {
        getArr(R.array.arr_dataState)
    }

    /** 现有职业资格证书 */
    private val dataCertificate: ArrayList<IFilter> by lazy {
        getArr(R.array.arr_dataCertificate)
    }

    /** 报名培训级别 */
    private val dataRank: ArrayList<IFilter> by lazy {
        getArr(R.array.arr_dataRegisterRank)
    }

    private fun getArr(i: Int): ArrayList<IFilter> {
        var arr = resources.getStringArray(i)
        var l = ArrayList<IFilter>()
        for (item in arr) {
            l.add(ShowInfoBean().setInfo(item))
        }
        return l
    }

    private fun skip(data: ArrayList<IFilter>, i: Int) {
        val intent = Intent(this, FilterItemActivity::class.java)
        intent.putExtra(BaseConstant.FILTER_SELECT, i)
        intent.putExtra(BaseConstant.FILTER_LIST_DATA, data as Serializable)
        startActivityForResult(intent, i)
    }


    override fun getLayoutId(): Int {
        return R.layout.app_activity_student_regiser
    }

    override fun setTitleBar(titleBar: TitleBar) {
        super.setTitleBar(titleBar)
        titleBar?.setTitle("培训项目学员登记")
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        llRegisterContent02.visibility = View.GONE

        JinLinApp.studentDetailsBean2?.apply {

            //姓名
            tvRegisterName.text = name ?: ""
            //性别
            tvRegisterSex.text = if (!sex.isNullOrBlank() && sex.contains("女")) "女" else "男"
            //出生
            tvRegisterBirth.text = born.replace(".", "-")
            //身份证
            tvRegisterSfzh.text = sfzh ?: ""
            //民族
            tvNation.text = nation ?: ""
        }

        btNext.setOnClickListener(object :SingleClickListener(){
            override fun onSingleClick(v: View?) {
                checkInfo()
            }
        })

        llRegisterCulture.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                skip(dataCulture, CONST_CULTURE)
            }
        })

        llEmploymentIntention.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                skip(dataEmploymentIntention, CONST_EMPLOYMENT_INTENTION)
            }
        })

        llRegisterPurpose.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                skip(dataEmploymentIntention, CONST_PURPOSE)
            }
        })

        llRegisterState.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                skip(dataState, CONST_STATE)
            }
        })

        llRegisterCertificate.setOnClickListener(object :SingleClickListener(){
            override fun onSingleClick(v: View?) {
                skip(dataCertificate, CONST_CERTIFICATE)
            }
        })

        llRegisterRank.setOnClickListener(object :SingleClickListener(){
            override fun onSingleClick(v: View?) {
                skip(dataRank, CONST_RANK)
            }
        })
    }

    private fun checkInfo() {

        if (etCultivate.text.isNullOrBlank()) {
            showToast("培训机构不能为空")
            return
        }

        if (tvRegisterName.text.isNullOrBlank()) {
            showToast("姓名")
            return;
        }

        if (tvRegisterSex.text.isNullOrBlank()) {
            showToast("性别不能为空")
            return;
        }

        if (tvNation.text.isNullOrBlank()) {
            showToast("民族不能为空")
            return;
        }

        if (tvRegisterSfzh.text.isNullOrBlank()) {
            showToast("身份证号不能为空")
            return;
        }


        if (tvRegisterBirth.text.isNullOrBlank()) {
            showToast("出生年月不能为空")
            return;
        }

        if (tvRegisterCulture.text.isNullOrBlank()) {
            showToast("请选择学历")
            return;
        }

        if (etRegisterPhone.text.isNullOrBlank()) {
            showToast("手机号码不能为空")
            return;
        }

        if (!BaseRegexUtil.isMobile(etRegisterPhone.text.toString())) {
            showToast("请输入正确的手机号码")
            return
        }

        if (tvEmploymentIntention.text.isNullOrBlank()) {
            showToast("请选择就业意愿")
            return;
        }

        if (tvRegisterPurpose.text.isNullOrBlank()) {
            showToast("请选择就业意向")
            return;
        }

        if (tvRegisterState.text.isNullOrBlank()) {
            showToast("请选择目前状态")
            return;
        }

        if (etResidenceAddress.text.isNullOrBlank()) {
            showToast("户籍地址不能为空")
            return;
        }

        if (etAddress.text.isNullOrBlank()) {
            showToast("现住地址不能为空")
            return;
        }

        if (tvRegisterCertificate.text.isNullOrBlank()) {
            showToast("请选择现有职业资格证书")
            return;
        }

        if (etProfession.text.isNullOrBlank()) {
            showToast("拥有证书工种不能为空")
            return;
        }

        if (etTrainProfession.text.isNullOrBlank()) {
            showToast("报名培训工种不能为空")
            return;
        }

        if (tvRegisterRank.text.isNullOrBlank()) {
            showToast("请选择培训级别")
            return;
        }

//        uploadNegativePic()

        var param = TrainRegisterParam()

        //现住地址 address
        param.address = etAddress.text.toString()

        //出生年月 birth
        param.birth = tvRegisterBirth.text.toString()

        //证件号码 certNo
        param.certNo = tvRegisterSfzh.text.toString()

        //现有技术等级、职业资格证书,数据字典编码：TELV
        param.certificate = tvRegisterCertificate.text.toString()

        //拥有证书的工种 certificateType
        param.certificateType = etProfession.text.toString()

        //createTime	string

        //户籍地址  domicile
        param.domicile = etResidenceAddress.text.toString()

        //学历,数据字典编码：EDU  educatio
        param.education = tvRegisterCulture.text.toString()

        //就业意向,1:是 0:否 employmentIntent
        param.employmentIntent = tvRegisterPurpose.text.toString()

        //目前状态,数据字典编码：TRAIN_JOB_STATUS employmentStatus
        param.employmentStatus = tvRegisterState.text.toString()

        //就业意愿,1:是 0:否 employmentWish
        param.employmentWish = tvEmploymentIntention.text.toString()

        //性别,1:男 0:女 gender
        param.gender = tvRegisterSex.text.toString()

        //报名参加培训级别,数据字典编码：TELV_TYPE
        param.jobTrainLevel = tvRegisterRank.text.toString()

        //报名参加培训工种 jobTrainType
        param.jobTrainType = etTrainProfession.text.toString()

        //联系电话 mobile
        param.mobile = etRegisterPhone.text.toString()

        //民族 nation
        param.nation = tvNation.text.toString()

        // 调用上传照片接口返回的id picDown

        //调用上传照片接口返回的id picUp

        // trainId	integer($int32)

        //培训机构 trainOrg
        param.trainOrg = etCultivate.text.toString()

        //updateTime	string

        //userName 姓名
        param.userName = tvRegisterName.text.toString()


        var intent = Intent(this,StudentRegisterConfirmActivity::class.java)
        intent.putExtra(KEY_PARAM,param)
        startActivity(intent)

    }

    fun uploadNegativePic() {
        //TODO

        uploadPositivePic()
    }


    fun uploadPositivePic() {
        //TODO

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        LogUtil.e(TAG, "requestCode :$requestCode")
        LogUtil.e(TAG, "resultCode :$resultCode")

        var item = data?.getSerializableExtra(BaseConstant.FILTER_SELECT_DATA)
        val bean =
            if (item != null) {
                item as ShowInfoBean
            } else {
                null
            }

        bean?.info?.apply {
            when (resultCode) {
                CONST_CULTURE -> tvRegisterCulture.text = this
                CONST_EMPLOYMENT_INTENTION -> tvEmploymentIntention.text = this
                CONST_PURPOSE -> tvRegisterPurpose.text = this
                CONST_STATE -> tvRegisterState.text = this
                CONST_CERTIFICATE -> tvRegisterCertificate.text = this
                CONST_RANK -> tvRegisterRank.text = this
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 重新提交
     */
    override fun resubmit() {
        super.resubmit()
        checkInfo()
    }


    /**
     * 初始化titlebar控件，可重写
     */
    override fun initTitleView() {
//        super.initTitleView()
        setImmersiveStatusBar()
        val titleBar = findViewById<TitleBar>(com.tecsun.jc.base.R.id.title_bar)
        if (titleBar == null) {
            return
        }

        if (BuildUtils.hasKitKat()) {
            titleBar.setImmersive(true)
        }

        titleBar.setBackgroundColor(resources.getColor(com.tecsun.jc.base.R.color.c_2358ff))

        titleBar.setLeftImageResource(com.tecsun.jc.base.R.drawable.ic_title_back)//base_zhanjiang_back_left //
        titleBar.setLeftTextColor(Color.WHITE)
        titleBar.setLeftClickListener {
            KeyboardUtils.hideSoftKeyboard(this)
            // 处理返回按钮事件
//            this.finish()
            showExitDialog()
        }

        titleBar.setTitleColor(Color.WHITE)
        titleBar.setSubTitleColor(Color.WHITE)
        setTitleBar(titleBar)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {

            showExitDialog()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}

































