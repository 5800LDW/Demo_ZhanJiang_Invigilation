package com.tecsun.jc.base.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.tecsun.jc.base.R;
import com.tecsun.jc.base.collector.BaseActivityCollector;

/**
 * 单按钮弹框
 * Created by _Smile on 2017/5/24.
 */
public class BaseSingleDialog extends Dialog {

    private ImageView mIvDialogIcon;
    private TextView mTvDialogContent;
    private Button mBtnDialog;
    private View.OnClickListener defaultClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cancel();
        }
    };

    private boolean isShowIcon = true;
    private int mIconRes = -1;
    private Bitmap mIconBmp = null;
    private Object mDialogContent;
    private Object mDialogBtnContent;
    private View.OnClickListener mBtnClickListener = defaultClickListener;

    public BaseSingleDialog(@NonNull Context context) {
        super(context, R.style.base_register_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_base_single_btn);

        mIvDialogIcon = (ImageView) findViewById(R.id.iv_s_dialog_icon);
        mTvDialogContent = (TextView) findViewById(R.id.tv_s_dialog_content);
        mBtnDialog = (Button) findViewById(R.id.btn_s_dialog_confirm);
    }

    @Override
    public void show() {
        super.show();
        show(this);
    }

    private void show(BaseSingleDialog mDialog) {
        if (isShowIcon) {
            if (mIconRes != -1) {
                mIvDialogIcon.setImageResource(mIconRes);
            } else if (mIconBmp != null) {
                mIvDialogIcon.setImageBitmap(mIconBmp);
            }else {
                mIvDialogIcon.setImageResource(R.drawable.ic_failed);
            }
        } else {
            mIvDialogIcon.setVisibility(View.GONE);
        }

        if (mDialogContent != null) {
            if (mDialogContent instanceof Integer) {
                mDialog.mTvDialogContent.setText((Integer) mDialogContent);
            } else if (mDialogContent instanceof CharSequence) {
                mDialog.mTvDialogContent.setText((CharSequence) mDialogContent);
            }
        }

        if (mDialogBtnContent != null) {
            if (mDialogBtnContent instanceof Integer) {
                mDialog.mBtnDialog.setText((Integer) mDialogBtnContent);
            } else if (mDialogBtnContent instanceof CharSequence) {
                mDialog.mBtnDialog.setText((CharSequence) mDialogBtnContent);
            }
        }

        mDialog.mBtnDialog.setOnClickListener(mBtnClickListener);
    }

    public static class Builder {

        private BaseSingleDialog mDialog;

        public Builder(Context context) {
            Activity activity = BaseActivityCollector.getTopActivity();
            if(activity!=null){
                mDialog = new BaseSingleDialog(activity);
            }
            else{
                mDialog = new BaseSingleDialog(context);
            }


            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(false);
        }

        /**
         * 是否显示提示图标
         * @param isShow true:显示，默认显示ic_failed     false:不显示
         */
        public Builder isShowIcon(boolean isShow) {
            mDialog.isShowIcon = isShow;
            return this;
        }

        /**
         *  设置显示提示图标
         * @param iconRes 显示图标的资源ID  isShowIcon为false是设置无效，请勿设置过大图标
         */
        public Builder setIconRes(int iconRes) {
            mDialog.mIconRes = iconRes;
            return this;
        }

        /**
         *  设置显示提示图标
         * @param iconBmp 显示图标  isShowIcon为false是设置无效，请勿设置过大图标
         */
        public Builder setIconBmp(Bitmap iconBmp) {
            mDialog.mIconBmp = iconBmp;
            return this;
        }

        /**
         * 设置对话框显示内容
         * @param content 显示内容
         */
        public Builder setDailogContent(Object content) {
            mDialog.mDialogContent = content;
            return this;
        }

        /**
         * 设置按钮的显示内容
         * @param btnText 按钮显示内容
         */
        public Builder setPositiveButtonText(Object btnText) {
            mDialog.mDialogBtnContent = btnText;
            return this;
        }

        /**
         * 设置按钮的回调
         * @param onClickListener 按钮点击事件
         */
        public Builder setPositiveClickListener(View.OnClickListener onClickListener) {
            mDialog.mBtnClickListener = onClickListener;
            return this;
        }

        /**
         * 设置按钮的回调,按钮显示内容
         * @param btnText 按钮显示内容
         * @param onClickListener 按钮点击事件
         */
        public Builder setPositiveButton(Object btnText, View.OnClickListener onClickListener) {
            mDialog.mDialogBtnContent = btnText;
            mDialog.mBtnClickListener = onClickListener;
            return this;
        }

        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public BaseSingleDialog build() {
            return mDialog;
        }
    }
}
