package com.tecsun.jc.base.base

import android.os.Bundle
import androidx.fragment.app.FragmentPagerAdapter
import com.tecsun.jc.base.R
import com.tecsun.jc.base.ui.fragment.TopTabLayoutFragment
import kotlinx.android.synthetic.main.layout_top_tab_view_pager.*
import kotlinx.android.synthetic.main.layout_top_tablayout.*

abstract class BaseTopTabLayoutFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.layout_top_tab_view_pager
    }

    abstract fun topTabNames(): Array<String>

    override fun initView(savedInstanceState: Bundle?) {
        val topTabFragment = childFragmentManager.findFragmentById(R.id.fm_top_tab_title) as TopTabLayoutFragment
        topTabFragment.topTabNames = topTabNames()

        vp_top_tab_content.offscreenPageLimit = 4

        initTabContent()
    }

    abstract fun setTabAdapter(): FragmentPagerAdapter

    private fun initTabContent() {
        vp_top_tab_content.adapter = setTabAdapter()
        tl_top_tab.setupWithViewPager(vp_top_tab_content)
    }

    override fun onResume() {
        super.onResume()
        val params = head_view.layoutParams
        val mStatusBarHeight = getStatusBarHeight()
        params.height = mStatusBarHeight
        head_view.layoutParams = params
    }
}