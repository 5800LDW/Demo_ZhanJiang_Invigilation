package com.tecsun.jc.demo.invigilation.zhanjiang.collect_data

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.alibaba.android.arouter.facade.annotation.Route
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.ShowInfoBean
import com.tecsun.jc.base.bean.filter.IFilter
import com.tecsun.jc.base.builder.ResultSuccessTipsBuilder
import com.tecsun.jc.base.builder.StudentOwnSFZImageBuilder
import com.tecsun.jc.base.builder.ZhanJiangRegisterSFZImageBuilder
import com.tecsun.jc.base.builder.ZhanJiangRegisterSFZImageBuilder.CONST_NEGATIVE_PIC_ID
import com.tecsun.jc.base.builder.ZhanJiangRegisterSFZImageBuilder.CONST_POSITIVE_PIC_ID
import com.tecsun.jc.base.collector.BaseActivityCollector
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.listener.IEvents
import com.tecsun.jc.base.listener.IEvents2
import com.tecsun.jc.base.listener.OkGoRequestCallback
import com.tecsun.jc.base.utils.BaseRegexUtil
import com.tecsun.jc.base.utils.OkGoManager
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.SingleClickListener
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.ui.FilterItemActivity
import com.tecsun.jc.demo.invigilation.util.BitmapUtils2
import com.tecsun.jc.demo.invigilation.zhanjiang.bean.DeclareResultEntity
import com.tecsun.jc.demo.invigilation.zhanjiang.bean.TrainRegisterParam
import com.tecsun.jc.demo.invigilation.zhanjiang.bean.UploadPicEntity
import com.tecsun.jc.demo.invigilation.zhanjiang.bean.UploadPicParam
import com.tecsun.jc.demo.invigilation.zhanjiang.builder.DictionariesInfoBuilder
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
@Route(path = RouterHub.ROUTER_APP_STUDENT_REGISTER_CONFIRM)
class StudentRegisterConfirmActivity : BaseActivity() {

    private val TAG = StudentRegisterConfirmActivity::class.java.simpleName

    var tParam: TrainRegisterParam? = null

    private var positivePicId = ""

    private var negativePicId = ""

    private val studentRegisterConfirmActivity = this

    override fun getLayoutId(): Int {
        return R.layout.app_activity_student_regiser
    }

    override fun setTitleBar(titleBar: TitleBar) {
        super.setTitleBar(titleBar)
        titleBar?.setTitle("确认信息")
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)


        var p = intent?.getSerializableExtra(BaseConstant.KEY_PARAM)
        if (p != null) {
            var param = p as TrainRegisterParam
            tParam = param
            param?.let {

                //现住地址 address
                etAddress.setText(param.address ?: "")

                //出生年月 birth
                tvRegisterBirth.text = param.birth ?: ""

                //证件号码 certNo
                tvRegisterSfzh.text = param.certNo ?: ""

                //现有技术等级、职业资格证书,数据字典编码：TELV
                tvRegisterCertificate.text = param.certificate ?: ""

                //拥有证书的工种 certificateType
                etProfession.setText(param.certificateType ?: "")

                //createTime	string

                //户籍地址  domicile
                etResidenceAddress.setText(param.domicile ?: "")

                //学历,数据字典编码：EDU  educatio
                tvRegisterCulture.text = param.education ?: ""

                //就业意向,1:是 0:否 employmentIntent
                tvRegisterPurpose.text = param.employmentIntent ?: ""

                //目前状态,数据字典编码：TRAIN_JOB_STATUS employmentStatus
                tvRegisterState.text = param.employmentStatus ?: ""

                //就业意愿,1:是 0:否 employmentWish
                tvEmploymentIntention.text = param.employmentWish ?: ""

                //性别,1:男 0:女 gender
                tvRegisterSex.text = param.gender ?: ""

                //报名参加培训级别,数据字典编码：TELV_TYPE
                tvRegisterRank.text = param.jobTrainLevel ?: ""

                //报名参加培训工种 jobTrainType
                etTrainProfession.setText(param.jobTrainType ?: "")

                //联系电话 mobile
                etRegisterPhone.setText(param.mobile ?: "")

                //民族 nation
                tvNation.text = param.nation ?: ""

                // 调用上传照片接口返回的id picDown

                //调用上传照片接口返回的id picUp

                // trainId	integer($int32)

                //培训机构 trainOrg
                etCultivate.setText(param.trainOrg ?: "")

                //updateTime	string

                //userName 姓名
                tvRegisterName.text = param.userName ?: ""
            }
        }

