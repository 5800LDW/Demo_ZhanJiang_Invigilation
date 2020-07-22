package com.tecsun.jc.base.widget.diy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tecsun.jc.base.JinLinApp;
import com.tecsun.jc.base.R;
import com.tecsun.jc.base.utils.ScreenUtil;

public class MyTitleBar extends LinearLayout {




    private ImageView imageView;
    private TextView textView;
    private LinearLayout llBackground;


    private Drawable bg_background;
    private Drawable iv_pic;
    private int text_color = Color.BLACK;
    private String text = "";
    private float text_size;

    public MyTitleBar(Context context) {
        super(context);
        initView(context);
    }

    public MyTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeValue(context,attrs);
        initView(context);
    }

    public MyTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypeValue(context,attrs);
        initView(context);
    }

    public void  initTypeValue(Context context ,AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OnClickItemView);
        text_size = a.getDimension(R.styleable.OnClickItemView_text_size, ScreenUtil.dip2px(JinLinApp.getContext(),18));
        text = a.getString(R.styleable.OnClickItemView_text);
        text_color = a.getColor(R.styleable.OnClickItemView_text_color,Color.BLACK);
        bg_background = a.getDrawable(R.styleable.OnClickItemView_bg_background);
        iv_pic = a.getDrawable(R.styleable.OnClickItemView_iv_pic);
        a.recycle();
    }

    public void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.base_item_on_click_item_view,this,true);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        llBackground = findViewById(R.id.llBackground);

        llBackground.setBackground(JinLinApp.getContext().getResources().getDrawable(R.drawable.white_base_white_round_selector));
        if(bg_background!=null ){
            llBackground.setBackground(bg_background);
        }

        if(iv_pic!=null){
            imageView.setBackground(iv_pic);
        }

        textView.setTextColor(text_color);
        textView.setText(text);
        textView.setTextSize(text_size);
    }

}

















