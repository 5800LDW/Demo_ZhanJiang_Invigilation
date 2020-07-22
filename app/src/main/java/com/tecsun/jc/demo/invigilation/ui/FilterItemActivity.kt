package com.tecsun.jc.demo.invigilation.ui

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tecsun.jc.base.adapter.FilterInfoAdapter
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.filter.IFilter
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.demo.invigilation.R
import kotlinx.android.synthetic.main.dialog_popwindow_filter_view.*


class FilterItemActivity : BaseActivity(), BaseQuickAdapter.OnItemClickListener {

    private var resultCode: Int = 0
    private var infoList: List<IFilter>? = null
    override fun getLayoutId(): Int {
        return R.layout.dialog_popwindow_filter_view
    }

    override fun initView(savedInstanceState: Bundle?) {
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        rv_filter_list_view.layoutManager = LinearLayoutManager(this)

        resultCode = intent.getIntExtra(BaseConstant.FILTER_SELECT, 0)

        infoList =
            intent.getSerializableExtra(BaseConstant.FILTER_LIST_DATA) as List<IFilter>
        val adapter = FilterInfoAdapter(infoList)
        rv_filter_list_view.adapter = adapter
        adapter.onItemClickListener = this
        adapter.loadMoreComplete()

        pop_layout.setOnClickListener {}
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        this.finish()
        return true
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (position >= 0 && position < infoList?.size ?: 0) {
            val intent = Intent()
            LogUtil.e(infoList?.get(position))
            intent.putExtra(BaseConstant.FILTER_SELECT_DATA, infoList?.get(position))
            setResult(resultCode, intent)
        }
        this.finish()
    }

    override fun finish() {
        super.finish()
        //注释掉activity本身的过渡动画
//        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
    }
}













