package com.tecsun.jc.demo.invigilation.ui.admin.download

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.ProctorDetailsBean
import com.tecsun.jc.base.bean.db.invigilation.bean.StudentDetailsBean
import com.tecsun.jc.base.bean.db.invigilation.bean.TestingDetailsBean
import com.tecsun.jc.base.bean.filter.IFilter
import com.tecsun.jc.base.builder.StudentOwnImageBuilder
import com.tecsun.jc.base.common.BaseConstant.FILTER_LIST_DATA
import com.tecsun.jc.base.common.BaseConstant.FILTER_SELECT
import com.tecsun.jc.base.common.BaseConstant.FILTER_SELECT_DATA
import com.tecsun.jc.base.dialog.DialogUtils
import com.tecsun.jc.base.utils.JavaStringTool
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.builder.BasicDataBuilder
import com.tecsun.jc.demo.invigilation.ui.FilterItemActivity
import com.tecsun.jc.demo.invigilation.ui.student.StudentInfoShowActivity
import com.tecsun.jc.register.util.constant.Const
import kotlinx.android.synthetic.main.activity_admin_download.*
import org.litepal.LitePal
import java.io.Serializable
import java.util.*

class DownloadInfoActivity : BaseActivity(), BaseQuickAdapter.OnItemClickListener {
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (position >= 0 && position < studentInfoListForAdapter?.size ?: 0) {
            LogUtil.e(studentInfoListForAdapter?.get(position))
            val intent = Intent(this, StudentInfoShowActivity::class.java)
            intent.putExtra(FILTER_SELECT, studentInfoListForAdapter?.get(position) as Serializable)
            startActivity(intent)
        }
    }

    private val EXAMINER_INFO_CODE = Const.EXAMINER_INFO_CODE
    private val EXAMINATION_ROOM_INFO_CODE = Const.EXAMINATION_ROOM_INFO_CODE
    private val examinerInfoList: LinkedList<IFilter> = LinkedList()
    private val examinationRoomInfoList: LinkedList<IFilter> = LinkedList()
    private val ROOM_1 = Const.ROOM_1
    private val ROOM_2 = Const.ROOM_2
    private val studentInfoList1: LinkedList<StudentDetailsBean> = LinkedList()
    private val studentInfoList2: LinkedList<StudentDetailsBean> = LinkedList()
    private val studentInfoListOther: LinkedList<StudentDetailsBean> = LinkedList()
    private val studentInfoListForAdapter: LinkedList<StudentDetailsBean> = LinkedList()

    override fun getLayoutId(): Int {
        return R.layout.activity_admin_download
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle(R.string.base_download_info)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setExaminerInfo()
        setExaminationRoom()
        setStudentInfo()

        rl01.setOnClickListener {
            getExaminerInfo()
        }
        rl02.setOnClickListener {
            getExaminationRoomInfo()
        }
        btnNextStep.setOnClickListener {

            DialogUtils.showDialog(
                this@DownloadInfoActivity, "", resources.getColor(R.color.c_e60012), "确定进行绑定?",
                R.string.app_sure, R.string.app_cancel,
                DialogInterface.OnClickListener { _, _ ->
                    band()
                },
                DialogInterface.OnClickListener { _, which -> })

        }
    }

    private fun band() {
        if (tvExaminer.text.isNullOrBlank()) {
            showToast(getString(R.string.app_please_select_examiner))
            return
        }

        if (tvVenue.text.isNullOrBlank()) {
            showToast(getString(R.string.app_please_select_venue))
            return
        }

//            var count = DBFunctionUtil.count(ProctorDetailsBean::class.java)
//            if (count > 0) {
//                showToast("本地已经存在绑定数据, 请到上个界面进行初始化!")
//                return@setOnClickListener
//            }


        showLoadingDialog(tipContent = "正在处理...")
//            var b = false
//            for (item in examinerInfoList) {
//                if (item is ProctorDetailsBean && item.name == tvExaminer.text.toString()) {
//
//                    item.schoolName = tvVenue.text.toString()
//                    b = item.save()
//                }
//            }
//            if (b) {
//                for (item in examinationRoomInfoList) {
//                    if (item is TestingDetailsBean && item.schoolName == tvVenue.text.toString()) {
//                        b = item.save()
//                    }
//                }
//            }


        if (!tvVenue.text.isNullOrBlank() && tvVenue.text == ROOM_1) {
            for (item in studentInfoListForAdapter) {
                val values = ContentValues()
                /***更新考生的监考老师名称*/
                values.put("invigilator", tvExaminer.text.toString())
                var updateCount = LitePal.updateAll(
                    StudentDetailsBean::class.java,
                    values,
                    "sfzh = ? ",
                    item.sfzh
                )

                LogUtil.e(">>>>>>>>>>> 更新的数据条数:$updateCount")
                // 更新不成功就保存
                if (updateCount == 0) {
                    /**更新不成功就进行保存*/
                    item.invigilator = tvExaminer.text.toString()
                    item.save()
                }
            }
        }
        //保存当前考场
        for (item in examinationRoomInfoList) {
            if (item is TestingDetailsBean && item.schoolName == tvVenue.text.toString()) {
                var saveCount = LitePal.where("schoolName = ?", item.schoolName)
                    .count(TestingDetailsBean::class.java)
                LogUtil.e(">>>>>>>>>>>>>>> 当前记录的考点:${item.schoolName} 有$saveCount 条")
                if (saveCount == 0) {
                    item.save()
                }
                LogUtil.e(">>>>>>>>>>>>>>> 当前记录的考点:${item.schoolName} 有$saveCount 条")
            }
        }
        //更新监考老师监考的考场
        for (item in examinerInfoList) {
            //监考老师名字一致
            if (item is ProctorDetailsBean && item.name == tvExaminer.text.toString()) {

                val values = ContentValues()
                /***更新考生的监考老师名称*/
                values.put("schoolName",tvVenue.text.toString())
                var updateCount = LitePal.updateAll(
                    ProctorDetailsBean::class.java,
                    values,
                    "sfzh = ? ",
                    item.sfzh
                )

                LogUtil.e(">>>>>>>>>>> 更新的数据条数:$updateCount")
                if (updateCount == 0) {
                    /**更新不成功就进行保存*/
                    item.schoolName = tvVenue.text.toString()
                    item.save()
                }
            }
        }




//       if (b) {
        Handler().postDelayed({
            dismissLoadingDialog()
//            showToast("绑定成功!")
            showSuccessMessageDialog("绑定成功!")

        }, 3000)
//            } else {
//                dismissLoadingDialog()
//                showToast("保存失败!")
//            }
    }


    private fun getExaminerInfo() {
        val intent = Intent(this, FilterItemActivity::class.java)
        intent.putExtra(FILTER_SELECT, EXAMINER_INFO_CODE)
        intent.putExtra(FILTER_LIST_DATA, examinerInfoList as Serializable)
        startActivityForResult(intent, EXAMINER_INFO_CODE)
    }

    private fun getExaminationRoomInfo() {
        val intent = Intent(this, FilterItemActivity::class.java)
        intent.putExtra(FILTER_SELECT, EXAMINATION_ROOM_INFO_CODE)
        intent.putExtra(FILTER_LIST_DATA, examinationRoomInfoList as Serializable)
        startActivityForResult(intent, EXAMINATION_ROOM_INFO_CODE)
    }

    private fun setExaminerInfo() {
        examinerInfoList.clear()
        examinerInfoList.addAll(BasicDataBuilder.getExaminerInfo())
    }


    private fun setExaminationRoom() {
        if (BasicDataBuilder.getExaminationRoom().size != 0) {
            examinationRoomInfoList.clear()
            examinationRoomInfoList.addAll(BasicDataBuilder.getExaminationRoom())
        }
    }

    private fun setStudentInfo() {
        studentInfoList1.clear()
        studentInfoList1.addAll(BasicDataBuilder.getStudentInfoList1())
        studentInfoList2.clear()
        studentInfoList2.addAll(BasicDataBuilder.getStudentInfoList2())
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        LogUtil.e("requestCode :$requestCode")
        LogUtil.e("resultCode :$resultCode")

        when (resultCode) {
            EXAMINER_INFO_CODE -> {
                var item = data?.getSerializableExtra(FILTER_SELECT_DATA)
                val bean =
                    if (item != null) {
                        item as ProctorDetailsBean
                    } else {
                        null
                    }
                bean?.let {
                    tvExaminer.text = bean.name
                }


            }
            EXAMINATION_ROOM_INFO_CODE -> {
                var item = data?.getSerializableExtra(FILTER_SELECT_DATA)
                val district =
                    if (item != null) {
                        item as TestingDetailsBean
                    } else {
                        null
                    }

                district?.let {
                    tvVenue.text = district.schoolName

                    if (district.schoolName == ROOM_1) {
                        showNewData(studentInfoList1)
                    } else if (district.schoolName == ROOM_2) {
                        showNewData(studentInfoList2)
                    }
                    else{
                        showLoadingDialog(tipContent = "处理中...")
                        studentInfoListOther.clear()
                        var list = BasicDataBuilder.getStudentInfoListOther(district.schoolName)
                        if(!list.isNullOrEmpty()){
                            studentInfoListOther.addAll(list)
                        }
                        showNewData(studentInfoListOther)
                        dismissLoadingDialog()
                    }
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun showNewData(list: LinkedList<StudentDetailsBean>) {
        if (rv_filter_list_view.adapter == null) {
            rv_filter_list_view.layoutManager = LinearLayoutManager(this)
            studentInfoListForAdapter.addAll(list)
            val adapter = Adapter(studentInfoListForAdapter)
            adapter.onItemClickListener = this
            rv_filter_list_view.adapter = adapter

            adapter.loadMoreComplete()
        } else {
            studentInfoListForAdapter.clear()
            studentInfoListForAdapter.addAll(list)
            rv_filter_list_view.adapter?.notifyDataSetChanged()
        }

    }


    class Adapter(data: List<StudentDetailsBean>?) :
        BaseQuickAdapter<StudentDetailsBean, BaseViewHolder>(
            R.layout.app_item_examinee, data
        ) {
        override fun convert(helper: BaseViewHolder?, item: StudentDetailsBean?) {
            if (item != null) {


                if (!item.sfzh.isNullOrBlank()) {
                    //显示身份证
                    helper?.getView<TextView>(R.id.tv_mine_user_sfzh)?.text =
                        JavaStringTool.replaceIdNum(item.sfzh)

                    //显示图片
                    var bitmap = StudentOwnImageBuilder.getSFZBitmap(item.sfzh)
                    if (bitmap != null) {
                        helper?.getView<ImageView>(R.id.iv_mine_avatar)?.setImageBitmap(bitmap)
                    } else {
                        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>. bitmap = null")
                        helper?.getView<ImageView>(R.id.iv_mine_avatar)?.setImageBitmap(
                            BitmapFactory.decodeResource(
                                JinLinApp.context!!.getResources(),
                                R.drawable.ic_default_avatar
                            )
                        )
                    }
                } else {
                    LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>. bitmap = null")
                    helper?.getView<ImageView>(R.id.iv_mine_avatar)?.setImageBitmap(
                        BitmapFactory.decodeResource(
                            JinLinApp.context!!.getResources(),
                            R.drawable.ic_default_avatar
                        )
                    )
                }


                //显示名字
                if (!item.name.isNullOrBlank()) {
                    helper?.getView<TextView>(R.id.tv_mine_user_name)?.text =
                        item.name!!
                }

//                var testingDetailsBean = DBFunctionUtil.findFirstTestingRoom()
//                testingDetailsBean?.let {
//                    helper?.getView<TextView>(R.id.tv_mine_user_card)?.text =
//                        testingDetailsBean.schoolName ?: ""
//                }
            }
        }
    }


}



















