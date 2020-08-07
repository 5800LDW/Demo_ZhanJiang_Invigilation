package com.tecsun.jc.base.builder

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.tecsun.jc.base.R
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.listener.IEvents
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.SingleClickListener

object ResultSuccessTipsBuilder {

    private val TAG = ResultSuccessTipsBuilder::class.java.simpleName

    private val mHandler = Handler(Looper.getMainLooper())

    var recognitionDialog: AppCompatDialog? = null


    fun showSuccessDialog(activity: BaseActivity, ev: IEvents = IEvents { }, info: String = "") {
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
                recognitionDialog!!.setCanceledOnTouchOutside(false)

//                val appDialogLoadingFL =
//                    recognitionDialog!!.findViewById<View>(R.id.appDialogLoadingFL)
//                appDialogLoadingFL!!.setOnClickListener { v: View? ->
//                    recognitionDialog!!.dismiss()
//                }
                LogUtil.e(TAG, ">>>>>>>>>>>3")

                recognitionDialog!!.findViewById<TextView>(R.id.btnCancel)?.visibility = View.GONE
                recognitionDialog!!.findViewById<TextView>(R.id.tvFailContent)?.visibility =
                    View.GONE
                recognitionDialog!!.findViewById<View>(R.id.v1)?.visibility = View.VISIBLE
                recognitionDialog!!.findViewById<View>(R.id.v2)?.visibility = View.VISIBLE
                recognitionDialog!!.findViewById<View>(R.id.v3)?.visibility = View.GONE

//                val ivTipIcon = recognitionDialog!!.findViewById<ImageView>(R.id.ivTipIcon)
//                ivTipIcon?.background =
//                    activity?.resources.getDrawable(R.drawable.base_zhanjiang_tips_success)


                recognitionDialog!!.findViewById<ImageView>(R.id.ivTipIcon)?.visibility = View.GONE
                recognitionDialog!!.findViewById<ImageView>(R.id.ivTipIcon2)?.visibility =
                    View.VISIBLE


                LogUtil.e(TAG, ">>>>>>>>>>>4")

                val tvFailTitle =
                    recognitionDialog?.findViewById<TextView>(R.id.tvFailTitle)
                tvFailTitle?.text = if (info.isNullOrBlank()) "个人申报信息提交成功" else info


                val btnSubmit = recognitionDialog!!.findViewById<TextView>(R.id.btnSubmit)
                btnSubmit?.text = "返回首页"

                btnSubmit?.setOnClickListener(object : SingleClickListener() {
                    override fun onSingleClick(v: View?) {
                        dismissTipsDialogAndRelease()
                        activity?.myFinish()
                        ev?.biz()
                    }
                })

                LogUtil.e(TAG, ">>>>>>>>>>>5")

            }
            if (recognitionDialog != null) {
                try {
                    LogUtil.e(TAG, ">>>>>>>>>>>6")
                    recognitionDialog!!.show()
                } catch (e: Exception) {
                    LogUtil.e(TAG, ">>>>>>>>>>>>>>> $e")
                }
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
            recognitionDialog = null
        }
    }


}



























