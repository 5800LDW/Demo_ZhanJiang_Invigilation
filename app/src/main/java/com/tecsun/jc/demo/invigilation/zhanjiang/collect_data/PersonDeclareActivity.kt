package com.tecsun.jc.demo.invigilation.zhanjiang.collect_data

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.ShowInfoBean
import com.tecsun.jc.base.bean.filter.IFilter
import com.tecsun.jc.base.builder.ResultFailTipsBuilder
import com.tecsun.jc.base.builder.ResultSuccessTipsBuilder
import com.tecsun.jc.base.builder.StudentOwnSFZImageBuilder
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.common.OtherConstant
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.listener.IEvents
import com.tecsun.jc.base.listener.IEvents2
import com.tecsun.jc.base.listener.OkGoRequestCallback
import com.tecsun.jc.base.utils.BaseRegexUtil
import com.tecsun.jc.base.utils.OkGoManager
import com.tecsun.jc.base.utils.ToastUtils
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.utils.time.TimeUtil
import com.tecsun.jc.base.widget.SingleClickListener
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.base.widget.builder.TimePickerBuilder
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.ui.FilterItemActivity
import com.tecsun.jc.demo.invigilation.ui.pic.MyBaseActivity
import com.tecsun.jc.demo.invigilation.util.BitmapUtils2
import com.tecsun.jc.demo.invigilation.zhanjiang.bean.*
import com.tecsun.jc.demo.invigilation.zhanjiang.builder.DictionariesInfoBuilder
import kotlinx.android.synthetic.main.app_activity_person_declare.*
import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * 个人申报
 */
@Route(path = RouterHub.ROUTER_APP_PERSON_DECLARE)
class PersonDeclareActivity : BaseActivity() {

    private val TAG = PersonDeclareActivity::class.java.simpleName

    private val CONST_CULTURE = 0x0001

    private val CONST_RANK = 0x0002

    private val CONST_EXAM_TYPE = 0x0003

    private val CONST_SUBJECT = 0x0004

    private val CONST_TRAIN = 0x0005

    private var timeDeclareProfessionStart: TimePickerBuilder? = null

    private var timeDeclareProfessionEnd: TimePickerBuilder? = null

    private var timeDeclareTrainTimeStart: TimePickerBuilder? = null

    private var timeDeclareTrainTimeEnd: TimePickerBuilder? = null

    private var timeDeclareCertificate: TimePickerBuilder? = null

    private val personDeclareActivity = this;


    /** 文化程度 */
    private val dataCulture: ArrayList<IFilter> by lazy {
        getArr(R.array.arr_dataCulture)
    }

    /** 申报级别 */
    private val dataRank: ArrayList<IFilter> by lazy {
        getArr(R.array.arr_dataRank)
    }

    /** 考试类型 */
    private val dataExamType: ArrayList<IFilter> by lazy {
        getArr(R.array.arr_dataExamType)
    }

    /** 考试科目 */
    private val dataSubject: ArrayList<IFilter> by lazy {
        getArr(R.array.arr_dataSubject)
    }

    /** 职业培训 */
    private val dataTrain: ArrayList<IFilter> by lazy {
        getArr(R.array.arr_dataTrain)
    }


    private fun getArr(i: Int): ArrayList<IFilter> {
        var arr = resources.getStringArray(i)
        var l = ArrayList<IFilter>()
        for (item in arr) {
            l.add(ShowInfoBean().setInfo(item))
        }
        return l
    }

    override fun getLayoutId(): Int {
        return R.layout.app_activity_person_declare
    }

