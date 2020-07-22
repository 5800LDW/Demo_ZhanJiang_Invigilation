package com.tecsun.jc.base.listener.impl;

import android.os.Handler;
import android.os.Looper;
import com.tecsun.jc.base.listener.ILogin;
import com.tecsun.jc.base.utils.log.LogUtil;

import java.util.HashSet;

final public class ILoginImpl {

    public static HashSet<ILogin> list = new HashSet<>();


    public static void addEvents(final ILogin listener) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (list != null && listener != null) {
                    list.add(listener);
                }
            }
        });

    }

    public static void removeEvents(final ILogin listener) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (list != null && listener != null) {
                    list.remove(listener);
                }
            }
        });
    }

    public static void removeAllEvents(){
        if (list != null ) {
            list.clear();
        }
    }

    public static void afterLoginBizStart() {
        LogUtil.e("afterLoginBizStart()");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (list.size() != 0) {
//                    for (int i = 0; i < list.size(); i++) {
//                        if (list.get(i) != null) {
//                            list.get(i).afterSuccessfulLogin();
//                        }
//                    }
                    for(ILogin iLogin:list){
                        if(iLogin!=null){
                            iLogin.afterSuccessfulLogin();
                        }
                    }

                }
            }
        });
    }


    public static void afterLogOutBizStart() {
        LogUtil.e("afterLogOutBizStart()");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (list.size() != 0) {
//                    for (int i = 0; i < list.size(); i++) {
//                        if (list.get(i) != null) {
//                            list.get(i).afterLogOut();
//                        }
//                    }


                    for(ILogin iLogin:list){
                        if(iLogin!=null){
                            iLogin.afterLogOut();
                        }
                    }

                }
            }
        });
    }



}
