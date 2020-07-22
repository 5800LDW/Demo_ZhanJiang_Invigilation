package com.tecsun.jc.demo.invigilation.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by liudongwen on 2019/1/8.
 */

public class RotateImage {

   public static Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {

       try{
           Matrix m = new Matrix();
           m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
           float targetX, targetY;
           if (orientationDegree == 90) {
               targetX = bm.getHeight();
               targetY = 0;
           } else {
               targetX = bm.getHeight();
               targetY = bm.getWidth();
           }

           final float[] values = new float[9];
           m.getValues(values);

           float x1 = values[Matrix.MTRANS_X];
           float y1 = values[Matrix.MTRANS_Y];

           m.postTranslate(targetX - x1, targetY - y1);

           Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(), Bitmap.Config.ARGB_8888);

           Paint paint = new Paint();
           Canvas canvas = new Canvas(bm1);
           canvas.drawBitmap(bm, m, paint);


           return bm1;

       }catch (Exception e){
//           ExceptionUtil.handleException(e);
           e.printStackTrace();
           return null;
       }
    }

    /**
     * 选择变换
     *
     * @param origin 原图
     * @param alpha  旋转角度，可正可负
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    /**
     * 选择变换
     *
     * @param origin 原图
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap2(Bitmap origin) {
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(1, -1);  //产生镜像
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        origin.recycle();
        return newBM;
    }

}













































