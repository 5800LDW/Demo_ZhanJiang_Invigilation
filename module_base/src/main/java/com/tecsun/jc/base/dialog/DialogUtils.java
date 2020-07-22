/**
 *
 */
package com.tecsun.jc.base.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import androidx.annotation.DrawableRes;
import com.tecsun.jc.base.R;

/**
 * 对话框辅助类
 */
public class DialogUtils {
    /**
     * 显示对话框（1个按钮，文字默认）
     * @param pContext  上下文
     * @param pMessage  提示信息
     * @param pPosttiveListener  确定按钮监听
     */
    public static void showDialog(Context pContext, String pMessage, DialogInterface.OnClickListener pPosttiveListener) {
        showDialog(pContext, pMessage, 0, (String) pContext.getText(R.string.base_lbl_confirm), pPosttiveListener);
    }

    /**
     * 显示对话框（1个按钮，文字自定义）
     * @param pContext  上下文
     * @param pMessage  提示信息
     * @param msgImgId  提示图标
     * @param pPosttiveListener  确定按钮监听
     */
    public static void showDialog(Context pContext, String pMessage, @DrawableRes int msgImgId, String posttiveText, DialogInterface.OnClickListener pPosttiveListener) {
        CustomDialog.Builder ibuilder = new CustomDialog.Builder(pContext);
        // ibuilder.setTitle(R.string.prompt);
        ibuilder.setMessage(pMessage);
        ibuilder.setMessageImgId(msgImgId);

        ibuilder.setPositiveButton(posttiveText, pPosttiveListener);

        ibuilder.create().show();
    }

    /**
     * 显示对话框（1个按钮，文字默认）
     * @param pContext  上下文
     * @param pMessage  提示信息
     * @param pPosttiveListener  确定按钮监听
     * @param isCancelable
     * @param isCanceledOnTouchOutside
     */
    public static void showDialog(Context pContext, String pMessage, DialogInterface.OnClickListener pPosttiveListener, boolean isCancelable, boolean isCanceledOnTouchOutside) {
        CustomDialog.Builder ibuilder = new CustomDialog.Builder(pContext);
        // ibuilder.setTitle(R.string.prompt);
        ibuilder.setMessage(pMessage);

        ibuilder.setPositiveButton(R.string.base_lbl_confirm, pPosttiveListener);

        ibuilder.setCancelable(isCancelable);
        ibuilder.setCanceledOnTouchOutside(isCanceledOnTouchOutside);

        ibuilder.create().show();
    }

    /**
     * 显示对话框（1个按钮，文字默认）
     * @param pContext  上下文
     * @param pMessage  提示信息
     * @param pPosttiveListener  确定按钮监听
     * @param isCancelable
     * @param isCanceledOnTouchOutside
     */
    public static CustomDialog showDialogHasReturn(Context pContext, String pMessage, DialogInterface.OnClickListener pPosttiveListener, boolean isCancelable, boolean isCanceledOnTouchOutside) {
        CustomDialog.Builder ibuilder = new CustomDialog.Builder(pContext);
        // ibuilder.setTitle(R.string.prompt);
        ibuilder.setMessage(pMessage);

        ibuilder.setPositiveButton(R.string.base_lbl_confirm, pPosttiveListener);

        ibuilder.setCancelable(isCancelable);
        ibuilder.setCanceledOnTouchOutside(isCanceledOnTouchOutside);

        CustomDialog dialog = ibuilder.create();
        dialog.show();

        return dialog;
    }

    /**
     * 显示对话框（1个按钮，文字默认）
     * @param pContext  上下文
     * @param pPosttiveListener  确定按钮监听
     */
    public static void showDialog(Context pContext, int pMessageId, DialogInterface.OnClickListener pPosttiveListener) {
        CustomDialog.Builder ibuilder = new CustomDialog.Builder(pContext);
        // ibuilder.setTitle(R.string.prompt);
        ibuilder.setMessage(pContext.getResources().getString(pMessageId));

        ibuilder.setPositiveButton(R.string.base_lbl_confirm, pPosttiveListener);

        ibuilder.create().show();
    }

