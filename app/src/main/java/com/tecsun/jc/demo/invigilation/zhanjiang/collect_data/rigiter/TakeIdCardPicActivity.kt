package com.tecsun.jc.demo.invigilation.zhanjiang.collect_data.rigiter

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.param.register.ApplyCardParam
import com.tecsun.jc.base.builder.PermissionTipsShowBuilder
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.utils.PermissionsUtils
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.TitleBar
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.register.util.constant.Const

/**
 * 身份证正反面取照
 */
@Route(path = RouterHub.ROUTER_REGISTER_TAKE_ID_CARD_PIC)
class TakeIdCardPicActivity : BaseActivity() {


    private var mIvIdPositive: ImageView? = null
    private var mIvIdNegative: ImageView? = null
    private var mLlNegative: TextView? = null
    private var mLlPositive: TextView? = null
    /**
     * 国徽面
     */
    private var mPositiveBitmap: Bitmap? = null
    /**
     * 人像面
     */
    private var mNegativeBitmap: Bitmap? = null

    private var mApplyCardParam: ApplyCardParam? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_register_take_id_card_pic
    }

    override fun setTitleBar(titleBar: TitleBar) {
//        titleBar.setTitle(R.string.register_upload_id_card_pic)
        titleBar.setTitle("拍摄身份证正反面")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mApplyCardParam = ApplyCardParam()
        initData()

    }
     fun initData() {

        //        mApplyCardParam = ((BaseApplication) getApplication()).getApplyCardParam();

        mIvIdPositive = findViewById(R.id.mIvIdPositive)
        mIvIdNegative = findViewById(R.id.mIvIdNegative)

        mLlNegative = findViewById(R.id.mLlNegative)
        mLlPositive = findViewById(R.id.mLlPositive)
    }


    override fun onResume() {
        super.onResume()

        if (mNegativeBitmap == null || mPositiveBitmap == null) {
            findViewById<View>(R.id.btNextNormal).visibility = View.GONE
            findViewById<View>(R.id.btNotNext).visibility = View.VISIBLE
        }
        else{
            findViewById<View>(R.id.btNextNormal).visibility = View.VISIBLE
            findViewById<View>(R.id.btNotNext).visibility = View.GONE
        }

    }

    fun applySubmit(view: View) {
        if (mNegativeBitmap == null) {
            showToast("请先拍摄身份证头像面")
            return
        }
        if (mPositiveBitmap == null) {
            showToast("请先拍摄身份证国徽面")
            return
        }

        ARouter.getInstance().build(RouterHub.ROUTER_APP_STUDENT_REGISTER).greenChannel().navigation()

    }


    fun takeIdPositivePic(view: View) {
        if (PermissionsUtils.isRequestPermission()) {
            if (PermissionsUtils.startRequestPermission(this, PermissionsUtils.CAMERA_PERMISSIONS, 321)) {
                startActivityResult(Const.SOURCE_ID_POSITIVE)
            }
        } else {
            startActivityResult(Const.SOURCE_ID_POSITIVE);
        }
    }

    fun takeIdNegativePic(view: View) {
        if (PermissionsUtils.isRequestPermission()) {
            if (PermissionsUtils.startRequestPermission(this, PermissionsUtils.CAMERA_PERMISSIONS, 322)) {
                startActivityResult(Const.SOURCE_ID_NEGATIVE)
            }
        } else {
            startActivityResult(Const.SOURCE_ID_NEGATIVE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array< String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (PermissionsUtils.requestPermissionResult(this, permissions, grantResults)) {
            if (requestCode == 321) {
                startActivityResult(Const.SOURCE_ID_POSITIVE)
            } else if (requestCode == 322) {
                startActivityResult(Const.SOURCE_ID_NEGATIVE)
            }
        }
        else{
            PermissionTipsShowBuilder.show(this,permissions)
        }
    }

    /**
     * 页面跳转
     * @param takeIdSource 拍照来源
     */
    private fun startActivityResult(takeIdSource: Int) {
        var intent = Intent(this, TakeIdCardActivity2::class.java)
        intent.putExtra(Const.TAKE_ID_SOURCE, takeIdSource)
        startActivityForResult(intent, 0x0001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0x0001) {
            Log.e("onActivityResult", resultCode.toString() + "")
            if (resultCode == Const.SOURCE_ID_POSITIVE) {
                mPositiveBitmap = JinLinApp.instance.getInsertPicture()
                if (mPositiveBitmap != null) {
                    mIvIdPositive!!.setImageBitmap(mPositiveBitmap)

                    LogUtil.e("TAG","---------------- mIvIdPositive ------------------")
                }
                mLlPositive!!.visibility = View.GONE
            } else if (resultCode == Const.SOURCE_ID_NEGATIVE) {
                mNegativeBitmap = JinLinApp.instance.getInsertPicture()
                if (mNegativeBitmap != null) {
                    mIvIdNegative!!.setImageBitmap(mNegativeBitmap)

                    LogUtil.e("TAG","---------------- mIvIdNegative ------------------")
                }
                mLlNegative!!.visibility = View.GONE
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


        override fun onDestroy() {
            if (mNegativeBitmap != null && !mNegativeBitmap!!.isRecycled) {
                mNegativeBitmap!!.recycle()
                mNegativeBitmap = null
            }
            if (mPositiveBitmap != null && !mPositiveBitmap!!.isRecycled) {
                mPositiveBitmap!!.recycle()
                mPositiveBitmap = null
            }
            //        ((BaseApplication) getApplication()).recyclePicture();
            super.onDestroy()
        }



}
































