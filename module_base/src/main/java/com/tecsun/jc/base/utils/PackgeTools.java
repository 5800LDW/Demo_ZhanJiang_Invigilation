package com.tecsun.jc.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;

import com.tecsun.jc.base.R;
import com.tecsun.jc.base.ui.activity.DownLoadActivity;
import com.tecsun.jc.base.widget.SelectPopupWindow;

import java.net.URISyntaxException;

/**
 * Created by psl on 2017/8/31.
 */

public class PackgeTools {
    public static boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName)){
            return false;
        }
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void intentmap(final Activity context, final String address, final String lat, final String lng, View view){
        String[] data = new String[]{ "百度地图", "高德地图", "取消"};
        //实例化SelectPicPopupWindow
        final SelectPopupWindow menuWindow = new SelectPopupWindow(context, data, new SelectPopupWindow.TextClickPositionListener() {
            @Override
            public void getTextClickPositionListener(View view) {
                if (view.getId()== R.id.tv_baidu){
                    if (PackgeTools.checkApkExist(context, "com.baidu.BaiduMap")) {
//                        LngLat lat = new LngLat(logtitu, lagtitu);
//                        LngLat lay1 = CoodinateCovertorUtil.bd_encrypt(lat);
                        Intent i1 = new Intent();
                        i1.setData(Uri.parse("baidumap://map/direction?destination=name:" + address + "|latlng:" + lat + "," + lng + "&mode=transit&sy=3&index=0&target=1"));
                        context.startActivity(i1);
                    } else {
                        Intent intent = new Intent(context, DownLoadActivity.class);
                        intent.putExtra("title", "百度地图");
                        context.startActivity(intent);
                    }
                }
                else if (view.getId()== R.id.tv_gaode){
                    if (PackgeTools.checkApkExist(context, "com.autonavi.minimap")) {
                        try {
                            Intent intent = Intent.getIntent("androidamap://viewMap?sourceApplication=吉林&poiname=" + address + "&lat=" + lat + "&lon=" + lng + "&dev=0");
                            context.startActivity(intent);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Intent intent = new Intent(context, DownLoadActivity.class);
                        intent.putExtra("title", "高德地图");
                        context.startActivity(intent);
                    }
                }
            }
        });


        menuWindow.setClippingEnabled(false);

//        setPopupWindowTouchModal(menuWindow,false);

        //显示窗口
        menuWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }


//    public static void setPopupWindowTouchModal(PopupWindow popupWindow,
//                                                boolean touchModal) {
//        if (null == popupWindow) {
//            return;
//        }
//        Method method;
//        try {
//
//            method = PopupWindow.class.getDeclaredMethod("setTouchModal",
//                    boolean.class);
//            method.setAccessible(false);
//            method.invoke(popupWindow, touchModal);
//
//            LogUtil.e(">>>>>>>>>>>>>>> setPopupWindowTouchModal  1");
//        }
//        catch (Exception e) {
//            LogUtil.e(">>>>>>>>>>>>>>> setPopupWindowTouchModal Exception");
//            LogUtil.e(e);
//        }
//
//    }


}
