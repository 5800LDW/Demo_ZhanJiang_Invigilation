package com.tecsun.jc.base.utils.log;

import android.util.Log;
import com.tecsun.jc.base.BuildConfig;

/**
 * log日志工具类
 *
 * @author liudongwen
 * @version
 * @date 2019/05/29
 */
public class LogUtil {

    public static void e( String tag, Object obj){
        if(BuildConfig.isRelease){
            return;
        }

        if(tag !=null && obj !=null){
            if(obj.toString().length() > 2000){
                LogOverLengthInfo.INSTANCE.e(tag,obj);
            }
            else{
                Log.e(tag,obj.toString());
            }
        }
    }

    public static void e( Object obj){
        if(BuildConfig.isRelease){
            return;
        }

        if( obj !=null){
            e("TAG","-------  " + obj.toString() + "  --------");
        }
    }

    public static void d( String tag, Object obj){
        if(BuildConfig.isRelease){
            return;
        }

        if( tag!=null && obj !=null){
            e(tag,obj);
        }
    }



}
