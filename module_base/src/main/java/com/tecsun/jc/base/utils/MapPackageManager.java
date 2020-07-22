package com.tecsun.jc.base.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import com.tecsun.jc.base.JinLinApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapPackageManager {

    //1.百度地图包名
    public static final String BAIDUMAP_PACKAGENAME = "com.baidu.BaiduMap";
    //2.高德地图包名
    public static final String AUTONAVI_PACKAGENAME = "com.autonavi.minimap";
    //3.腾讯地图包名
    public static final String QQMAP_PACKAGENAME = "com.tencent.map";

    public static final String[] MAP_PACKAGES = {BAIDUMAP_PACKAGENAME, AUTONAVI_PACKAGENAME, QQMAP_PACKAGENAME};
//    public static List<String> packages = checkInstalledPackage(MAP_PACKAGES);


    /**
     * 参数的key
     * 高德的坐标系 "gd_lng" (高德_经度)、"gd_lat"（纬度）、"destination"（目的地名称）
     */
    public static final String GCJO2_LNG = "gd_lng";
    public static final String GCJO2_LAT = "gd_lat";
    public static final String DESTINATION = "destination";
    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param packageNames 可变参数 String[]
     * @return 目标软件中已安装的列表
     */
    public static List<String> checkInstalledPackage(String... packageNames) {

        //获取packageManager
        final PackageManager packageManager = JinLinApp.getContext().getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储
        List<String> newPackageNames = new ArrayList<>();
        int count = packageNames.length;

        if (packageInfos != null && packageInfos.size() > 0) {

            outermost:for (String packageName : packageNames) {
                for (int i = 0; i < packageInfos.size(); i++) {
                    String packageInfo = packageInfos.get(i).packageName;
                    if (packageInfo.contains(packageName)) {
                        newPackageNames.add(packageName);
                        if (newPackageNames.size() == count) {
                            break outermost;//这里使用了循环标记，跳出外层循环
                        }
                    }
                }
            }
        }


        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return newPackageNames;
    }


    /**
     * 调用百度地图----------------
     *
     * @param context 上下文对象
     * @param arg     参数
     */
    public static void invokeBaiDuMap(Context context, Map arg) {

        try {
            Uri uri = Uri.parse("baidumap://map/geocoder?" +
                    "location=" + arg.get(GCJO2_LAT) + "," + arg.get(GCJO2_LNG) +
                    "&name=" + arg.get(DESTINATION) + //终点的显示名称
                    "&coord_type=gcj02");//坐标 （百度同样支持他自己的db0911的坐标，但是高德和腾讯不支持）
            Intent intent = new Intent();
            intent.setPackage(BAIDUMAP_PACKAGENAME);
            intent.setData(uri);

            context.startActivity(intent);
        } catch (Exception e) {
            Log.d("百度地图异常",e.getMessage());
        }

    }

    /**
     * 调用高德地图
     *
     * @param context 上下文对象s
     * @param arg     经纬度参数map
     */
    public static void invokeAuToNaveMap(Context context, Map arg) {

        try {
            Uri uri = Uri.parse("androidamap://route?sourceApplication={你的应用名称}" +
                    "&dlat=" + arg.get(GCJO2_LAT)//终点的纬度
                    + "&dlon=" + arg.get(GCJO2_LNG)//终点的经度
                    + "&dname=" + arg.get(DESTINATION)////终点的显示名称
                    + "&dev=0&m=0&t=0");
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            intent.addCategory("android.intent.category.DEFAULT");

            context.startActivity(intent);
        } catch (Exception e) {
            Log.d("高德地图异常",e.getMessage());
        }

    }

    /**
     * 调用腾讯地图
     *
     * @param context 上下文对象s
     * @param arg     经纬度参数map
     */
    public static void invokeQQMap(Context context, Map arg) {
        try {
            Uri uri = Uri.parse("qqmap://map/routeplan?type=drive" +
                    "&to=" + arg.get(DESTINATION)//终点的显示名称 必要参数
                    + "&tocoord=" + arg.get(GCJO2_LAT) + "," + arg.get(GCJO2_LNG)//终点的经纬度
                    + "&referer={你的应用名称}");
            Intent intent = new Intent();
            intent.setData(uri);

            context.startActivity(intent);
        } catch (Exception e) {
            Log.d("腾讯地图异常",e.getMessage());
        }
    }


    public static void IntentMap(Context context,String packageName,String lng,String lat,String address){
        Map<String,String> arg=new HashMap<>();
        arg.put(MapPackageManager.GCJO2_LNG,lng);
        arg.put(MapPackageManager.GCJO2_LAT,lat);
        arg.put(MapPackageManager.DESTINATION,address);
        switch (packageName) {
            case MapPackageManager.BAIDUMAP_PACKAGENAME:
                MapPackageManager.invokeBaiDuMap(context, arg);
                break;
            case MapPackageManager.AUTONAVI_PACKAGENAME:
                MapPackageManager.invokeAuToNaveMap(context, arg);
                break;
            case MapPackageManager.QQMAP_PACKAGENAME:
                MapPackageManager.invokeQQMap(context, arg);
                break;
        }
    }
}
