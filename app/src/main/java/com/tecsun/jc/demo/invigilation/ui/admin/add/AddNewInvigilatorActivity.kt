package com.tecsun.jc.demo.invigilation.ui.admin.add

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.ivsign.android.IDCReader.IdentityCard
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.ReadCardInfoBean
import com.tecsun.jc.base.builder.ReadCardImageBuilder
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.utils.DBFunctionUtil
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.demo.lib_readcard.ConstantList
import com.tecsun.jc.demo.lib_readcard.DynamicPermission
import com.yishu.YSBluetoothCardReader.BluetoothReader
import com.yishu.YSNfcCardReader.NfcCardReader
import kotlinx.android.synthetic.main.activity_examiner_login.*
import java.lang.ref.WeakReference

class AddNewInvigilatorActivity : BaseActivity() {


    private var mHandler: Handler? = null
    private var dynamicPermission: DynamicPermission? = null

    private var activity: AddNewInvigilatorActivity? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_add_new_inivigalator
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle(R.string.app_add_new_invigilator_data)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        activity = this

        Glide.with(this).load(R.drawable.pos_read_sfz2).into(iv)


        //权限判断
        //权限判断
        dynamicPermission =
            DynamicPermission(this@AddNewInvigilatorActivity, DynamicPermission.PassPermission { })
        dynamicPermission!!.getPermissionStart()
        if (!dynamicPermission!!.checkPermissionPassState()) {
            dynamicPermission!!.showPermissionDialog()
        }
        Log.d("权限", dynamicPermission!!.checkPermissionPassState().toString() + "")
        mHandler = MyHandler(this@AddNewInvigilatorActivity)
        mHandler!!.sendEmptyMessage(ConstantList.MESSAGE_VALID_NFCSTART)
        nfcCardReaderAPI = NfcCardReader(mHandler, this@AddNewInvigilatorActivity)
        bluetoothReaderAPI = BluetoothReader(this@AddNewInvigilatorActivity, mHandler)
    }


    override fun onNewIntent(intent: Intent) {
        Log.i("Info", "enter onNewIntent")
        super.onNewIntent(intent)
        thisIntent = null
        thisIntent = intent
        mHandler!!.sendEmptyMessage(ConstantList.MESSAGE_VALID_NFCBUTTON)
    }

    override fun onResume() {
        Log.i("Info", "enter onResume")
        super.onResume()
        if (!dynamicPermission!!.checkPermissionPassState()) {
            dynamicPermission!!.showPermissionDialog()
        }
        mHandler!!.sendEmptyMessage(ConstantList.MESSAGE_VALID_NFCSTART)
    }

    private class MyHandler(activity: AddNewInvigilatorActivity) : Handler() {
        private val activityWeakReference: WeakReference<AddNewInvigilatorActivity>
        //蓝牙nfc初始化之后不再初始化
        private var nfcInit = false
        private var btInit = false

        init {
            activityWeakReference = WeakReference(activity)
        }

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val activity = activityWeakReference.get() ?: return
            when (msg.what) {
                ConstantList.MESSAGE_VALID_NFCSTART -> {
                    Log.i("Info", "enter MESSAGE_VALID_NFCSTART")
                    bluetoothReaderAPI!!.closeBlueTooth()
                    nfcCardReaderAPI!!.startNFCListener()
                    if (nfcInit) {
                        return
                    }
                    val enabledNFC = nfcCardReaderAPI!!.enabledNFCMessage()
                    if (enabledNFC) {
                        nfcInit = true
                        activity.showToast("NFC初始化成功")
//                        Toast.makeText(activity, "NFC初始化成功", Toast.LENGTH_SHORT).show()
                    } else {
                        activity.showToast("NFC初始化失败")
//                        Toast.makeText(activity, "NFC初始化失败", Toast.LENGTH_SHORT).show()
                    }
                }
                ConstantList.MESSAGE_VALID_BTSTART -> {
                    Log.i("Info", "enter MESSAGE_VALID_BTSTART")
                    if (btInit) {
                        return
                    }
                    if (bluetoothReaderAPI!!.checkBltDevice(activity)) {
                        btInit = true
                        nfcCardReaderAPI!!.endNFCListener()
                        Toast.makeText(activity, "蓝牙初始化成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "当前设备无蓝牙或者蓝牙未开启", Toast.LENGTH_SHORT).show()
                    }
                }
                ConstantList.BLUETOOTH_CONNECTION_SUCCESS -> {
                    Log.i("Info", "enter BLUETOOTH_CONNECTION_SUCCESS")
                    val device = msg.obj as BluetoothDevice
                }
                ConstantList.BLUETOOTH_CONNECTION_FAILED -> {
                    Log.i("Info", "enter BLUETOOTH_CONNECTION_FAILED")
                    activity.showToast("设备连接失败，请重新连接")
//                    Toast.makeText(activity, "设备连接失败，请重新连接", Toast.LENGTH_SHORT).show()
                }
                ConstantList.MESSAGE_VALID_NFCBUTTON -> {
                    Log.i("Info", "enter MESSAGE_VALID_NFCBUTTON")
                    val isNFC = nfcCardReaderAPI!!.isNFC(thisIntent!!)!!
                    if (isNFC) {
                        nfcCardReaderAPI!!.CreateCard(thisIntent)
                    } else {
                        activity.showToast("获取nfc失败")
//                        Toast.makeText(activity, "获取nfc失败", Toast.LENGTH_SHORT).show()
                    }
                }
                ConstantList.READ_CARD_START -> {
                    Log.i("Info", "enter READ_CARD_START")
                    //                    main_tvContent_show.setText("开始读卡，请稍后...");
//                    Toast.makeText(activity, "开始读卡", Toast.LENGTH_SHORT).show()
                    activity.showToast("开始读卡")

                }
                ConstantList.READ_CARD_FAILED -> {
                    Log.i("Info", "enter READ_CARD_FAILED")

                    activity.showToast("读卡失败:" + msg.obj)
//                    Toast.makeText(activity, "读卡失败:" + msg.obj, Toast.LENGTH_SHORT).show()
                }
                ConstantList.READ_CARD_SUCCESS -> {
                    Log.i("Info", "enter READ_CARD_SUCCESS")
                    if (78 != nfcCardReaderAPI!!.errorFlag) {
                        val message = nfcCardReaderAPI!!.message
                        if ("" != message) {
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                            return
                        }
                    }

                    activity.showToast("读卡成功!")

//                    Toast.makeText(activity, "读卡成功", Toast.LENGTH_SHORT).show()
                    val card = msg.obj as IdentityCard
                    if (card != null) {
                        val name = card.nameText
                        val sex = card.sexText
                        val birthday = card.birthdayText
                        val nation = card.mingZuText
                        val address = card.addressText
                        val number = card.numberText
                        val qianfa = card.qianfaText
                        val effdate = card.effectiveDate
                        val head = card.image
                        if (head == null) {
                            Toast.makeText(activity, "头像读取失败", Toast.LENGTH_SHORT).show()
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

                        var count = DBFunctionUtil.findReadCardInfoBeanCountBySFZH(number)
                        if(count == 0){
                            bean.save()
                        }

                        //保存登陆者的图片
                        if (head != null && number != null) {
                            ReadCardImageBuilder.savePic(head, number)
                        }

//                        //登录成功
//                        JinLinApp.loginExaminer = bean
//                        JinLinApp.accountName = name
//                        JinLinApp.sfzh = number
//                        activity?.myStartActivity(ExaminerManageActivity::class.java)
//                        activity?.myFinish()


                        activity?.let {
                            val intent = Intent(activity, ShowNewInvigilatorInfoActivity::class.java)
                            intent.putExtra(BaseConstant.FILTER_SELECT_DATA, bean)
                            activity?.startActivity(intent)
                        }
                    }
                }
                ConstantList.BTREAD_BUTTON_ENABLED -> Log.i("Info", "enter BTREAD_BUTTON_ENABLED")
                ConstantList.MESSAGE_VALID_OTGSTART -> {
                    Log.i("Info", "enter MESSAGE_VALID_OTGSTART")
                    bluetoothReaderAPI!!.closeBlueTooth()
                }
                else -> {
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothReaderAPI!!.closeBlueTooth()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        dynamicPermission!!.permissionRequestOperation(requestCode, permissions, grantResults)
    }

    companion object {
        private var nfcCardReaderAPI: NfcCardReader? = null
        private var bluetoothReaderAPI: BluetoothReader? = null
        private var thisIntent: Intent? = null
    }
}



















