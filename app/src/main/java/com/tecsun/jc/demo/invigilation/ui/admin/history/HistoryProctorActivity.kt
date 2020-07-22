package com.tecsun.jc.demo.invigilation.ui.admin.history

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.ProctorDetailsBean
import com.tecsun.jc.base.bean.db.invigilation.bean.ReadCardInfoBean
import com.tecsun.jc.base.bean.filter.IFilter
import com.tecsun.jc.base.builder.ReadCardImageBuilder
import com.tecsun.jc.base.utils.DBFunctionUtil
import com.tecsun.jc.base.utils.JavaStringTool
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.builder.BasicDataBuilder
import kotlinx.android.synthetic.main.dialog_popwindow_filter_view.*
import org.litepal.LitePal
import java.util.*
import kotlin.collections.ArrayList

class HistoryProctorActivity : BaseActivity() {

    private val list: LinkedList<ReadCardInfoBean> = LinkedList()

    override fun getLayoutId(): Int {
        return R.layout.activity_admin_history
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle(R.string.app_examiner_login_history)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        var beanList = ArrayList<ReadCardInfoBean>()

        var inDBList = DBFunctionUtil.findAllReadCardInfoBean()
        val examinerInfoList: LinkedList<IFilter> = LinkedList()
        examinerInfoList.addAll(BasicDataBuilder.getExaminerInfo())

        if (inDBList != null && inDBList.size != 0 && examinerInfoList != null && examinerInfoList.size != 0) {
            for (item in inDBList) {
                if (item != null && DBFunctionUtil.findExaminerCount(item.number ?: "") > 0){
                    beanList.add(item)
                }
            }
        }


//        var beanList = DBFunctionUtil.findAllReadCardInfoBean()
        if (beanList != null && beanList.size > 0) {
            list.addAll(beanList)
        }
        rv_filter_list_view.layoutManager = LinearLayoutManager(this)

        val adapter = Adapter(list)
        rv_filter_list_view.adapter = adapter
        adapter.loadMoreComplete()
    }


    class Adapter(data: List<ReadCardInfoBean>?) :
        BaseQuickAdapter<ReadCardInfoBean, BaseViewHolder>(
            R.layout.app_item_history_proctor, data
        ) {
        override fun convert(helper: BaseViewHolder?, item: ReadCardInfoBean?) {
            if (item != null) {

                item.number?.let {
                    helper?.getView<TextView>(R.id.tv_mine_user_sfzh)?.text =
                        JavaStringTool.replaceIdNum(item.number)

                    var bitmap = ReadCardImageBuilder.getSFZBitmap(item.number)
                    bitmap?.let {
                        helper?.getView<ImageView>(R.id.iv_mine_avatar)?.setImageBitmap(bitmap)
                    }
                }

                if (!item.name.isNullOrBlank()) {
                    helper?.getView<TextView>(R.id.tv_mine_user_name)?.text =
                        item.name!!
                }

//                var testingDetailsBean = DBFunctionUtil.findFirstTestingRoom()
//                testingDetailsBean?.let {
//                    helper?.getView<TextView>(R.id.tv_mine_user_card)?.text =
//                        testingDetailsBean.schoolName ?: ""
//                }


                var bean = LitePal.where("sfzh = ?", item.number ?: "")
                    .findFirst(ProctorDetailsBean::class.java)
                helper?.getView<TextView>(R.id.tv_mine_user_card)?.text =
                    bean?.schoolName ?: ""
            }
        }
    }
}



