    /**
     * 显示对话框（1个按钮，文字默认）
     * @param pMessage  提示信息
     * @param pPosttiveListener  确定按钮监听
     * @param activity  调用该方法的activity
     */
    public static void showDialog(String pMessage, DialogInterface.OnClickListener pPosttiveListener, Activity activity) {
        if (!activity.isFinishing()) {
            CustomDialog.Builder ibuilder = new CustomDialog.Builder(activity);
            //ibuilder.setTitle(R.string.prompt);
            ibuilder.setMessage(pMessage);
            ibuilder.setPositiveButton(R.string.base_lbl_confirm, pPosttiveListener);
            ibuilder.create().show();
        }
    }

    /**
     * 显示对话框（1个按钮，文字默认）
     * @param pMessage  提示信息
     * @param pPosttiveListener  确定按钮监听
     * @param activity  调用该方法的activity
     */
    public static void showDialog(Activity activity, int msgResId, String pMessage, DialogInterface.OnClickListener pPosttiveListener) {
        if (!activity.isFinishing()) {
            CustomDialog.Builder ibuilder = new CustomDialog.Builder(activity);
            ibuilder.setMessageImgId(msgResId);
            ibuilder.setMessage(pMessage);
            ibuilder.setPositiveButton(R.string.base_lbl_confirm, pPosttiveListener);
            ibuilder.create().show();
        }
    }
    /**
     * 显示对话框（1个按钮）
     * @param pContext  上下文
     * @param title    标题
     * @param pMessage  提示信息
     * @param pPostTextId  确定按钮文字
     * @param pPosttiveListener  确定按钮监听
     */
    public static void showDialog(Context pContext, String title, int confirmColor, String pMessage, int pPostTextId, DialogInterface.OnClickListener pPosttiveListener) {
        CustomDialog.Builder ibuilder = new CustomDialog.Builder(pContext);
        ibuilder.setTitle(title);
        ibuilder.setMessage(pMessage);
        ibuilder.setConfirmBtnColor(confirmColor);
        ibuilder.setPositiveButton(pPostTextId, pPosttiveListener);

        ibuilder.create().show();
    }
    /**
     * 显示对话框（2个按钮，文字默认）
     * @param pContext  上下文
     * @param pMessage  提示信息
     * @param pPosttiveListener  确定按钮监听
     * @param pNegativeListener  取消按钮监听
     * @param isCancelable
     * @param isCanceledOnTouchOutside
     */
    public static void showDialog(Context pContext, String pMessage, DialogInterface.OnClickListener pPosttiveListener,
                                  DialogInterface.OnClickListener pNegativeListener, boolean isCancelable, boolean isCanceledOnTouchOutside) {
        CustomDialog.Builder ibuilder = new CustomDialog.Builder(pContext);
        // ibuilder.setTitle(R.string.prompt);
        ibuilder.setMessage(pMessage);

        ibuilder.setPositiveButton(R.string.base_lbl_confirm, pPosttiveListener);
        ibuilder.setNegativeButton(R.string.base_lbl_cancel, pNegativeListener);

        ibuilder.setCancelable(isCancelable);
        ibuilder.setCanceledOnTouchOutside(isCanceledOnTouchOutside);

        ibuilder.create().show();
    }

    /**
     * 显示对话框（2个按钮，文字默认）
     * @param pContext  上下文
     * @param pPosttiveListener  确定按钮监听
     * @param pNegativeListener  取消按钮监听
     */
    public static void showDialog(Context pContext, int pMessageId, DialogInterface.OnClickListener pPosttiveListener, DialogInterface.OnClickListener pNegativeListener) {
        showDialog(pContext, pContext.getString(pMessageId), pPosttiveListener, pNegativeListener);
    }

    /**
     * 显示对话框（2个按钮，文字默认）
     * @param pContext  上下文
     * @param pMessage  资源中的提示信息id
     * @param pPosttiveListener  确定按钮监听
     * @param pNegativeListener  取消按钮监听
     */
    public static void showDialog(Context pContext, String pMessage, DialogInterface.OnClickListener pPosttiveListener, DialogInterface.OnClickListener pNegativeListener) {
        CustomDialog.Builder ibuilder = new CustomDialog.Builder(pContext);
        // ibuilder.setTitle(R.string.prompt);
        ibuilder.setMessage(pMessage);

        ibuilder.setPositiveButton(R.string.base_lbl_confirm, pPosttiveListener);
        ibuilder.setNegativeButton(R.string.base_lbl_cancel, pNegativeListener);

        ibuilder.create().show();
    }

