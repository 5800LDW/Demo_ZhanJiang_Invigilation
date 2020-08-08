package com.tecsun.jc.base.builder

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.tecsun.jc.base.R
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.listener.IEvents
import com.tecsun.jc.base.listener.IEvents3
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.SingleClickListener

object TakePhotoImgSureBuilder {

    private val TAG = TakePhotoImgSureBuilder::class.java.simpleName

    private val mHandler = Handler(Looper.getMainLooper())

    var recognitionDialog: AppCompatDialog? = null


    //    @JvmOverloads
    fun showSuccessDialog(
        activity: BaseActivity,
        ev: IEvents3?,
        info: String = "",
        b: Bitmap?
    ) {
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
                val layout: View = inflater.inflate(R.layout.base_dialog_show_confirm, null)
                recognitionDialog!!.window!!.setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL)
                recognitionDialog!!.addContentView(
                    layout,
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
                recognitionDialog!!.setCanceledOnTouchOutside(false)


                var ivShowPic =
                    recognitionDialog!!.findViewById<ImageView>(R.id.ivShowPic)
                ivShowPic!!.setImageBitmap(b)

                //提示语
                var mTvTakeIdTip = recognitionDialog!!.findViewById<TextView>(R.id.mTvTakeIdTip)
                mTvTakeIdTip?.text = info?:""

                //点击完成
                var tvComplete = recognitionDialog!!.findViewById<TextView>(R.id.tvComplete)
                tvComplete?.setOnClickListener(object :SingleClickListener(){
                    override fun onSingleClick(v: View?) {
                        dismissTipsDialog()
                        ev?.complete()
                    }
                })

                //点击重新拍摄
                var tvTakePhoto = recognitionDialog!!.findViewById<TextView>(R.id.tvTakePhoto)
                tvTakePhoto?.setOnClickListener(object :SingleClickListener(){
                    override fun onSingleClick(v: View?) {
                        dismissTipsDialog()
                        ev?.rephotography()
                    }
                })


                //点击关闭
                var ivClose = recognitionDialog!!.findViewById<View>(R.id.ivClose)
                ivClose?.setOnClickListener(object :SingleClickListener(){
                    override fun onSingleClick(v: View?) {
                        dismissTipsDialog()
                    }
                })


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



























