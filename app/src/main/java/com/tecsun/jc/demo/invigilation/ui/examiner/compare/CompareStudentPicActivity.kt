package com.tecsun.jc.demo.invigilation.ui.examiner.compare

import android.content.ContentValues
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
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
import com.tecsun.jc.base.common.BaseConstant.FILTER_SELECT_POSITION
import com.tecsun.jc.base.utils.JavaStringTool
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.builder.BasicDataBuilder
import com.tecsun.jc.register.util.constant.Const.EXAMINATION_ROOM_INFO_CODE
import com.tecsun.jc.register.util.constant.Const.ROOM_1
import com.tecsun.jc.register.util.constant.Const.ROOM_2
import kotlinx.android.synthetic.main.activity_admin_download.*
import org.litepal.LitePal
import java.util.*

class CompareStudentPicActivity : BaseActivity(), BaseQuickAdapter.OnItemClickListener {
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (position < studentInfoListForAdapter.size) {
            var item = studentInfoListForAdapter.get(position)

            //无法比对
//            if (item == null || item.sfzh.isNullOrBlank() || StudentOwnImageBuilder.getSFZBitmap(
//                    item.sfzh
//                ) == null
//            ) {
//                showToast("该考生没有图片数据，无法进行审核！")
//                return
//            }

            if (item == null || item.sfzh.isNullOrBlank()
            ) {
                showToast("该考生数据不全，无法进行审核！")
                return
            }

            //进行比对
            val intent = Intent(this, ShowStudentPicActivity::class.java)
            intent.putExtra(BaseConstant.FILTER_SELECT, item)
            intent.putExtra(BaseConstant.FILTER_SELECT_POSITION, position)
            startActivityForResult(intent, EXAMINATION_ROOM_INFO_CODE)
        }
    }

    private val studentInfoListForAdapter: LinkedList<StudentDetailsBean> = LinkedList()


    override fun getLayoutId(): Int {
        return R.layout.activity_student_compare
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle(getString(R.string.app_student_compare_pic))
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        if (JinLinApp.loginExaminer?.number == null) {
            finish()
            return
        }

        var teacher =
            LitePal.where("sfzh = ?", JinLinApp.loginExaminer?.number ?: "")
                .findFirst(ProctorDetailsBean::class.java)

        if (teacher != null && !teacher.schoolName.isNullOrBlank()) {
            if (teacher.schoolName == ROOM_1) {
                var list = BasicDataBuilder.getStudentInfoList1()
                list?.let {
                    studentInfoListForAdapter.addAll(list)
                }
            } else if (teacher.schoolName == ROOM_2) {
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



        rv_filter_list_view.layoutManager = LinearLayoutManager(this)
        val adapter = Adapter(studentInfoListForAdapter)
        adapter.onItemClickListener = this
        rv_filter_list_view.adapter = adapter

        adapter.loadMoreComplete()
    }


    class Adapter(data: List<StudentDetailsBean>?) :
        BaseQuickAdapter<StudentDetailsBean, BaseViewHolder>(
            R.layout.app_item_student_pic_compare, data
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

                //显示是否已经审核 等于0就是未审核
                if (item.isCheck.isNullOrBlank() || item.isCheck == "0") {
                    helper?.getView<TextView>(R.id.tvUnChecked)?.visibility = View.VISIBLE
                    helper?.getView<TextView>(R.id.tvChecked)?.visibility = View.GONE
                } else {
                    helper?.getView<TextView>(R.id.tvUnChecked)?.visibility = View.GONE
                    helper?.getView<TextView>(R.id.tvChecked)?.visibility = View.VISIBLE
                }

                LogUtil.e(">>>>>>>>>>>>>>>>身份证号:${item.sfzh} ischeck: ${item.isCheck}")

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        LogUtil.e("requestCode :$requestCode")
        LogUtil.e("resultCode :$resultCode")

        when (resultCode) {
            EXAMINATION_ROOM_INFO_CODE -> {
                var position = data?.getIntExtra(FILTER_SELECT_POSITION, -1)
                if (position != null && position != -1 && position < studentInfoListForAdapter.size) {
                    var item = studentInfoListForAdapter.get(position)
                    if(item!=null && !item.sfzh.isNullOrBlank()){

                        val values = ContentValues()
                        /***更新考生的监考老师名称*/
                        values.put("isCheck", "1")
                        var updateCount = LitePal.updateAll(
                            StudentDetailsBean::class.java,
                            values,
                            "sfzh = ? ",
                            item.sfzh
                        )
                        LogUtil.e(">>>>>>>>>>> 更新的数据条数:$updateCount")
                        if (updateCount == 0) {
                            /**更新不成功就进行保存*/
                            item.isCheck = "1"
                            item.save()
                        }

                        item.isCheck = "1"
                        studentInfoListForAdapter.set(position,item)
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























