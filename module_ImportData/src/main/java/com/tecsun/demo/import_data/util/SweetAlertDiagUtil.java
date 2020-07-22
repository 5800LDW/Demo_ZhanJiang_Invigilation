package com.tecsun.demo.import_data.util;

import android.content.Context;


import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by LDW10000000 on 07/12/2016.
 */

public class SweetAlertDiagUtil {

    public interface OnConfirmListener{
        public void confirm();
    }



    public  interface  OnSweetAlertListener {
        public void sweetEvent();
    };

   static SweetAlertDialog mDialog;

    public  static void getWaringDialog(Context context , String title , String content, final OnSweetAlertListener listener){

//        if(mDialog==null){
//            mDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
//                    .setTitleText(title)
//    //                .setContentText("Won't be able to recover this file!")
//                    .setConfirmText("好的")
//                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sDialog) {
//                            // reuse previous dialog instance
//    //                        sDialog.setTitleText("Deleted!")
//    //                                .setContentText("Your imaginary file has been deleted!")
//    //                                .setConfirmText("OK")
//    //                                .setConfirmClickListener(null)
//    //                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//
//                            if(listener!=null){
//                                listener.sweetEvent();
//                            }
//                            mDialog.hide();
//                        }
//                    });
//        }
//        else {
//            mDialog.setTitleText(title);
//        }
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText("确定")
//                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        // reuse previous dialog instance
//                        //                        sDialog.setTitleText("Deleted!")
//                        //                                .setContentText("Your imaginary file has been deleted!")
//                        //                                .setConfirmText("OK")
//                        //                                .setConfirmClickListener(null)
//                        //                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//
//                        if(listener!=null){
//                            listener.sweetEvent();
//                        }
//                        mDialog.hide();
//                    }
//                })
                .show();

    }



    public static SweetAlertDialog getSuccessDialog(Context context , String title , String... content){
        if(title == null){
            title = "添加成功!";
        }


        SweetAlertDialog sDialog ;
        sDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
//                .setContentText(content)
                .setConfirmText("确定")
//        .setConfirmClickListener(null)
        ;
        if(content.length!=0){
            sDialog.setContentText(content[0]);
        }

        sDialog.show();

//        sDialog.setTitleText("Deleted!")
//                .setContentText("Your imaginary file has been deleted!")
//                .setConfirmText("OK")
//                .setConfirmClickListener(null)
//                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

        return  sDialog;
    }


    public static void warningConfirmDialog(Context context,String title,String content ,final OnConfirmListener listener){
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setCancelText("取消")
                .setConfirmText("确定")
                .showCancelButton(true)
                //.setCancelable(true)
                .setCancelClickListener(null
//                        new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        // reuse previous dialog instance, keep widget user state, reset them if you need
//                        sDialog.setTitleText("Cancelled!")
//                                .setContentText("Your imaginary file is safe :)")
//                                .setConfirmText("OK")
//                                .showCancelButton(false)
//                                .setCancelClickListener(null)
//                                .setConfirmClickListener(null)
//                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
//
//                        // or you can new a SweetAlertDialog to show
//                               /* sDialog.dismiss();
//                                new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
//                                        .setTitleText("Cancelled!")
//                                        .setContentText("Your imaginary file is safe :)")
//                                        .setConfirmText("OK")
//                                        .show();*/
//                    }
//                }
                )
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
//                        sDialog.setTitleText("Deleted!")
//                                .setContentText("Your imaginary file has been deleted!")
//                                .setConfirmText("OK")
//                                .showCancelButton(false)
//                                .setCancelClickListener(null)
//                                .setConfirmClickListener(null)
//                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                        listener.confirm();



                    }
                })
                .show();
    }

    public static void getErrorDialog(Context context,String title ,String info){
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(info)
                .setConfirmText("确定")
                .show();
    }




}
