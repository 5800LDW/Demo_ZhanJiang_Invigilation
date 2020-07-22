package com.tecsun.jc.demo.invigilation.ui.pic

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ProgressBar
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.collector.BaseActivityCollector
import com.tecsun.jc.base.dialog.DialogUtils
import com.tecsun.jc.base.dialog.LoadingProgressDialog
import com.tecsun.jc.base.utils.ToastUtils
import com.tecsun.jc.demo.invigilation.R

abstract class MyBaseActivity : BaseActivity() {
    var mActivity: Activity? = null
    val mTokenId: String? = JinLinApp.mTokenId
    val mDeviceId: String? = JinLinApp.mDeviceId
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivity = this

    }
    /**
     * 按钮是否显示蓝色通过
     */
    fun check(btn:Button ,b: Boolean) {
        if (b) {
            btn.setBackground(resources.getDrawable(R.drawable.btn_base_bg_blue_selector))//默认蓝色
            btn.setEnabled(true)
        } else {
            btn.setBackground(resources.getDrawable(R.drawable.btn_base_bg_gray_selector))//默认灰色
            btn.setEnabled(false)
        }
    }
//    fun myFinish(){
////        finish()
//        //退出其他的activity
//        finishAllModuleRegisterActivity()
//    }

     fun finishAllModuleRegisterActivity(){
        BaseActivityCollector.finishAllActivity()
    }

    fun showToast(stringInt: Int) {
        ToastUtils.showGravityLongToast(this@MyBaseActivity, getString(stringInt))
//        Toast.makeText(this, stringInt, Toast.LENGTH_LONG).show()
    }


    fun failedDialog(strInt: Int) {
        failedDialog(getString(strInt)?:"")
    }

    fun failedDialog(str: String) {
        if(str!=null){
            runOnUiThread{
//                SingleBtnDialogUtil.showFailedDialog(str, mActivity)
            }
        }
    }

    private var loadingDialog: LoadingProgressDialog? = null
    fun myShowLoadingDialog(
        isCancelable: Boolean = false,
        isCanceledOnTouchOutside: Boolean = false,
        tipContent: String?)
    {
        loadingDialog = LoadingProgressDialog.Builder(this)
            .setCancelable(isCancelable)
            .setCanceledOnTouchOutside(isCanceledOnTouchOutside)
            .setDialogTip(tipContent)
            .build()
        runOnUiThread {
            if (!isDestroyed) {
                loadingDialog?.show()
            }

        }

    }

    fun myDismissLoadingDialog() {
        loadingDialog?.dismiss()
    }


    protected fun showProgressDialog() {
        showProgressDialog( ProgressBar(mActivity))
    }
    private fun showProgressDialog( v: View) {
        dismissProgressDialog()
        progressDialog = BaseProgressDialog.newInstance(this)
        if(progressDialog!=null && !isDestroyed){
            progressDialog!!.setContentView(v)
            runOnUiThread{
                progressDialog!!.show()
            }

        }

    }
    protected fun dismissProgressDialog() {
        if(progressDialog!=null){
            progressDialog!!.dismiss()
        }

    }
    private var progressDialog:BaseProgressDialog?=null

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    fun hideInputMethod(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showNetWorkError(){
        DialogUtils.showDialog(mActivity, R.string.register_tip_network_error, null)
    }


    class BaseProgressDialog : Dialog {


        constructor(context: Context) : this(context, R.style.register_progress_dialog) {
            initUI()
        }

        constructor(context: Context, themeResId: Int) : super(context, themeResId) {
            initUI()
        }

        protected constructor(
            context: Context,
            cancelable: Boolean,
            cancelListener: DialogInterface.OnCancelListener
        ) : super(context, cancelable, cancelListener) {
            initUI()
        }


        private fun initUI() {
            setContentView(ProgressBar(context))
            window!!.attributes.gravity = Gravity.CENTER
            setCanceledOnTouchOutside(false)
        }

        companion object {

            //    private TextView tv;


            fun newInstance(context: Context): BaseProgressDialog {

                return BaseProgressDialog(context)
            }
        }

    }

}

































