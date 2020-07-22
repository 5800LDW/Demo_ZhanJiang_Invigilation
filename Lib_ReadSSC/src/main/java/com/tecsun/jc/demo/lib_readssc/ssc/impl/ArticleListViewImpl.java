package com.tecsun.jc.demo.lib_readssc.ssc.impl;
/**
 * @author     LiangZZ
 * 时间    2017/7/11 18:10
 * 描述   列表基类View
 */

public interface ArticleListViewImpl<T> extends BaseMvpViewImpl {

    /**
     *有数据的时候更新View
     * @param content  上下文
     * @param pageIndex  页数
     * @param mFlag  标志
     */
    public void onSuccess(T content, int pageIndex, int mFlag);
    /**
     *加载数据失败
     * @param error 返回错误
     * @param mFlag 标志
     */
    public void showLoadFailMsg(String error, int mFlag);
    /**
     *无数据的时候显示的
     * @param message  返回信息
     * @param mFlag 标志
     */
    public  void viewNoData(String message, int mFlag);

}
