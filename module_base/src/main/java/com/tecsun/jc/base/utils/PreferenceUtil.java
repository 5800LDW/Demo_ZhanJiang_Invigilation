package com.tecsun.jc.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.tecsun.jc.base.utils.log.LogUtil;

/**
 * 
 * @author dongwenliu
 * 
 */
public class PreferenceUtil {

	static SharedPreferences preferences;
	static Editor editor;

	public static boolean set(Context context, String key, String value) {
		setObjectNotNull(context);
		try {
			editor.putString(key, value);
			editor.commit();
		} catch (Exception e) {
			LogUtil.e(e);
			// fail 
			return false;
		}
		// success
		return true;
	}

	public static String get(Context context, String key, String value) {
		setObjectNotNull(context);
		String str = preferences.getString(key, value);
		return str;
	}


	/**
	 * 20180918 不需要value,默认返回""
	 *
	 * @param context
	 * @param key
	 * @return
	 */
	public static String get(Context context, String key) {
		setObjectNotNull(context);
		String str = preferences.getString(key, "");
		return str;
	}
	
	private static void setObjectNotNull(Context context) {
		if (preferences == null) {
			preferences = getPreferences(context);
			editor = preferences.edit();
		}
	}

	private static SharedPreferences getPreferences(Context context) {

		return context.getSharedPreferences("$_ElectronicCard$", Context.MODE_PRIVATE);
	}


	/**
	 * 清除指定key 的内容
	 * @param key
	 */
	public static void remove(Context context,String key){
		setObjectNotNull(context);
		Editor editor = preferences.edit();
		editor.remove(key).commit();
	}

}
