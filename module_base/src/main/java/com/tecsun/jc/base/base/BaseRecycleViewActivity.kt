package com.tecsun.jc.base.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tecsun.jc.base.R
import com.tecsun.jc.base.common.BaseConstant

abstract class BaseRecycleViewActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener,
    BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    lateinit var mRecyclerView: RecyclerView
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    var isRefresh = true
    var isFristShowFragment = true

    var isError: Boolean = false
    var mLoadMoreEndGone = false

    var currentPageNo = 1

    override fun getLayoutId(): Int {
        return R.layout.fragment_common_list_view
    }

    abstract fun setRecycleViewAdapter(): BaseQuickAdapter<Any, BaseViewHolder>

    override fun initView(savedInstanceState: Bundle?) {
        mRecyclerView = findViewById(R.id.rv_common_list_view)
        mSwipeRefreshLayout = findViewById(R.id.srl_common_swipe)
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189))
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        mSwipeRefreshLayout.setOnRefreshListener(this)
        mRecyclerView.adapter = setRecycleViewAdapter()
        setRecycleViewAdapter().setOnLoadMoreListener(this, mRecyclerView)
        setRecycleViewAdapter().onItemClickListener = this

        mSwipeRefreshLayout.isRefreshing = true



        //mRecyclerView
        mRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    open fun refreshData() {
        mSwipeRefreshLayout.isRefreshing = true
        currentPageNo = 1
        setRecycleViewAdapter().loadMoreEnd(false)
    }

    override fun onRefresh() {
        isRefresh = true
        refreshData()
    }

    override fun onLoadMoreRequested() {
        isRefresh = false
    }

    fun setListData(isRefresh: Boolean, data: MutableList<Any>) {
        currentPageNo += 1
        val size = data.size
        mSwipeRefreshLayout.isRefreshing = false

        if (data.isEmpty()) {
            setRecycleViewAdapter().setNewData(data.toList())
            setRecycleViewAdapter().setEmptyView(R.layout.layout_empty_view, mRecyclerView.parent as ViewGroup)
            return
        }

        if (isRefresh) {
            setRecycleViewAdapter().setNewData(data.toList())
        } else {
//            if (size > 0) {
//                for (i in 0 until data.size) {
//                    setRecycleViewAdapter().addData(data[i])
//                }
//            }

//            if (size > 0) {
//                if(endSize > firstSize){
//                    for (i in firstSize until data.size) {
//                        setRecycleViewAdapter().addData(data[i])
//                    }
//                }
//            }

            setRecycleViewAdapter().replaceData(data)

        }

        if (size < BaseConstant.PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
//            setRecycleViewAdapter().loadMoreEnd(isRefresh)

            // isRefresh为true 代表是进行刷新了; 刷新后返回的数据小于20条就代表所有数据都过来了,所以显示"没有更多数据"
            if(isRefresh){
                setRecycleViewAdapter().loadMoreEnd(false)
            }

        } else {
            setRecycleViewAdapter().loadMoreComplete()
        }
    }

    fun requestErrorView(clickListener: View.OnClickListener) {
        mSwipeRefreshLayout.isRefreshing = false
        val errorView =
            LayoutInflater.from(this).inflate(R.layout.layout_error_view, mRecyclerView.parent as ViewGroup, false)
        setRecycleViewAdapter().emptyView = errorView
        errorView.setOnClickListener(clickListener)

        //如果列表一开始有数据, 就在列表最后显示: 数据加载失败的view; 如果列表一开始没数据, 就显示上面的"暂无数据"的view
        setRecycleViewAdapter().loadMoreFail()

    }
}