    /**
     * 显示对话框（2个按钮）
     * @param pContext  上下文
     * @param customView  自定义视图
     * @param pPostText  确定按钮文字
     * @param pNegaText  取消按钮文字
     * @param pPosttiveListener  确定按钮监听
     * @param pNegativeListener  取消按钮监听
     */
    public static void showDialog(Context pContext, View customView, String pPostText, String pNegaText, DialogInterface.OnClickListener pPosttiveListener, DialogInterface.OnClickListener pNegativeListener) {
        CustomDialog.Builder ibuilder = new CustomDialog.Builder(pContext);
        // ibuilder.setTitle(R.string.prompt);
        ibuilder.setContentView(customView);

        ibuilder.setPositiveButton(pPostText, pPosttiveListener);
        ibuilder.setNegativeButton(pNegaText, pNegativeListener);

        ibuilder.create().show();
    }

    /**
     * 显示对话框（2个按钮）
     * @param pContext  上下文
     * @param pMessage  提示信息
     * @param msgImgId  提示图标
     * @param pPostText  确定按钮文字
     * @param pNegaText  取消按钮文字
     * @param pPosttiveListener  确定按钮监听
     * @param pNegativeListener  取消按钮监听
     */
    public static void showDialog(Context pContext, String pMessage, @DrawableRes int msgImgId, String pPostText, String pNegaText, DialogInterface.OnClickListener pPosttiveListener, DialogInterface.OnClickListener pNegativeListener) {
        CustomDialog.Builder ibuilder = new CustomDialog.Builder(pContext);
        // ibuilder.setTitle(R.string.prompt);
        ibuilder.setMessage(pMessage);
        ibuilder.setMessageImgId(msgImgId);

        ibuilder.setPositiveButton(pPostText, pPosttiveListener);
        ibuilder.setNegativeButton(pNegaText, pNegativeListener);

        ibuilder.create().show();
    }

    /**
     * 显示对话框（2个按钮）
     * @param pContext  上下文
     * @param pMessageId  资源中的提示信息id
     * @param pPostTextId  确定按钮文字id
     * @param pNegaTextId  取消按钮文字id
     * @param pPosttiveListener  确定按钮监听
     * @param pNegativeListener  取消按钮监听
     */
    public static void showDialog(Context pContext, int pMessageId, int pPostTextId, int pNegaTextId, DialogInterface.OnClickListener pPosttiveListener, DialogInterface.OnClickListener pNegativeListener) {
        CustomDialog.Builder ibuilder = new CustomDialog.Builder(pContext);
        // ibuilder.setTitle(R.string.prompt);
        ibuilder.setMessage(pContext.getResources().getString(pMessageId));

        ibuilder.setPositiveButton(pPostTextId, pPosttiveListener);
        ibuilder.setNegativeButton(pNegaTextId, pNegativeListener);

        ibuilder.create().show();
    }

    /**
     * 显示对话框（2个按钮）
     * @param pContext  上下文
     * @param title    标题
     * @param pMessage  提示信息
     * @param pPostTextId  确定按钮文字
     * @param pNegaTextId  取消按钮文字
     * @param pPosttiveListener  确定按钮监听
     * @param pNegativeListener  取消按钮监听
     */
    public static void showDialog(Context pContext, String title, String pMessage, int pPostTextId, int pNegaTextId, DialogInterface.OnClickListener pPosttiveListener, DialogInterface.OnClickListener pNegativeListener) {
        CustomDialog.Builder ibuilder = new CustomDialog.Builder(pContext);
        ibuilder.setTitle(title);
        ibuilder.setMessage(pMessage);

        ibuilder.setPositiveButton(pPostTextId, pPosttiveListener);
        ibuilder.setNegativeButton(pNegaTextId, pNegativeListener);

        ibuilder.create().show();
    }

