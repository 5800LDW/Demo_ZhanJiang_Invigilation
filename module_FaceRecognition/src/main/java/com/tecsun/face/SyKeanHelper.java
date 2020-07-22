package com.tecsun.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tecsun.jc.base.utils.log.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.tecsun.face.Constants.getPixelsBGR;

/**
 * Created by Zwt <br>
 */
public class SyKeanHelper {

    private static final String TAG = "SyKeanHelper";
    //人脸识别工具
    private com.sykean.SykeanFD mSyKeanFd;
    //人脸识别用到的句柄
    private long[] syHandle = new long[4];
    //最低相似度
    private static int MIN_SIMILARITY = 86;
    //人脸识别库成功标志
    private static final int FME_OK = 0;

    private Context context;
    private volatile boolean hasInit;

    private float[] IDCardCharacteristic;


    public int getMinSimilarity() {
        return MIN_SIMILARITY;
    }

    public void setMinSimilarity(int minSimilarity) {
        if (minSimilarity < 0) {
            MIN_SIMILARITY = 0;
        } else if (minSimilarity > 100) {
            MIN_SIMILARITY = 100;
        } else {
            MIN_SIMILARITY = minSimilarity;
        }
    }

    public boolean myIsHasInit(){
        return hasInit;
    }


    public synchronized void init(@NonNull Context context) {
        this.context = context;
        if (hasInit) {
            return;
        }
        initModel();
        mSyKeanFd = new com.sykean.SykeanFD();
        byte[] returnInfo = new byte[64];
        mSyKeanFd.GetExtraInfo("version", returnInfo);
        Log.e(TAG, "Sy keanFD version : " + new String(returnInfo));

        //初始化人脸识别工具
        //人脸模型存放位置
        final String modelDir = context.getFilesDir().getAbsolutePath() + File.separator + "facedata/model/";
        int result = mSyKeanFd.CreateIns(syHandle, modelDir, "com.tecsun.face");
        if (result == FME_OK) {
            mSyKeanFd.GetFeatureLen(syHandle);
            mSyKeanFd.SetParams(syHandle, 1.3f, 0.6f, 64, 0);
            hasInit = true;
            Log.e(TAG, "人脸识别初始化成功!");
        } else {
            Toast.makeText(context, "人脸识别初始化失败，请重启设备", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "人脸识别初始化失败：" + result);
        }
    }

    public void dispose() {
        if (hasInit) {
            mSyKeanFd.DestroyIns(syHandle);
            hasInit = false;
        }
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>> hasInit = " + hasInit);
    }

    private void initModel() {
        String path = Environment.getExternalStorageDirectory().getPath();
        copyAssetsDir2Phone(context, "facedata");
        File file = new File(path + File.separator + "facedata");
        file.mkdirs();
        file = new File(path + File.separator + "facedata/Alive");
        file.mkdirs();
        file = new File(path + File.separator + "facedata/Nolive");
        file.mkdirs();
        file = new File(path + File.separator + "batchEnroll");
        file.mkdirs();
        file = new File(path + File.separator + "FailEnroll");
        file.mkdirs();

        String udisk = findDirs("/storage");
        String absudisk;
        if (!udisk.equals("")) {
            absudisk = "/storage/" + udisk;
            file = new File(absudisk + "/Alive");
            file.mkdirs();
            file = new File(absudisk + "/Nolive");
            file.mkdirs();
            file = new File(absudisk + "/IrAlive");
            file.mkdirs();
            file = new File(absudisk + "/capture");
            file.mkdirs();
            Log.i(TAG, "mkdir " + absudisk + "/capture");
            file = new File(absudisk + "/capture/human");
            file.mkdirs();

            file = new File(absudisk + "/capture/nirphoto");
            file.mkdirs();

            file = new File(absudisk + "/capture/visphoto");
            file.mkdirs();
            file = new File(absudisk + "/capture/video");
            file.mkdirs();

        } else {
            file = new File(path + File.separator + "capture");
            file.mkdirs();

            file = new File(path + File.separator + "capture/human");
            file.mkdirs();

            file = new File(path + File.separator + "capture/nirphoto");
            file.mkdirs();

            file = new File(path + File.separator + "capture/visphoto");
            file.mkdirs();
            file = new File(path + File.separator + "capture/video");
            file.mkdirs();
        }
    }

    private static String findDirs(String filePath) {
        File file = new File(filePath);
        if (file.isDirectory()) { // 否则如果它是一个目录
            File[] files = file.listFiles();
            if (files != null) {
                for (File value : files) { // 遍历目录下所有的文件
                    if ((!value.getName().equals("emulated")) && (!value.getName().equals("self")) && (value.isDirectory())) {
                        Log.i(TAG, value.getName());
                        return value.getName();
                    }
                }
            }
        }
        return "";
    }

