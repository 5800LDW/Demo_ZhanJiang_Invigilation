package com.tecsun.jc.base.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.tecsun.jc.base.R;
import com.tecsun.jc.base.base.BaseActivity;
import com.tecsun.jc.base.widget.TitleBar;
import org.jetbrains.annotations.Nullable;

/**
 * 地图下载
 * Create by psl on 2017/08/11
 */
public class DownLoadActivity extends BaseActivity  {
    private String title;
    private ImageView imageMap;
    private TextView tvMap;
    private Button btn_down;
    private Button btn_cancel;

    @Override
    public void setTitleBar(TitleBar titleBar) {
        title = getIntent().getStringExtra("title");
        titleBar.setTitle(title);
        if ("腾讯地图".equals(title)) {
            imageMap.setImageResource(R.drawable.ic_tencen);
            tvMap.setText(title);
        }else if ("百度地图".equals(title)){
            imageMap.setImageResource(R.drawable.ic_baidu);
            tvMap.setText(title);
        }else if ("高德地图".equals(title)){
            imageMap.setImageResource(R.drawable.ic_gaode);
            tvMap.setText(title);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_down_load;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        imageMap = (ImageView) findViewById(R.id.image_map);
        tvMap = (TextView) findViewById(R.id.tv_map);
        btn_down = (Button) findViewById(R.id.btn_down);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //个别手机会抛出异常(三星)，异常选择网页打开
                    if ("腾讯地图".equals(title)) {
                        Uri uri = Uri.parse("market://details?id=com.tencent.map");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else if ("百度地图".equals(title)) {
                        Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                            intentUrl("https://sj.qq.com/myapp/detail.htm?apkName=com.baidu.BaiduMap");
                        }
                    } else {
                        Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                            intentUrl("https://sj.qq.com/myapp/detail.htm?apkName=com.autonavi.minimap");
                        }
                    }

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void intentUrl(String Url){
        Uri uri = Uri.parse(Url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
