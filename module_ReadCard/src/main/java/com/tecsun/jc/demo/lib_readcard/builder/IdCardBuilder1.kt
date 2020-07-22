package com.tecsun.jc.demo.lib_readcard.builder

import android.graphics.Bitmap
import android.os.Handler
import android.os.Message
import com.alibaba.android.arouter.launcher.ARouter
import com.common.pos.api.util.posutil.PosUtil
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.ProctorDetailsBean
import com.tecsun.jc.base.bean.db.invigilation.bean.ReadCardInfoBean
import com.tecsun.jc.base.builder.ReadCardImageBuilder
import com.tecsun.jc.base.builder.StudentOwnSFZImageBuilder
import com.tecsun.jc.base.builder.sound.SoundBuilder
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.common.BaseConstant.STUDENT_SFZH
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.utils.DBFunctionUtil
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.demo.lib_readcard.R
import com.tecsun.jc.demo.lib_readcard.util.PowerUtil
import com.telpo.tps550.api.TelpoException
import com.telpo.tps550.api.fingerprint.FingerPrint
import com.telpo.tps550.api.idcard.IdCard
import com.telpo.tps550.api.idcard.IdentityMsg
import com.telpo.tps550.api.util.ShellUtils
import org.litepal.LitePal

object IdCardBuilder{
    private var activity: BaseActivity? = null
    var studentSFZH:String? = ""
    private var isRead = false
    private var isStop = false
    private var mReadThread: ReadThread? = null
    private var mIdcard: IdCard? = null
    private var info: IdentityMsg? = null
    var titleString = ""
    private var image: ByteArray? = null
    private var bitmap: Bitmap? = null

    private var mHandler: Handler = object : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> {
                    info = msg.obj as IdentityMsg
                    try {
                        if (info != null) {
                            image = mIdcard?.getIdCardImageOverseas(info)
                            if (image?.size == 2048 || image?.size == 1024) {
                                bitmap = mIdcard?.decodeIdCardImageOverseas(image)
                            }
//                            tv_idcard_info?.text =
//                                (getString(R.string.idcard_xm) + info!!.name + "\n\n" + getString(
//                                    R.string.idcard_xb
//                                )
//                                        + info!!.sex + "\n\n" + getString(R.string.idcard_csrq) + info!!.born + "\n\n"
//                                        + getString(R.string.idcard_country) + info?.nation + "\n\n"
//                                        + "住址:" + info?.address + "\n\n"
//                                        + info!!.period + "\n\n" + getString(R.string.idcard_qzjg) + info!!.apartment
//                                        + "\n\n" + getString(R.string.idcard_sfhm) + info!!.no + "\n\n")
//                            iv_idcard_pic?.setImageBitmap(bitmap)


                            if (info!!.name != null) {
                                info!!.name = info!!.name.replace(" ", "")
                            }
                            if (info!!.sex != null) {
                                info!!.sex = info!!.sex.replace(" ", "")
                            }
                            if (info!!.no != null) {
                                info!!.no = info!!.no.replace(" ", "")
                            }


                            startChange(
                                info!!.name ?: "",
                                info!!.sex ?: "",
                                info!!.born ?: "",
                                info?.nation ?: "",
                                info?.address ?: "",
                                info!!.no ?: "",
                                info!!.apartment ?: "",
                                info!!.period ?: "",
                                bitmap
                            )
                        }

                        isStop = false
                    } catch (e: TelpoException) {
                        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>> e:$e")
                        isStop = false
                    }

                }
                1 -> {
//                    tv_idcard_info?.text = ""
//                    iv_idcard_pic?.setImageBitmap(null)
                    isStop = false
                }
                else -> {
                }
            }
        }
    }

    fun getString(id: Int): String {
        return activity?.getString(id) ?: ""
    }




