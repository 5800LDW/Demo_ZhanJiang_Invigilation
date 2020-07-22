package com.tecsun.jc.base.utils.file;

import android.os.Environment;

import com.tecsun.jc.base.utils.log.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by LDW10000000 on 19/06/2017.
 */

public class FileUtil {

    public static String FOLDER_NAME = "BeiYe";
    public static String FOLDER_PATH = Environment.getExternalStorageDirectory() + File.separator + FOLDER_NAME;

    public static boolean createFile() {
        File file = new File(FOLDER_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                LogUtil.e(e);
            }
        }
        return file.exists();
    }

    public static boolean createFolder() {
        File dirFolder = new File(FOLDER_PATH);
        if (!dirFolder.exists()) {
            try {
                dirFolder.mkdirs();
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }
        return dirFolder.exists();
    }

    /**
     * 读取sd卡上指定后缀的所有文件,包括此路径下的其他文件夹里面的指定后缀文件
     *
     * @param files    返回的所有文件
     * @param filePath 路径(可传入sd卡路径)
     * @param suffix   后缀名称 比如 .gif
     * @return
     */
    public final static List<File> getSuffixFile(List<File> files, String filePath, String suffix) {
        File f = new File(filePath);
        if (!f.exists()) {
            return null;
        }
//        f.listFiles()
        File[] subFiles = f.listFiles();
        if (subFiles != null) {
            for (File subFile : subFiles) {
                if (subFile.isFile() && subFile.getName().endsWith(suffix)) {
                    // ToastUtil.showToast(mContext,subFile.getName());
                    files.add(subFile);
                } else if (subFile.isDirectory()) {
                    getSuffixFile(files, subFile.getAbsolutePath(), suffix);
                } else {
                    //非指定目录文件 不做处理
                }
            }
        }

        return files;
    }
}
