package com.tecsun.jc.demo.lib_readcard.sfz

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.common.pos.api.util.posutil.PosUtil
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.JinLinApp.Companion.isIDCardModulePowerOff
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.ProctorDetailsBean
import com.tecsun.jc.base.bean.db.invigilation.bean.ReadCardInfoBean
import com.tecsun.jc.base.bean.db.invigilation.bean.StudentDetailsBean
import com.tecsun.jc.base.builder.ReadCardImageBuilder
import com.tecsun.jc.base.builder.StudentOwnSFZImageBuilder
import com.tecsun.jc.base.builder.sound.SoundBuilder
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.common.BaseConstant.STUDENT_SFZH
import com.tecsun.jc.base.common.BaseConstant.TITLE_NAME
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.utils.DBFunctionUtil
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.lib_readcard.R
import com.tecsun.jc.demo.lib_readcard.util.PowerUtil
import com.telpo.tps550.api.TelpoException
import com.telpo.tps550.api.fingerprint.FingerPrint
import com.telpo.tps550.api.idcard.IdCard
import com.telpo.tps550.api.idcard.IdentityMsg
import com.telpo.tps550.api.util.ShellUtils
import kotlinx.android.synthetic.main.activity_idcard_new.*
import org.litepal.LitePal

@Route(path = RouterHub.ROUTER_LIB_READCARD_IDCARDACTIVITY_COMBINE)
class IdcardActivity_Combine : BaseActivity() {
    private var activity: BaseActivity? = null
    private var mContext: Context? = null
    private var mIdcard: IdCard? = null
    private var bitmap: Bitmap? = null
    private var image: ByteArray? = null
    private var info: IdentityMsg? = null
    private var btn_init: Button? = null
    private var btn_read: Button? = null
    private var btn_read_repeat: Button? = null
    private var tv_idcard_info: TextView? = null
    private var iv_idcard_pic: ImageView? = null
    private var isRead = false
    private var isStop = false
    private var mReadThread: ReadThread? = null
    private var mHandler: Handler = object : Handler() {

        override fun handleMessage(msg: Message) {
            // TODO Auto-generated method stub
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
                            tv_idcard_info?.text =
                                (getString(R.string.idcard_xm) + info!!.name + "\n\n" + getString(
                                    R.string.idcard_xb
                                )
                                        + info!!.sex + "\n\n" + getString(R.string.idcard_csrq) + info!!.born + "\n\n"
                                        + getString(R.string.idcard_country) + info?.nation + "\n\n"
                                        + "住址:" + info?.address + "\n\n"
                                        + info!!.period + "\n\n" + getString(R.string.idcard_qzjg) + info!!.apartment
                                        + "\n\n" + getString(R.string.idcard_sfhm) + info!!.no + "\n\n")
                            iv_idcard_pic?.setImageBitmap(bitmap)


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
                    tv_idcard_info?.text = ""
                    iv_idcard_pic?.setImageBitmap(null)
                    isStop = false
                }
                else -> {
                }
            }
        }
    }


    //LDW Begin
    private var mView: View? = null
    private var timeInternal: Long? = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //		requestWindowFeature(Window.FEATURE_NO_TITLE);
        //		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //		setContentView(R.layout.activity_idcard_new);
        activity = this
        mContext = this@IdcardActivity_Combine
        btn_init = findViewById<View>(R.id.btn_init) as Button
        btn_read = findViewById<View>(R.id.btn_read_info) as Button
        btn_read_repeat = findViewById<View>(R.id.btn_read_repeate) as Button
        tv_idcard_info = findViewById<View>(R.id.tv_idcard_info) as TextView
        iv_idcard_pic = findViewById<View>(R.id.iv_idcard_pic) as ImageView

        Glide.with(this).load(R.drawable.read_card_sfz_for_module)
            .into(findViewById<View>(R.id.ivPic) as ImageView)

        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>> onCreate:")

        beginRead()

        btSkip.setOnClickListener {
            //保存登录者的信息;
            var bean = ReadCardInfoBean()
            bean.name = ""
            bean.sex = ""
            bean.birthday = ""
            bean.nation = ""
            bean.address = ""
            bean.number = ""
            bean.qianfa = ""
            bean.effdate = ""
            isGoAllRight = true
            stopReadThread()
            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>> btSkip:")
            ARouter.getInstance().build(RouterHub.ROUTER_APP_ADD_NEW_STUDENT)
                .withSerializable(BaseConstant.FILTER_SELECT_DATA, bean)
                .greenChannel().navigation()
            myFinish()
        }
    }

    override fun onStart() {
        super.onStart()
        if(!titleString.isNullOrBlank() && titleString == getString(R.string.app_add_new_student)){
            llSkip.visibility = View.VISIBLE
        }
        else{
            llSkip.visibility = View.GONE
        }
    }


    var titleString = ""
    override fun setTitleBar(titleBar: TitleBar) {
        intent?.let {
            titleString = intent.getStringExtra("titleName") ?: ""
            titleBar.setTitle(titleString)
        }
    }

    /**
     * LDW 开始读取
     */
    private fun beginRead() {
        mView = View(this)
        timeInternal = System.currentTimeMillis()
        powerOn(mView!!)
    }

    override fun onRestart() {
        super.onRestart()
        if (System.currentTimeMillis() - timeInternal!! > 3000) {
            isGoAllRight = false
            //进行循环读取
            btn_read_repeat?.performClick()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_idcard_new
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
        //新增学生
        else if (titleString != null && titleString == getString(R.string.app_add_new_student)) {


            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> 新增学生 ")
            forAddNewStudentInfo(
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
            forRecordSudentSFZINFO( name,
                sex,
                birthday,
                nation,
                address,
                number,
                qianfa,
                effdate,
                head)
        }

        //请刷社保卡
        else if (titleString != null && titleString == getString(R.string.app_student_brush_ssc_card)) {
            //TODO
        }

        //学员签到
        if (titleString != null && titleString == getString(R.string.base_student_sign_in)) {
            forStudentSingIn(name, sex, birthday, nation, address, number, qianfa, effdate, head)
        }

        else {
            finish()
        }
    }

    var isGoAllRight = false

    private fun forAddNewStudentInfo(
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
            ARouter.getInstance().build(RouterHub.ROUTER_APP_ADD_NEW_STUDENT)
                .withSerializable(BaseConstant.FILTER_SELECT_DATA, bean)
                .greenChannel().navigation()
            myFinish()
            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> 新增学生2 ")
        }
    }

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
            myFinish()
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


    private fun forStudentSingIn(
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


        if(isGoAllRight){
            return
        }

        //过滤身份证不同的;
        var studentSFZH = intent.getStringExtra(STUDENT_SFZH)

        LogUtil.e(">>>>>>>>>>>>>>>>>>>>> 从intent获取的学生的身份证号信息: $studentSFZH")
        if (!studentSFZH.isNullOrBlank() && studentSFZH != number) {
            Handler().postDelayed({
                showToast("身份证号跟该学生的不相同!")
            },10)

            return
        }

        //保存学生的身份证图片
        if (head != null && number != null) {
            StudentOwnSFZImageBuilder.savePic(head, number)

            activity?.let {
                isGoAllRight = true
                stopReadThread()


                SoundBuilder.playDiscurnSuccess()


                var studentDetailsBean =  StudentDetailsBean()
                studentDetailsBean.name = name
                studentDetailsBean.sfzh = number
                JinLinApp.studentDetailsBean = studentDetailsBean



                ARouter.getInstance().build(RouterHub.ROUTER_APP_TAKE_PHOTO)
                    .withString(
                        STUDENT_SFZH,
                        number)
                    .withString(TITLE_NAME,resources.getString(R.string.base_student_Certification))
                    .greenChannel().navigation()
                myFinish()
                return
            }
        }

        if(head == null ||  number == null){
            Handler().postDelayed({
                showToast("读取信息有误!")
            },10)
        }
    }




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


        if(isGoAllRight){
            return
        }

        //过滤身份证不同的;
        var studentSFZH = intent.getStringExtra(STUDENT_SFZH)

        LogUtil.e(">>>>>>>>>>>>>>>>>>>>> 从intent获取的学生的身份证号信息: $studentSFZH")
        if (!studentSFZH.isNullOrBlank() && studentSFZH != number) {
            Handler().postDelayed({
                showToast("身份证号跟该学生的不相同!")
            },10)

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
                        number).greenChannel().navigation()
                myFinish()
                return
            }
        }

        if(head == null ||  number == null){
            Handler().postDelayed({
                showToast("读取信息有误!")
            },10)
        }
    }


    /***设备上电 */
    fun powerOn(view: View) {
        showLoadingDialog(tipContent = "模块上电中...")
        powerOn()
    }

    /***设备关电 */
    fun powerOff(view: View) {
        powerOff()
    }

    /***初始化设备 */
    fun init_idcard(view: View) {
        showLoadingDialog(tipContent = "初始化中...")

//        if(IDCardBuilder.getIdCard() == null){
//            var c  = IdCard(JinLinApp.context)
//            IDCardBuilder.setIdCard(c)
//            mIdcard = c
//        }

        mIdcard = IdCard(mContext)
        Handler().postDelayed({
            //进行循环读取
            btn_read_repeat?.performClick()

            Handler().postDelayed({
//                showToast("初始化成功!")
                dismissLoadingDialog()
            }, 50)
        }, 100)

    }

    fun read_info(view: View) {
        val mThread = Thread(Runnable {
            try {
                info = mIdcard?.checkIdCardOverseas()
                if (info != null) {
                    val msg = Message()
                    msg.what = 0
                    msg.obj = info
                    mHandler.sendMessage(msg)

                }
            } catch (e: TelpoException) {
                LogUtil.e(">>>>>>>>>>>>>>>>>>>>>> TelpoException : $e")
            }
        })
        mThread.start()
    }

    /**
     * 循环读取
     */
    fun read_repeate(view: View) {

        readLoop()
    }

    /**
     * 停止循环读取
     */
    fun read_stop(view: View) {

        stopReadThread()
    }

    private fun powerOn() {
        if(isIDCardModulePowerOff?:true){
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
            isIDCardModulePowerOff = false

            Handler().postDelayed({ btn_init?.performClick() }, 2200)

            //        try {
            //            Thread.sleep(2000);
            //        } catch (InterruptedException e1) {
            //            // TODO Auto-generated catch block
            //            LogUtil.e(">>>>>>>>>>>>>>>>>>>>. e" + e1.toString());
            //        }
        }
        else{
            btn_init?.performClick()
        }


    }

    /***设备停止供电 */
    private fun powerOff() {
        PowerUtil.readSFZPowerOff()
    }

    fun readLoop() {
        if (mReadThread == null) {
            isRead = true
            mReadThread = ReadThread()
            mReadThread!!.start()
        }
    }

    private fun stopReadThread() {
        if (mReadThread != null && mReadThread!!.isAlive) {

            mReadThread!!.interrupt()
            isRead = false
            mReadThread = null
        }
    }

    override fun onPause() {
        super.onPause()
        stopReadThread()
    }

    override fun onDestroy() {
        stopReadThread()
        //        mIdcard.close()//IdCard
        IdCard.close()//
//        powerOff()
        mHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {

    }

    internal inner class ReadThread : Thread() {
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
}
