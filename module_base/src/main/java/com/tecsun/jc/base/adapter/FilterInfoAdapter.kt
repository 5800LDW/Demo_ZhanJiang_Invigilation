package com.tecsun.jc.base.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tecsun.jc.base.R
import com.tecsun.jc.base.bean.filter.IFilter

class FilterInfoAdapter(data: List<IFilter>?) :
    BaseQuickAdapter<IFilter, BaseViewHolder>(
        R.layout.item_filter_view, data
    ) {

    override fun convert(helper: BaseViewHolder?, item: IFilter?) {
        if (item != null) {
            helper?.getView<TextView>(R.id.tv_filter_item)?.text = item.info?:""
        }
    }
}