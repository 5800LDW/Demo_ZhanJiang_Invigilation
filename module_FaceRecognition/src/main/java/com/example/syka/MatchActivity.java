package com.example.syka;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.module_facerecognition.R;
import com.sykean.SykeanFD;
import com.sykean.sykabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Math.abs;

//import com.ulsee.ULSeeLiveMgr;


public class MatchActivity extends Activity implements SurfaceHolder.Callback, Camera.PictureCallback {
    SurfaceView vSurfaceView = null;
    ImageView Surfaceimage = null;
    private TextView seekpos;
    private UrlString urlString;
    VerticalSeekBar seekBars;
    long LastIrFaceTime = 0;
    int orgwidth = 640;
    int Action = 0;
    int IrAction = 0;
    int captures_start = 0;
    int ircontrol = 0;
    int levelthrash = 86;
    int alivethrash = 80;
    private ReadWriteLock rwl;
    private ReadWriteLock rwl2;
    private ReadWriteLock rwl3;
    private final Semaphore semaphore = new Semaphore(1);
    SurfaceHolder vSurfaceHolder = null;
    private TextView info;
    private TextView numbershow;
    private TextView aliveshow;
    private TextView actionshow;
    private TextView timeshow;

    private Spinner cameraselects;
    private Spinner cameradegreed;
    private Spinner hflips;
    private Spinner aliveflags;

    private TextView showdirect;
    private TextView showcamera;
    private TextView showflip;
    private TextView showlive;
    long timecount = 0;
    int aliveflag = 0;

    String absudisk = "";
    private Button enroll;
    static MediaPlayer mp;
    Bitmap showBit;
    Bitmap showIrBit;
    Bitmap aliveBit;
    Bitmap aliveIrBit;
    long LowLevelModeTimer = 0;
    boolean LowLevelMode = false;
    public final int FME_OK = 0;//成功
    public final int FME_INVALID_PRIMIT = -10002;//无效授权
    public final int FME_INVALID_INSTANCE = -10001;//无效实例
    public final int FME_INVALID_FACE_ID = -10000;//无效人脸ID
    public final int FME_BAD_ALLOC = -9999;//分配内存失败
    public final int FME_FACE_DETECT_INIT_MODEL_FAILED = -9998;//初始化检测模型失败
    public final int FME_FACE_EXTRACT_INIT_MODEL_FAILED = -9997; //初始化提取模型失败
    public final int FME_INVALID_INPUT_ARGUMENTS = -9996;//输入参数错误
    public final int FME_INVALID_OUTPUT_ARGUMENTS = -9995;//输出参数错误
    public final int FME_INVALID_INPUT_CORE = -9994;//无效实例
    public final int FME_FACE_DETECT_NONE = -9987;//没有检测到人脸
    public final int FME_FACE_INVALID_RECT = -9983;//无效输入人脸区域

    int cameraw = 640;
    int camerah = 480;

    int picwith = 640;
    int pichight = 480;

    int ircameraw = 640;
    int ircamerah = 480;

    int irpicwith = 640;
    int irpichight = 480;

    int facecount;
    int facecountsuccess = 0;
    int facecountfail = 0;
    int indexrgb = 0;
    int indexir = 0;
    boolean detect = false;
    boolean irdetect = false;
    byte[] rgb32;
    byte[] rgb32test;
    byte[] irrgb32;
    byte[] rgb24;
    byte[] irrgb24;
    byte[] rgb32forcopy;
    byte[] irrgb32forcopy;
    byte[] Maxrgb32forcopy;
    byte[] Maxrgb24;
    int[] Maxfacerect;
    byte[] gray;
    byte[] irgray;
    byte[] rgb24_Test;
    byte[] Ir24_Test;
    int[] last_facerect;

    int[] alive_facerect;
    int[] facerect;
    int[] irfacerect;
    int[] rectfinal;
    int[] irrectfinal;
    int[] mouth_s;
    float[] heard_h;
    float[] heard_v;
    float[] blurness;
    int[] rocrect;
    float[] landmark;
    float[] landmarkMax;
    long changeirtime = 0;
    int[] irmouth_s;
    float[] irheard_h;
    float[] irheard_v;
    float[] irblurness;
    int[] irrocrect;
    float[] irlandmark;
    float[] irlandmarkMax;

    float[] fQuality;
    float[] facefeater;

    float[] faceirfeater;
    float[] alivefaceirfeater;
    int[] outside;
    int[] fscore;
    int[] iroutside;
    int[] irfscore;
    float[] facefeaterdet;
    int maxscore;
    int irmaxscore;
    byte[] facefeaterraw;
    float[] facelist;
    Thread facethread;
    Thread faceirthread;
    boolean last_status = false;
    int frameCount = 0;
    boolean Running;
    boolean isPause;
    boolean capture_status;
    boolean saveyuv = true;
    long[] facehandle;
    long[] faceirhandle;
    int detecttimers;
    int showdelay = 0;
    int direct = 0;//切换摄像头角度时使用
    int cmdirect = 0;
    int cameraselect = 0;//优先选择摄像头 0 front 1 back
    int aliveindex = 0;//活体检测 1 否则0
    int hflip = 1; //0 正常 1 左右镜像

    int capturemode = 0; //0 真人 1 彩色照片 2 黑白照片 3视频
    dbHelper userdb;
    static private Handler mHandler;
    String TAG = "syface";
    private Camera camera;
    private Camera camerair;
    SykeanFD facedetect;
    SykeanFD faceirdetect;
    sykabase facedebase;
    sykabase faceirdebase;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        setContentView(R.layout.activity_match);
        Button btn1 = (Button) findViewById(R.id.startenroll);
        //给btn1绑定监听事件
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessage(message);
            }
        });
        //btn1.setClickable(false);
        Button btn2 = (Button) findViewById(R.id.exit);
        //给btn1绑定监听事件
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 给bnt1添加点击响应事件
                finish();
            }
        });
        //btn2.setClickable(false);
        enroll = (Button) findViewById(R.id.enrollall);
        //给btn1绑定监听事件
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 5;
                Log.i(TAG,"Send 5");
                mHandler.sendMessage(message);
            }
        });
        //enroll.setClickable(false);
        Button btn3 = (Button) findViewById(R.id.clear);
        //给btn1绑定监听事件
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 4;
                mHandler.sendMessage(message);
            }
        });
        //btn3.setClickable(false);
        final Button captures = (Button)findViewById(R.id.capturebmp) ;
        captures.setBackgroundColor(Color.GRAY);
        //给btn1绑定监听事件
        captures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Message message = new Message();
            if(capture_status)
            {
                message.what = 10;
                capture_status = false;
            }
            else
            {
                message.what = 9;
                capture_status = true;
            }
            mHandler.sendMessage(message);
            }
        });

        seekpos = (TextView) findViewById(R.id.textView7);
        seekpos.setText(Integer.toString(alivethrash));
        seekBars = (VerticalSeekBar)findViewById(R.id.vSeekBar);
        seekBars.setProgress(alivethrash);
        seekBars.setOnSeekBarChangeListener(verticalSeekBarChangeListener);
        vSurfaceView = (SurfaceView) findViewById(R.id.faceview);
        Surfaceimage = (ImageView) findViewById(R.id.faceimage);
        vSurfaceHolder = vSurfaceView.getHolder();
        vSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        vSurfaceHolder.addCallback(this);
        vSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        Surfaceimage.setBackgroundColor(Color.WHITE);
        info = (TextView) findViewById(R.id.matchinfo);
        numbershow = (TextView) findViewById(R.id.number);
        timeshow = (TextView) findViewById(R.id.losttime);
        aliveshow = (TextView) findViewById(R.id.aliveshow);
        actionshow = (TextView) findViewById(R.id.actionshow);

        showdirect = (TextView) findViewById(R.id.showdirect);
        showcamera = (TextView) findViewById(R.id.showcamera);
        showflip = (TextView) findViewById(R.id.showflip);
        showlive = (TextView) findViewById(R.id.showlive);
        cameraselects = (Spinner) findViewById(R.id.camerafont);
        cameradegreed = (Spinner) findViewById(R.id.cameradegree);

        aliveflags = (Spinner) findViewById(R.id.aliveflag);

        hflips = (Spinner) findViewById(R.id.hflips);
        getDisplayInfomation();


        SharedPreferences read = getSharedPreferences("syfaceinfo", MODE_WORLD_READABLE);


        String value = read.getString("ir", "0");
        indexir = Integer.parseInt(value);

        value = read.getString("rgb", "0");
        indexrgb = Integer.parseInt(value);

