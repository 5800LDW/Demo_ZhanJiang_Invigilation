package com.tecsun.jc.base.sign.check;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.tecsun.jc.base.sign.APPSignUtils;
import com.tecsun.jc.base.utils.TokenUtil;

public class CheckTokenUtil {


    public static void start(Context context, CheckTokenService.CheckListener l){
        if(context!=null){
            CheckTokenService.setListener(l);
            Intent i = new Intent(context, CheckTokenService.class);
            context.startService(i);
        }
    }


    public static void check(final Activity activity){
        if(activity!=null){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(activity.isFinishing() == false){
                        String tokenStr = TokenUtil.get(activity, TokenUtil.ID_TOKEN);
                        long timeStr = Long.valueOf(TokenUtil.get(activity,TokenUtil.TIME_TOKEN,"0"));
                        long finalTime = 3 * 60 * 1000;//3分钟拿一次;
                        long internalTime = System.currentTimeMillis() - timeStr;
                        if(tokenStr == null){
                            Log.e("TAG","APPSignUtils.INSTANCE.signApp()");
                            APPSignUtils.INSTANCE.signApp();
                        }
                        if(internalTime > finalTime){
                            Log.e("TAG","APPSignUtils.INSTANCE.signApp()");
                            APPSignUtils.INSTANCE.signApp();
                        }
                    }
                }
            });
        }
    }
//    //start 20190516
//    var tokenStr:String = TokenUtil.get(JinLinApp.context, TokenUtil.ID_TOKEN)
//    var timeStr = TokenUtil.get(JinLinApp.context,TokenUtil.TIME_TOKEN,"0").toLong()
//    val finalTime = 3 * 60 * 1000 //3分钟拿一次;
//    var internalTime = System.currentTimeMillis() - timeStr
//        if(tokenStr == null){
//        APPSignUtils.signApp()
//    }
//        if(internalTime > finalTime){
//        APPSignUtils.signApp()
//    }

}
