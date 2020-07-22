package com.tecsun.jc.demo.lib_readssc.ssc.impl;

/**
 * @author     LiangZZ
 * 时间    2017/7/11 18:04
 * 描述   View 层基类接口
 */

public interface BaseMvpViewImpl {
    /**
     *显示加载框
     * @param type 类型
     */
    public  void  showProgress(int type);
    /**
     *隐藏加载框
     */
    public  void  hideProgress();

}
