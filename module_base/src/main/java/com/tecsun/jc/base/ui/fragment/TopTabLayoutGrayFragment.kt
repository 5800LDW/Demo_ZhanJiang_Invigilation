package com.tecsun.jc.base.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.tecsun.jc.base.base.BaseFragment
import com.tecsun.jc.base.R

class TopTabLayoutGrayFragment : BaseFragment() {

    private lateinit var tlTopTabView: TabLayout
    lateinit var topTabNames: Array<String>

    override fun getLayoutId(): Int {
        return R.layout.layout_top_tablayout_gray
    }

    override fun initView(savedInstanceState: Bundle?) {
        tlTopTabView = mView.findViewById(R.id.tl_top_tab)

        tlTopTabView.tabMode = TabLayout.MODE_FIXED
        tlTopTabView.tabGravity = TabLayout.GRAVITY_FILL
        tlTopTabView.removeAllTabs()

        loadTabView()
    }

    private fun loadTabView() {
        var holder: TabViewHolder
        for (i in 0 until topTabNames.size) {
            tlTopTabView.addTab(tlTopTabView.newTab())
            val tab = tlTopTabView.getTabAt(i)
            tab?.setCustomView(R.layout.item_tabyout_view)
            holder = TabViewHolder(tab?.customView)

            holder.tabItemName?.text = topTabNames[i]
            holder.tabItemName?.setTextColor(resources.getColor(R.color.c_8a8a8a))
            holder.tabItemIndicator?.setBackgroundColor(resources.getColor(R.color.c_2358ff))
            if (i == 0) {
                holder.tabItemName?.isSelected = true
                holder.tabItemIndicator?.isSelected = true
                holder.tabItemName?.textSize = 18F
                holder.tabItemName?.setTextColor(resources.getColor(R.color.c_2358ff))
                holder.tabItemIndicator?.visibility = View.VISIBLE
            }
        }

        tlTopTabView.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                holder = TabViewHolder(tab?.customView)
                holder.tabItemName?.isSelected = true
                holder.tabItemIndicator?.isSelected = true
                //设置选中后的字体大小
                holder.tabItemName?.textSize = 18F
                holder.tabItemName?.setTextColor(resources.getColor(R.color.c_2358ff))
                holder.tabItemIndicator?.visibility = View.VISIBLE
                //关联Viewpager
//                mViewPager.setCurrentItem(tab?.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                holder = TabViewHolder(tab?.customView)
                holder.tabItemName?.isSelected = false
                holder.tabItemIndicator?.isSelected = false
                //设置选中后的字体大小
                holder.tabItemName?.textSize = 14F
                holder.tabItemName?.setTextColor(resources.getColor(R.color.c_8a8a8a))
                holder.tabItemIndicator?.visibility = View.INVISIBLE
            }
        })
    }

    internal class TabViewHolder(tabView: View?) {
        var tabItemName: TextView? = null
        var tabItemIndicator: View? = null

        init {
            tabItemName = tabView?.findViewById(R.id.tv_top_tab_name)
            tabItemIndicator = tabView?.findViewById(R.id.iv_top_tab_indicator)

        }
    }
}