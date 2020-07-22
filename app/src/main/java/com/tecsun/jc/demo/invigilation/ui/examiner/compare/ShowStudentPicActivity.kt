package com.tecsun.jc.demo.invigilation.ui.examiner.compare

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.StudentDetailsBean
import com.tecsun.jc.base.builder.StudentOwnImageBuilder
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.common.BaseConstant.FILTER_SELECT_POSITION
import com.tecsun.jc.base.event.NetWorkChangeEvent
import com.tecsun.jc.base.net.monitor.NetChangeObserver
import com.tecsun.jc.base.net.monitor.NetUtils
import com.tecsun.jc.base.sign.APPSignUtils
import com.tecsun.jc.base.utils.BitmapUtils
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.invigilation.builder.compare.CompareBuilderFactory
import com.tecsun.jc.demo.invigilation.builder.device.DeviceBuilder
import com.tecsun.jc.demo.invigilation.ui.pic.TakePhotoActivity
import com.tecsun.jc.demo.invigilation.util.constant.ActivityConstant
import com.tecsun.jc.demo.invigilation.util.constant.Constants
import com.tecsun.jc.register.util.constant.Const.EXAMINATION_ROOM_INFO_CODE
import kotlinx.android.synthetic.main.activity_student_pic_show.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class ShowStudentPicActivity : BaseActivity() {


    private var selectPosition = 0

    private var studentDetailsBean: StudentDetailsBean? = null

    private var localBitmap: Bitmap? = null

    private var localPicID = 0
    private var takePicID = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_student_pic_show
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle(getString(R.string.app_student_compare_pic))
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        APPSignUtils.signApp()

        selectPosition = intent.getIntExtra(BaseConstant.FILTER_SELECT_POSITION, -1)

        var bean = intent.getSerializableExtra(BaseConstant.FILTER_SELECT)
        bean?.let {
            studentDetailsBean = bean as StudentDetailsBean
        }

        if (studentDetailsBean != null && !studentDetailsBean!!.sfzh.isNullOrBlank()) {
            var bitmap = StudentOwnImageBuilder.getSFZBitmap(studentDetailsBean!!.sfzh)
            if (bitmap != null) {
                localBitmap = bitmap
                ivLocalImage.setImageBitmap(bitmap)
            }
        }

        //拍照
        flTakePic.setOnClickListener {
            takePic()
        }

        //取消
        btn_change_communication_cancel.setOnClickListener {
            finish()
        }
        //发送到后台比对;
        btn_change_communication_confirm.setOnClickListener {
            //TODO

            if (pic == null) {

                showToast("请进行人像拍照!")
                return@setOnClickListener
            }
            showLoadingDialog(tipContent = "正在上传本地图片数据...")
            var base64 = BitmapUtils.bitmapToBase64(localBitmap)
//            uploadGetMessageResultPictureForLocalPicID(base64)
        }


        //开启网络广播监听
        NetStateReceiver.registerNetworkStateReceiver(this)
        monitorNetWork()

        //start 20190628
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }


        //身份证比对:
        vSFZCompare.setOnClickListener {
            DeviceBuilder.skip(this, getString(R.string.app_student_brush_sfz_card),studentDetailsBean?.sfzh?:"")
        }
        //刷社保卡比对
        vSocialSecurityCardsCompare.setOnClickListener {
            DeviceBuilder.skipReadSSC(this, getString(R.string.app_student_brush_ssc_card),studentDetailsBean?.sfzh?:"")
        }


        CompareBuilderFactory.initICompare(studentDetailsBean)
        CompareBuilderFactory.setSelectPosition(selectPosition)


        if (ActivityConstant.showStudentPicActivity != null) {
            if (!ActivityConstant.showStudentPicActivity.isFinishing) {
                ActivityConstant.showStudentPicActivity.finish()
            }
        }
        ActivityConstant.showStudentPicActivity = this

    }

    override fun onResume() {
        super.onResume()
        //TODO
//        //清空学生的身份证照片:
//        StudentOwnSFZImageBuilder.deleteAllSFZImage()
//        //清空学生的全部社保卡照片(第二代社保卡是没有身份证照片的):
//        StudentOwnSSCImageBuilder.deleteAllSFZImage()
    }


    /**
     * 拍照
     */
    fun takePic() {
        TakePhotoActivity.isThis = true
        val intent = Intent(this, TakePhotoActivity::class.java)
        intent.putExtra(BaseConstant.FILTER_SELECT, EXAMINATION_ROOM_INFO_CODE)
        intent.putExtra(Constants.LAST_ACTIVITY_PHOTO, Constants.APPLY_PHOTO)
        startActivityForResult(intent, EXAMINATION_ROOM_INFO_CODE)
    }


    /**自拍照片 */
    private var pic: Bitmap? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        LogUtil.e("requestCode :$requestCode")
        LogUtil.e("resultCode :$resultCode")
        when (resultCode) {
            EXAMINATION_ROOM_INFO_CODE -> {
                var picPath = data?.getStringExtra(BaseConstant.FILTER_SELECT_DATA)
                var mBitmap = BitmapFactory.decodeFile(picPath)
                pic = mBitmap
                pic?.let {
                    LogUtil.e("高:${pic!!.height},宽:${pic!!.width}")
                    img_show_card_selfie_bitmap.setImageBitmap(pic)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }



    override fun onDestroy() {
        NetStateReceiver.removeRegisterObserver(netObserver)
        NetStateReceiver.unRegisterNetworkStateReceiver(this)

        CompareBuilderFactory.release()

        if (ActivityConstant.showStudentPicActivity != null) {
            if (!ActivityConstant.showStudentPicActivity.isFinishing) {
                ActivityConstant.showStudentPicActivity.finish()
            }
        }
        ActivityConstant.showStudentPicActivity = null

        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    fun finishForResult(){
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> finishForResult()")
        val intent = Intent()
        intent.putExtra(FILTER_SELECT_POSITION, selectPosition)
        setResult(EXAMINATION_ROOM_INFO_CODE, intent)
        finish()
    }




    private var lastSignTime = 0L
    /***网络状态监听变化*/
    private var netObserver = object : NetChangeObserver {
        override fun onNetConnected(type: NetUtils.NetType?) {
            com.tecsun.jc.base.utils.log.LogUtil.e(">>>>>>>>>>>>>onNetConnected()")
            com.tecsun.jc.base.utils.log.LogUtil.e(type)
            //网络变化,重新获取token,由于网络变化这个方法会回调多次,所以这里根据时间过滤;
            if (System.currentTimeMillis() - lastSignTime < 100) {
                return
            }
            com.tecsun.jc.base.utils.log.LogUtil.e("APPSignUtils.signApp()")
            lastSignTime = System.currentTimeMillis()
            APPSignUtils.signApp()

            //发送网络变化通知
            EventBus.getDefault().post(NetWorkChangeEvent(1))

        }

        override fun onNetDisConnect() {
            com.tecsun.jc.base.utils.log.LogUtil.e(">>>>>>>>>>>>>>onNetDisConnect()")
        }
    }

    /**监听网络变化*/
    private fun monitorNetWork() {
        NetStateReceiver.registerObserver(netObserver)
    }

    /**网络变化自动回调下面的方法*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun afterNetWorkChange(event: NetWorkChangeEvent) {
//        //没拿成功才进行数据请求
//        LogUtil.e("MainCycleFragment 拿取轮播图片")
//        getPicUrl()
    }

    /**
     * 使用广播去监听网络
     * 16/9/13.
     */
    class NetStateReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            mBroadcastReceiver = this@NetStateReceiver
            if (intent.action!!.equals(
                    ANDROID_NET_CHANGE_ACTION,
                    ignoreCase = true
                ) || intent.action!!.equals(
                    CUSTOM_ANDROID_NET_CHANGE_ACTION,
                    ignoreCase = true
                )
            ) {
                if (!NetUtils.isNetworkAvailable(context)) {
                    Log.e(this.javaClass.name, "<--- network disconnected --->")
                    isNetworkAvailable = false
                } else {
                    Log.e(this.javaClass.name, "<--- network connected --->")
                    isNetworkAvailable = true
                    apnType = NetUtils.getAPNType(context)
                }
                notifyObserver()
            }
        }

        private fun notifyObserver() {
            if (!mNetChangeObservers!!.isEmpty()) {
                val size = mNetChangeObservers!!.size
                for (i in 0 until size) {
                    val observer = mNetChangeObservers!![i]
                    if (observer != null) {
                        if (isNetworkAvailable) {
                            observer.onNetConnected(apnType)
                        } else {
                            observer.onNetDisConnect()
                        }
                    }
                }
            }
        }


        companion object {

            val CUSTOM_ANDROID_NET_CHANGE_ACTION = "com.zhanyun.api.netstatus.CONNECTIVITY_CHANGE"
            private val ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
            private val TAG = NetStateReceiver::class.java.simpleName

            var isNetworkAvailable = false
                private set
            var apnType: NetUtils.NetType? = null
                private set
            private var mNetChangeObservers: ArrayList<NetChangeObserver>? = ArrayList()
            private var mBroadcastReceiver: BroadcastReceiver? = null

            private val receiver: BroadcastReceiver
                get() {
                    if (null == mBroadcastReceiver) {
                        synchronized(NetStateReceiver::class.java) {
                            if (null == mBroadcastReceiver) {
                                mBroadcastReceiver = NetStateReceiver()
                            }
                        }
                    }
                    return mBroadcastReceiver!!
                }

            /**
             * 注册
             *
             * @param mContext
             */
            fun registerNetworkStateReceiver(mContext: Context?) {
                if (mContext == null) {
                    return
                }

                //动态注册网络变化广播
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //实例化IntentFilter对象
                    val filter = IntentFilter()
                    filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
                    val netBroadcastReceiver = NetStateReceiver()
                    //注册广播接收
                    mContext.registerReceiver(netBroadcastReceiver, filter)
                }


                val filter = IntentFilter()
                filter.addAction(CUSTOM_ANDROID_NET_CHANGE_ACTION)
                filter.addAction(ANDROID_NET_CHANGE_ACTION)
                mContext.applicationContext.registerReceiver(receiver, filter)
            }

            //    /**
            //     * 清除
            //     *
            //     * @param mContext
            //     */
            //    public static void checkNetworkState(Context mContext) {
            //        Intent intent = new Intent();
            //        intent.setAction(CUSTOM_ANDROID_NET_CHANGE_ACTION);
            //        mContext.sendBroadcast(intent);
            //    }

            /**
             * 反注册
             *
             * @param mContext
             */
            fun unRegisterNetworkStateReceiver(mContext: Context) {
                if (mBroadcastReceiver != null) {
                    try {
                        mContext.applicationContext.unregisterReceiver(mBroadcastReceiver)
                    } catch (e: Exception) {

                    }

                }

            }

            /**
             * 添加网络监听
             *
             * @param observer
             */
            fun registerObserver(observer: NetChangeObserver) {
                if (mNetChangeObservers == null) {
                    mNetChangeObservers = ArrayList()
                }
                mNetChangeObservers!!.add(observer)
            }

            /**
             * 移除网络监听
             *
             * @param observer
             */
            fun removeRegisterObserver(observer: NetChangeObserver) {
                if (mNetChangeObservers != null) {
                    if (mNetChangeObservers!!.contains(observer)) {
                        mNetChangeObservers!!.remove(observer)
                    }
                }
            }
        }
    }

}























