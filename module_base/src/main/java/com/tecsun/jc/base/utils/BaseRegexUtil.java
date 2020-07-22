package com.tecsun.jc.base.utils;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 正则工具类
 * Created by _Smile on 2017/5/11.
 */
public class BaseRegexUtil {

    /**
     * 手机号验证
     *
     * @param str 手机号字符串
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = compile("^[1][3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isSecret(String str) {
        Pattern p = compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,15}$"); // 验证密码
        Matcher m = p.matcher(str);
        return m.matches();
    }

    // 判断邮箱
    public static boolean isEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }

//        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
//        Pattern p = compile(str);
//        Matcher m = p.matcher(email);
//        return m.matches();

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}


















