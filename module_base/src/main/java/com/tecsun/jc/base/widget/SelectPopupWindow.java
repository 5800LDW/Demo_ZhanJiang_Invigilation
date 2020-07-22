package com.tecsun.jc.base.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.tecsun.jc.base.R;

/**
 * 选择菜单弹出
 * Created by _Smile on 2017/5/17.
 */
public class SelectPopupWindow<T> extends PopupWindow {
    private View mView;
    /**
     * 内容视图外部点击监听
     */
    private OutSideTouchListener mOutSideTouchListener;
    /**
     * 点击内容视图外部监听。非必须
     */
    public interface OutSideTouchListener {
        void outSideTouchListener();
    }

    /**
     * 设置内容视图外部点击监听
     */
    public void setOutSideTouchListener(OutSideTouchListener outSideTouchListener) {
        mOutSideTouchListener = outSideTouchListener;
    }

    public interface TextClickPositionListener {
        void getTextClickPositionListener(View view);
    }

    public SelectPopupWindow(Activity activity, String[] data, final TextClickPositionListener textClickPositionListener) {
        super(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.dialog_location_map, null);
        TextView baidu = (TextView) mView.findViewById(R.id.tv_baidu);
        TextView gaode = (TextView) mView.findViewById(R.id.tv_gaode);
        TextView cancle = (TextView) mView.findViewById(R.id.tv_cancle);
        baidu.setText(data[0]);
        gaode.setText(data[1]);
        cancle.setText(data[2]);
        setmView(0, R.style.style_pop_up_window);
        baidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (textClickPositionListener != null) {
                    textClickPositionListener.getTextClickPositionListener(v);
                }
            }
        });
        gaode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (textClickPositionListener != null) {
                    textClickPositionListener.getTextClickPositionListener(v);
                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void setmView(final int bgHeight, int style) {
        //设置SelectPicPopupWindow的View
        this.setContentView(mView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        if (0 == bgHeight) {
            this.setAnimationStyle(style);
        }
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(33000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (0 == bgHeight) {
                        if (y < height) {
                            if (mOutSideTouchListener != null) {
                                mOutSideTouchListener.outSideTouchListener();
                            }
                            dismiss();
                        }
                    } else {
                        if (y > height) {
                            if (mOutSideTouchListener != null) {
                                mOutSideTouchListener.outSideTouchListener();
                            }
                            dismiss();
                        }
                    }
                }
                return true;
            }
        });
    }
}
