package com.tecsun.jc.base.utils;

import android.content.Context;
import com.tecsun.jc.base.utils.log.LogUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 对象缓存到本地的辅助类
 */
public class DataCache2LocalFileHelper {
	
	/**
	 * 将对象序列化为本地文件
	 * @param context 上下文，要求为 Activity 实例
	 * @param obj 对象，不能为空
	 * @param filename 文件名，不能为空
	 * @return 保存是否成功
	 */
	public static boolean serialize2File(Context context, Object obj, String filename) {
		try {
			FileOutputStream fos = context.openFileOutput(filename, 0);
			ObjectOutputStream os =  new ObjectOutputStream(fos);  
			os.writeObject(obj);
			os.close();   
			return true;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		return false;
	}
	
	/**
	 * 从本地文件中加载序列化过的对象
	 * @param context 上下文，要求为 Activity 实例
	 * @param filename 文件名，不能为空
	 * @return 对象
	 */
	public static Object loadSerializedObject(Context context, String filename) {
		if (!existFile(context, filename)) {

			LogUtil.e(">>>>>>>>>>>>>> 文件不存在");
			return null;
		}
		
		try {			
			FileInputStream fis = context.openFileInput(filename);
			ObjectInputStream oin = new ObjectInputStream(fis);
			Object obj = oin.readObject();
			oin.close();
			return obj;
		} catch (Exception e) {
			LogUtil.e(">>>>>>>>>>>>>> 文件存在, 但报错了!!");
			LogUtil.e(e);
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 移除序列化文件
	 * @param context 上下文，要求为 Activity 实例
	 * @param filename 文件名，不能为空
	 * @return
	 */
	public static boolean removeFile(Context context, String filename) {
		try {
			return context.deleteFile(filename);
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		return false;
	}
	
	/**
	 * 检查是否存在序列化过的对象文件
	 * @param context 上下文，要求为 Activity 实例
	 * @param filename 文件名，不能为空
	 * @return
	 */
	private static boolean existFile(Context context, String filename) {
		String[] files = context.fileList();
		for (String file : files) {
			if (filename.equals(file.toLowerCase())) {
				return true;
			}
		}
		
		return false;
	}
}