//1
    fun powerOn(){
        if (android.os.Build.MODEL.contains("TPS350")) {
            // PosUtil.setFingerPrintPower(PosUtil.FINGERPRINT_POWER_ON);
        } else if (android.os.Build.MODEL.contains("TPS616")) {
            ShellUtils.execCommand(
                "echo 3 >/sys/class/telpoio/power_status",
                true
            )// usb

        } else if (android.os.Build.MODEL.contains("TPS520A")) {
            FingerPrint.fingerPrintPower(1)
        } else {
            PosUtil.setIdCardPower(PosUtil.IDCARD_POWER_ON)
        }
    }

    //2
    fun initModule(){
        mIdcard = IdCard(activity!!)
    }

    //3
    fun recycleRead(){
        if (mReadThread == null) {
            isRead = true
            mReadThread = ReadThread()
            mReadThread!!.start()
        }
    }

    fun forOnPause(){
        stopReadThread()
    }

    fun forOnReStart(){
        isGoAllRight = false
        recycleRead()
    }

    fun powerOff(){
        PowerUtil.readSFZPowerOff()
    }










    private fun stopReadThread() {
        if (mReadThread != null && mReadThread!!.isAlive) {

            mReadThread!!.interrupt()
            isRead = false
            mReadThread = null
        }
    }

    internal  class ReadThread : Thread() {
        override fun run() {
            super.run()
            while (isRead) {
                if (!isStop) {
                    var isFindcard = mIdcard?.findCard()
                    isFindcard = true
                    if (isFindcard) {
                        isStop = true
                        try {
                            info = mIdcard?.checkIdCardOverseas()
                        } catch (e: TelpoException) {
                            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>> TelpoException :$e")
                            mHandler.sendEmptyMessage(1)
                        }
                        if (info != null) {
                            val msg = Message()
                            msg.what = 0
                            msg.obj = info
                            mHandler.sendMessage(msg)
                        } else {
                            mHandler.sendEmptyMessage(1)
                        }
                    } else {
                        mHandler.sendEmptyMessage(1)

                    }

                }

            }

        }
    }












    var isGoAllRight = false

    private fun forAddNewInvigilator(
        name: String,
        sex: String,
        birthday: String,
        nation: String,
        address: String,
        number: String,
        qianfa: String,
        effdate: String,
        head: Bitmap?
    ) {
        if (isGoAllRight) {
            return
        }
        LogUtil.e(
            "Info:",
            name + "\n" +
                    sex + "\n" +
                    birthday + "\n" +
                    nation + "\n" +
                    address + "\n" +
                    number + "\n" +
                    qianfa + "\n" +
                    effdate + "\n"
        )

        //保存登录者的信息;
        var bean = ReadCardInfoBean()
        bean.name = name
        bean.sex = sex
        bean.birthday = birthday
        bean.nation = nation
        bean.address = address
        bean.number = number
        bean.qianfa = qianfa
        bean.effdate = effdate

        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>. name的长度:${name.length}")

        var count = DBFunctionUtil.findReadCardInfoBeanCountBySFZH(number)
        if (count == 0) {
            bean.save()
        }

        //保存登陆者的图片
        if (head != null && number != null) {
            ReadCardImageBuilder.savePic(head, number)
        }

        activity?.let {
            isGoAllRight = true
            stopReadThread()
            SoundBuilder.playDiscurnSuccess()
            ARouter.getInstance().build(RouterHub.ROUTER_APP_Show_New_Invigilator_Info)
                .withSerializable(BaseConstant.FILTER_SELECT_DATA, bean)
                .greenChannel().navigation()
        }

    }


    private fun forExaminerLogin(
        name: String,
        sex: String,
        birthday: String,
        nation: String,
        address: String,
        number: String,
        qianfa: String,
        effdate: String,
        head: Bitmap?
    ) {
        if (isGoAllRight) {
            return
        }
        //保存登录者的信息;
        var bean = ReadCardInfoBean()
        bean.name = name
        bean.sex = sex
        bean.birthday = birthday
        bean.nation = nation
        bean.address = address
        bean.number = number
        bean.qianfa = qianfa
        bean.effdate = effdate

        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> ReadCardInfoBean:$bean")

        var proctorDetailsBean =
            LitePal.where("sfzh = ?", bean.number ?: "")
                .findFirst(ProctorDetailsBean::class.java)

        if (proctorDetailsBean == null) {
            isGoAllRight = true
            stopReadThread()
            activity?.showErrorMessageDialog("登录失败！您的身份证信息没有绑定！请联系管理员。")


        } else {

            var count = DBFunctionUtil.findReadCardInfoBeanCountBySFZH(number)
            if (count == 0) {
                bean.save()
            }

            //保存登陆者的图片
            if (head != null && number != null) {
                ReadCardImageBuilder.savePic(head, number)
            }

            //登录成功
            JinLinApp.loginExaminer = bean
            JinLinApp.accountName = name
            JinLinApp.sfzh = number
            isGoAllRight = true
            SoundBuilder.playDiscurnSuccess()
            ARouter.getInstance().build(RouterHub.ROUTER_APP_EXAMINER_MANAGE).greenChannel()
                .navigation()
            activity?.myFinish()

        }
    }
    //LDW End

    private fun forRecordSudentSFZINFO(
        name: String,
        sex: String,
        birthday: String,
        nation: String,
        address: String,
        number: String,
        qianfa: String,
        effdate: String,
        head: Bitmap?
    ) {

        LogUtil.e(
            "Info:",
            name + "\n" +
                    sex + "\n" +
                    birthday + "\n" +
                    nation + "\n" +
                    address + "\n" +
                    number + "\n" +
                    qianfa + "\n" +
                    effdate + "\n"
        )


        if (isGoAllRight) {
            return
        }

        //过滤身份证不同的;
        var studentSFZH = studentSFZH

        LogUtil.e(">>>>>>>>>>>>>>>>>>>>> 从intent获取的学生的身份证号信息: $studentSFZH")
        if (!studentSFZH.isNullOrBlank() && studentSFZH != number) {
            Handler().postDelayed({
                activity?.showToast("身份证号跟该学生的不相同!")
            }, 10)

            return
        }


        //保存学生的身份证图片
        if (head != null && number != null) {
            StudentOwnSFZImageBuilder.savePic(head, number)

            activity?.let {
                isGoAllRight = true
                stopReadThread()
                SoundBuilder.playDiscurnSuccess()
                ARouter.getInstance().build(RouterHub.ROUTER_APP_TAKE_PHOTO)
                    .withString(
                        STUDENT_SFZH,
                        number
                    ).greenChannel().navigation()
                activity?.myFinish()
                return
            }
        }

        if (head == null || number == null) {
            Handler().postDelayed({
                activity?.showToast("读取信息有误!")
            }, 10)

        }
    }


    private fun startChange(
        name: String,
        sex: String,
        birthday: String,
        nation: String,
        address: String,
        number: String,
        qianfa: String,
        effdate: String,
        head: Bitmap?
    ) {

        //考官登录
        if (titleString != null && titleString == getString(R.string.app_examiner_login)) {
            forExaminerLogin(name, sex, birthday, nation, address, number, qianfa, effdate, head)
        }
        //新增监考老师
        else if (titleString != null && titleString == getString(R.string.app_add_new_invigilator_data)) {
            forAddNewInvigilator(
                name,
                sex,
                birthday,
                nation,
                address,
                number,
                qianfa,
                effdate,
                head
            )
        }
        //请刷身份证
        else if (titleString != null && titleString == getString(R.string.app_student_brush_sfz_card)) {
            forRecordSudentSFZINFO(
                name,
                sex,
                birthday,
                nation,
                address,
                number,
                qianfa,
                effdate,
                head
            )
        }
    }


}
