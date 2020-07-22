package com.tecsun.jc.base.listener.impl;

import android.os.Handler;
import android.os.Looper;
import com.tecsun.jc.base.listener.IClose;
import com.tecsun.jc.base.listener.IElectroniccard;
import com.tecsun.jc.base.utils.log.LogUtil;

import java.util.HashSet;

final public class IElectroniccardImpl {

    public static HashSet<IElectroniccard> list = new HashSet<>();

    public static void addEvents(final IElectroniccard listener) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (list != null && listener != null) {
                    list.add(listener);
                }
            }
        });
    }

    public static void removeEvents(final IElectroniccard listener) {
        LogUtil.e("removeEvents()");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (list != null && listener != null) {
                    list.remove(listener);
                }
            }
        });
    }

    public static void removeAllEvents() {
        if (list != null) {
            list.clear();
        }
    }

    public static int getEventsSize() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }


    public static void afterOnResult(final String p0, final String signNo, final IClose iClose) {
        LogUtil.e("afterOnResult()");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (list.size() != 0) {
//                    for (int i = 0; i < list.size(); i++) {
//                        if (list.get(i) != null) {
//                            list.get(i).onResult(p0,signNo,iClose);
//                        }
//                    }

                    for (IElectroniccard iElectroniccard : list) {
                        if (iElectroniccard != null) {
                            iElectroniccard.onResult(p0, signNo, iClose);
                        }
                    }
                }
            }
        });
    }


    public static void afterSceneResult(final String p0, final String busiSeq, final IClose iClose) {
        LogUtil.e("afterSceneResult()");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (list.size() != 0) {
//                    for (int i = 0; i < list.size(); i++) {
//                        if (list.get(i) != null) {
//                            list.get(i).onSceneResult(p0,busiSeq, iClose);
//                        }
//                    }

                    for (IElectroniccard iElectroniccard : list) {
                        if (iElectroniccard != null) {
                            iElectroniccard.onSceneResult(p0, busiSeq, iClose);
                        }
                    }
                }
            }
        });
    }


}
