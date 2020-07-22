package com.tecsun.jc.base.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class JavaStringTool {

    public static String replacePhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return phone;
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }

    public static String replaceIdNum(String idNum) {
        if (TextUtils.isEmpty(idNum)) {
            return idNum;
        }
        return idNum.replaceAll("(\\d{6})\\d{10}(\\w{2})","$1**********$2");
    }

    public static boolean isSecret(String str){
        Pattern p = compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,15}$"); // 验证密码
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String replaceName(String name) {
        if (TextUtils.isEmpty(name) || name.length() == 1) {
            return name;
        }
        return "*" + name.substring(1);
    }
}
