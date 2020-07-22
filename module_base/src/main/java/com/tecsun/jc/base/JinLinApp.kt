package com.tecsun.jc.base

//import com.squareup.leakcanary.LeakCanary
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import com.alibaba.android.arouter.launcher.ARouter
import com.lzy.okgo.OkGo
import com.pgyersdk.PgyerActivityManager
import com.pgyersdk.crash.PgyCrashManager
import com.pgyersdk.crash.PgyerCrashObservable
import com.tecsun.jc.base.bean.db.invigilation.bean.ReadCardInfoBean
import com.tecsun.jc.base.bean.db.invigilation.bean.StudentDetailsBean
import com.tecsun.jc.base.bean.param.register.ApplyCardParam
import com.tecsun.jc.base.builder.ActivityLifecycleCallBacksBuilder
import com.tecsun.jc.base.builder.WebViewCacheSettingBuilder
import com.tecsun.jc.base.collector.BaseActivityCollector
import com.tecsun.jc.base.common.BaseConstant
import org.litepal.LitePal


class JinLinApp : Application(){

    companion object {
        @JvmStatic
        var mDeviceId: String = BaseConstant.DEFAULT_DEVICE_ID
        @JvmStatic
        var mTokenId: String? = null
        @JvmStatic
        var context: Context? = null
            private set
        @JvmStatic
        val instance: JinLinApp by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            JinLinApp()
        }
        @JvmStatic
        var accountName:String? = ""
        @JvmStatic
        var sfzh:String? = ""

        @JvmStatic
        var isLogin:Boolean? = false

        @JvmStatic
        var istype :Int = 1;//1实名、2社保卡申领

        @JvmStatic
        var loginExaminer: ReadCardInfoBean? = null

        @JvmStatic
        var isIDCardModulePowerOff: Boolean? = true

        @JvmStatic
        var studentDetailsBean: StudentDetailsBean? = null

        @JvmStatic
        var courseId:Int? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        if (BuildConfig.DEBUG) {  // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.printStackTrace()
        }
        ARouter.init(this)
        OkGo.getInstance().init(this)


        //启动 Pgyer(蒲公英) 检测 Crash 功能；
        PgyCrashManager.register()
        //添加自定义的 Crash 功能（新功能）
        PgyerCrashObservable.get().attach { thread, throwable ->
            if (throwable != null && throwable is Exception) {
                //发布版本才上报异常
                if(BuildConfig.isRelease){
                    PgyCrashManager.reportCaughtException(throwable)
                }

                //退出程序
                BaseActivityCollector.finishAllActivity()
                var  intent = getPackageManager().getLaunchIntentForPackage(getPackageName())
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
        PgyerActivityManager.set(this)


        //设置webView的缓存
        WebViewCacheSettingBuilder().normalSet(this)
        //腾讯X5内核设置
//        X5WebViewSettingBuilder().set(this)


        //本地数据库
        LitePal.initialize(this)


        ActivityLifecycleCallBacksBuilder.set(this)

//        LeakCanary.install(this)
        
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()

    }


    var mApplyCardParam: ApplyCardParam? = ApplyCardParam()
    fun setApplyCardParamSetImageNull(){
        mApplyCardParam?.show_card_back_bitmap = null
        mApplyCardParam?.show_card_right_bitmap = null
        mApplyCardParam?.show_card_selfie_bitmap = null
    }

    private  var mInsertPicture: Bitmap? = null
    fun getInsertPicture(): Bitmap? {
        return mInsertPicture
    }


    fun setInsertPicture(insertPicture: Bitmap) {
        mInsertPicture = insertPicture
    }


    var isCanAutoLogin = false
    var autoLoginSFZH:String? = null
    var autoLoginPWD:String? = null




}






















