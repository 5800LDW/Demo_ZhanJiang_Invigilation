package com.tecsun.jc.base.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tecsun.jc.base.R
import com.tecsun.jc.base.common.BaseConstant

abstract class BaseRecycleViewFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener,
    BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    private lateinit var mRecyclerView: RecyclerView
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
        mRecyclerView = mView.findViewById(R.id.rv_common_list_view)
        mSwipeRefreshLayout = mView.findViewById(R.id.srl_common_swipe)
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189))
        mRecyclerView.layoutManager = LinearLayoutManager(context)

        mSwipeRefreshLayout.setOnRefreshListener(this)
        mRecyclerView.adapter = setRecycleViewAdapter()
        setRecycleViewAdapter().setOnLoadMoreListener(this, mRecyclerView)
        setRecycleViewAdapter().onItemClickListener = this

        mSwipeRefreshLayout.isRefreshing = true
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


    fun setListData(isRefresh: Boolean, data: MutableList<Any>, vararg args: Int) {
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

            setRecycleViewAdapter().replaceData(data)

        }



        if (args != null && args.isNotEmpty() && args[0] == STATUS_LET_ME_HANDLE) {
            return
        }

        if (size < BaseConstant.PAGE_SIZE) {
            // isRefresh为true 代表是进行刷新了;
            if (isRefresh) {
                //刷新后返回的数据小于 BaseConstant.PAGE_SIZE 就代表所有数据都过来了,所以显示"没有更多数据"
                setRecycleViewAdapter().loadMoreEnd(false)
            }

        } else {
            //加载完成（注意不是加载结束，而是本次数据加载结束并且还有下页数据）
            setRecycleViewAdapter().loadMoreComplete()
        }
    }


    private val STATUS_LET_ME_HANDLE = 1
    /**
     * 不显示"没有更多数据"
     */
    val STATUS_HIDE_END = 4
    /**
     * 显示"没有更多数据"
     */
    val STATUS_SHOW_END = 5

    /**
     * @param data 当前adapter的数据条数;
     * @param totalNumberInServer 后台服务器的数据总条数;
     * @param afterReFreshIsShowEnd true 就是第一次刷新小于BaseConstant.PAGE_SIZE的数据条数就出现"没有更多数据",false就相反
     * @param showType STATUS_HIDE_END or  STATUS_SHOW_END 默认是 STATUS_SHOW_END
     */
    fun setListData2(
        data: MutableList<Any>,
        totalNumberInServer: Int,
        afterReFreshIsShowEnd: Boolean,
        vararg showType: Int?
    ) {

        setListData(isRefresh, data, STATUS_LET_ME_HANDLE)

        //处理刷新业务
        if ((isRefresh && data.isNotEmpty() && data.size < BaseConstant.PAGE_SIZE)) {

            //第一次刷新数据小于 BaseConstant.PAGE_SIZE 而且不显示 最末"没有更多数据"
            if (afterReFreshIsShowEnd == null || !afterReFreshIsShowEnd) {
                hideEnd()
                return
            }

            if (showType == null) {
                showEnd()
            } else if (showType.isNotEmpty() && showType[0] == STATUS_HIDE_END) {
                hideEnd()
            } else if (showType.isNotEmpty() && showType[0] == STATUS_SHOW_END) {
                showEnd()
            } else {
                showEnd()
            }

            return
        }

        //处理数据多次加载的业务;
        if ((data != null && data.isNotEmpty() && totalNumberInServer != null && data.size >= totalNumberInServer)) {

            if (showType == null) {
                showEnd()
            } else if ( showType.isNotEmpty() && showType[0] == STATUS_HIDE_END) {
                hideEnd()
            } else if ( showType.isNotEmpty() && showType[0] == STATUS_SHOW_END) {
                showEnd()
            } else {
                showEnd()
            }

            return

        }
        else if(data != null && data.isNotEmpty()){
            setRecycleViewAdapter().loadMoreComplete()
        }
    }

    private fun showEnd() {
        setRecycleViewAdapter().loadMoreEnd(false)
    }

    private fun hideEnd() {
        setRecycleViewAdapter().loadMoreEnd(true)
    }


    fun requestErrorView(clickListener: View.OnClickListener) {
        mSwipeRefreshLayout.isRefreshing = false
        val errorView =
            LayoutInflater.from(context).inflate(R.layout.layout_error_view, mRecyclerView.parent as ViewGroup, false)
        setRecycleViewAdapter().emptyView = errorView
        errorView.setOnClickListener(clickListener)

        //如果列表一开始有数据, 就在列表最后显示: 数据加载失败的view; 如果列表一开始没数据, 就显示上面的"暂无数据"的view
        setRecycleViewAdapter().loadMoreFail()

    }
}