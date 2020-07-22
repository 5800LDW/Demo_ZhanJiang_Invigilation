package com.tecsun.jc.demo.lib_readcard.ssc

import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.builder.apdu.DataDoWithBuilder
import com.tecsun.jc.base.builder.sound.SoundBuilder
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.listener.IEvents
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.lib_readcard.R
import com.telpo.tps550.api.fingerprint.FingerPrint
import com.telpo.tps550.api.reader.SmartCardReader
import com.telpo.tps550.api.util.StringUtil
import com.telpo.tps550.api.util.SystemUtil
import kotlinx.android.synthetic.main.activity_read_card_ssc.*

/**
 * 读取社保卡
 * @author liuDongWen
 * @date 2019/10/31
 */
@Route(path = RouterHub.ROUTER_LIB_READCARD_SSC)
class ReadSSCActivity : BaseActivity() {

    private var reader: SmartCardReader? = null
    private val mHandler = Handler()
    private val intervalTime = 200L
    private var studentSFZH = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_read_card_ssc
    }

    private var titleString = ""
    override fun setTitleBar(titleBar: TitleBar) {
        intent?.let {
            titleString = intent.getStringExtra("titleName") ?: ""
            titleBar.setTitle(titleString)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Glide.with(this).load(R.drawable.read_card_ssc_for_module)
            .into(findViewById<View>(R.id.ivPic) as ImageView)
        reader = SmartCardReader(this@ReadSSCActivity)
        openReadModule()
        mHandler.postDelayed({
            recyclePowerOn()
        }, intervalTime)

        studentSFZH = intent.getStringExtra(BaseConstant.STUDENT_SFZH) ?: ""

        if (studentSFZH.isNullOrBlank()) {
            showToast("学生身份证号不能为空!")
            myFinish()
        }
    }

    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        powerOff()
        closeReadModule()
        super.onDestroy()
    }


    private fun recyclePowerOn() {
        var isPowerOn = powerOn()
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>> 通电的结果: $isPowerOn")
        //通电失败循环通电
        if (!isPowerOn) {

            mHandler.postDelayed({
                recyclePowerOn()
            }, intervalTime)
            tvPlugInCard.visibility = View.VISIBLE
//            tvReading.visibility = View.GONE
        }
        //通电成功进行读取
        else {
//            showToast(getString(R.string.app_reading_ssc))
            tvPlugInCard.visibility = View.GONE
//          tvReading.visibility = View.VISIBLE
            doReadSSC1()
        }
    }

    /**
     * step1
     * 打开读写器
     */
    private fun openReadModule() {
        openTask().execute()
    }

    /**
     * step2
     * 通电
     * 作为检测社保卡在不在, 通电一直失败就表示: 正在检测中...
     *
     * contentHashMap 如果没有数据的话 ,就重新上电读取, 如果还没有的话就提示: 请重新插拔社保卡, 正在检测中...
     *
     * 如果获取三个参数不成功就重新获取;
     */
    private fun powerOn(): Boolean {
        if (reader == null) {
            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> ICC power on failed ")
            return false
        }

        if (isTPS360IC()) {
            if (!reader!!.iccPowerOn(1)) {
                LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> ICC power on failed ")
                return false
            }
        } else {
            if (!reader!!.iccPowerOn()) {
                LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> ICC power on failed ")
                return false
            }
        }
        return true
    }

    /**
     * step3
     * 开始读取ssc的数据1
     */
    private fun doReadSSC1() {
        showLoadingDialog(tipContent = getString(R.string.app_reading_ssc))

        val builder = DataDoWithBuilder()
        builder.doWithData(Send2SSC_TPS900Impl(reader), IEvents {

            dismissLoadingDialog()

            var name = builder.contentHashMap.get(builder.NAME_ORDER)
            var sscNO = builder.contentHashMap.get(builder.SOCIAL_SECURITY_CARD_NUMBER_ORDER)
            var sfzh = builder.contentHashMap.get(builder.SOCIAL_NUMBER_ORDER)
            //任何一项为空就重新走上电/断电流程
            if (name == null || sscNO == null || sfzh == null) {
                powerOff()
                recyclePowerOn()
                return@IEvents
            }

            LogUtil.e(">>>>>>>>>>>>>>>>>>>姓名:$name 社保卡号:$sscNO 社会保障卡号:$sfzh")

            if (studentSFZH == sfzh) {
                Log.e("TAG", ">>>>>>>>>>>>>>>>>>>>>>>>>>> 学生信息校验成功! $sfzh")

                SoundBuilder.playDiscurnSuccess()
                //由于第二代社保卡是没有照片信息的,所以这里不做图片保存
                ARouter.getInstance().build(RouterHub.ROUTER_APP_TAKE_PHOTO)
                    .withString(
                        BaseConstant.STUDENT_SFZH,
                        sfzh
                    ).greenChannel().navigation()
                myFinish()

            } else {
                showErrorMessageDialog(
                    "社会保障号跟学生信息不一致!",
                    isCanAfterClickFinishActivity = false,
                    iEvents = IEvents {
                        powerOff()
                        recyclePowerOn()
                    })
            }

        })
    }


    /**
     * 关闭 step1
     * 断电
     */
    private fun powerOff(): Boolean {
        Log.e("TAG", ">>>>>>>>>>>>>>>>>>>>>>>>>>> powerOff()")
        if (isTPS360IC()) {
            return reader?.iccPowerOff(1) ?: false
        } else {
            return reader?.iccPowerOff() ?: false
        }
    }

    /**
     * 其实直接执行这一步也行, 也包含了断电了;
     * 关闭 step2
     * 关闭读写模块
     */
    private fun closeReadModule(): Boolean {
        Log.e("TAG", ">>>>>>>>>>>>>>>>>>>>>>>>>>> closeReadModule()")
        if (isTPS450C()) {
            FingerPrint.fingericPower(0)
        }
        if (isTPS360IC()) {
            return reader?.close(1) ?: false
        } else {
            return reader?.close() ?: false
        }
    }


    private inner class openTask : AsyncTask<Void, Int, Boolean>() {
        override fun onPreExecute() {
            super.onPreExecute()
            if (isTPS450C()) {
                showLoadingDialog(tipContent = "请稍候...")
            }
        }

        override fun doInBackground(vararg params: Void): Boolean? {
            if (isTPS450C()) {
                FingerPrint.fingericPower(1)
                try {
                    Thread.sleep(3000)
                } catch (e: InterruptedException) {
                    LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>> openTask : $e")
                }

            }
            return if (isTPS450C() || isTPS360IC()) {
                reader?.open(1)
            } else {
                reader?.open()
            }
        }

        override fun onPostExecute(result: Boolean?) {
            if (isTPS450C()) {
                dismissLoadingDialog()
            }
            if (!result!!) {
                showErrorMessageDialog("打开读写模块失败!")
            }
        }
    }

    private fun isTPS450C(): Boolean {
        return SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS450C.ordinal
    }

    private fun isTPS360IC(): Boolean {
        return SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS360IC.ordinal
    }

}