    private static void copyAssetsDir2Phone(Context activity, String filePath) {
        try {
            String[] fileList = activity.getAssets().list(filePath);
            if (fileList != null) {
                if (fileList.length > 0) {//如果是目录
                    File file = new File(activity.getFilesDir().getAbsolutePath() + File.separator + filePath);
                    file.mkdirs();//如果文件夹不存在，则递归
                    for (String fileName : fileList) {
                        filePath = filePath + File.separator + fileName;

                        copyAssetsDir2Phone(activity, filePath);
                        filePath = filePath.substring(0, filePath.lastIndexOf(File.separator));
                        Log.e("oldPath", filePath);
                    }
                } else {//如果是文件

                    InputStream inputStream = activity.getAssets().open(filePath);
                    String InputMd5 = MD5Util.getMD5FromIS(inputStream);
                    File file = new File(activity.getFilesDir().getAbsolutePath() + File.separator + filePath);
                    if (!file.exists() || file.length() == 0) {

                        FileOutputStream fos = new FileOutputStream(file);
                        int len = -1;
                        byte[] buffer = new byte[1024];
                        while ((len = inputStream.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
                        fos.flush();
                        inputStream.close();
                        fos.close();
                        buffer = null;
                        Log.i(TAG, "模型文件复制完毕");
                    } else {
                        FileInputStream inputStream2 = new FileInputStream(file);
                        String OutputMd5 = MD5Util.getMD5FromFIS(inputStream2);
                        inputStream2.close();
                        if (TextUtils.equals(InputMd5, OutputMd5)) {
                            deleteFile(file);
                            file = new File(activity.getFilesDir().getAbsolutePath() + File.separator + filePath);
                            FileOutputStream fos = new FileOutputStream(file);
                            int len = -1;
                            byte[] buffer = new byte[1024];
                            while ((len = inputStream.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                            }
                            fos.flush();
                            inputStream.close();
                            fos.close();
                            buffer = null;
                            Log.i(TAG, "模型文件更新完毕");
                        } else {
                            Log.i(TAG, "模型文件已存在");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                // 设置属性:让文件可执行，可读，可写
                file.setExecutable(true, false);
                file.setReadable(true, false);
                file.setWritable(true, false);
                file.delete(); // delete()方法
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File[] files = file.listFiles(); // 声明目录下所有的文件 files[];
                if (files != null) {
                    for (File value : files) { // 遍历目录下所有的文件
                        deleteFile(value); // 把每个文件 用这个方法进行迭代
                    }
                }
                file.delete();
            }
            Log.i("deleteFile", file.getName() + "成功删除！！");
        } else {
            Log.i("deleteFile", file.getName() + "不存在！！！");
        }
    }


    @Nullable
    public float[] detectBitmapOnSmallBitmap(@Nullable Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        //1、处理图片
        Bitmap target = Bitmap.createBitmap(350, 400, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
//        canvas.drawColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.ANTI_ALIAS_FLAG));
//        canvas.drawBitmap(bitmap,240,320,new Paint(Paint.ANTI_ALIAS_FLAG));

        return detectBitmap(target);
    }

    @Nullable
    public float[] detectBitmapOnSmallBitmap3(@Nullable Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        return detectBitmap(bitmap);
    }


    /**
     * 查找特征
     *
     * @param bitmap 图
     * @return 特征
     */
    @Nullable
    private float[] detectBitmap(@Nullable Bitmap bitmap) {
        float[] characteristic;
        if (bitmap == null) {
            return null;
        }

        //1、处理图片
        //bitmap转换为RGB24
        int picWith = bitmap.getWidth();
        int picHeight = bitmap.getHeight();
        Log.e(TAG, "picWidth : " + picWith + " , picHeight : " + picHeight);
        byte[] rgb24 = getPixelsBGR(bitmap);

        //2、定位人脸
        //存放定位
        int[] location = new int[40];
        int detectResult = mSyKeanFd.DetectFaceRgb(syHandle, rgb24, picWith, picHeight, location, 1);
        if (detectResult < 0) {
            Log.e(TAG, "detect  photo failed , error code : " + detectResult);
            return null;
        } else {
            Log.e(TAG, "detect photo success ");
        }
        //3、活体检测
        int[] mouth_s = new int[2];
        float[] heard_h = new float[2];
        float[] heard_v = new float[2];
        //清晰度 <80为高清晰度
        float[] blurness = new float[2];

        //存放人脸关键点信息 活体检测后获得数据
        float[] landMark = new float[166];
        //人脸精确坐标
        int[] preciseLocation = new int[4];
        int aliveResult = mSyKeanFd.AliveActionDetect(syHandle, rgb24, picWith, picHeight, location, mouth_s, heard_h, heard_v, blurness, landMark, preciseLocation);
        if (aliveResult != 0) {
            Log.e(TAG, "活体检测失败 ：" + aliveResult);
            return null;
        }
        //4、获取人脸特征
        characteristic = new float[128];
        int featureResult = mSyKeanFd.ExtractFeatureBGR(syHandle, characteristic, rgb24, picWith, picHeight, null, location, landMark);
        if (featureResult != 0) {
            Log.e(TAG, "获取脸部特征失败 ：" + featureResult);
        }
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>  SyKeanHelper detectBitmap  查找特征");

        return characteristic;
    }

    public boolean canFindFace(@Nullable Bitmap bitmap) {
        if (bitmap == null) return false;
        int picWith = bitmap.getWidth();
        int picHeight = bitmap.getHeight();
//        Log.e(TAG, "picWidth : " + picWith + " , picHeight : " + picHeight);
        byte[] rgb24 = getPixelsBGR(bitmap);

        //2、定位人脸
        //存放定位
        int[] location = new int[40];
        int detectResult = mSyKeanFd.DetectFaceRgb(syHandle, rgb24, picWith, picHeight, location, 1);
        if (detectResult < 0) {
            Log.e(TAG, "canFindFace >>>>>>>>>>  detect photo failed , error code : " + detectResult);
            return false;
        } else {
            Log.e(TAG, "canFindFace >>>>>>>>>>  detect photo success ");
            return true;
        }
    }

    /***比对有没有人脸*/
    public Bitmap findFace(@Nullable Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int picWith = bitmap.getWidth();
        int picHeight = bitmap.getHeight();
//        Log.e(TAG, "picWidth : " + picWith + " , picHeight : " + picHeight);
        byte[] rgb24 = getPixelsBGR(bitmap);

        //定位人脸
        //存放定位
        int[] location = new int[40];
        int detectResult = mSyKeanFd.DetectFaceRgb(syHandle, rgb24, picWith, picHeight, location, 1);
        if (detectResult < 0) {
            Log.e(TAG, "findFace >>>>>>>>>>>>> detect photo failed , error code : " + detectResult);
            return null;
        } else {
            Log.e(TAG, "findFace  >>>>>>>>>>>> detect photo success ");
            return Bitmap.createBitmap(bitmap, location[0], location[1], location[2], location[3]);
        }
    }

    /***比对出有人脸后, 然后调用下面的方法进行处理*/
    public boolean comparison(Bitmap bitmap) {
        if (bitmap == null) {
            Log.e(TAG, "bitmap == null");
            return false;
        }
        if (IDCardCharacteristic == null) {
            Log.e(TAG, "没有先注册证件照");
            return false;
        }
        float[] characteristic = detectBitmap(bitmap);
        if (characteristic == null) {
            return false;
        }
        int similarity = mSyKeanFd.Match(syHandle, IDCardCharacteristic, characteristic, 128, 0);
        Log.e(TAG, "相似度：" + similarity);
        return similarity > MIN_SIMILARITY;
    }

    /***比对出有人脸后, 也可以调用下面的方法进行处理,返回调用的分数*/
    public int comparisonGetScore(Bitmap bitmap) {
        if (bitmap == null) {
            Log.e(TAG, "bitmap == null");
            return -1;
        }
        if (IDCardCharacteristic == null) {
            Log.e(TAG, "没有先注册证件照");
            return -1;
        }
        float[] characteristic = detectBitmap(bitmap);
        if (characteristic == null) {
            return -1;
        }
        int similarity = mSyKeanFd.Match(syHandle, IDCardCharacteristic, characteristic, 128, 0);
        Log.e(TAG, "相似度：" + similarity);
        return similarity;
    }

    /***这是第一步把图片放进去比对*/
    public boolean registerPhotoForSFZPic(@Nullable Bitmap bitmap) {
        IDCardCharacteristic = detectBitmapOnSmallBitmap3(bitmap);
        return IDCardCharacteristic != null;
    }

    /***这是第一步把图片放进去比对*/
    public boolean registerPhoto(@Nullable Bitmap bitmap) {
        IDCardCharacteristic = detectBitmapOnSmallBitmap2(bitmap);
        return IDCardCharacteristic != null;
    }

    @Nullable
    private float[] detectBitmapOnSmallBitmap2(@Nullable Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        //1、处理图片
        Bitmap target = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
//        canvas.drawColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.ANTI_ALIAS_FLAG));
//        canvas.drawBitmap(bitmap,240,320,new Paint(Paint.ANTI_ALIAS_FLAG));

        return detectBitmap(target);
    }


    public Bitmap ldwDoWith(@Nullable Bitmap bitmap) {
        if(bitmap == null){
            return null;
        }
        //1、处理图片
        Bitmap target = Bitmap.createBitmap(350, 400, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.ANTI_ALIAS_FLAG));
//        canvas.drawBitmap(bitmap,240,320,new Paint(Paint.ANTI_ALIAS_FLAG));

        return target;
    }

}
