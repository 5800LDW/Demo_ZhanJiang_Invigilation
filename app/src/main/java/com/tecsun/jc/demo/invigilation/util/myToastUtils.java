/*
 Copyright © 2015, 2016 Jenly Yu <a href="mailto:jenly1314@gmail.com">Jenly</a>

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */
package com.tecsun.jc.demo.invigilation.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Jenly
 */
public class myToastUtils {

    private static Toast toast;

    private myToastUtils(){
        throw new AssertionError();
    }

    public static void showToast(Context context,int resId){
        showToast(context,context.getResources().getString(resId));
    }

    public static void showToast(Context context,int resId,int duration){
        showToast(context,context.getResources().getString(resId),duration);
    }

    public static void showToast(Context context,CharSequence text){
        showToast(context,text,Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context,String text,int duration,Object...args){
        showToast(context,String.format(text,args),duration);
    }

    public static void showToast(Context context,CharSequence text,int duration){

        if(toast == null){
            toast =  Toast.makeText(context,text,duration);
        }else{
            toast.setText(text);
            toast.setDuration(duration);
        }
        toast.show();
    }

//    public static void showCustomToast(Context context, CharSequence text, int duration) {
//        View layout = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
//        TextView textView = (TextView) layout.findViewById(R.id.tv_text);
//        textView.setText(text);
//
//        if (toast != null) {
//            toast.cancel();
//        }
//
//        Toast toast = new Toast(context);
//        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 200);
//        toast.setDuration(duration);
//        toast.setView(layout);
//        toast.show();
//    }
//    public static void showCancleToast(Context context, CharSequence text, int duration,Drawable drawableId) {
//        View layout = LayoutInflater.from(context).inflate(R.layout.toast_cancle, null);
//        TextView textView = (TextView) layout.findViewById(R.id.tv_text);
//        ImageView imageView = (ImageView) layout.findViewById(R.id.iv_tip);
//        textView.setText(text);
//        imageView.setBackground(drawableId);
//
//        if (toast != null) {
//            toast.cancel();
//        }
//
//        Toast toast = new Toast(context);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.setDuration(duration);
//        toast.setView(layout);
//        toast.show();
//    }
}
