package com.tecsun.jc.demo.invigilation.zhanjiang.collect_data

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.ShowInfoBean
import com.tecsun.jc.base.bean.filter.IFilter
import com.tecsun.jc.base.builder.StudentOwnSFZImageBuilder
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.common.OtherConstant
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.utils.time.TimeUtil
import com.tecsun.jc.base.widget.SingleClickListener
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.base.widget.builder.TimePickerBuilder
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.ui.FilterItemActivity
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

    private var timeDeclareTrainTime: TimePickerBuilder? = null

    private var timeDeclareCertificate: TimePickerBuilder? = null


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

        tvDeclareTrainTime.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                timeDeclareTrainTime?.showView()
            }
        })

        ivDeclareTrainTime.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                timeDeclareTrainTime?.showView()
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

        timeDeclareTrainTime = TimePickerBuilder()
            .setActivity(this)
            .setDefaultDate(endDate)
            .setTitleText("选择培训时间")
            //.setDefaultDate(TimeUtil.getCurrentDateAccordingToFormat(OtherConstant.DEFAULT_FORMAT))
            .setListener { date, v ->

                timeDeclareProfessionEnd?.defaultDate = SimpleDateFormat(
                    OtherConstant.DEFAULT_FORMAT,
                    Locale.getDefault()
                ).format(date)
                tvDeclareTrainTime.text = timeDeclareProfessionEnd?.defaultDate

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
            LogUtil.e(TAG,"d1 = $d1")
            LogUtil.e(TAG,"d2 = $d2")
            val diff = d1.time - d2.time
            return diff > 0
        } catch (e: Exception) {
            LogUtil.e(e)
        }
        return false
    }
}





















