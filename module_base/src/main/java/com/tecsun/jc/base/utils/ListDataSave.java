package com.tecsun.jc.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tecsun.jc.base.JinLinApp;
import com.tecsun.jc.base.bean.MyNewsEnitity;
import com.tecsun.jc.base.utils.log.LogUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 本地数据保存
 */
public class ListDataSave {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static String FileName = "filename_message";
    public static String MessageStatus_noread="0";
    public static String MessageStatus_read="1";

    public ListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
        FileName=preferenceName;
    }

    /**
     * 保存List
     *
     * @param tag
     * @param datalist
     */
    public <T> void setDataList(String tag, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0) {
            return;
        }

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();
        //更新界面
        update();
    }

    /**
     * 保存List
     *
     * @param tag
     * @param datalist
     */
    public <T> void setDataList2(String tag, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0) {
            return;
        }

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();

    }

    public <T> void setDataList() {
        editor.putString(FileName, null);
        editor.commit();

    }

    /**
     * 获取List
     *
     * @param tag
     * @return
     */
    public List<MyNewsEnitity.Bean> getDataList(String tag) {
        List<MyNewsEnitity.Bean> datalist = new ArrayList<>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<MyNewsEnitity.Bean>>() {
        }.getType());
        return datalist;

    }

    private List<MyNewsEnitity.Bean> MyList_local = new ArrayList<MyNewsEnitity.Bean>();
    /**
     * 本地消息集合
     */
    private List<MyNewsEnitity.Bean> MyList_news = new ArrayList<MyNewsEnitity.Bean>();
    /**
     * 网络与本地匹对新消息集合
     */
    boolean flag = true;
    /**
     * 消息总集合
     * */
    private List<MyNewsEnitity.Bean> MyList_All = new ArrayList<MyNewsEnitity.Bean>();
//    /**
//     * 匹配结果是否归类到新消息集合
//     */
//    public int GetMessage(List<MyNewsEnitity.Bean> MyList) {
//        MyList_local.clear();
//        MyList_news.clear();
//        MyList_local.addAll(getDataList(FileName));
//        if (MyList_local.size() == 0 && MyList.size() > 0) {/**本地没消息*/
//            setDataList(FileName, MyList);/**网络新消息保存在本地*/
//            return MyList.size();
//        } else if (MyList_local.size() > 0) {
////            //设置概率性添加通知栏（测试）
////            Random r = new Random();
////            int n = r.nextInt(100);
////            int m ; //随机数字
////            if(n<30){
////                Log.d("概率性能添加新通知栏","概率性能添加新通知栏"+n);
////                MyList_local.get(0).setMessageId(n+"");
////                MyList.addAll(MyList_local);
////            }
//
//
//        }
//        else if (MyList_local.size()>0){
//            /**配对网络的数据与本地的数据是否一样，不一样为新的消息*/
//            for (int i = 0; i < MyList.size(); i++) {
//                loop:
//                for (int j = 0; j < MyList_local.size(); j++) {
//                    if (MyList.get(i).getMessageId().equals(MyList_local.get(j).getMessageId())) {
//                        flag = false;
//                        break loop;/**网络消息id与本地消息id存在一样的跳出循环*/
//                    }
//                }
//                /**网络与本地匹配没有相同的纳入新消息集合*/
//                if (flag) {
//                    MyList_news.add(MyList.get(i));
//                }
//                flag = true;
//            }
//            /**最终把网络最新的集合保存在本地*/
//            setDataList(FileName, MyList);
//        }
//        return MyList_news.size();
//    }


    public static void clearAllMessage() {
        SharedPreferences preferences = JinLinApp.getContext().getSharedPreferences(ListDataSave.FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();

        editor.commit();
        LogUtil.e("clearAllMessage");
        //更新界面
        update();
    }


    /**更新业务接口*/
    public interface IUpdate {
        /**更新方法*/
        void biz();
    }

    private static HashSet<IUpdate> updatesList = new HashSet<>();

    public static void addUpdateEvents (final IUpdate listener) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (updatesList != null) {
                    updatesList.add(listener);
                }
            }
        });

    }

    public static void removeUpdateEvents(final IUpdate listener) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (updatesList != null) {
                    updatesList.remove(listener);
                }
            }
        });

    }
    /**内部调用的更新方法*/
    private static void update() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (updatesList.size() != 0) {
//                    for (int i = 0; i < updatesList.size(); i++) {
//                        if (updatesList.get(i) != null) {
//                            updatesList.get(i).biz();
//                        }
//                    }
                    for (IUpdate listener : updatesList) {
                        if (listener != null) {
                            listener.biz();
                        }
                    }
                }
            }
        });



    }


    /****service那里使用，把多的集合和本地集合加载本地**/
    public List<MyNewsEnitity.Bean> GetMessageBeanService(List<MyNewsEnitity.Bean> MyList,String MessageStatus){
        MyList_local.clear();
        MyList_news.clear();
        MyList_All.clear();
        MyList_local.addAll(getDataList(FileName));
        if (MyList_local.size()==0&&MyList.size()>0){/**本地没消息*/
            setDataList(FileName, MyList);/**网络新消息保存在本地*/
            return MyList;
        }
        else if (MyList_local.size()>0){
            /**配对网络的数据与本地的数据是否一样，不一样并且messagetype为0的时候为新的消息*/
            for (int i=0;i<MyList.size();i++){
                loop:for (int j=0;j<MyList_local.size();j++){
                    if (MyList.get(i).getMessageId().equals(MyList_local.get(j).getMessageId())){
                        flag=false;
                        break loop;/**网络消息id与本地消息id存在一样的跳出循环*/
                    }
                }
                /**网络与本地匹配没有相同的纳入新消息集合*/
                if (flag&&MessageStatus.equals(MyList.get(i).getMessageStatus())){
                    MyList_news.add(MyList.get(i));
                }
                flag=true;
            }
            /**最终把网络最新的集合+本地的集合保存在本地*/
            MyList_All.addAll(MyList_news);//新消息
            MyList_All.addAll(MyList_local);//本地消息
            Log.d("新消息+本地消息","新消息"+MyList_news.size()+"--本地消息--"+MyList_local.size());
            setDataList(FileName, MyList_All);
        }
        return MyList_news;
    }



    public List<MyNewsEnitity.Bean> GetMessageBean(List<MyNewsEnitity.Bean> MyList,String MessageStatus){
        MyList_local.clear();
        MyList_news.clear();
        MyList_local.addAll(getDataList(FileName));
        Log.d("本地消息大小",MyList_local.size()+"");
        if (MyList_local.size()==0&&MyList.size()>0){/**本地没消息*/
//            setDataList2(FileName, MyList);/**网络新消息保存在本地*/
            return MyList;
        }
        else if (MyList_local.size()>0){
            /**配对网络的数据与本地的数据是否一样，不一样并且messagetype为0的时候为新的消息*/
            for (int i=0;i<MyList.size();i++){
                loop:for (int j=0;j<MyList_local.size();j++){
                    if (MyList.get(i).getMessageId().equals(MyList_local.get(j).getMessageId())&&!MessageStatus.equals(MyList_local.get(i).getMessageStatus())){
                        flag=false;
                        break loop;/**网络消息id与本地消息id存在一样的跳出循环*/
                    }
                }
                /**网络与本地匹配没有相同的纳入新消息集合*/
                if (flag){
                    MyList_news.add(MyList.get(i));
                }
                flag=true;
            }
//            /**最终把网络最新的集合保存在本地*/
//            setDataList2(FileName, MyList);
        }
        return MyList_news;
    }
}
