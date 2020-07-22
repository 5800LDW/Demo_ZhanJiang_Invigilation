package com.tecsun.face;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.module_facerecognition.R;
import com.example.syka.dbHelper;
import com.sykean.SykeanFD;
import com.sykean.sykabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.serialport.api.SerialPortUtil.TAG;

/**
 * Created by zhangchao on 18-1-9.
 */

public class Constants_ldw {
    //region key { //此处是demokey，集成时请客户替换为自己的key
    public static final String KEY = "eyJrZXlfaWQiOjE0NCwidGltZXN0YW1wIjoxNTQwNTQyMjgyfQ==";   //用来验证是否在有效期内
    public static final String SECRET = "655c252546c8f2115f9618a10853e8ee";   //用来验证是否在有效期内
    static SykeanFD facedetect;
    static long[] facehandle;
    //endregion

    public static byte[] getPixelsBGR(Bitmap image) {
        // calculate how many bytes our image consists of
        int bytes = image.getByteCount();

        ByteBuffer buffer = ByteBuffer.allocate(bytes); // Create a new buffer
        image.copyPixelsToBuffer(buffer); // Move the byte data to the buffer

        byte[] temp = buffer.array(); // Get the underlying array containing the data.

        byte[] pixels = new byte[(temp.length/4) * 3]; // Allocate for BGR

        // Copy pixels into place
        for (int i = 0; i < temp.length/4; i++) {

            pixels[i * 3] = temp[i * 4 + 2];		//B
            pixels[i * 3 + 1] = temp[i * 4 + 1]; 	//G
            pixels[i * 3 + 2] = temp[i * 4 ];		//R

        }

        return pixels;
    }

    public static int initFace(Context context){
        byte[] returnInfo = new byte[64];
        facedetect = new SykeanFD();
        int ret = facedetect.GetExtraInfo("version", returnInfo);

        String str = new String(returnInfo);
        Log.i(TAG, "GetExtraInfo " + ret +"-"+ str);
        facehandle= new long[4];
        ret = facedetect.CreateIns(facehandle,context.getFilesDir().getAbsolutePath() + File.separator + "facedata/model/","com.example.syka");
        return ret;
    }