//        value = read.getString("front", "0");
        cameraselect = Integer.parseInt(value);
        Log.i(TAG,"cameraselect:"+cameraselect);
        cameraselects.setSelection(cameraselect,true);

        //showcamera.setText(cameraselects.getItemAtPosition(cameraselect).toString());
        cameraselects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cameraselect = position;
                Log.i(TAG,"cameraselect:"+cameraselect);
                //showcamera.setText(cameraselects.getItemAtPosition(cameraselect).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        value = read.getString("degreed", "3");
        direct = Integer.parseInt(value);
        if(direct == 0)
        {
            cmdirect = 0;
        }
        else if(direct == 1)
        {
            cmdirect = 90;
        }
        else if(direct == 2)
        {
            cmdirect = 180;
        }
        else
        {
            cmdirect = 270;
        }
        Log.i(TAG,"cameradegreed:"+direct);
        cameradegreed.setSelection(direct,true);
        Log.i(TAG,"cameradegreed:"+cameradegreed.getItemAtPosition(direct).toString());
        //showdirect.setText(cameradegreed.getItemAtPosition(direct).toString());
        cameradegreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                direct = position;
                //showdirect.setText(cameradegreed.getItemAtPosition(direct).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        value = read.getString("alive", "0");
        aliveindex = Integer.parseInt(value);
        aliveflags.setSelection(aliveindex,true);
        //showlive.setText(aliveflags.getItemAtPosition(aliveindex).toString());
        Log.i(TAG,"aliveflags:"+aliveflags.getItemAtPosition(aliveindex).toString());
        aliveflags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aliveindex = position;
                //showlive.setText(aliveflags.getItemAtPosition(aliveindex).toString());
                Log.i(TAG,"aliveflags:"+aliveflags.getItemAtPosition(aliveindex).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        value = read.getString("mode", "0");
        capturemode = Integer.parseInt(value);
        hflips.setSelection(capturemode);
        //showflip.setText(hflips.getItemAtPosition(hflip).toString());
        hflips.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                capturemode = position;
                //showflip.setText(hflips.getItemAtPosition(hflip).toString());
                Log.i(TAG,"capturemode "+capturemode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String ID;
                Bitmap bt;
                int ranf = 0;
                Log.i(TAG, String.format("接收消息:%d\n", msg.what));
                if(!Running)
                {
                    return;
                }
                switch (msg.what)
                {
                    case 0:
                        ID = (String)msg.obj;
                        Log.i(TAG, ID);
                        if(fileIsExists(ID)) {
                            bt = BitmapFactory.decodeFile(ID);
                            if(bt.getHeight()>bt.getWidth())
                            {
                                ranf =bt.getWidth()/2;
                            }
                            else
                            {
                                ranf =bt.getHeight()/2;
                            }
                            facedebase.ImageDrawCircle(bt,bt.getWidth(), bt.getHeight(),ranf);
                            Surfaceimage.setImageBitmap(bt);
                        }
                        if(Action != 5)
                        {
                            info.setText(R.string.MatchSuccess);
                        }

                        timeshow.setText("耗时 "+String.valueOf(timecount));
                        aliveshow.setText("真人");
                        play_wav(R.raw.identifyok);
                        Log.i(TAG, "match success");
                        break;
                    case 1:
                        ID = (String) msg.obj;
                        Log.i(TAG, ID);
                        if(fileIsExists(ID)) {
                            bt = BitmapFactory.decodeFile(ID);
                            if(bt.getHeight()>bt.getWidth())
                            {
                                ranf =bt.getWidth()/2;
                            }
                            else
                            {
                                ranf =bt.getHeight()/2;
                            }
                            facedebase.ImageDrawCircle(bt,bt.getWidth(), bt.getHeight(),ranf);
                            Surfaceimage.setImageBitmap(bt);
                        }
                        info.setText(R.string.EnorllSuccess);
                        play_wav(R.raw.register_ok);
                        Log.i(TAG, "enroll success");
                        break;
                    case 2:
                        Log.i(TAG, "start enroll");
                        detect = false;
                        Action = 2;
                        IrAction = 0;
                        frameCount = 0;
                        info.setText(R.string.Enorll);
                        break;
                    case 3:
                        Log.i(TAG, "match start");
                        if(Action!=5) {
                            Action = 1;
                            info.setText(R.string.Match);
                        }
                        if(IrAction!=5) {
                            IrAction = 1;
                            //info.setText(R.string.Match);
                        }
                        detect = false;
                        irdetect = false;
                        showdelay = 0;
                        frameCount = 0;
                        break;
                    case 4:
                        Log.i(TAG, "start Clear");
                        detect = false;
                        Action = 3;
                        IrAction = 0;
                        frameCount = 0;
                        info.setText(R.string.Clear);
                        numbershow.setText("人数 0");
                        break;
                    case 5:
                        Log.i(TAG, "start enroll all");
                        detect = false;
                        Action = 4;
                        IrAction = 0;
                        info.setText(R.string.batchEnorll);
                        enroll.setText("加载 0");
                        break;
                    case 6:
                        int person = (int)msg.obj;
                        Log.i(TAG,"msg "+String.valueOf(msg.obj));
                        if(person == 100)
                        {
                            enroll.setText("批量注册");
                            numbershow.setText("人数 "+String.valueOf(facecountsuccess));
                            info.setText(R.string.Match);
                        }
                        else
                        {
                            enroll.setText("加载 " + String.valueOf(msg.obj));

                            numbershow.setText("成功 "+String.valueOf(facecountsuccess)+" "+"失败 "+String.valueOf(facecountfail));
                        }
                        break;
                    case 7:
                        numbershow.setText("人数 "+String.valueOf(facecount));
                        break;
                    case 8:
                        ID = (String)msg.obj;
                        Log.i(TAG, ID);
                        if(fileIsExists(ID)) {
                            bt = BitmapFactory.decodeFile(ID);
                            if(bt.getHeight()>bt.getWidth())
                            {
                                ranf =bt.getWidth()/2;
                            }
                            else
                            {
                                ranf =bt.getHeight()/2;
                            }
                            facedebase.ImageDrawCircleRed(bt, bt.getWidth(), bt.getHeight(), ranf);
                            Surfaceimage.setImageBitmap(bt);
                        }
                        info.setText(R.string.MatchSuccess);
                        timeshow.setText("耗时 "+String.valueOf(timecount));
                        aliveshow.setText("真人");
                        play_wav(R.raw.identifyok);
                        Log.i(TAG, "match success");
                        break;
                    case 9:
                        Log.i(TAG, "start captrue");
                        detect = false;
                        irdetect = false;
                        Action = 5;
                        IrAction = 5;
                        //captures_start = 1;
                        captures.setBackgroundColor(Color.RED);
                        info.setText(R.string.captrue);
                        break;
                    case 10:
                        Log.i(TAG, "end captrue");
                        detect = false;
                        Action = 1;
                        irdetect = false;
                        IrAction = 1;
                        //frameCount = 0;
                        //captures_start = 0;
                        captures.setBackgroundColor(Color.GRAY);
                        info.setText(R.string.Match);
                        break;
                    default:
                        break;
                }
           }
        };
    }

    private OnSeekBarChangeListener verticalSeekBarChangeListener = new OnSeekBarChangeListener()
    {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser)
        {
            alivethrash = progress;
            seekpos.setText(Integer.toString(progress));
        }
    };

    public int initIr(boolean mode)
    {

        if(mode)
        {
            String[] cmd3 = { "/system/bin/sh", "-c", "echo 5 > /sys/class/leds/ir_pwm/brightness"};
            try {
                Process p3 = Runtime.getRuntime().exec(cmd3);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            String[] cmd3 = { "/system/bin/sh", "-c", "echo 58 > /sys/class/leds/ir_pwm/brightness"};
            try {
                Process p3 = Runtime.getRuntime().exec(cmd3);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    public int controlIr(boolean mode)
    {

        if(mode == true) {
            if (ircontrol == 0) {
                String[] cmd1 = {"/system/bin/sh", "-c", "echo 0 > /sys/class/leds/ir_l/brightness"};
                String[] cmd2 = {"/system/bin/sh", "-c", "echo 1 > /sys/class/leds/ir_r/brightness"};
                try {
                    Process p = Runtime.getRuntime().exec(cmd1);
                    Process p2 = Runtime.getRuntime().exec(cmd2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ircontrol = 1;
            } else if (ircontrol == 1) {
                String[] cmd1 = {"/system/bin/sh", "-c", "echo 0 > /sys/class/leds/ir_r/brightness"};
                String[] cmd2 = {"/system/bin/sh", "-c", "echo 0 > /sys/class/leds/ir_l/brightness"};
                try {
                    Process p = Runtime.getRuntime().exec(cmd1);
                    Process p2 = Runtime.getRuntime().exec(cmd2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ircontrol = 2;
            } else {
                String[] cmd1 = {"/system/bin/sh", "-c", "echo 0 > /sys/class/leds/ir_r/brightness"};
                String[] cmd2 = {"/system/bin/sh", "-c", "echo 1 > /sys/class/leds/ir_l/brightness"};
                try {
                    Process p = Runtime.getRuntime().exec(cmd1);
                    Process p2 = Runtime.getRuntime().exec(cmd2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ircontrol = 0;
            }
        }
        else
        {
            if (ircontrol == 0) {
                String[] cmd1 = {"/system/bin/sh", "-c", "echo 0 > /sys/class/leds/ir_l/brightness"};
                String[] cmd2 = {"/system/bin/sh", "-c", "echo 1 > /sys/class/leds/ir_r/brightness"};
                try {
                    Process p = Runtime.getRuntime().exec(cmd1);
                    Process p2 = Runtime.getRuntime().exec(cmd2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ircontrol = 1;
            } else {
                String[] cmd1 = {"/system/bin/sh", "-c", "echo 0 > /sys/class/leds/ir_r/brightness"};
                String[] cmd2 = {"/system/bin/sh", "-c", "echo 1 > /sys/class/leds/ir_l/brightness"};
                try {
                    Process p = Runtime.getRuntime().exec(cmd1);
                    Process p2 = Runtime.getRuntime().exec(cmd2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ircontrol = 0;
            }
        }
        return 1;
    }

    public int closeIr()
    {
        String[] cmd1 = { "/system/bin/sh", "-c", "echo 0 > /sys/class/leds/ir_l/brightness"};
        String[] cmd2 = { "/system/bin/sh", "-c", "echo 0 > /sys/class/leds/ir_r/brightness"};
        try {
            Process p = Runtime.getRuntime().exec(cmd1);
            Process p2 = Runtime.getRuntime().exec(cmd2);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int openwhite()
    {
        String[] cmd1 = { "/system/bin/sh", "-c", "echo 1 > /sys/class/leds/white/brightness"};
        try {
            Process p = Runtime.getRuntime().exec(cmd1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int closewhite()
    {
        String[] cmd1 = { "/system/bin/sh", "-c", "echo 0 > /sys/class/leds/white/brightness"};
        try {
            Process p = Runtime.getRuntime().exec(cmd1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int FindBackCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                return camIdx;
            }
        }
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
//            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
//                return camIdx;
//            }
        }
        return -1;
    }

    public int FindFrontCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                return camIdx;
            }
        }
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                return camIdx;
            }
        }
        return -1;
    }
    public void getDisplayInfomation() {
        //Point point = new Point();
        //getWindowManager().getDefaultDisplay().getSize(point);
        //Log.i(TAG, "the screen size is " + point.toString());
        //getWindowManager().getDefaultDisplay().getRealSize(point);
        //Log.i(TAG, "the screen real size is " + point.toString());
    }

    public void rotateMyBitmap(Bitmap bmp) {
        //*****旋转一下

        Matrix matrix = new Matrix();
        matrix.postScale(1, -1);
        matrix.postRotate(270);
        Bitmap nbmp2 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        if (!detect) {
            facedebase.ConverArgbToRgb(nbmp2, rgb24, gray, nbmp2.getWidth(), nbmp2.getHeight(), 1);
            detect = true;
        }
        int i = 0;
        int j = 0;
        int y0 = facerect[1];
        int x0 = facerect[0];
        int y1 = facerect[3] + facerect[1];
        int x1 = facerect[2] + facerect[0];
        for (i = y0; i <= y1; i++) {
            if (i == y0 || i == y1) {
                for (j = x0; j <= x1; j++) {
                    nbmp2.setPixel(j, i, Color.GREEN);
                }
            } else {
                nbmp2.setPixel(x0, i, Color.GREEN);
                nbmp2.setPixel(x1, i, Color.GREEN);
            }
        }
        Surfaceimage.setImageBitmap(nbmp2);
    }

    public boolean fileIsExists(String strFile)
    {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                return false;
            }

        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

    public String getMD5(InputStream in) {
        MessageDigest digest = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.reset();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bytesToHexString(digest.digest());
    }

    public String getFileMD5(FileInputStream in) {
        MessageDigest digest = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            //in.reset();
        } catch (Exception e) {
            return null;
        }
        return bytesToHexString(digest.digest());
    }

    public String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public String FindDirs(String filePath) {
        File file = new File(filePath);
        if (file.isDirectory())
        { // 否则如果它是一个目录
            File files[] = file.listFiles();
            for (int i = 0; i < files.length; i++)
            { // 遍历目录下所有的文件
                if((!files[i].getName().equals("emulated"))&&(!files[i].getName().equals("self"))&&(files[i].isDirectory()))
                {
                    Log.i(TAG,files[i].getName());
                    return files[i].getName();
                }
            }
        }
        return "";
    }

    public void readIrImage(byte[] bgr)
    {
        int len = 0;
        int total = 0;
        File file=new File("/sdcard/facedata/1_ActionDetect.raw");
        if(!file.exists() || file.length()==0) {
            return;
        }
        try {
            FileInputStream inputStream=new FileInputStream(file);
            byte[] buffer=new byte[1024];
            while ((len=inputStream.read(buffer))!=-1){
                System.arraycopy(buffer, 0, bgr,total,len);
                total += len;
            }
            Log.i(TAG,"get image "+len);
            inputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readBgrImage(byte[] bgr)
    {
        int len = 0;
        int total = 0;
        File file=new File("/sdcard/facedata/0_ActionDetect.raw");
        if(!file.exists() || file.length()==0) {
            return;
        }
        try {
            FileInputStream inputStream=new FileInputStream(file);
            byte[] buffer=new byte[1024];
            while ((len=inputStream.read(buffer))!=-1){
                System.arraycopy(buffer, 0, bgr,total,len);
                total += len;
            }
            Log.i(TAG,"get image "+len);
            inputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeBgrImage(byte[] bgr)
    {
        int len = 0;
        int total = 0;
        File file=new File("/sdcard/facedata/0_ActionDetect.raw");
        try {
            Log.i(TAG,"save "+file);
            FileOutputStream outputStream=new FileOutputStream(file);
            outputStream.write(bgr);
            outputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     *  从assets目录中复制整个文件夹内容,考贝到 /data/data/包名/files/目录中
     *  @param  activity  activity 使用CopyFiles类的Activity
     *  @param  filePath  String  文件路径,如：/assets/aa
     */
    public void copyAssetsDir2Phone(Activity activity, String filePath){
        try {
            String[] fileList = activity.getAssets().list(filePath);
            if(fileList.length>0)
            {//如果是目录
                File file=new File(activity.getFilesDir().getAbsolutePath()+ File.separator+filePath);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName:fileList)
                {
                    filePath=filePath+File.separator+fileName;

                    copyAssetsDir2Phone(activity,filePath);
                    filePath=filePath.substring(0,filePath.lastIndexOf(File.separator));
                    Log.e("oldPath",filePath);
                }
            }
            else
            {//如果是文件

                InputStream inputStream=activity.getAssets().open(filePath);
                String InputMd5 = getMD5(inputStream);
                File file=new File(activity.getFilesDir().getAbsolutePath()+ File.separator+filePath);
                if(!file.exists() || file.length()==0) {

                    FileOutputStream fos=new FileOutputStream(file);
                    int len=-1;
                    byte[] buffer=new byte[1024];
                    while ((len=inputStream.read(buffer))!=-1){
                        fos.write(buffer,0,len);
                    }
                    fos.flush();
                    inputStream.close();
                    fos.close();
                    buffer= null;
                    Log.i(TAG,"模型文件复制完毕");
                } else {
                    FileInputStream inputStream2=new FileInputStream(file);
                    String OutputMd5 = getFileMD5(inputStream2);
                    inputStream2.close();
                    if(!InputMd5.equals(OutputMd5))
                    {
                        deleteFile(file);
                        file=new File(activity.getFilesDir().getAbsolutePath()+ File.separator+filePath);
                        FileOutputStream fos=new FileOutputStream(file);
                        int len=-1;
                        byte[] buffer=new byte[1024];
                        while ((len=inputStream.read(buffer))!=-1){
                            fos.write(buffer,0,len);
                        }
                        fos.flush();
                        inputStream.close();
                        fos.close();
                        buffer= null;
                        Log.i(TAG,"模型文件更新完毕");
                    }
                    else
                    {
                        //Log.i(TAG, "模型文件已存在");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                // 设置属性:让文件可执行，可读，可写
                file.setExecutable(true, false);
                file.setReadable(true, false);
                file.setWritable(true, false);
                file.delete(); // delete()方法
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
                file.delete();
            }
            Log.i("deleteFile", file.getName() + "成功删除！！");
        } else {
            Log.i("deleteFile", file.getName() + "不存在！！！");
        }
    }

    public void SetLowLevelTime()
    {
        rwl3.writeLock().lock();
        LowLevelModeTimer = System.currentTimeMillis()+60000;
        if(LowLevelMode == true) {
            openwhite();
            LowLevelMode = false;
        }
        rwl3.writeLock().unlock();
        return;

    }

    public boolean CheckLowLevel()
    {

        if(LowLevelMode == true)
        {
            return true;
        }
        if(System.currentTimeMillis() > LowLevelModeTimer)
        {
            rwl3.writeLock().lock();
            LowLevelMode = true;
            closewhite();
            rwl3.writeLock().unlock();
            return true;
        }
        return false;
    }
    protected void onResume() {
        int posi = 0;
        int ret = 0;
        int modColumn;
        super.onResume();
        rwl= new ReentrantReadWriteLock();
        rwl2= new ReentrantReadWriteLock();
        rwl3 = new ReentrantReadWriteLock();
        copyAssetsDir2Phone(this, "facedata");
        File file=new File("/sdcard/facedata");
        file.mkdirs();
        file=new File("/sdcard/facedata/Alive");
        file.mkdirs();
        file=new File("/sdcard/facedata/Nolive");
        file.mkdirs();
        file=new File("/sdcard/batchEnroll");
        file.mkdirs();
        file=new File("/sdcard/FailEnroll");
        file.mkdirs();

        String udisk = FindDirs("/storage");
        if(udisk != "")
        {
            absudisk = "/storage/"+udisk;
            file=new File(absudisk+"/Alive");
            file.mkdirs();
            file=new File(absudisk+"/Nolive");
            file.mkdirs();
            file=new File(absudisk+"/IrAlive");
            file.mkdirs();
            file=new File(absudisk+"/capture");
            file.mkdirs();
            Log.i(TAG,"mkdir "+absudisk+"/capture");
            file=new File(absudisk+"/capture/human");
            file.mkdirs();

            file=new File(absudisk+"/capture/nirphoto");
            file.mkdirs();

            file=new File(absudisk+"/capture/visphoto");
            file.mkdirs();
            file=new File(absudisk+"/capture/video");
            file.mkdirs();

        }
        else
        {
            file=new File("/sdcard/capture");
            file.mkdirs();

            file=new File("/sdcard/capture/human");
            file.mkdirs();

            file=new File("/sdcard/capture/nirphoto");
            file.mkdirs();

            file=new File("/sdcard/capture/visphoto");
            file.mkdirs();
            file=new File("/sdcard/capture/video");
            file.mkdirs();
            absudisk = "";
        }

        facedetect = new SykeanFD();
        facedebase = new sykabase();

        faceirdetect = new SykeanFD();
        faceirdebase = new sykabase();
        byte[] returnInfo = new byte[64];
        ret = facedetect.GetExtraInfo("version", returnInfo);

        String str = new String(returnInfo);
        Log.i(TAG, "GetExtraInfo " + ret +"-"+ str);
        facehandle= new long[4];
        ret = facedetect.CreateIns(facehandle,this.getFilesDir().getAbsolutePath() + File.separator + "facedata/model/","com.example.syka");
        if(FME_OK == ret)
        {
            Log.i(TAG, "CreateIns success");


            ret = facedetect.GetFeatureLen(facehandle);
            Log.i(TAG, "GetFeatureLen" + ret);
            ret = facedetect.SetParams(facehandle,1.3f,0.6f,64,0);
            Log.i(TAG, "SetParams" + ret);
        }
        else
        {
            Log.i(TAG, "CreateIns fail");
        }
        
        faceirhandle= new long[4];
        ret = faceirdetect.CreateIns(faceirhandle,this.getFilesDir().getAbsolutePath() + File.separator + "facedata/model/","com.example.syka");
        if(FME_OK == ret)
        {
            Log.i(TAG, "CreateIns success");


            ret = faceirdetect.GetFeatureLen(faceirhandle);
            Log.i(TAG, "GetFeatureLen" + ret);
            ret = faceirdetect.SetParams(faceirhandle,1.2f,0.4f,64,0);
            Log.i(TAG, "SetParams" + ret);
        }
        else
        {
            Log.i(TAG, "CreateIns fail");
        }
        userdb = new dbHelper(this);
        int cameraforindex = -1;
        int ircameraforindex = -1;
        if(cameraselect == 1)
        {
            ircameraforindex = FindBackCamera();
//            cameraforindex = FindFrontCamera();
        }
        else
        {
            cameraforindex = FindBackCamera();
//            ircameraforindex = FindFrontCamera();
        }
        camera = Camera.open(cameraforindex);
        openwhite();
        initIr(false);
        if (camera != null) {
            Camera.Parameters myParam = camera.getParameters();
            myParam.setPictureFormat(PixelFormat.JPEG);// 设置拍照后存储的图片格式
            // 设置大小和方向等参数
            List<Camera.Size> sizeList = myParam.getSupportedPreviewSizes();
            for (int i = 1; i < sizeList.size(); i++){
                Log.i(TAG, " 1 support "+sizeList.get(i).width+"x"+sizeList.get(i).height);
                if(sizeList.get(i).width==orgwidth)
                {
                    camerah = sizeList.get(i).height;
                    cameraw = sizeList.get(i).width;
                    //break;
                    //break;
                }
            }

            if(direct == 0||direct ==2)
            {
                picwith = cameraw;
                pichight = camerah;
            }
            else
            {
                picwith = camerah;
                pichight = cameraw;
            }
            Log.i(TAG,"pic w "+picwith);
            Log.i(TAG,"pic h "+pichight);
            showBit = Bitmap.createBitmap( picwith, pichight, Bitmap.Config.ARGB_8888);
            showIrBit = Bitmap.createBitmap( picwith, pichight, Bitmap.Config.ARGB_8888);
            aliveBit = Bitmap.createBitmap(picwith, pichight, Bitmap.Config.ARGB_8888);
            aliveIrBit = Bitmap.createBitmap(picwith, pichight, Bitmap.Config.ARGB_8888);
            myParam.setPreviewSize(cameraw, camerah);
            Log.i(TAG, "set  " + cameraw + " x " + camerah);
            camera.setParameters(myParam);
            //camera.setDisplayOrientation(90);
            SetLowLevelTime();
            camera.startPreview();
            camera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, Camera camera) {
                    if (bytes == null)
                        return;

                    Camera.Size size = camera.getParameters().getPreviewSize();
                    try {
                        //Log.i(TAG,"camera "+showdelay);
                        if (showdelay == 0) {
                            boolean status = CheckLowLevel();
                            if(status==false)
                            {
                                if(last_status == true)
                                {
                                    last_status = status;
                                    initIr(status);
                                }

                                facedebase.ConverYuvToRgb(showBit, rgb32, bytes, size.width, size.height, cmdirect, hflip);
                                if (!detect) {
                                    //Log.i(TAG, "w " + showBit.getWidth() + " h " + showBit.getHeight());
                                    facedebase.ConverArgbToRgb(showBit, rgb24, gray, showBit.getWidth(), showBit.getHeight(), 1);
                                    //readBgrImage(rgb24);
                                    System.arraycopy(rgb32, 0, rgb32forcopy, 0, rgb32.length);
                                    detect = true;
                                }
                                //Log.i(TAG,"camera " + facerect[0]+"-"+facerect[1]+"-"+facerect[2]+"-"+facerect[3]);
                                rwl2.readLock().lock();
                                int y0 = facerect[1];
                                int x0 = facerect[0];
                                int y1 = facerect[3] + facerect[1];
                                int x1 = facerect[2] + facerect[0];
                                rwl2.readLock().unlock();

                                if (x0 > 0 && y0 > 0 && y1 < (showBit.getHeight() - 1) && x1 < (showBit.getWidth() - 1)) {
                                    int i = 0;
                                    int j = 0;
                                    for (i = y0 + 1; i < y1; i++) {
                                        if (i == (y0 + 1) || i == (y1 - 1)) {
                                            for (j = x0 + 1; j < x1; j++) {
                                                showBit.setPixel(j, i, Color.GREEN);
                                                showBit.setPixel(j, i + 1, Color.GREEN);
                                            }
                                        } else {
                                            showBit.setPixel(x0, i, Color.GREEN);
                                            showBit.setPixel(x1, i, Color.GREEN);
                                            showBit.setPixel(x0 + 1, i, Color.GREEN);
                                            showBit.setPixel(x1 + 1, i, Color.GREEN);
                                        }
                                    }
                                }
                                Surfaceimage.setImageBitmap(showBit);
                                
//                            if(mouth_s[0] == 1)
//                            {
//                                actionshow.setText("张嘴 ");
//                            }
//                            else if(heard_h[0] == 1)
//                            {
//                                actionshow.setText("右转 ");
//                            }
//                            else if(heard_h[0] == 2)
//                            {
//                                actionshow.setText("左转 ");
//                            }
//                            else if(heard_v[0] == 1)
//                            {
//                                actionshow.setText("抬头 ");
//                            }
//                            else if(heard_v[0] == 2)
//                            {
//                                actionshow.setText("低头 ");
//                            }
//                            else
//                            {
//                                actionshow.setText("");
//                            }
                            }
                            else
                            {
                                if(last_status == false)
                                {
                                    last_status = status;
                                    initIr(status);
                                    int i = 0;
                                    int j = 0;
                                    for (i = 0; i < pichight; i++) {
                                        for(j=0;j<picwith;j++)
                                        {
                                            showBit.setPixel(j, i, Color.TRANSPARENT);
                                        }
                                    }
                                Surfaceimage.setImageBitmap(showBit);

                                }
                            }
                        }
                        else
                        {
                            //Log.i(TAG,"no show ");
                        }
                    } catch (Exception ex) {
                        Log.e(TAG, "Error:" + ex.getMessage());
                    }
                }
            });
        }

//        try{
//            camerair = Camera.open(ircameraforindex);
//        }catch (Exception e){
//            Log.e("TAG",">>>>>>>>>>>>>>>>>>>>>>>报错1 :  "+e.toString());
//        }
//
//        try{
//            camerair = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
//        }catch (Exception e){
//            Log.e("TAG",">>>>>>>>>>>>>>>>>>>>>>>报错2:  "+e.toString());
//        }

        camerair = Camera.open(ircameraforindex);


        if (camerair != null) {
            Camera.Parameters myParam = camerair.getParameters();
            myParam.setPictureFormat(PixelFormat.JPEG);// 设置拍照后存储的图片格式
            // 设置大小和方向等参数
            List<Camera.Size> sizeList = myParam.getSupportedPreviewSizes();
            for (int i = 1; i < sizeList.size(); i++){
                Log.i(TAG, " ir support "+sizeList.get(i).width+"x"+sizeList.get(i).height);
                if(sizeList.get(i).width==orgwidth)
                {
                    ircamerah = sizeList.get(i).height;
                    ircameraw = sizeList.get(i).width;
                    //break;
                }
            }

            if(direct == 0||direct ==2)
            {
                irpicwith = ircameraw;
                irpichight = ircamerah;
            }
            else
            {
                irpicwith = ircamerah;
                irpichight = ircameraw;
            }
            Log.i(TAG,"pic w "+irpicwith);
            Log.i(TAG,"pic h "+irpichight);
            showIrBit = Bitmap.createBitmap( irpicwith, irpichight, Bitmap.Config.ARGB_8888);
            myParam.setPreviewSize(ircameraw, ircamerah);
            Log.i(TAG, "set  " + ircameraw + " x " + ircamerah);
            camerair.setParameters(myParam);
            //camera.setDisplayOrientation(90);
            camerair.startPreview();
            camerair.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, Camera camera) {
                    if (bytes == null)
                        return;

                    Camera.Size size = camera.getParameters().getPreviewSize();
                    try {
                        if(System.currentTimeMillis() > changeirtime)
                        {
                            boolean status = CheckLowLevel();
                            controlIr(status);
                            changeirtime = System.currentTimeMillis()+1000;
                        }
                        if (showdelay == 0) {
                                if (!irdetect) {
                                    faceirdebase.ConverYuvGrayToRgb(showIrBit, irrgb32, bytes, size.width, size.height, cmdirect, hflip);
                                    //Log.i(TAG, "gray--w " + showBit.getWidth() + " h " + showBit.getHeight());
                                    faceirdebase.ConverArgbToRgb(showIrBit, irrgb24, irgray, showIrBit.getWidth(), showIrBit.getHeight(), 1);
                                    System.arraycopy(irrgb32, 0, irrgb32forcopy, 0, irrgb32.length);
                                    irdetect = true;
                                }
//                            if(irfacerect[0]!=0||irfacerect[1]!=0||irfacerect[3]!=0||irfacerect[2]!=0) {
//                                int i = 0;
//                                int j = 0;
//                                int y0 = irfacerect[1];
//                                int x0 = irfacerect[0];
//                                int y1 = irfacerect[3] + irfacerect[1];
//                                int x1 = irfacerect[2] + irfacerect[0];
//                                for (i = y0 + 1; i < y1; i++) {
//                                    if (i == (y0 + 1) || i == (y1 - 1)) {
//                                        for (j = x0 + 1; j < x1; j++) {
//                                            showIrBit.setPixel(j, i, Color.GREEN);
//                                            showIrBit.setPixel(j, i + 1, Color.GREEN);
//                                        }
//                                    } else {
//                                        showIrBit.setPixel(x0, i, Color.GREEN);
//                                        showIrBit.setPixel(x1, i, Color.GREEN);
//                                        showIrBit.setPixel(x0 + 1, i, Color.GREEN);
//                                        showIrBit.setPixel(x1 + 1, i, Color.GREEN);
//                                    }
//                                }
//                            }
//                            Surfaceimage.setImageBitmap(showIrBit);
                        }
                        else
                        {
                            //Log.i(TAG,"no show ");
                        }
                    } catch (Exception ex) {
                        Log.e(TAG, "Error:" + ex.getMessage());
                    }
                }
            });
        }
        //rgb32test = new byte[640 * 480*4];
        rgb32 = new byte[picwith * pichight*4];
        irrgb32 = new byte[picwith * pichight*4];
        rgb32forcopy = new byte[picwith * pichight*4];
        irrgb32forcopy = new byte[picwith * pichight*4];
        Maxrgb32forcopy = new byte[picwith * pichight*4];
        rgb24 = new byte[picwith * pichight * 3];
        irrgb24 = new byte[picwith * pichight * 3];
        Maxrgb24 = new byte[picwith * pichight * 3];
        gray = new byte[picwith * pichight];
        irgray = new byte[picwith * pichight];

        //rgb24_Test = new byte[600 * 800 * 3];
        //Ir24_Test = new byte[600 * 800 * 3];
        //readBgrImage(rgb24_Test);
        //readBgrImage(Ir24_Test);
        frameCount = 0;
        facerect = new int[4];
        irfacerect = new int[4];
        rectfinal = new int[40];
        irrectfinal = new int[40];
        mouth_s = new int[2];
        heard_h = new float[2];
        heard_v = new float[2];
        blurness = new float[2];
        irmouth_s = new int[2];
        irheard_h = new float[2];
        irheard_v = new float[2];
        irblurness = new float[2];
        fQuality = new float[2];

        rocrect = new int[4];
        landmark = new float[166];
        landmarkMax = new float[166];

        irrocrect = new int[4];
        irlandmark = new float[166];
        irlandmarkMax = new float[166];
        last_facerect = new int[4];
        alive_facerect = new int[4];
        Maxfacerect = new int[4];
        facerect[0] = 0;
        facerect[1] = 0;
        facerect[2] = 0;
        facerect[3] = 0;
        facefeater = new float[128];
        faceirfeater = new float[128];
        alivefaceirfeater = new float[128];
        facefeaterdet = new float[128];
        facefeaterraw = new byte[128 * 4];
        maxscore = -1;
        outside = new int[4];
        fscore = new int[4];
        iroutside = new int[4];
        irfscore = new int[4];
        Running = true;
        Action = 1;
        IrAction = 1;
        facecount = 0;
        detecttimers = 0;
        Cursor cursor = userdb.select("all");
        facecount = cursor.getCount();
        Log.e(TAG, String.format("获取记录数%d\n", facecount));
        if (facecount > 0) {
            facelist = new float[128 * facecount];
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                modColumn = cursor.getColumnIndex("module");
                byte[] mod = cursor.getBlob(modColumn);
                facedebase.CharToFloat(mod, facefeaterdet);
                System.arraycopy(facefeaterdet, 0, facelist, posi * 128, 128);
                posi++;
            }
        } else {
            facelist = null;
        }
        cursor.close();
        numbershow.setText(String.valueOf(facecount));
        facethread = new Thread() {
            public void run() {
                Message message;
                int ret = 0;
                float aliveret = 0.0f;
                float MinQuality = 100f;
                int facetlost = 0;
                int alive = 0;
                while (Running) {
                    try {
                            switch(Action)
                            {
                                case 0:
                                    Thread.currentThread().sleep(50);
                                    break;
                                case 1:
                                    if(facecount<=0)
                                    {
                                        Thread.currentThread().sleep(50);
                                        break;
                                    }
                                    if(detect)
                                    {
                                        Date dts = new Date();
                                        ret = facedetect.DetectFaceRgb(facehandle, rgb24, picwith, pichight, rectfinal,1);
                                        if(ret > 0)
                                        {
                                            SetLowLevelTime();
                                            //facetlost = 0;
                                            //Log.i(TAG,"rgb Match face"+rectfinal[0]+"-"+rectfinal[1]+"-"+rectfinal[2]+"-"+rectfinal[3]);
                                            //MinQuality = 100;
                                            //frameCount = 0;
                                            facedetect.AliveActionDetect(facehandle, rgb24, picwith, pichight, rectfinal, mouth_s, heard_h, heard_v,blurness,landmark,rocrect);
                                            Log.i(TAG,"rgb LLAA mouth_s:"+mouth_s[0]+"heard_h: "+heard_h[0]+"heard_v: "+heard_v[0]+"blurness: "+blurness[0]);
                                            rwl2.writeLock().lock();
                                            facerect[0] = rocrect[0];
                                            facerect[1] = rocrect[1];
                                            facerect[2] = rocrect[2];
                                            facerect[3] = rocrect[3];
                                            rwl2.writeLock().unlock();
//                                            if(blurness[0] >= 80)
//                                            {
//                                                detect = false;
//                                                break;
//                                            }
                                            rwl.writeLock().lock();
                                            if(aliveflag == 1)
                                            {
                                                alive = 1;
                                                if (rectfinal[0] > alive_facerect[0]) {
                                                    if (rectfinal[0] > (alive_facerect[0] + alive_facerect[2])) {
                                                        alive = 0;
                                                    }
                                                } else {
                                                    if (alive_facerect[0] > (rectfinal[0] + rectfinal[2])) {
                                                        alive = 0;
                                                    }
                                                }
                                                if (rectfinal[1] > alive_facerect[1]) {
                                                    if (rectfinal[1] > (alive_facerect[1] + alive_facerect[3])) {
                                                        alive = 0;
                                                    }
                                                } else {
                                                    if (alive_facerect[1] > (rectfinal[1] + rectfinal[3])) {
                                                        alive = 0;
                                                    }
                                                }

                                                if(abs(rectfinal[0]-alive_facerect[0])>100||abs(rectfinal[1]-alive_facerect[1])>100||abs(rectfinal[2]-alive_facerect[2])>50||abs(rectfinal[3]-alive_facerect[3])>50)
                                                {
                                                    alive = 0;
                                                }

                                            }
                                            else
                                            {
                                                alive = 0;
                                            }
                                            rwl.writeLock().unlock();
                                            if(alive != 1)
                                            {
                                                detect = false;
                                                break;
                                            }
                                            Log.i(TAG,"rgb LLAA2 mouth_s:"+mouth_s[0]+"heard_h: "+heard_h[0]+"heard_v: "+heard_v[0]+"blurness: "+blurness[0]);
                                            ret = facedetect.ExtractFeatureBGR(facehandle, facefeater, rgb24, picwith, pichight, null, rectfinal,landmark);
                                            if(ret == FME_OK)
                                            {
                                                //maxscore = facedetect.Match(facehandle,facefeater,facelist,128);
                                                //Log.i(TAG,"1：1 "+maxscore);
                                                maxscore = facedetect.MatchN(facehandle,facefeater,facelist,facecount,outside,fscore,3,0);

                                                //Log.i(TAG,"find index:"+outside[0]+" :"+outside[1]+" :"+outside[2]);
                                                Log.i(TAG,"rgb find fscore:"+fscore[0]+" :"+fscore[1]+" :"+fscore[2]);

                                                if(maxscore >= levelthrash)
                                                {

                                                    //Log.i(TAG, "find people:" + outside[0] + " maxscore:" + maxscore);
                                                    Cursor cursor = userdb.select("all");
                                                    cursor.moveToFirst();
                                                    cursor.moveToPosition(outside[0]);
                                                    int modColumn = cursor.getColumnIndex("username");
                                                    int modColumn2 = cursor.getColumnIndex("time");
                                                    String id = cursor.getString(modColumn);
                                                    Log.i(TAG, id);
                                                    String timeenroll = cursor.getString(modColumn2);
                                                    //Log.i(TAG, "find :" + id);
                                                    //Log.i(TAG, "Enroll at " + timeenroll);
                                                    cursor.close();


                                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-S");



                                                    String ID;
                                                    facedebase.CopyToImage(aliveBit, rgb32forcopy, picwith, pichight);
                                                    semaphore.acquire();
                                                    if(aliveflag == 1)
                                                    {
                                                        alive = 1;
                                                        if (rectfinal[0] > alive_facerect[0]) {
                                                            if (rectfinal[0] > (alive_facerect[0] + alive_facerect[2])) {
                                                                alive = 0;
                                                            }
                                                        } else {
                                                            if (alive_facerect[0] > (rectfinal[0] + rectfinal[2])) {
                                                                alive = 0;
                                                            }
                                                        }
                                                        if (rectfinal[1] > alive_facerect[1]) {
                                                            if (rectfinal[1] > (alive_facerect[1] + alive_facerect[3])) {
                                                                alive = 0;
                                                            }
                                                        } else {
                                                            if (alive_facerect[1] > (rectfinal[1] + rectfinal[3])) {
                                                                alive = 0;
                                                            }
                                                        }

                                                        if(abs(rectfinal[0]-alive_facerect[0])>100||abs(rectfinal[1]-alive_facerect[1])>100||abs(rectfinal[2]-alive_facerect[2])>50||abs(rectfinal[3]-alive_facerect[3])>50)
                                                        {
                                                            alive = 0;
                                                        }

                                                    }
                                                    else
                                                    {
                                                        alive = 0;
                                                    }
                                                    //alive = aliveflag;
                                                    semaphore.release();
                                                    Date dte = new Date();
                                                    String timestr = formatter.format(dte);
                                                    timecount = (dte.getTime() - dts.getTime());
                                                    if(alive == 1)
                                                    {
                                                        ID = "/sdcard/facedata/Alive/" + timeenroll + "/" + timestr + "_" + String.valueOf(rectfinal[0]) + "_" + String.valueOf(rectfinal[1]) + "_" + String.valueOf(rectfinal[2]) + "_" + String.valueOf(rectfinal[3]) + ".jpeg";
                                                    }
                                                    else
                                                    {
                                                        ID = "/sdcard/facedata/Nolive/" + timeenroll + "/" + timestr + "_" + String.valueOf(rectfinal[0]) + "_" + String.valueOf(rectfinal[1]) + "_" + String.valueOf(rectfinal[2]) + "_" + String.valueOf(rectfinal[3]) + ".jpeg";
                                                    }
                                                    Log.i(TAG, "rgb success at " + ID);
                                                    FileOutputStream fileOutStream = null;
                                                    fileOutStream = new FileOutputStream(ID);
                                                    aliveBit.compress(Bitmap.CompressFormat.JPEG, 70, fileOutStream);  //把位图输出到指定的文件中
                                                    //Log.i(TAG, "mpfs.compress");
                                                    fileOutStream.flush();
                                                    fileOutStream.close();
                                                    if(alive == 1)
                                                    {
                                                        if(showdelay == 0)
                                                        {
                                                            showdelay = 30;
                                                            //Log.i(TAG, "send success");
                                                            message = new Message();
                                                            message.obj = id;
                                                            message.what = 0;
                                                            if(Action != 5) {
                                                                Action = 0;
                                                            }

                                                            mHandler.sendMessage(message);
                                                            //Thread.currentThread().sleep(200);
                                                            message = new Message();
                                                            message.what = 3;
                                                            mHandler.sendMessageDelayed(message, 1000);
                                                        }
                                                        else
                                                        {
                                                            Action = 0;
                                                        }
                                                    }
                                                    else
                                                    {
                                                        Log.i(TAG, "rgb nolive success");
                                                    }
                                                }

                                            }
                                            detecttimers = 0;
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
                                                frameCount = 0;
                                            }
                                            if(CheckLowLevel()==true)
                                            {
                                                Thread.currentThread().sleep(200);
                                            }

                                                //Log.i(TAG, "no find people:"+"maxscore:"+maxscore);
                                            //Log.i(TAG,"no find face "+ret);
                                        }
                                        detect = false;
                                    }
                                    else
                                    {
                                        Thread.currentThread().sleep(50);
                                        break;
                                    }
                                    break;
                                case 2:
                                    if(detect)
                                    {
                                        ret = facedetect.DetectFaceRgb(facehandle, rgb24, picwith, pichight, rectfinal,1);
                                        Log.i(TAG,"Enroll detect "+ret);
                                        if(ret > 0)
                                        {
                                            facetlost = 0;
                                            Log.i(TAG,"Enroll face"+rectfinal[0]+"-"+rectfinal[1]+"-"+rectfinal[2]+"-"+rectfinal[3]);
                                            facedetect.AliveActionDetect(facehandle, rgb24, picwith, pichight, rectfinal, mouth_s, heard_h, heard_v,blurness,landmark,rocrect);
                                            Log.i(TAG,"Enroll blurness: "+blurness[0]+"time:"+frameCount);
                                            facerect[0] = rocrect[0];
                                            facerect[1] = rocrect[1];
                                            facerect[2] = rocrect[2];
                                            facerect[3] = rocrect[3];
                                            if(blurness[0] >= 50)
                                            {
                                                detect = false;
                                                break;
                                            }
                                            frameCount++;
                                            Log.i(TAG,"Enroll MinQuality: "+MinQuality+"timer:"+frameCount);

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
                                            Log.i(TAG,"Enroll proc "+ MinQuality+"timer "+frameCount);
                                            if(frameCount < 10)
                                            {
                                                detect = false;
                                                break;
                                            }
                                            Log.i(TAG,"Enroll Go "+ MinQuality);
                                            MinQuality = 100;
                                            frameCount = 0;
                                            ret = facedetect.ExtractFeatureBGR(facehandle, facefeater, Maxrgb24, picwith, pichight, null, Maxfacerect,landmarkMax);
                                            if (ret == 0)
                                            {
                                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-S");
                                                String timestr = formatter.format(new Date());
                                                String ID = "/sdcard/facedata/" + timestr + "_" + String.valueOf(Maxfacerect[0]) + "_" + String.valueOf(Maxfacerect[1]) + "_" + String.valueOf(Maxfacerect[2]) + "_" + String.valueOf(Maxfacerect[3]) + ".jpeg";
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
                                                Bitmap bmpfs = Bitmap.createBitmap(picwith,pichight, Bitmap.Config.ARGB_8888);
                                                facedebase.CopyToImage(bmpfs,Maxrgb32forcopy,picwith,pichight);
                                                FileOutputStream fileOutStream = null;
                                                fileOutStream = new FileOutputStream(ID);

                                                bmpfs.compress(Bitmap.CompressFormat.JPEG, 80, fileOutStream);  //把位图输出到指定的文件中
                                                fileOutStream.flush();
                                                fileOutStream.close();
                                                bmpfs.recycle();
                                                message = new Message();
                                                message.what = 7;
                                                mHandler.sendMessage(message);

                                                message = new Message();
                                                message.obj = ID;
                                                message.what = 1;
                                                showdelay = 30;
                                                Action = 0;

                                                mHandler.sendMessage(message);
                                                Thread.sleep(500);
                                                message = new Message();
                                                message.what = 3;
                                                mHandler.sendMessageDelayed(message,2000);
                                            }
                                            detecttimers = 0;
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
                                                frameCount = 0;
                                            }
                                            Log.i(TAG,"Enroll find face");
                                        }
                                        detect = false;
                                    }
                                    else
                                    {
                                        Thread.currentThread().sleep(50);
                                        break;
                                    }
                                    break;
                                case 3:
                                    facecount = 0;
                                    Action = 0;
                                    IrAction = 0;
                                    userdb.delete("*");
                                    File file1 = new File("/sdcard/facedata");
                                    deleteFile(file1);
                                    facelist = null;
                                    facecount = 0;
                                    last_facerect[0] = 0;
                                    last_facerect[1] = 0;
                                    last_facerect[2] = 0;
                                    last_facerect[3] = 0;

                                    facerect[0] = 0;
                                    facerect[1] = 0;
                                    facerect[2] = 0;
                                    facerect[3] = 0;

                                    File file=new File("/sdcard/facedata");
                                    file.mkdirs();
                                    file=new File("/sdcard/facedata/Alive");
                                    file.mkdirs();
                                    file=new File("/sdcard/facedata/Nolive");
                                    file.mkdirs();
                                    file=new File("/sdcard/batchEnroll");
                                    file.mkdirs();
                                    file=new File("/sdcard/FailEnroll");
                                    file.mkdirs();

                                    String udisk = FindDirs("/storage");
                                    if(udisk != "")
                                    {
                                        absudisk = "/storage/"+udisk;
                                        file=new File(absudisk+"/Alive");
                                        file.mkdirs();
                                        file=new File(absudisk+"/Nolive");
                                        file.mkdirs();
                                        file=new File(absudisk+"/IrAlive");
                                        file.mkdirs();
                                    }
                                    else
                                    {
                                        absudisk = "";
                                    }
                                    message = new Message();
                                    message.what = 3;
                                    mHandler.sendMessage(message);
                                    break;
                                case 4:
                                    Log.i(TAG, "batch Enroll");

                                    facecountsuccess = 0;
                                    facecountfail = 0;
                                    ret = facedetect.SetParams(facehandle,1.2f,0.5f,64,0);
                                    File file2 = new File("/sdcard/batchEnroll");
                                    File files[] = file2.listFiles(); // 声明目录下所有的文件 files[];
                                    int filecnt = files.length;
                                    Log.i(TAG,"batchEnroll file is "+filecnt);
                                    for (int i = 0; i < filecnt; i++) { // 遍历目录下所有的文件
                                        if (files[i].exists()) { // 判断文件是否存在
                                            if (files[i].isFile()) { // 判断是否是文件

                                                String destoder = files[i].getAbsolutePath();
                                                Log.i(TAG,destoder);
                                                Bitmap bt = BitmapFactory.decodeFile(destoder);
                                                if(bt==null)
                                                {
                                                    Log.i(TAG, "decode fail");
                                                    facecountfail++;
                                                    continue;
                                                }
                                                byte[] grgb = new byte[bt.getWidth()*bt.getHeight()*3];
                                                byte[] ggray = new byte[bt.getWidth()*bt.getHeight()];
                                                Log.i(TAG, "batchEnroll "+ i + ":" + bt.getWidth() + "-" + bt.getHeight());
                                                facedebase.ConverArgbToRgb(bt, grgb, ggray, bt.getWidth(), bt.getHeight(), 1);
                                                //writeBgrImage(grgb);
                                                ret = facedetect.DetectFaceRgb(facehandle, grgb, bt.getWidth(), bt.getHeight(), rectfinal,1);
                                                if(ret > 0) {
                                                    Log.i(TAG,"batchEnroll face"+rectfinal[0]+"-"+rectfinal[1]+"-"+rectfinal[2]+"-"+rectfinal[3]);
                                                    facedetect.AliveActionDetect(facehandle, grgb, bt.getWidth(), bt.getHeight(),rectfinal, mouth_s, heard_h, heard_v,blurness,landmark,rocrect);
                                                    Log.i(TAG,"batchEnroll-action"+mouth_s[0]+"-"+heard_h[0]+"-"+heard_v[0]+"-"+blurness[0]);
                                                    Log.i(TAG,"batchEnroll face"+rocrect[0]+"-"+rocrect[1]+"-"+rocrect[2]+"-"+rocrect[3]);
                                                    ret = facedetect.ExtractFeatureBGR(facehandle, facefeater, grgb, bt.getWidth(), bt.getHeight(), null, rectfinal,landmark);
                                                    if (ret == 0) {
                                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-S");
                                                        String timestr = formatter.format(new Date());
                                                        String ID = "/sdcard/facedata/" + timestr + "_" + String.valueOf(rectfinal[0]) + "_" + String.valueOf(rectfinal[1]) + "_" + String.valueOf(rectfinal[2]) + "_" + String.valueOf(rectfinal[3]) + ".jpeg";
                                                        Log.i(TAG, i + "Enroll " + ID);
                                                        facedebase.FloatToChar(facefeater, facefeaterraw);
                                                        userdb.insert(ID, timestr, facefeaterraw);
                                                        File file3 = new File("/sdcard/facedata/Alive/" + timestr);
                                                        Log.i(TAG,"mkdir /sdcard/facedata/Alive/" + timestr);
                                                        file3.mkdir();
                                                        file3 = null;
                                                        file3 = new File("/sdcard/facedata/Nolive/" + timestr);
                                                        Log.i(TAG,"mkdir /sdcard/facedata/Nolive/" + timestr);
                                                        file3.mkdir();
                                                        copyFile(destoder, ID);
                                                        facecountsuccess++;
                                                    }
                                                    else
                                                    {
                                                        facecountfail++;
                                                    }
                                                }
                                                else
                                                {
                                                    copyFile(destoder, "/sdcard/FailEnroll/"+getFileName(destoder));
                                                    facecountfail++;
                                                }
                                                bt = null;
                                                grgb = null;
                                                ggray = null;
                                                int pers = ((i+1)*100)/filecnt;
                                                message = new Message();
                                                message.what = 6;
                                                message.obj = pers;
                                                mHandler.sendMessage(message);
                                            }
                                        }
                                    }
                                    facelist = null;
                                    Cursor cursor = userdb.select("all");
                                    facecount = cursor.getCount();
                                    Log.e(TAG, String.format("获取记录数%d\n", facecount));
                                    if (facecount > 0) {
                                        int offset = 0;
                                        facelist = new float[128 * facecount];
                                        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                                            int modColumn = cursor.getColumnIndex("module");
                                            byte[] mod = cursor.getBlob(modColumn);
                                            facedebase.CharToFloat(mod, facefeaterdet);
                                            System.arraycopy(facefeaterdet, 0, facelist, offset * 128, 128);
                                            offset++;
                                            Log.e(TAG, String.format("获取模板%d\n", offset));
                                        }
                                    }
                                    showdelay = 0;
                                    Action = 1;
                                    ret = faceirdetect.SetParams(faceirhandle,1.3f,0.8f,64,0);
                                    //ret = facedetect.SetParams(facehandle,1.2f,4,64,0);
                                    break;
                                case 5:
                                    if(detect) {
                                        Date dts = new Date();
                                        ret = facedetect.DetectFaceRgb(facehandle, rgb24, picwith, pichight, rectfinal, 1);
                                        if (ret > 0) {
                                            rwl2.writeLock().lock();
                                            facerect[0] = rectfinal[0];
                                            facerect[1] = rectfinal[1];
                                            facerect[2] = rectfinal[2];
                                            facerect[3] = rectfinal[3];
                                            //Log.i(TAG,"RGB face"+rectfinal[0]+"-"+rectfinal[1]+"-"+rectfinal[2]+"-"+rectfinal[3]);
                                            rwl2.writeLock().unlock();
                                            if (capture_status == false) {
                                                
                                                detect = false;
                                                continue;
                                            }
                                            SimpleDateFormat formatterl = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
                                            Date dtel = new Date();
                                            String timestrl = formatterl.format(dtel);
                                            rwl.writeLock().lock();
                                            if (indexrgb == indexir) {
                                                indexrgb++;
                                            } else if (indexrgb < indexir) {
                                                indexrgb = indexir;
                                            } else {
                                                indexrgb++;
                                            }
                                            String fname;
                                            if (absudisk == "") {
                                                if (capturemode == 0) {
                                                    fname = "/sdcard/capture/human/" + timestrl + "_" + indexrgb + "_vis.jpg";
                                                } else if (capturemode == 1) {
                                                    fname = "/sdcard/capture/visphoto/" + timestrl + "_" + indexrgb + "_vis.jpg";
                                                } else if (capturemode == 2) {
                                                    fname = "/sdcard/capture/nirphoto/" + timestrl + "_" + indexrgb + "_vis.jpg";
                                                } else {
                                                    fname = "/sdcard/capture/video/" + timestrl + "_" + indexrgb + "_vis.jpg";
                                                }

                                            } else {
                                                if (capturemode == 0) {
                                                    fname = absudisk + "/capture/human/" + timestrl + "_" + indexrgb + "_vis.jpg";
                                                } else if (capturemode == 1) {
                                                    fname = absudisk + "/capture/visphoto/" + timestrl + "_" + indexrgb + "_vis.jpg";
                                                } else if (capturemode == 2) {
                                                    fname = absudisk + "/capture/nirphoto/" + timestrl + "_" + indexrgb + "_vis.jpg";
                                                } else {
                                                    fname = absudisk + "/capture/video/" + timestrl + "_" + indexrgb + "_vis.jpg";
                                                }

                                            }
                                            rwl.writeLock().unlock();
         
                                        }
                                        detect = false;
                                    }
                                    else
                                    {
                                        Thread.currentThread().sleep(50);
                                    }

                                    break;
                                default:
                                    break;
                            }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i(TAG,"run exception");
                    }
                    }
            }
        };
        facethread.start();

        faceirthread = new Thread() {
            public void run() {
                Message message;
                int ret = 0;
                float aliveret = 0.0f;
                while (Running) {
                    try {
                        switch(IrAction)
                        {
                            case 0:
                                Thread.currentThread().sleep(50);
                                break;
                            case 1:
                                if(facecount<=0)
                                {
                                    Thread.currentThread().sleep(50);
                                    break;
                                }
                                if(irdetect)
                                {
                                    ret = faceirdetect.DetectFaceRgb(faceirhandle, irrgb24, irpicwith, irpichight, irrectfinal,1);
                                    if(ret > 0)
                                    {
                                        SetLowLevelTime();
                                        Log.i(TAG,"Ir Match face"+irrectfinal[0]+"-"+irrectfinal[1]+"-"+irrectfinal[2]+"-"+irrectfinal[3]);
                                        semaphore.acquire();
                                        aliveret = facedetect.AliveStaticDetect(faceirhandle, irrgb24, irpicwith, irpichight, irrectfinal);
                                        Log.i(TAG,"Ir alive "+aliveret);
                                        if(aliveret*100 < alivethrash)
                                        {

                                            irdetect = false;
                                            rwl.writeLock().lock();
                                            aliveflag = 0;
                                            rwl.writeLock().unlock();
                                            semaphore.release();
                                            break;
                                        }
                                        rwl.writeLock().lock();
                                        aliveflag = 1;
                                        alive_facerect[0] = irrectfinal[0];
                                        alive_facerect[1] = irrectfinal[1];
                                        alive_facerect[2] = irrectfinal[2];
                                        alive_facerect[3] = irrectfinal[3];
                                        rwl.writeLock().unlock();
                                        semaphore.release();

//                                        //frameCount = 0;
//                                        faceirdetect.AliveActionDetect(faceirhandle, irrgb24, irpicwith, irpichight, irrectfinal, irmouth_s, irheard_h, irheard_v,irblurness,irlandmark,irrocrect);
//                                        Log.i(TAG,"Ir blurness: "+irblurness[0]);
//                                        if(irblurness[0] >= 80||irheard_h[0] != 0)
//                                        {
//                                            irdetect = false;
//                                            break;
//                                        }
//
//                                        ret = faceirdetect.ExtractFeatureBGR(faceirhandle, faceirfeater, irrgb24, irpicwith, irpichight, null, irrectfinal,irlandmark);
//                                        if(ret == FME_OK)
//                                        {
//
//                                            //Log.i(TAG,"1：1 "+irmaxscore);
//                                            irmaxscore = faceirdetect.MatchN(faceirhandle,faceirfeater,facelist,facecount,iroutside,irfscore,3,0);
//
//                                            //Log.i(TAG,"find index:"+iroutside[0]+" :"+iroutside[1]+" :"+iroutside[2]);
//                                            Log.i(TAG,"Ir find fscore:"+irfscore[0]+" :"+irfscore[1]+" :"+irfscore[2]);
//
//                                            if(irmaxscore >= levelthrash)
//                                            {
//                                                if(facecount<=0)
//                                                {
//                                                    Thread.currentThread().sleep(50);
//                                                    irdetect = false;
//                                                    break;
//                                                }
//                                                //Log.i(TAG, "find people:" + iroutside[0] + " maxscore:" + irmaxscore);
//                                                Cursor cursor = userdb.select("all");
//                                                cursor.moveToFirst();
//                                                cursor.moveToPosition(iroutside[0]);
//                                                int modColumn = cursor.getColumnIndex("username");
//                                                int modColumn2 = cursor.getColumnIndex("time");
//                                                String id = cursor.getString(modColumn);
//                                                Log.i(TAG, id);
//                                                String timeenroll = cursor.getString(modColumn2);
//                                                Log.i(TAG, "Ir find :" + id);
//                                                //Log.i(TAG, "Enroll at " + timeenroll);
//                                                cursor.close();
//
//                                                if(showdelay == 0)
//                                                {
//                                                    showdelay = 30;
//                                                    Log.i(TAG, "Ir send success");
//                                                    message = new Message();
//                                                    message.obj = id;
//                                                    message.what = 0;
//                                                    if(IrAction != 5) {
//                                                        IrAction = 0;
//                                                    }
//                                                    mHandler.sendMessage(message);
//                                                    message = new Message();
//                                                    message.what = 3;
//                                                    mHandler.sendMessageDelayed(message, 1000);
//                                                }
//                                            }
//                                            else
//                                            {
//                                                //Log.i(TAG, "no find people:"+"maxscore:"+irmaxscore);
//                                            }
//                                        }
                                    }
                                    else
                                    {
                                        rwl.writeLock().lock();
                                        aliveflag = 0;
                                        rwl.writeLock().unlock();
                                        if(CheckLowLevel()==true)
                                        {
                                            Thread.currentThread().sleep(400);
                                        }

                                    }
                                    irdetect = false;
                                }
                                else
                                {
                                    Thread.currentThread().sleep(50);
                                    break;
                                }
                                break;
                            case 5:
                                if(irdetect) {
                                    ret = faceirdetect.DetectFaceRgb(faceirhandle, irrgb24, irpicwith, irpichight, irrectfinal, 1);
                                    if (ret > 0) {
                                        SetLowLevelTime();
                                        //Log.i(TAG,"Ir Match face"+irrectfinal[0]+"-"+irrectfinal[1]+"-"+irrectfinal[2]+"-"+irrectfinal[3]);
                                        SimpleDateFormat formatterl = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
                                        Date dtel = new Date();
                                        String timestrl = formatterl.format(dtel);
                                        rwl.writeLock().lock();
                                        if (indexrgb == indexir) {
                                            indexir++;
                                        } else if (indexir < indexrgb) {
                                            indexir = indexrgb;
                                        } else {
                                            indexir++;
                                        }
                                        String fname;
                                        if (absudisk == "") {
                                            if (capturemode == 0) {
                                                fname = "/sdcard/capture/human/" + timestrl + "_" + indexir + "_nir.jpg";
                                            } else if (capturemode == 1) {
                                                fname = "/sdcard/capture/visphoto/" + timestrl + "_" + indexir + "_nir.jpg";
                                            } else if (capturemode == 2) {
                                                fname = "/sdcard/capture/nirphoto/" + timestrl + "_" + indexir + "_nir.jpg";
                                            } else {
                                                fname = "/sdcard/capture/video/" + timestrl + "_" + indexir + "_nir.jpg";
                                            }

                                        } else {
                                            if (capturemode == 0) {
                                                fname = absudisk + "/capture/human/" + timestrl + "_" + indexir + "_nir.jpg";
                                            } else if (capturemode == 1) {
                                                fname = absudisk + "/capture/visphoto/" + timestrl + "_" + indexir + "_nir.jpg";
                                            } else if (capturemode == 2) {
                                                fname = absudisk + "/capture/nirphoto/" + timestrl + "_" + indexir + "_nir.jpg";
                                            } else {
                                                fname = absudisk + "/capture/video/" + timestrl + "_" + indexir + "_nir.jpg";
                                            }

                                        }
                                        rwl.writeLock().unlock();
                                    }
                                    else {
                                        if(CheckLowLevel()==true)
                                        {
                                            Thread.currentThread().sleep(400);
                                        }
                                    }
                                    irdetect = false;
                                }
                                else
                                {
                                    Thread.currentThread().sleep(50);
                                }
                                break;
                            default:
                                break;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i(TAG,"run 2 exception");
                    }
                }
            }
        };
        faceirthread.start();
        isPause = false;
    }

    public String getFileName(String pathandname){
        int start=pathandname.lastIndexOf("/");
        int end=pathandname.lastIndexOf(".");
        if(start!=-1 && end!=-1){
            return pathandname.substring(start+1,end);
        }else{
            return null;
        }
    }

    public boolean copyFile(String srcpath, String destpath) {
        boolean result = false;
        if ((srcpath == null) || (destpath== null)) {
            return result;
        }
        File src= new File(srcpath);
        File dest= new File(destpath);
        if (dest!= null && dest.exists()) {
            dest.delete(); // delete file
        }
        try {
            dest.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileChannel srcChannel = null;
        FileChannel dstChannel = null;

        try {
            srcChannel = new FileInputStream(src).getChannel();
            dstChannel = new FileOutputStream(dest).getChannel();
            srcChannel.transferTo(0, srcChannel.size(), dstChannel);
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
        try {
            srcChannel.close();
            dstChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    protected void onPause() {
        Log.e(TAG, String.format("onPause: ready to pause app\n"));
        Running = false;

        SharedPreferences.Editor editor = getSharedPreferences("syfaceinfo", MODE_WORLD_WRITEABLE).edit();
        editor.putString("front", String.valueOf(cameraselect));
        editor.putString("degreed", String.valueOf(direct));
        editor.putString("alive", String.valueOf(aliveindex));
        editor.putString("mode", String.valueOf(capturemode));

        editor.putString("ir", String.valueOf(indexir));
        editor.putString("rgb", String.valueOf(indexrgb));
        editor.commit();
        closeIr();
        closewhite();
        try {

            facethread.join(); // wait for secondary to finish
            Log.e(TAG, String.format("onPause: facethread finish\n"));
            facethread = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {

            faceirthread.join(); // wait for secondary to finish
            Log.e(TAG, String.format("onPause: facethread finish\n"));
            faceirthread = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (null != camera) {
            camera.cancelAutoFocus();
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
        if (null != camerair) {
            camerair.cancelAutoFocus();
            camerair.stopPreview();
            camerair.setPreviewCallback(null);
            camerair.release();
            camerair = null;
        }
        if(mp != null)
        {
            if(mp.isPlaying())
            {
                mp.stop();
            }
            mp.release();
            mp = null;
        }
        vSurfaceHolder = null;
        facedetect.DestroyIns(facehandle);
        faceirdetect.DestroyIns(faceirhandle);
        rwl2 = null;
        rwl = null;
//        ulSeeLiveMgr.destroy();
        facedetect = null;
        facedebase = null;
        faceirdetect = null;
        faceirdebase = null;

        gray = null;
        rgb24 = null;
        rgb32 = null;
        rgb32forcopy = null;
        Maxrgb32forcopy = null;

        irgray = null;
        irrgb24 = null;
        irrgb32 = null;
        irrgb32forcopy = null;

        mHandler = null;
        //Surfaceimage = null;
        //showBit.recycle();
        userdb.close();
        super.onPause();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            if (null != camera) {
                camera.release();
                camera = null;
            }
            e.printStackTrace();
        }
    }

    void play_wav(int  resid)
    {
        if(Running == false)
        {
            return;
        }
        if(mp != null)
        {
            if(mp.isPlaying())
            {
                mp.stop();
            }
            mp.release();
            mp = null;
        }
        mp = MediaPlayer.create(this, resid);
        mp.start();//播放音乐
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        if (null != camera) {
            Camera.Parameters myParam = camera.getParameters();
            myParam.setPictureFormat(PixelFormat.JPEG);// 设置拍照后存储的图片格式
            // 设置大小和方向等参数
            List<Camera.Size> sizeList = myParam.getSupportedPreviewSizes();
		    for (int i = 1; i < sizeList.size(); i++){
		        if(sizeList.get(i).width==orgwidth)
                {
                    camerah = sizeList.get(i).height;
                    cameraw = sizeList.get(i).width;
                    //break;
                }
	        }
	        myParam.setPreviewSize(cameraw, camerah);
            if(direct == 0||direct ==2)
            {
                picwith = cameraw;
                pichight = camerah;
            }
            else
            {
                picwith = camerah;
                pichight = cameraw;
            }
            Log.i(TAG, "set  " + cameraw + " x " + camerah);
            camera.setParameters(myParam);
            //camera.setDisplayOrientation(90);

            camera.startPreview();
        }
        if (null != camerair) {
            Camera.Parameters myParam = camerair.getParameters();
            myParam.setPictureFormat(PixelFormat.JPEG);// 设置拍照后存储的图片格式
            // 设置大小和方向等参数
            List<Camera.Size> sizeList = myParam.getSupportedPreviewSizes();
            for (int i = 1; i < sizeList.size(); i++){
                if(sizeList.get(i).width==orgwidth)
                {
                    ircamerah = sizeList.get(i).height;
                    ircameraw = sizeList.get(i).width;
                    //break;
                }
            }
            myParam.setPreviewSize(ircameraw, ircamerah);
            if(direct == 0||direct ==2)
            {
                irpicwith = ircameraw;
                irpichight = ircamerah;
            }
            else
            {
                irpicwith = ircamerah;
                irpichight = ircameraw;
            }
            Log.i(TAG, "set  " + ircameraw + " x " + ircamerah);
            camerair.setParameters(myParam);
            //camera.setDisplayOrientation(90);

            camerair.startPreview();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (null != camera) {
            camera.setPreviewCallback(null);
            camera.cancelAutoFocus();
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        if (null != camerair) {
            camerair.setPreviewCallback(null);
            camerair.cancelAutoFocus();
            camerair.stopPreview();
            camerair.release();
            camerair = null;
        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

    }
}