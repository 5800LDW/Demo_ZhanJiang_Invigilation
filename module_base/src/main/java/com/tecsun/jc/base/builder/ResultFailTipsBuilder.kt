package com.tecsun.jc.base.builder

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.tecsun.jc.base.R
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.SingleClickListener

object ResultFailTipsBuilder {

    private val TAG = ResultFailTipsBuilder::class.java.simpleName

    private val mHandler = Handler(Looper.getMainLooper())

    var recognitionDialog: AppCompatDialog? = null

    fun showFailDialog(activity: BaseActivity, recognitionText: String) {

        LogUtil.e(TAG, ">>>>>>>>>>>1")
        dismissTipsDialog()
        LogUtil.e(TAG, ">>>>>>>>>>>2")

        if (activity != null && !activity.isFinishing) {
            dismissTipsDialog()
            if (recognitionDialog == null) {
                val inflater =
                    activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                recognitionDialog = AppCompatDialog(activity, R.style.TransparentDialogStyle)
                recognitionDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                val layout: View = inflater.inflate(R.layout.base_result_tips_fail, null)
                recognitionDialog!!.window!!.setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL)
                recognitionDialog!!.addContentView(
                    layout,
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
                recognitionDialog!!.setCanceledOnTouchOutside(true)
                val appDialogLoadingFL =
                    recognitionDialog!!.findViewById<View>(R.id.appDialogLoadingFL)
                appDialogLoadingFL!!.setOnClickListener { v: View? ->
                    dismissTipsDialog()
                }

                LogUtil.e(TAG, ">>>>>>>>>>>3")

                val btnCancel = recognitionDialog!!.findViewById<TextView>(R.id.btnCancel)
                btnCancel?.text = "返回主页"
                btnCancel?.setOnClickListener(object : SingleClickListener() {
                    override fun onSingleClick(v: View?) {
                        dismissTipsDialogAndRelease()
                        activity?.myFinish()
                    }
                })

                val btnSubmit = recognitionDialog!!.findViewById<TextView>(R.id.btnSubmit)
                btnSubmit?.text = "重新提交"
                btnSubmit?.setOnClickListener(object : SingleClickListener() {
                    override fun onSingleClick(v: View?) {
                        activity?.resubmit()
                    }
                })

                LogUtil.e(TAG, ">>>>>>>>>>>4")

                var tvFailTitle = recognitionDialog!!.findViewById<TextView>(R.id.tvFailTitle)
                tvFailTitle?.text = "信息提交失败，请重新提交"

            }
            if (recognitionDialog != null) {
                try {
                    LogUtil.e(TAG, ">>>>>>>>>>>5")
                    recognitionDialog!!.show()
                } catch (e: Exception) {
                    LogUtil.e(TAG, ">>>>>>>>>>>>>>> $e")
                }

                val tvFailContent = recognitionDialog!!.findViewById<TextView>(R.id.tvFailContent)
                tvFailContent!!.text = ""
                tvFailContent.text = recognitionText + ""

            }
        }
    }

    fun dismissTipsDialog() {
        if (recognitionDialog != null) {
            recognitionDialog!!.dismiss()
            recognitionDialog = null
        }
    }

    fun dismissTipsDialogAndRelease() {
        dismissTipsDialog()
        if (recognitionDialog != null) {
            recognitionDialog = null;
        }
    }
}



























