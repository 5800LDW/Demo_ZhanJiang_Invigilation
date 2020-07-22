package com.tecsun.jc.base.common;

import android.app.Application;

public class CommonToken {

    public static Application mJinLinApp;

    public static String mToken  = "";

    public static String getmToken() {

//        Log.e("TAG","getmToken");
//        Log.e("TAG",mToken);
        return mToken;
    }

    public static void setmToken(String mToken) {
//        TokenUtil.set(mJinLinApp,TokenUtil.ID_TOKEN,mToken);
//        Log.e("TAG","setmToken");
//        Log.e("TAG",mToken);
        CommonToken.mToken = mToken;
    }
}
