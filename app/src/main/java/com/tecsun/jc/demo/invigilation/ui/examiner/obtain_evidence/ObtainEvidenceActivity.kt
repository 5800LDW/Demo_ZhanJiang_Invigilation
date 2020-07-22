package com.tecsun.jc.demo.invigilation.ui.examiner.obtain_evidence

import android.content.ContentValues
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
import com.tecsun.jc.base.builder.StudentOwnImageBuilder
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.utils.JavaStringTool
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.builder.BasicDataBuilder
import com.tecsun.jc.register.util.constant.Const
import kotlinx.android.synthetic.main.activity_admin_download.rv_filter_list_view
import kotlinx.android.synthetic.main.activity_student_obtain_evidence.*
import org.litepal.LitePal
import java.io.Serializable
import java.util.*


/***考场内取证*/
class ObtainEvidenceActivity : BaseActivity(), BaseQuickAdapter.OnItemClickListener {
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        //TODO
        if (position < studentInfoListForAdapter.size) {
//            var item = studentInfoListForAdapter.get(position)
//
//            //无法比对
//            if (item == null || item.sfzh.isNullOrBlank() || StudentOwnImageBuilder.getSFZBitmap(
//                    item.sfzh
//                ) == null
//            ) {
//                showToast("该考生没有图片数据，无法进行审核！")
//                return
//            }
//
//            //进行比对
//            val intent = Intent(this, ShowStudentPicActivity::class.java)
//            intent.putExtra(BaseConstant.FILTER_SELECT, item)
//            intent.putExtra(BaseConstant.FILTER_SELECT_POSITION, position)
//            startActivityForResult(intent, Const.EXAMINATION_ROOM_INFO_CODE)


            if (position >= 0 && position < studentInfoListForAdapter?.size ?: 0) {
                LogUtil.e(studentInfoListForAdapter?.get(position))
                val intent = Intent(this, ObtainEvidence2TakePicActivity::class.java)
                intent.putExtra(BaseConstant.FILTER_SELECT_POSITION, position)
                intent.putExtra(
                    BaseConstant.FILTER_SELECT,
                    studentInfoListForAdapter?.get(position) as Serializable
                )
                startActivityForResult(intent, Const.EXAMINATION_ROOM_INFO_CODE)
            }
        }
    }

    private val studentInfoListForAdapter: LinkedList<StudentDetailsBean> = LinkedList()


    override fun getLayoutId(): Int {
        return R.layout.activity_student_obtain_evidence
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle(getString(R.string.app_student_obtain_evidence))
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        if (JinLinApp.loginExaminer?.number == null) {
            finish()
            return
        }


        getData()


        rv_filter_list_view.layoutManager = LinearLayoutManager(this)
        val adapter = Adapter(studentInfoListForAdapter)
        adapter.onItemClickListener = this
        rv_filter_list_view.adapter = adapter

        adapter.loadMoreComplete()


        btConfirmObtainEvidence.setOnClickListener {

            var count = LitePal.where("isTake2PicAR = ? and isUploadAR = ?","1","0")
                .count(StudentDetailsBean::class.java)
            if (count == 0) {
                showToast("没有已取证的未上传数据, 请进行取证!")
                return@setOnClickListener
            }

            var startTime = System.currentTimeMillis()
            showLoadingDialog(tipContent = "正在处理，请稍候．．．")
            for ((index, item) in studentInfoListForAdapter.withIndex()) {
                if (item.isTake2PicAR != null && item.isUploadAR != null) {
                    if (item.isTake2PicAR == "1" && item.isUploadAR == "0"){
                        item.isUploadAR = "1"
                        val values = ContentValues()
                        /***更新考生的监考老师名称*/
                        values.put("isUploadAR", "1")
                        var updateCount = LitePal.updateAll(
                            StudentDetailsBean::class.java,
                            values,
                            "sfzh = ? ",
                            item.sfzh
                        )
                        if(index < studentInfoListForAdapter.size){
                            studentInfoListForAdapter.set(index,item)
                        }
                    }
                }
            }

            var endTime = System.currentTimeMillis()

            if(endTime - startTime < 1000){
                Handler().postDelayed({
                    dismissLoadingDialog()
                    showSuccessMessageDialog(msg = "已取证数据上传完成!" , isCanAfterClickFinishActivity = false)
                },1000)
            }
            else{
                dismissLoadingDialog()
                showSuccessMessageDialog(msg = "已取证数据上传完成!" , isCanAfterClickFinishActivity = false)
            }

            rv_filter_list_view.adapter?.notifyDataSetChanged()
        }
    }


    private fun getData() {
        var teacher =
            LitePal.where("sfzh = ?", JinLinApp.loginExaminer?.number ?: "")
                .findFirst(ProctorDetailsBean::class.java)

        if (teacher != null && !teacher.schoolName.isNullOrBlank()) {
            if (teacher.schoolName == Const.ROOM_1) {
                var list = BasicDataBuilder.getStudentInfoList1()
                list?.let {
                    studentInfoListForAdapter.addAll(list)
                }
            } else if (teacher.schoolName == Const.ROOM_2) {
                var list = BasicDataBuilder.getStudentInfoList2()
                list?.let {
                    studentInfoListForAdapter.addAll(list)
                }
            }
            else{
                var list = BasicDataBuilder.getStudentInfoListOther(teacher.schoolName)
                list?.let {
                    studentInfoListForAdapter.addAll(list)
                }
            }
        }
    }


    class Adapter(data: List<StudentDetailsBean>?) :
        BaseQuickAdapter<StudentDetailsBean, BaseViewHolder>(
            R.layout.app_item_student_obtain_evidence, data
        ) {
        override fun convert(helper: BaseViewHolder?, item: StudentDetailsBean?) {
            if (item != null) {


                var position = helper!!.layoutPosition
                var positionAdd = position + 1
                if (positionAdd < 10) {

                    helper?.getView<TextView>(R.id.tvNumericalOrder)?.text = "0$positionAdd"
                } else {
                    helper?.getView<TextView>(R.id.tvNumericalOrder)?.text = positionAdd.toString()
                }


                if (!item.sfzh.isNullOrBlank()) {
                    //显示身份证
                    helper?.getView<TextView>(R.id.tv_mine_user_sfzh)?.text =
                        JavaStringTool.replaceIdNum(item.sfzh)

                    //显示图片
                    var bitmap = StudentOwnImageBuilder.getSFZBitmap(item.sfzh)
                    if (bitmap != null) {
                        helper?.getView<ImageView>(R.id.iv_mine_avatar)?.setImageBitmap(bitmap)
                        helper?.getView<ImageView>(R.id.iv_mine_avatar)?.visibility = View.VISIBLE
                        helper?.getView<ImageView>(R.id.iv_mine_avatar2)?.visibility = View.GONE
                    } else {
                        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>. bitmap = null")
//                        helper?.getView<ImageView>(R.id.iv_mine_avatar)?.setImageBitmap(
//                            BitmapFactory.decodeResource(
//                                JinLinApp.context!!.getResources(),
//                                R.drawable.ic_default_avatar
//                            )
//                        )
                        helper?.getView<ImageView>(R.id.iv_mine_avatar)?.visibility = View.GONE
                        helper?.getView<ImageView>(R.id.iv_mine_avatar2)?.visibility = View.VISIBLE
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

                //显示是否已经取证 等于0就是未拍两张照片
                if (item.isTake2PicAR.isNullOrBlank() || item.isTake2PicAR == "0") {
                    helper?.getView<TextView>(R.id.tvUnTake2PicAR)?.visibility = View.VISIBLE
                    helper?.getView<TextView>(R.id.tvTake2PicAR)?.visibility = View.GONE
                } else {
                    helper?.getView<TextView>(R.id.tvUnTake2PicAR)?.visibility = View.GONE
                    helper?.getView<TextView>(R.id.tvTake2PicAR)?.visibility = View.VISIBLE
                }


                //显示是否已经上传 等于0就是未上传
                if (item.isUploadAR.isNullOrBlank() || item.isUploadAR == "0") {
                    helper?.getView<TextView>(R.id.tvUpLoad)?.visibility = View.GONE
                    helper?.getView<TextView>(R.id.tvUnUpload)?.visibility = View.VISIBLE
                } else {
                    helper?.getView<TextView>(R.id.tvUpLoad)?.visibility = View.VISIBLE
                    helper?.getView<TextView>(R.id.tvUnUpload)?.visibility = View.GONE
                }





                LogUtil.e(">>>>>>>>>>>>>>>>身份证号:${item.sfzh} isTake2PicAR: ${item.isTake2PicAR}")

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        LogUtil.e("requestCode :$requestCode")
        LogUtil.e("resultCode :$resultCode")

        when (resultCode) {
            Const.EXAMINATION_ROOM_INFO_CODE -> {
//                studentInfoListForAdapter.clear()
//                getData()
//                rv_filter_list_view.adapter?.notifyDataSetChanged()


                var position = data?.getIntExtra(BaseConstant.FILTER_SELECT_POSITION, -1)
                if (position != null && position != -1 && position < studentInfoListForAdapter.size) {
                    var item = studentInfoListForAdapter.get(position)
                    if (item != null && !item.sfzh.isNullOrBlank()) {

                        val values = ContentValues()
                        /***更新考生的监考老师名称*/
                        values.put("isTake2PicAR", "1")
                        var updateCount = LitePal.updateAll(
                            StudentDetailsBean::class.java,
                            values,
                            "sfzh = ? ",
                            item.sfzh
                        )
                        LogUtil.e(">>>>>>>>>>> 更新的数据条数:$updateCount")
                        if (updateCount == 0) {
                            /**更新不成功就进行保存*/
                            item.isTake2PicAR = "1"
                            item.save()
                        }

                        item.isTake2PicAR = "1"
                        studentInfoListForAdapter.set(position, item)
                        LogUtil.e(">>>>>>>>>>>>>>>>>>>>> 新的item :${item}")

                        rv_filter_list_view.adapter?.notifyDataSetChanged()
                        LogUtil.e(">>>>>>>>>>> 已经进行刷新了...")

                    }
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
























