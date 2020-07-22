package com.tecsun.jc.demo.invigilation.widget.surfaceview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Date         2018/6/24
 * Desc	        ${相机工具类}
 */
public class CameraUtils {

    public static Camera camera;

    public static final int FRONT = 1;//前置摄像头标记
    public static final int BACK = 2;//后置摄像头标记
    public static int currentCameraType = -1;//当前打开的摄像头标记

    /**
     * 检查是否有相机
     *
     * @param context
     * @return
     */
    public static boolean hasCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * 打开相机
     *
     * @return
     */
    public static Camera openCamera() {
//        camera = null;
        try {
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK); // attempt to get a Camera instance

//            LogUtil.INSTANCE.e("TAG","openCamera");
            if(camera == null){
//                LogUtil.INSTANCE.e("TAG",camera+"");
            }
            else{
//                LogUtil.INSTANCE.e("TAG",camera);
            }

        } catch (Exception e) {
            camera = null;
//            LogUtil.INSTANCE.e("TAG","openCamera Exception");
//            LogUtil.INSTANCE.e("TAG",e);
            // Camera is not available (in use or does not exist)
        }
        return camera; // returns null if camera is unavailable
    }


    @SuppressLint("NewApi")
    private Camera openCamera(int type){
        int frontIndex =-1;
        int backIndex = -1;
        int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for(int cameraIndex = 0; cameraIndex<cameraCount; cameraIndex++){
            Camera.getCameraInfo(cameraIndex, info);
            if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                frontIndex = cameraIndex;
            }else if(info.facing == Camera.CameraInfo.CAMERA_FACING_BACK){
                backIndex = cameraIndex;
            }
        }

        currentCameraType = type;
        if(type == FRONT && frontIndex != -1){
            return Camera.open(frontIndex);
        }else if(type == BACK && backIndex != -1){
            return Camera.open(backIndex);
        }
        return null;
    }

    public static Camera getCamera() {
        return camera;
    }
}
