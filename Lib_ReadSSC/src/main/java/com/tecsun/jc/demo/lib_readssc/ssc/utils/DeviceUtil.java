package com.tecsun.jc.demo.lib_readssc.ssc.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.topwise.cloudpos.aidl.AidlDeviceService;
import com.topwise.cloudpos.aidl.iccard.AidlICCard;
import com.topwise.cloudpos.aidl.magcard.AidlMagCard;
import com.topwise.cloudpos.aidl.printer.AidlPrinter;

import java.util.List;

public class DeviceUtil {

    protected Context  mContext;
    private Object mCardReader ;
    private int mCardReaderType = 0 ;

    public DeviceUtil(Context context,int mCardReaderType ) {
        this.mContext = context;
        this.mCardReaderType = mCardReaderType ;
    }

    public static final String TOPWISE_SERVICE_ACTION = "topwise_cloudpos_device_service";
    private static final String TAG = "TPW-BaseTestActivity";
    //设别服务连接桥
    private ServiceConnection conn = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder serviceBinder) {
            Log.d(TAG,"aidlService服务连接成功");
            if(serviceBinder != null){	//绑定成功
                AidlDeviceService serviceManager = AidlDeviceService.Stub.asInterface(serviceBinder);
                onDeviceConnected(serviceManager);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"AidlService服务断开了");
        }
    };

    public void onDeviceConnected(AidlDeviceService serviceManager) {
        try {
            Log.d(TAG,"mCardReaderType:"+mCardReaderType);
            if(this.mCardReaderType==0) {
                this.mCardReader = AidlICCard.Stub.asInterface(serviceManager
                        .getInsertCardReader());
            }else if(this.mCardReaderType==1) {
                this.mCardReader = AidlMagCard.Stub.asInterface(serviceManager
                        .getMagCardReader());
            }else if(this.mCardReaderType==2){
                this.mCardReader = AidlPrinter.Stub.asInterface(serviceManager.getPrinter());
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Object getmCardReader() {
        return mCardReader;
    }


    //绑定服务
    public void bindService(){
        Intent intent = new Intent();
        intent.setAction(TOPWISE_SERVICE_ACTION);
        final Intent eintent = new Intent(createExplicitFromImplicitIntent(mContext,intent));
        boolean flag = mContext.bindService(eintent, conn, Context.BIND_AUTO_CREATE);
        if(flag){
            Log.d(TAG,"服务绑定成功");
        }else{
            Log.d(TAG,"服务绑定失败");
        }
    }

    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    public void unBindService(){
        mContext.unbindService(conn);
    }
}
