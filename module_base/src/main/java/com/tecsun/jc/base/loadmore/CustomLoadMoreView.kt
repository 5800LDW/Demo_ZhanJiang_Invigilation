package com.tecsun.jc.base.loadmore


import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.tecsun.jc.base.R


class CustomLoadMoreView : LoadMoreView() {
    override fun getLayoutId(): Int {
        return R.layout.view_load_more2
    }

    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_load_fail_view
    }

    override fun getLoadEndViewId(): Int {
        return R.id.load_more_load_end_view
    }






//    override fun getLayoutId(): Int {
//        return R.layout.view_load_more
//    }
//
//    override fun getLoadingViewId(): Int {
//        return R.id.load_more_loading_view
//    }
//
//    override fun getLoadFailViewId(): Int {
//        return R.id.load_more_load_fail_view
//    }
//
//    override fun getLoadEndViewId(): Int {
//        return R.id.load_more_load_end_view
//    }
}