    /**
     * 显示对话框（2个按钮）
     * @param pContext  上下文
     * @param title    标题
     * @param pMessage  提示信息
     * @param pPostTextId  确定按钮文字
     * @param pNegaTextId  取消按钮文字
     * @param pPosttiveListener  确定按钮监听
     * @param pNegativeListener  取消按钮监听
     */
    public static void showDialog(Context pContext, String title, int confirmColor, String pMessage, int pPostTextId, int pNegaTextId, DialogInterface.OnClickListener pPosttiveListener, DialogInterface.OnClickListener pNegativeListener) {
        CustomDialog.Builder ibuilder = new CustomDialog.Builder(pContext);
        ibuilder.setTitle(title);
        ibuilder.setMessage(pMessage);
        ibuilder.setConfirmBtnColor(confirmColor);
        ibuilder.setPositiveButton(pPostTextId, pPosttiveListener);
        ibuilder.setNegativeButton(pNegaTextId, pNegativeListener);

        ibuilder.create().show();
    }



    /**
     * 显示对话框（2个按钮）
     * @param title    标题
     * @param pMessage  提示信息
     * @param pPosttiveListener  确定按钮监听
     * @param pNegativeListener  取消按钮监听
     */
    public static void showDialog(String title, String pMessage, DialogInterface.OnClickListener pPosttiveListener, DialogInterface.OnClickListener pNegativeListener,
                                  boolean isCancelable, boolean isCanceledOnTouchOutside, Activity activity) {
        if (!activity.isFinishing()) {
            CustomDialog.Builder ibuilder = new CustomDialog.Builder(activity);
            ibuilder.setTitle(title);
            ibuilder.setMessage(pMessage);

            ibuilder.setPositiveButton(R.string.base_lbl_confirm, pPosttiveListener);
            ibuilder.setNegativeButton(R.string.base_lbl_cancel, pNegativeListener);

            ibuilder.setCancelable(isCancelable);
            ibuilder.setCanceledOnTouchOutside(isCanceledOnTouchOutside);

            ibuilder.create().show();
        }
    }

    /**
     * 显示对话框（2个按钮）
     * @param title    标题
     * @param pMessage  提示信息
     * @param pPostTextId  确定按钮文字
     * @param pNegaTextId  取消按钮文字
     * @param pPosttiveListener  确定按钮监听
     * @param pNegativeListener  取消按钮监听
     */
    public static void showDialog(String title, String pMessage, int pPostTextId, int pNegaTextId, DialogInterface.OnClickListener pPosttiveListener, DialogInterface.OnClickListener pNegativeListener,
                                  Activity activity) {
        if (!activity.isFinishing()) {
            CustomDialog.Builder ibuilder = new CustomDialog.Builder(activity);
            ibuilder.setTitle(title);
            ibuilder.setMessage(pMessage);

            ibuilder.setPositiveButton(pPostTextId, pPosttiveListener);
            ibuilder.setNegativeButton(pNegaTextId, pNegativeListener);

            ibuilder.create().show();
        }
    }

    /**
     * 显示对话框（2个按钮）
     * @param title    标题
     * @param pMessage  提示信息
     * @param pPostTextId  确定按钮文字
     * @param pNegaTextId  取消按钮文字
     * @param pPosttiveListener  确定按钮监听
     * @param pNegativeListener  取消按钮监听
     */
    public static void showDialog2(int id ,String title, String pMessage, int pPostTextId, int pNegaTextId, DialogInterface.OnClickListener pPosttiveListener, DialogInterface.OnClickListener pNegativeListener,
                                  Activity activity) {
        if (!activity.isFinishing()) {
            CustomDialog.Builder ibuilder = new CustomDialog.Builder(activity);
//            ibuilder.setTitle(title);
            ibuilder.setMessage(pMessage);

            ibuilder.setMessageImgId(id);

            ibuilder.setPositiveButton(pPostTextId, pPosttiveListener);
            ibuilder.setNegativeButton(pNegaTextId, pNegativeListener);

            ibuilder.create().show();
        }
    }
}