        viewHide(
            arrayOf(
                ivRegister1,
                ivRegister2,
                ivRegister3,
                ivRegister4,
                ivRegister5,
                ivRegister6,
                ivRegister7,
                ivRegister8,
                ivRegister9,
                ivRegister10,
                ivRegister11,
                ivRegister12
            )
        )


        notClick(
            arrayOf(
                etAddress,
                etProfession,
                etResidenceAddress,
                etTrainProfession,
                etRegisterPhone,
                etCultivate
            )
        )

        ivRegisterNegative.setImageBitmap(
            ZhanJiangRegisterSFZImageBuilder.getSFZBitmap(
                CONST_NEGATIVE_PIC_ID
            )
        )
        ivRegisterPositive.setImageBitmap(
            ZhanJiangRegisterSFZImageBuilder.getSFZBitmap(
                CONST_POSITIVE_PIC_ID
            )
        )

        tvRegisterTips.text = "为了更好的了解及服务你的培训需求，请您认真核对以下信息"

        llRegisterContent01.isClickable = false
        llRegisterContent01.isFocusable = false
        llRegisterContent02.visibility = View.VISIBLE
        btNext.text = "确认提交"
        btNext.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                checkInfo()
            }
        })
    }


    private fun notClick(et: Array<EditText?>) {
        for (i in et.indices) {
            et[i]?.isClickable = false
            et[i]?.isFocusable = false
        }
    }

    private fun viewHide(vL: Array<View?>) {
        for (i in vL.indices) {
            vL[i]?.visibility = View.INVISIBLE
        }
    }


    private fun checkInfo() {
        uploadNegativePic()
    }

    private fun submitInfo() {

        //现住地址 address
//        tParam?.address = etAddress.text.toString()

        //出生年月 birth
        tParam?.birth = tvRegisterBirth.text.toString()
            .replaceFirst("-", "年")
            .replaceFirst("-", "月") + "日"

        //证件号码 certNo
//        tParam?.certNo = tvRegisterSfzh.text.toString()

        //现有技术等级、职业资格证书,数据字典编码：TELV
//        tParam?.certificate = tvRegisterCertificate.text.toString()
        //现有技术等级、职业资格证书,数据字典编码：TELV
        if (DictionariesInfoBuilder.data_TELV.size == 0
//            || !DictionariesInfoBuilder.data_TELV.contains(tvRegisterCertificate.text?.toString() ?: "")
        ) {
            //跳转到取获取字典
            DictionariesInfoBuilder.getTELV(object : IEvents2 {
                override fun biz() {
                    resubmit()
                }

                override fun failBiz(str: String) {
                    dismissLoadingDialog()
                    LogUtil.e(TAG, ">>>>>>>>>.1")
                    showErrorDialog(str)
                }
            })
            return
        }
        //现有技术等级、职业资格证书,数据字典编码：TELV
        if (DictionariesInfoBuilder.data_TELV.contains(
                tvRegisterCertificate.text?.toString() ?: ""
            )
        ) {
            tParam?.certificate =
                DictionariesInfoBuilder.data_TELV[tvRegisterCertificate.text.toString()]
            LogUtil.e(
                TAG,
                ">>>>>>>>>> ${tvRegisterCertificate.text?.toString() ?: ""}  : ${tParam?.certificate}"
            )
        } else {
            tParam?.certificate = ""
        }


        //拥有证书的工种 certificateType
//        tParam.certificateType = etProfession.text.toString()

        //createTime	string

        //户籍地址  domicile
//        param.domicile = etResidenceAddress.text.toString()


        //学历,数据字典编码：EDU  education
//        tParam?.education = tvRegisterCulture.text.toString()
        //学历,数据字典编码：EDU  education

        LogUtil.e(TAG, "******************* 1 ")

        LogUtil.e(TAG, "******************* tvRegisterCulture.text = ${tvRegisterCulture.text}")


        if (DictionariesInfoBuilder.data_EDU.size == 0
//            || !DictionariesInfoBuilder.data_EDU.contains(tvRegisterCulture.text?.toString() ?: "")
        ) {
            //跳转到取获取字典
            DictionariesInfoBuilder.getEDU(object : IEvents2 {
                override fun biz() {
                    resubmit()
                }

                override fun failBiz(str: String) {
                    dismissLoadingDialog()
                    LogUtil.e(TAG, ">>>>>>>>>.1")
                    showErrorDialog(str)
                }
            })
            return
        }
        //学历,数据字典编码：EDU  education
        if (DictionariesInfoBuilder.data_EDU.contains(tvRegisterCulture.text?.toString() ?: "")) {
            tParam?.education = DictionariesInfoBuilder.data_EDU[tvRegisterCulture.text.toString()]

            LogUtil.e(
                TAG,
                ">>>>>>>>>>EDU ${tvRegisterCulture.text?.toString() ?: ""}  : ${tParam?.education}"
            )
        } else {
            tParam?.education = ""
        }




        LogUtil.e(TAG, "******************* 2 ")



        //就业意向,1:是 0:否 employmentIntent
        tParam?.employmentIntent = if (tvRegisterPurpose.text.toString().contains("是")) "1" else "0"







        //目前状态,数据字典编码：TRAIN_JOB_STATUS employmentStatus
//        tParam.employmentStatus = tvRegisterState.text.toString()
        //目前状态,数据字典编码：TRAIN_JOB_STATUS employmentStatus
        if (DictionariesInfoBuilder.data_TRAIN_JOB_STATUS.size == 0
//            || !DictionariesInfoBuilder.data_TRAIN_JOB_STATUS.contains(tvRegisterState.text?.toString() ?: "")
        ) {
            //跳转到取获取字典
            DictionariesInfoBuilder.getTRAIN_JOB_STATUS(object : IEvents2 {
                override fun biz() {
                    resubmit()
                }

                override fun failBiz(str: String) {
                    dismissLoadingDialog()
                    LogUtil.e(TAG, ">>>>>>>>>.1")
                    showErrorDialog(str)
                }
            })
            return
        }
        //目前状态,数据字典编码：TRAIN_JOB_STATUS employmentStatus
        if (DictionariesInfoBuilder.data_TRAIN_JOB_STATUS.contains(tvRegisterState.text?.toString() ?: "")) {
            tParam?.employmentStatus = DictionariesInfoBuilder.data_TRAIN_JOB_STATUS[tvRegisterState.text.toString()]
            LogUtil.e(
                TAG,
                ">>>>>>>>>> ${tvRegisterState.text?.toString() ?: ""}  : ${tParam?.employmentStatus}"
            )
        } else {
            tParam?.employmentStatus = ""
        }


        //就业意愿,1:是 0:否 employmentWish
        tParam?.employmentWish = if(tvEmploymentIntention.text.toString().contains("是")) "1" else "0"


        //性别,1:男 0:女 gender
        tParam?.gender = if(tvRegisterSex.text.toString().contains("男")) "1" else "0"


        //报名参加培训级别,数据字典编码：TELV_TYPE
//        param.jobTrainLevel = tvRegisterRank.text.toString()
        //报名参加培训级别,数据字典编码：TELV_TYPE
        if (DictionariesInfoBuilder.data_TELV_TYPE.size == 0
//            || !DictionariesInfoBuilder.data_TELV_TYPE.contains(tvRegisterRank.text?.toString() ?: "")
        ) {
            //跳转到取获取字典
            DictionariesInfoBuilder.getTELV_TYPE(object : IEvents2 {
                override fun biz() {
                    resubmit()
                }

                override fun failBiz(str: String) {
                    dismissLoadingDialog()
                    LogUtil.e(TAG, ">>>>>>>>>.1")
                    showErrorDialog(str)
                }
            })
            return
        }
        //报名参加培训级别,数据字典编码：TELV_TYPE
        if (DictionariesInfoBuilder.data_TELV_TYPE.contains(tvRegisterRank.text?.toString() ?: "")) {
            tParam?.jobTrainLevel = DictionariesInfoBuilder.data_TELV_TYPE[tvRegisterRank.text.toString()]
            LogUtil.e(
                TAG,
                ">>>>>>>>>> ${tvRegisterRank.text?.toString() ?: ""}  : ${tParam?.jobTrainLevel}"
            )
        } else {
            tParam?.jobTrainLevel = ""
        }

        //调用上传照片接口返回的id
        tParam?.picDown = negativePicId

        //调用上传照片接口返回的id
        tParam?.picUp = positivePicId



        //报名参加培训工种 jobTrainType
//        param.jobTrainType = etTrainProfession.text.toString()

        //联系电话 mobile
//        param.mobile = etRegisterPhone.text.toString()

        //民族 nation
//        param.nation = tvNation.text.toString()

        // 调用上传照片接口返回的id picDown

        //调用上传照片接口返回的id picUp

        // trainId	integer($int32)

        //培训机构 trainOrg
//        param.trainOrg = etCultivate.text.toString()

        //updateTime	string

        //userName 姓名
//        param.userName = tvRegisterName.text.toString()



        LogUtil.e(TAG, "学员登记: TrainRegisterParam = \n $tParam")
        showLoadingDialog(tipContent = "正在上传数据...")
        //发送请求
        OkGoManager.instance.okGoRequestManage(
            com.tecsun.jc.demo.invigilation.zhanjiang.constant.Constants.URL_STUDENT_REGISTER, tParam!!
            , DeclareResultEntity::class.java, object : OkGoRequestCallback<DeclareResultEntity> {
                override fun onSuccess(t: DeclareResultEntity) {
                    dismissLoadingDialog()
                    if (t != null && t.isSuccess) {
                        ResultSuccessTipsBuilder.showSuccessDialog(studentRegisterConfirmActivity,
                            IEvents {
                                BaseActivityCollector.finishAllActivity()
                            },info = "学员登记成功")
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

    /**
     * 人像面
     */
    fun uploadNegativePic() {
        if (!negativePicId.isNullOrBlank()) {
            //直接上传数据,因为图片已经有了
            uploadPositivePic()
            return
        }
        showLoadingDialog(tipContent = "正在上传人像面照片...")
        var param = UploadPicParam()
        param.sfzh = tParam?.certificate ?: ""
        param.name = tParam?.userName ?: ""
        param.picBase64 = BitmapUtils2.bitmapToBase64(
            ZhanJiangRegisterSFZImageBuilder.getSFZBitmap(
                CONST_NEGATIVE_PIC_ID
            )
        )

        OkGoManager.instance.okGoRequestManage(
            com.tecsun.jc.demo.invigilation.zhanjiang.constant.Constants.URL_UPLOAD_PICTURE, param
            , UploadPicEntity::class.java, object : OkGoRequestCallback<UploadPicEntity> {
                override fun onSuccess(t: UploadPicEntity) {
                    dismissLoadingDialog()
                    if (t != null && t.statusCode == "200" && t.data != null && t.data.picId != null) {
                        negativePicId = t.data.picId.toString()
                        //下一步 国徽面
                        uploadPositivePic()
                    } else {
                        showErrorDialog(t?.message ?: "")
                    }
                    LogUtil.e(">>>>>>>>>>>>>>>>> onSuccess $t")
                }

                override fun onError(throwable: Throwable?) {
                    dismissLoadingDialog()
                    LogUtil.e(TAG, ">>>>>>>>>.7")
                    showErrorDialog(throwable?.toString() ?: "")
                }
            })
    }


    /**
     * 国徽面
     */
    fun uploadPositivePic() {
        if (!positivePicId.isNullOrBlank()) {
            //直接上传数据,因为图片已经有了
            submitInfo()
            return
        }
        showLoadingDialog(tipContent = "正在上传国徽面照片...")
        var param = UploadPicParam()
        param.sfzh = tParam?.certificate ?: ""
        param.name = tParam?.userName ?: ""
        param.picBase64 = BitmapUtils2.bitmapToBase64(
            ZhanJiangRegisterSFZImageBuilder.getSFZBitmap(
                CONST_POSITIVE_PIC_ID
            )
        )

        OkGoManager.instance.okGoRequestManage(
            com.tecsun.jc.demo.invigilation.zhanjiang.constant.Constants.URL_UPLOAD_PICTURE, param
            , UploadPicEntity::class.java, object : OkGoRequestCallback<UploadPicEntity> {
                override fun onSuccess(t: UploadPicEntity) {
                    dismissLoadingDialog()
                    if (t != null && t.statusCode == "200" && t.data != null && t.data.picId != null) {
                        positivePicId = t.data.picId.toString()
                        //下一步 上传数据
                        submitInfo();
                    } else {
                        showErrorDialog(t?.message ?: "")
                    }
                    LogUtil.e(">>>>>>>>>>>>>>>>> onSuccess $t")
                }

                override fun onError(throwable: Throwable?) {
                    dismissLoadingDialog()
                    LogUtil.e(TAG, ">>>>>>>>>.7")
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

}

































