/**
 * Copyright (c) 2016. cy Inc. All rights reserved.
 */
package com.tecsun.jc.demo.lib_readssc.ssc.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


/**
 * fragment基类
 *@author  LiangZZ
 */
public abstract class BaseFragment extends Fragment {

    protected abstract void initView(View view, Bundle savedInstanceState);
     /** 获取布局文件ID*/
    protected abstract int getLayoutId();  
  
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);  
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);
        initData();
        return view;  
    } 

    protected void initData() {}

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
