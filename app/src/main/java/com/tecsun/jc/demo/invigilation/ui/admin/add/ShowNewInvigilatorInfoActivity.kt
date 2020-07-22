package com.tecsun.jc.demo.invigilation.ui.admin.add

import android.os.Bundle
import android.os.Handler
import com.alibaba.android.arouter.facade.annotation.Route
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.ProctorDetailsBean
import com.tecsun.jc.base.bean.db.invigilation.bean.ReadCardInfoBean
import com.tecsun.jc.base.builder.ReadCardImageBuilder
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import kotlinx.android.synthetic.main.activity_show_new_inivigalator_info.*
import kotlinx.android.synthetic.main.idmessage_main.*
import org.litepal.LitePal

/**
 *
 * 显示需要添加的新的老师的信息
 *
 */
@Route(path = RouterHub.ROUTER_APP_Show_New_Invigilator_Info)
class ShowNewInvigilatorInfoActivity : BaseActivity() {


    var readCardInfoBean:ReadCardInfoBean? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_show_new_inivigalator_info
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle("确认信息")
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        var bean = intent.getSerializableExtra(BaseConstant.FILTER_SELECT_DATA)
        if(bean!=null && bean is ReadCardInfoBean){
            readCardInfoBean = bean
            person_name.text = bean.name?.trim()
            person_gender.text = bean.sex?.trim()
            person_nation.text = bean.nation?.trim()
            person_birth.text = bean.birthday
            person_address.text = bean.address
            person_idCard.text = bean.number
            person_authority.text = bean.qianfa
            person_durationValidity.text = bean.effdate

            if(!bean.number.isNullOrBlank()){
                //显示图片
                var bitmap = ReadCardImageBuilder.getSFZBitmap(bean.number)
                bitmap?.let {
                    person_headImage.setImageBitmap(bitmap)
                }
            }
        }

        btn_cancel.setOnClickListener{
            finish()
        }

        btConfirm.setOnClickListener{

            if(readCardInfoBean == null || readCardInfoBean!!.number.isNullOrBlank()){
                showToast("保存失败，当前没有有效数据!")
                return@setOnClickListener
            }


            showLoadingDialog(tipContent = "正在处理...")

            var count = LitePal.where("sfzh = ?",readCardInfoBean?.number?:"").count(ProctorDetailsBean::class.java)
            if(count !=0){
                Handler().postDelayed({
                    dismissLoadingDialog()
                    showErrorMessageDialog("保存失败，数据在本地已存在!")
                },1000)
                return@setOnClickListener
            }


            var proctorDetailsBean = ProctorDetailsBean()
            proctorDetailsBean.name = readCardInfoBean?.name?:""
            proctorDetailsBean.sfzh = readCardInfoBean?.number?:""
            proctorDetailsBean.nation = readCardInfoBean?.nation?:""
            proctorDetailsBean.birthday = readCardInfoBean?.birthday?:""
            proctorDetailsBean.address = readCardInfoBean?.address?:""
            proctorDetailsBean.qianfa = readCardInfoBean?.qianfa?:""
            proctorDetailsBean.effdate = readCardInfoBean?.effdate?:""
            proctorDetailsBean.sex = readCardInfoBean?.sex?:""

            var b = proctorDetailsBean.save()
            if(b){
                Handler().postDelayed({
                    dismissLoadingDialog()
                    showSuccessMessageDialog("添加成功!")
                },1000)
            }
            else{
                Handler().postDelayed({
                    dismissLoadingDialog()
                    showErrorMessageDialog("添加失败!")
                },1000)
            }

        }
    }


}
































