package com.example.syka;

import android.content.Context;
import com.example.syka.ProperTies;

import java.util.Properties;

/**
 * 读写配置属性类
 */

public class UrlString {

    private String contrastCameraName = "cameradirect";

    // 上传路径
    private String direct;

    public void setIPAddress(Context context) {
        Properties proper = ProperTies.getProperties(context);
        this.direct = proper.getProperty(contrastCameraName, "0");
    }

    public String setIPAddress(Context context, String keyValue) {
        String result = ProperTies.setProperties(context, contrastCameraName, keyValue);
        this.direct = keyValue;
        return result;
    }

    public String getDirect() {
        return this.direct;
    }
}