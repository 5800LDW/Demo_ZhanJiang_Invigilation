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

    private var isShowing = false

    fun checkShowing(): Boolean {
        return isShowing
    }

    fun showFailDialog(activity: BaseActivity, recognitionText: String) {
        if (doNotShow) {
            dismissTipsDialog()
            return
        }

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


            }
            if (recognitionDialog != null) {
                try {
                    recognitionDialog!!.show()
                } catch (e: Exception) {
                    LogUtil.e(TAG, ">>>>>>>>>>>>>>> $e")
                }
                isShowing = true;

                val tvFailContent = recognitionDialog!!.findViewById<TextView>(R.id.tvFailContent)
                tvFailContent!!.text = ""
                tvFailContent.text = recognitionText + ""

            }
        }
    }

    fun dismissTipsDialog() {
        if (recognitionDialog != null) {
            isShowing = false
            recognitionDialog!!.dismiss()
        }
    }

    private var doNotShow = false;
    fun dismissTipsDialogAndRelease() {
        doNotShow = true;
        dismissTipsDialog()
        if (recognitionDialog != null) {
            recognitionDialog = null;
        }
    }
}



