    public static void registerAndSaveFace( Context context, dbHelper userdb, Handler mHandler){
        initFace(context);

        int ret = 0;
        int facetlost = 0;
        float MinQuality = 100f;

        Bitmap showBit;
         showBit = BitmapFactory.decodeResource(context.getResources(),(R.drawable.face_ldw));

        int picwith = showBit.getWidth();
        int pichight = showBit.getHeight();
        byte[] rgb32forcopy = new byte[picwith * pichight*4];
        byte[] irrgb32forcopy = new byte[picwith * pichight*4];
        byte[] Maxrgb32forcopy = new byte[picwith * pichight*4];
        byte[] Maxrgb24 = new byte[picwith * pichight * 3];
        byte[] gray = new byte[picwith * pichight];
        byte[] irgray = new byte[picwith * pichight];
        byte[] facefeaterraw = new byte[128 * 4];
        int[] rectfinal = new int[40];
        int[] mouth_s = new int[2];
        float[] heard_h = new float[2];
        float[] heard_v = new float[2];
        float[] blurness = new float[2];
        float[] landmark = new float[166];
        float[] landmarkMax = new float[166];
        float[] facefeater = new float[128];
        float[] facefeaterdet = new float[128];
        float[] facelist;
        int[] rocrect = new int[4];
        int[] facerect  = new int[4];
        int[] Maxfacerect = new int[4];
        int[] last_facerect = new int[4];
        int facecount;
        sykabase facedebase = new sykabase();

        if (showBit==null){
            return;
        }

//        facedebase.ConverArgbToRgb(showBit, rgb24, gray, showBit.getWidth(), showBit.getHeight(), 1);
        byte[] rgb24 = getPixelsBGR(showBit);

        System.arraycopy(rgb24, 0, rgb32forcopy, 0, rgb24.length);

        Message message;
        ret = facedetect.DetectFaceRgb(facehandle, rgb24, picwith, pichight, rectfinal,1);
        Log.i("Enroll","Enroll detect "+ret);
        if(ret > 0)
        {
            facetlost = 0;
            Log.e("rectfinal","Enroll face  "+rectfinal[0]+"-"+rectfinal[1]+"-"+rectfinal[2]+"-"+rectfinal[3]);
            facedetect.AliveActionDetect(facehandle, rgb24, picwith, pichight, rectfinal,
                    mouth_s, heard_h, heard_v,blurness,landmark,rocrect);
//            Log.i("Enroll","Enroll blurness: "+blurness[0]+"time:"+frameCount);
            facerect[0] = rocrect[0];
            facerect[1] = rocrect[1];
            facerect[2] = rocrect[2];
            facerect[3] = rocrect[3];
            if(blurness[0] >= 50)
            {
//                detect = false;
            }
//            frameCount++;
//            Log.i("Enroll","Enroll MinQuality: "+MinQuality+"timer:"+frameCount);

            if(MinQuality > blurness[0])
            {
                MinQuality  = blurness[0];
                System.arraycopy(rgb24,0,Maxrgb24,0,Maxrgb24.length);
                System.arraycopy(landmark,0,landmarkMax,0,landmark.length);

                System.arraycopy(rgb32forcopy,0,Maxrgb32forcopy,0,Maxrgb32forcopy.length);
                Maxfacerect[0] = rectfinal[0];
                Maxfacerect[1] = rectfinal[1];
                Maxfacerect[2] = rectfinal[2];
                Maxfacerect[3] = rectfinal[3];
            }
//            Log.i(TAG,"Enroll proc "+ MinQuality+"timer "+frameCount);
//            if(frameCount < 10)
//            {
//                detect = false;
//            }
            Log.i(TAG,"Enroll Go "+ MinQuality);
            MinQuality = 100;
//            frameCount = 0;
            ret = facedetect.ExtractFeatureBGR(facehandle, facefeater, Maxrgb24, picwith, pichight, null, Maxfacerect,landmarkMax);
            if (ret == 0)
            {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-S");
                String timestr = formatter.format(new Date());
                String ID = "/sdcard/facedata/" + timestr + "_" + String.valueOf(Maxfacerect[0]) + "_" +
                        String.valueOf(Maxfacerect[1]) + "_" + String.valueOf(Maxfacerect[2]) + "_" + String.valueOf(Maxfacerect[3]) + ".jpeg";

                facedebase.FloatToChar(facefeater, facefeaterraw);
                userdb.insert(ID, timestr, facefeaterraw);
                File file=new File("/sdcard/facedata/Alive/" + timestr);
                Log.i(TAG,"mkdir /sdcard/facedata/Alive/" + timestr);
                file.mkdir();
                file = null;
                file=new File("/sdcard/facedata/Nolive/" + timestr);
                Log.i(TAG,"mkdir /sdcard/facedata/Nolive/" + timestr);
                file.mkdir();
                facelist = null;
                Cursor cursor = userdb.select("all");
                facecount = cursor.getCount();
                Log.e(TAG, String.format("获取记录数%d\n", facecount));
                if(facecount>0)
                {
                    int offset = 0;
                    facelist = new float[128*facecount];
                    for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
                    {
                        int modColumn = cursor.getColumnIndex("module");
                        byte[] mod = cursor.getBlob(modColumn);
                        facedebase.CharToFloat(mod, facefeaterdet);
                        System.arraycopy(facefeaterdet, 0, facelist, offset * 128, 128);
                        offset++;
                        Log.e(TAG, String.format("获取模板%d\n",offset));
                    }
                }
//                Bitmap bmpfs = Bitmap.createBitmap(picwith,pichight, Bitmap.Config.ARGB_8888);
//                facedebase.CopyToImage(bmpfs,Maxrgb32forcopy,picwith,pichight);
                FileOutputStream fileOutStream = null;
                try {
                    fileOutStream = new FileOutputStream(ID);
                    showBit.compress(Bitmap.CompressFormat.JPEG, 80, fileOutStream);  //把位图输出到指定的文件中
                    fileOutStream.flush();
                    fileOutStream.close();
                    showBit.recycle();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(context,"照片注册成功",Toast.LENGTH_LONG).show();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                message = new Message();
                message.what = 3;
                mHandler.sendMessageDelayed(message,2000);
            }
//            detecttimers = 0;
        }
        else
        {
            last_facerect[0] = 0;
            last_facerect[1] = 0;
            last_facerect[2] = 0;
            last_facerect[3] = 0;

            facerect[0] = 0;
            facerect[1] = 0;
            facerect[2] = 0;
            facerect[3] = 0;
            facetlost++;
            if(facetlost >= 3) {
//                frameCount = 0;
            }
            Log.i(TAG,"Enroll find face");
        }

    }

}