    override fun setTitleBar(titleBar: TitleBar) {
        super.setTitleBar(titleBar)
        titleBar?.setTitle("职业技能鉴定个人申报")
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        DictionariesInfoBuilder.getAll()

        initTime()

        JinLinApp.studentDetailsBean2?.apply {
            //显示图片
            var bitmap =
                StudentOwnSFZImageBuilder.getSFZBitmap(sfzh ?: "")
            bitmap?.let {
                appDeclarePic.setImageBitmap(bitmap)
            }
            tvDeclareName.text = name ?: ""
            //性别
            tvDeclareSex.text = if (!sex.isNullOrBlank() && sex.contains("女")) "女" else "男"
            //出生
            tvDeclareBirth.text = born.replace(".", "-")
            //身份证
            tvDeclareSfzh.text = sfzh ?: ""
        }

        llCulture.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                skip(dataCulture, CONST_CULTURE)
            }
        })

        llRank.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                skip(dataRank, CONST_RANK)
            }
        })

        llExamType.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                skip(dataExamType, CONST_EXAM_TYPE)
            }
        })

        llDeclareSubject.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                skip(dataSubject, CONST_SUBJECT)
            }
        })

        llDeclareTrain.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                skip(dataTrain, CONST_TRAIN)
            }
        })

        tvDeclareProfessionStart.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                timeDeclareProfessionStart?.showView()
            }
        })

        ivDeclareProfessionStart.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                timeDeclareProfessionStart?.showView()
            }
        })

        tvDeclareProfessionEnd.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                timeDeclareProfessionEnd?.showView()
            }
        })

        ivDeclareProfessionEnd.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                timeDeclareProfessionEnd?.showView()
            }
        })

        tvDeclareTrainTimeStart.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                timeDeclareTrainTimeStart?.showView()
            }
        })

        ivDeclareTrainTimeStart.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                timeDeclareTrainTimeStart?.showView()
            }
        })

        tvDeclareTrainTimeEnd.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                timeDeclareTrainTimeEnd?.showView()
            }
        })

        ivDeclareTrainTimeEnd.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                timeDeclareTrainTimeEnd?.showView()
            }
        })



        ivDeclareTrainTime.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                timeDeclareTrainTimeStart?.showView()
            }
        })

        tvDeclareCertificate.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                timeDeclareCertificate?.showView()
            }
        })

        ivDeclareCertificate.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                timeDeclareCertificate?.showView()
            }
        })
        btSure.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                checkInfo()
            }
        })

    }

    private fun skip(data: ArrayList<IFilter>, i: Int) {
        val intent = Intent(this, FilterItemActivity::class.java)
        intent.putExtra(BaseConstant.FILTER_SELECT, i)
        intent.putExtra(BaseConstant.FILTER_LIST_DATA, data as Serializable)
        startActivityForResult(intent, i)
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
                CONST_CULTURE -> tvCulture.text = this
                CONST_RANK -> tvRank.text = this
                CONST_EXAM_TYPE -> tvExamType.text = this
                CONST_SUBJECT -> tvDeclareSubject.text = this
                CONST_TRAIN -> tvDeclareTrain.text = this
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initTime() {
        val yearStr = TimeUtil.getPastYear(OtherConstant.DEFAULT_FORMAT_YYYY, 0)
        val startDate: String = "$yearStr-01"
        val endDate: String = "$yearStr-12"



        timeDeclareProfessionStart = TimePickerBuilder()
            .setActivity(this)
            .setDefaultDate(startDate) //设置开始时间比结束时间少一年
            .setTitleText("选择开始年限")
            //.setDefaultDate(TimeUtil.getPastYear(OtherConstant.DEFAULT_FORMAT, 1))
            .setListener { date, v ->

                val b: Boolean =
                    isErrorDate(
                        SimpleDateFormat(
                            OtherConstant.DEFAULT_FORMAT,
                            Locale.getDefault()
                        ).format(date),
                        timeDeclareProfessionEnd?.defaultDate
                            ?: TimeUtil.getCurrentDateAccordingToFormat(OtherConstant.DEFAULT_FORMAT)
                    )
                if (!b) {
                    timeDeclareProfessionStart!!.defaultDate = SimpleDateFormat(
                        OtherConstant.DEFAULT_FORMAT,
                        Locale.getDefault()
                    ).format(date)
                    tvDeclareProfessionStart.text = timeDeclareProfessionStart?.defaultDate
                } else {
                    showToast(getString(R.string.base_error_time_select))
                }
            }


        timeDeclareProfessionEnd = TimePickerBuilder()
            .setActivity(this)
            .setDefaultDate(endDate)
            .setTitleText("选择结束年限")
            //.setDefaultDate(TimeUtil.getCurrentDateAccordingToFormat(OtherConstant.DEFAULT_FORMAT))
            .setListener { date, v ->
                val b: Boolean =
                    isErrorDate(
                        timeDeclareProfessionStart?.defaultDate ?: TimeUtil.getPastYear(
                            OtherConstant.DEFAULT_FORMAT,
                            1
                        ),
                        SimpleDateFormat(
                            OtherConstant.DEFAULT_FORMAT,
                            Locale.getDefault()
                        ).format(date)
                    )

                if (!b) {
                    timeDeclareProfessionEnd?.defaultDate = SimpleDateFormat(
                        OtherConstant.DEFAULT_FORMAT,
                        Locale.getDefault()
                    ).format(date)
                    tvDeclareProfessionEnd.text = timeDeclareProfessionEnd?.defaultDate
                } else {
                    showToast(getString(R.string.base_error_time_select))
                }
            }


        timeDeclareTrainTimeStart = TimePickerBuilder()
            .setActivity(this)
            .setDefaultDate(startDate) //设置开始时间比结束时间少一年
            .setTitleText("选择开始培训日期")
            //.setDefaultDate(TimeUtil.getPastYear(OtherConstant.DEFAULT_FORMAT, 1))
            .setListener { date, v ->

                val b: Boolean =
                    isErrorDate(
                        SimpleDateFormat(
                            OtherConstant.DEFAULT_FORMAT,
                            Locale.getDefault()
                        ).format(date),
                        timeDeclareTrainTimeEnd?.defaultDate
                            ?: TimeUtil.getCurrentDateAccordingToFormat(OtherConstant.DEFAULT_FORMAT)
                    )
                if (!b) {
                    timeDeclareTrainTimeStart!!.defaultDate = SimpleDateFormat(
                        OtherConstant.DEFAULT_FORMAT,
                        Locale.getDefault()
                    ).format(date)
                    tvDeclareTrainTimeStart.text = timeDeclareTrainTimeStart?.defaultDate
                } else {
                    showToast(getString(R.string.base_error_time_select))
                }
            }



        timeDeclareTrainTimeEnd = TimePickerBuilder()
            .setActivity(this)
            .setDefaultDate(endDate)
            .setTitleText("选择结束培训日期")
            //.setDefaultDate(TimeUtil.getCurrentDateAccordingToFormat(OtherConstant.DEFAULT_FORMAT))
            .setListener { date, v ->
                val b: Boolean =
                    isErrorDate(
                        timeDeclareTrainTimeStart?.defaultDate ?: TimeUtil.getPastYear(
                            OtherConstant.DEFAULT_FORMAT,
                            1
                        ),
                        SimpleDateFormat(
                            OtherConstant.DEFAULT_FORMAT,
                            Locale.getDefault()
                        ).format(date)
                    )

                if (!b) {
                    timeDeclareTrainTimeEnd?.defaultDate = SimpleDateFormat(
                        OtherConstant.DEFAULT_FORMAT,
                        Locale.getDefault()
                    ).format(date)
                    tvDeclareTrainTimeEnd.text = timeDeclareTrainTimeEnd?.defaultDate
                } else {
                    showToast(getString(R.string.base_error_time_select))
                }
            }

        timeDeclareCertificate = TimePickerBuilder()
            .setActivity(this)
            .setDefaultDate(endDate)
            .setTitleText("选择获证日期")
            //.setDefaultDate(TimeUtil.getCurrentDateAccordingToFormat(OtherConstant.DEFAULT_FORMAT))
            .setListener { date, v ->

                timeDeclareProfessionEnd?.defaultDate = SimpleDateFormat(
                    OtherConstant.DEFAULT_FORMAT,
                    Locale.getDefault()
                ).format(date)
                tvDeclareCertificate.text = timeDeclareProfessionEnd?.defaultDate
            }
    }

    /**
     * 时间对比
     */
    private fun isErrorDate(startDate: String, endDate: String): Boolean {
        val df: DateFormat = SimpleDateFormat(OtherConstant.DEFAULT_FORMAT)
        try {
            val d1 = df.parse(startDate)
            val d2 = df.parse(endDate)
            LogUtil.e(TAG, "d1 = $d1")
            LogUtil.e(TAG, "d2 = $d2")
            val diff = d1.time - d2.time
            return diff > 0
        } catch (e: Exception) {
            LogUtil.e(e)
        }
        return false
    }


    override fun onDestroy() {
        ResultSuccessTipsBuilder.dismissTipsDialogAndRelease()
        ResultFailTipsBuilder.dismissTipsDialogAndRelease()
        super.onDestroy()
    }

    private fun checkInfo() {

        if (tvDeclareName.text.isNullOrBlank()) {
            showToast("姓名不能为空")
            return
        }

        if (tvDeclareSex.text.isNullOrBlank()) {
            showToast("性别不能为空")
            return;
        }

        if (tvDeclareBirth.text.isNullOrBlank()) {
            showToast("出生日期不能为空")
            return;
        }

        if (tvDeclareSfzh.text.isNullOrBlank()) {
            showToast("证件号码不能为空")
            return;
        }

        if (tvCulture.text.isNullOrBlank()) {
            showToast("文化程度不能为空")
            return;
        }

        if (etDeclareCompany.text.isNullOrBlank()) {
            showToast("单位名称不能为空")
            return;
        }

        if (etDeclarePhone.text.isNullOrBlank()) {
            showToast("手机号码不能为空")
            return;
        }

        if (!BaseRegexUtil.isMobile(etDeclarePhone.text.toString())) {
            showToast("请输入正确的手机号码")
            return
        }

        if (etDeclareAddress.text.isNullOrBlank()) {
            showToast("通讯地址不能为空")
            return;
        }

        if (etDeclareCondition.text.isNullOrBlank()) {
            showToast("申报条件不能为空")
            return;
        }

        if (etDeclareProfession.text.isNullOrBlank()) {
            showToast("申报职业不能为空")
            return;
        }

        if (tvRank.text.isNullOrBlank()) {
            showToast("申报级别不能为空")
            return;
        }

        if (tvExamType.text.isNullOrBlank()) {
            showToast("考试类型不能为空")
            return;
        }

        if (tvDeclareSubject.text.isNullOrBlank()) {
            showToast("考核科目不能为空")
            return;
        }

        if (tvDeclareTrain.text.isNullOrBlank()) {
            showToast("请选择是否参加本地培训")
            return;
        }

        if (tvDeclareTrainTimeStart.text.isNullOrBlank()) {
            showToast("请选择开始培训日期")
            return;
        }

        if (tvDeclareTrainTimeEnd.text.isNullOrBlank()) {
            showToast("请选择结束培训日期")
            return;
        }

        if (etDeclareTrainTotalTime.text.isNullOrBlank()) {
            showToast("培训学时不能为空")
            return;
        }

        if (tvDeclareProfessionStart.text.isNullOrBlank()) {
            showToast("请选择开始年限")
            return;
        }

        if (tvDeclareProfessionEnd.text.isNullOrBlank()) {
            showToast("请选择结束年限")
            return;
        }

        if (tvDeclareProfessionEnd.text.isNullOrBlank()) {
            showToast("请选择结束年限")
            return;
        }

        if (teDeclareYear.text.isNullOrBlank()) {
            showToast("从事本职业工种年限不能为空w")
            return;
        }


        //先上传图片获取id;
        uploadSFZHPic()
        //获取数据字典;
        //declareNow()
    }


    private fun declareNow() {
        var param = DeclareParam()

        //通讯地址
        param.address = etDeclareAddress.text.toString()

        //出生年月
        param.birth = tvDeclareBirth.text.toString()
            .replaceFirst("-", "年")
            .replaceFirst("-", "月") + "日"

        //身份证号
        param.certNo = tvDeclareSfzh.text.toString()


        //证件类型，数据字典编码：CERT_TYPE
        if (DictionariesInfoBuilder.data_CERT_TYPE.size == 0 || !DictionariesInfoBuilder.data_CERT_TYPE.contains(
                tvDeclareType.text?.toString() ?: ""
            )
        ) {
            //跳转到取获取字典
            DictionariesInfoBuilder.getCERT_TYPE(object : IEvents2 {
                override fun biz() {
                    resubmit()
                }

                override fun failBiz(str: String) {
                    dismissLoadingDialog()
                    LogUtil.e(TAG,">>>>>>>>>.1")
                    showErrorDialog(str)
                }
            })
            return
        }
        //证件类型，数据字典编码：CERT_TYPE
        if (DictionariesInfoBuilder.data_CERT_TYPE.contains(tvDeclareType.text?.toString() ?: "")) {
            param.certType = DictionariesInfoBuilder.data_CERT_TYPE[tvDeclareType.text.toString()]
            LogUtil.e(
                TAG,
                ">>>>>>>>>> ${tvDeclareType.text?.toString() ?: ""}  : ${param.certType}"
            )
        } else {
            param.certType = ""
        }


        //单位名称
        param.companyName = etDeclareCompany.text.toString()

        //申报条件
        param.cond = etDeclareCondition.text.toString()
        //createTime	string


        //数据字典编码：EDU
        if (DictionariesInfoBuilder.data_EDU.size == 0 || !DictionariesInfoBuilder.data_EDU.contains(
                tvCulture.text?.toString() ?: ""
            )
        ) {
            //跳转到取获取字典
            DictionariesInfoBuilder.getEDU(object : IEvents2 {
                override fun biz() {
                    resubmit()
                }

                override fun failBiz(str: String) {
                    dismissLoadingDialog()
                    LogUtil.e(TAG,">>>>>>>>>.2")
                    showErrorDialog(str)
                }
            })
            return
        }
        //数据字典编码：EDU
        if (DictionariesInfoBuilder.data_EDU.contains(tvCulture.text?.toString() ?: "")) {
            param.education = DictionariesInfoBuilder.data_EDU[tvCulture.text.toString()]
            LogUtil.e(TAG, ">>>>>>>>>> ${tvCulture.text?.toString() ?: ""}  : ${param.education}")
        } else {
            param.education = ""
        }


        //考试类型，1:正考 0:补考
        param.examType = if (tvExamType.text.contains("正")) "1" else "0"

        //性别，1:男 0:女
        param.gender = if (tvDeclareSex.text.contains("男")) "1" else "0"

        //申报职业
        param.job = etDeclareProfession.text.toString() ?: ""


        //工作开始时间，时间格式：yyyy年mm月dd日
        param.jobBeginTime =
            tvDeclareProfessionStart.text.toString()
                .replaceFirst("-", "年")
                .replaceFirst("-", "月") + "日"


        //职业资格证书
        if (etDeclareCertificate.text.isNullOrBlank()) {
            param.jobCert = "无"
        } else {
            param.jobCert = etDeclareCertificate.text?.toString() ?: ""
        }

        //职业资格等级
        if (etDeclareCLevel.text.isNullOrBlank()) {
            param.jobCertLevel = "无"
        } else {
            param.jobCertLevel = etDeclareCLevel.text?.toString() ?: ""
        }


        //证书号码
        if (etDeclareCNum.text.isNullOrBlank()) {
            param.jobCertNo = "无"
        } else {
            param.jobCertNo = etDeclareCNum.text?.toString() ?: ""
        }

        //获证日期，时间格式：yyyy年mm月dd日
        if (tvDeclareCertificate.text.isNullOrBlank()) {
            param.jobCertTime = "无"
        } else {
            param.jobCertTime = tvDeclareCertificate.text.toString()
                .replaceFirst("-", "年")
                .replaceFirst("-", "月") + "日"
        }


        //工作结束时间，时间格式：yyyy年mm月dd日
        param.jobEndTime = tvDeclareProfessionEnd.text.toString()
            .replaceFirst("-", "年")
            .replaceFirst("-", "月") + "日"


        //申报职业级别，数据字典编码：APPLY_LEVEL
        if (DictionariesInfoBuilder.data_APPLY_LEVEL.size == 0 || !DictionariesInfoBuilder.data_APPLY_LEVEL.contains(
                tvRank.text.toString() ?: ""
            )
        ) {
            //跳转到取获取字典
            DictionariesInfoBuilder.getAPPLY_LEVEL(object : IEvents2 {
                override fun biz() {
                    resubmit()
                }

                override fun failBiz(str: String) {
                    dismissLoadingDialog()
                    LogUtil.e(TAG,">>>>>>>>>.3")
                    showErrorDialog(str)
                }
            })
            return
        }
        //申报职业级别，数据字典编码：APPLY_LEVEL
        if (DictionariesInfoBuilder.data_APPLY_LEVEL.contains(tvRank.text.toString())) {
            param.jobLevel = DictionariesInfoBuilder.data_APPLY_LEVEL[tvRank.text.toString()]
            LogUtil.e(TAG, ">>>>>>>>>> ${tvRank.text?.toString() ?: ""}  : ${param.jobLevel}")
        } else {
            param.jobLevel = ""
        }


        // 根据开始年限和结束年限进行计算
        param.jobTime = teDeclareYear.text.toString() ?: ""


        //手机号码
        param.mobile = etDeclarePhone.text?.toString() ?: ""
        //调用上传照片接口返回的id ; 需要先上传根据返回的结果来处理;
        param.pic = sfzhPic


        //数据字典编码：APPLY_SUBJECT 需要查数据字典
        if (DictionariesInfoBuilder.data_APPLY_SUBJECT.size == 0 || !DictionariesInfoBuilder.data_APPLY_SUBJECT.contains(
                tvDeclareSubject.text.toString() ?: ""
            )
        ) {
            //跳转到取获取字典
            DictionariesInfoBuilder.getAPPLY_SUBJECT(object : IEvents2 {
                override fun biz() {
                    resubmit()
                }

                override fun failBiz(str: String) {
                    dismissLoadingDialog()
                    LogUtil.e(TAG,">>>>>>>>>.4")
                    showErrorDialog(str)
                }
            })
            return
        }
        //申报职业级别，数据字典编码：APPLY_LEVEL
        if (DictionariesInfoBuilder.data_APPLY_SUBJECT.contains(tvDeclareSubject.text.toString())) {
            param.subject =
                DictionariesInfoBuilder.data_APPLY_SUBJECT[tvDeclareSubject.text.toString()]
            LogUtil.e(
                TAG,
                ">>>>>>>>>> ${tvDeclareSubject.text?.toString() ?: ""}  : ${param.subject}"
            )

        } else {
            param.subject = ""
        }


        //是否参加本职业培训，1:已参加培训 0:未参加培训
        param.train = if (tvDeclareTrain.text.contains("已")) "1" else "0"

        //培训开始时间，时间格式：yyyy年mm月dd日
        param.trainBeginTime = tvDeclareTrainTimeStart.text.toString()
            .replaceFirst("-", "年")
            .replaceFirst("-", "月") + "日"

        //培训结束时间，时间格式：yyyy年mm月dd日
        param.trainEndTime = tvDeclareTrainTimeEnd.text.toString()
            .replaceFirst("-", "年")
            .replaceFirst("-", "月") + "日"

        //培训机构
        param.trainOrg = "无"

        //培训学时
        param.trainTime = etDeclareTrainTotalTime.text.toString() ?: ""

        //姓名
        param.userName = tvDeclareName.text.toString() ?: ""




        LogUtil.e(TAG, "个人申报申请: DeclareParam = \n $param")


        showLoadingDialog(tipContent = "正在上传数据...")
        //发送请求
        OkGoManager.instance.okGoRequestManage(
            com.tecsun.jc.demo.invigilation.zhanjiang.constant.Constants.URL_SAVE_DECLARE, param
            , DeclareResultEntity::class.java, object : OkGoRequestCallback<DeclareResultEntity> {
                override fun onSuccess(t: DeclareResultEntity) {
                    dismissLoadingDialog()
                    if (t != null && t.isSuccess) {
                        ResultSuccessTipsBuilder.showSuccessDialog(personDeclareActivity)
                    } else {
                        LogUtil.e(TAG,">>>>>>>>>.5")
                        showErrorDialog(t?.message ?: "")
                    }
                }

                override fun onError(throwable: Throwable?) {
                    dismissLoadingDialog()
                    LogUtil.e(TAG,">>>>>>>>>.6")
                    showErrorDialog(throwable?.toString() ?: "")
                }
            })
    }


    private var sfzhPic: String? = ""
    fun uploadSFZHPic() {
        if (!sfzhPic.isNullOrBlank()) {
            //直接上传数据,因为图片已经有了
            declareNow()
            return
        }
        showLoadingDialog(tipContent = "正在上传照片...")
        var param = UploadPicParam()
        param.sfzh = JinLinApp.studentDetailsBean2?.sfzh ?: ""
        param.name = JinLinApp.studentDetailsBean2?.name ?: ""
        param.picBase64 = BitmapUtils2.bitmapToBase64(
            StudentOwnSFZImageBuilder.getSFZBitmap(
                JinLinApp.studentDetailsBean2?.sfzh ?: ""
            )
        )

        OkGoManager.instance.okGoRequestManage(
            com.tecsun.jc.demo.invigilation.zhanjiang.constant.Constants.URL_UPLOAD_PICTURE, param
            , UploadPicEntity::class.java, object : OkGoRequestCallback<UploadPicEntity> {
                override fun onSuccess(t: UploadPicEntity) {
                    dismissLoadingDialog()
                    if (t != null && t.statusCode == "200" && t.data != null && t.data.picId != null) {
                        sfzhPic = t.data.picId.toString()
                        //下一步
                        declareNow()
                    }
                    else{
                        showErrorDialog(t?.message?:"")
                    }
                    LogUtil.e(">>>>>>>>>>>>>>>>> onSuccess $t")
                }

                override fun onError(throwable: Throwable?) {
                    dismissLoadingDialog()
                    LogUtil.e(TAG,">>>>>>>>>.7")
                    showErrorDialog(throwable?.toString() ?: "")
                }
            })
    }


    /**
     * 重新提交
     */
    override fun resubmit() {
        super.resubmit()
        checkInfo()
    }

    private fun showErrorDialog(str: String) {
        ResultFailTipsBuilder.showFailDialog(personDeclareActivity, str ?: "")
    }

}





















