package com.tecsun.jc.base.sign.check;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;

public class CheckTokenService extends Service {

    private static CheckListener listener;

    public interface CheckListener{
        void start();
    }

    public static void setListener(CheckListener l){
        listener = l;
    }


    private final long intervalTime = 3 * 60 * 1000;//3分钟检查一次
    private long lastUpdateTime = 0;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d("CheckTokenService", "===================onStartCommand===================");
        if((System.currentTimeMillis() - lastUpdateTime )< intervalTime){
            return super.onStartCommand(intent, flags, startId);
        }
        lastUpdateTime = System.currentTimeMillis();

        Log.d("CheckTokenService", "executed at " + new Date().toString());
        if(listener!=null){
            listener.start();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(CheckTokenService.this, CheckTokenService.class);
                startService(i);
            }
        },intervalTime);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
