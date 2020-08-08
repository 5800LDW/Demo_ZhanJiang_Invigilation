package com.tecsun.jc.base.base

import android.annotation.TargetApi
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.R
import com.tecsun.jc.base.builder.ResultFailTipsBuilder
import com.tecsun.jc.base.collector.BaseActivityCollector
import com.tecsun.jc.base.dialog.DialogUtils
import com.tecsun.jc.base.dialog.LoadingProgressDialog
import com.tecsun.jc.base.listener.HandlerCallback
import com.tecsun.jc.base.listener.IEvents
import com.tecsun.jc.base.utils.BuildUtils.hasKitKat
import com.tecsun.jc.base.utils.BuildUtils.hasLollipop
import com.tecsun.jc.base.utils.KeyboardUtils
import com.tecsun.jc.base.utils.OkGoManager
import com.tecsun.jc.base.utils.SafeHandler
import com.tecsun.jc.base.utils.ToastUtils
import com.tecsun.jc.base.utils.activity.ActivityUtil
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.BaseSingleDialog
import com.tecsun.jc.base.widget.TitleBar
import org.greenrobot.eventbus.EventBus
import qiu.niorgai.StatusBarCompat

abstract class BaseActivity : AppCompatActivity(), HandlerCallback {

    private var loadingProgressDialog: LoadingProgressDialog? = null

    lateinit var handler: SafeHandler

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getLayoutId() != 0) {
            setContentView(getLayoutId())
        }
        StatusBarCompat.translucentStatusBar(this, true)
        handler = SafeHandler(this, this)
        ARouter.getInstance().inject(this)
        initView(savedInstanceState)
        initTitleView()

        //把activity加入到集合;
        if (isCanAddToCollector()) {
            BaseActivityCollector.addActivity(this)
        }
    }

    open fun isCanAddToCollector(): Boolean {
        return true
    }


    open fun initView(savedInstanceState: Bundle?) {}

    /**
     * 设置页面标题栏 可重新设置返回按钮、页面标题和菜单项(主要用于设置标题)
     *
     * @param titleBar
     */
    open fun setTitleBar(titleBar: TitleBar) {}

    /**
     * 初始化titlebar控件，可重写
     */
    open fun initTitleView() {
        setImmersiveStatusBar()
        val titleBar = findViewById<TitleBar>(R.id.title_bar)
        if (titleBar == null) {
            return
        }

        if (hasKitKat()) {
            titleBar.setImmersive(true)
        }

        titleBar.setBackgroundColor(resources.getColor(R.color.c_2358ff))

        titleBar.setLeftImageResource(R.drawable.ic_title_back)//base_zhanjiang_back_left //
        titleBar.setLeftTextColor(Color.WHITE)
        titleBar.setLeftClickListener {
            KeyboardUtils.hideSoftKeyboard(this)
            // 处理返回按钮事件
            this.finish()
        }

        titleBar.setTitleColor(Color.WHITE)
        titleBar.setSubTitleColor(Color.WHITE)
        setTitleBar(titleBar)
    }


    fun initTitleView2() {

        setImmersiveStatusBar()
        val titleBar = findViewById<TitleBar>(R.id.title_bar)
        if (titleBar == null) {
            return
        }

        if (hasKitKat()) {
            titleBar.setImmersive(true)
        }

        titleBar.setBackgroundColor(resources.getColor(R.color.c_f0f0f0))

        titleBar.setLeftImageResource(R.drawable.base_zhanjiang_back_left)
        titleBar.setLeftTextColor(Color.BLACK)
        titleBar.setLeftClickListener {
            KeyboardUtils.hideSoftKeyboard(this)
            // 处理返回按钮事件
            this.finish()
        }

        titleBar.setTitleColor(Color.WHITE)
        titleBar.setSubTitleColor(Color.WHITE)
        setTitleBar(titleBar)

    }


    /**
     * 设置沉浸式状态栏
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun setImmersiveStatusBar() {
        if (hasKitKat() && !hasLollipop()) {
            // 透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 透明导航栏
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (hasLollipop()) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun handleMessage(msg: Message?) {

    }

    fun showLoadingDialog() {
        showLoadingDialog(tipContent = this.getString(R.string.base_tip_load))
    }

    @JvmOverloads
    fun showLoadingDialogCanCancelable(l: DialogInterface.OnCancelListener = DialogInterface.OnCancelListener {}) {
        showLoadingDialog(
            tipContent = this.getString(R.string.base_tip_load),
            isCancelable = true,
            cancelListener = l
        )
    }

    @JvmOverloads
    fun showLoadingDialog(
        isCancelable: Boolean = false,
        isCanceledOnTouchOutside: Boolean = false,
        tipContent: String?,
        cancelListener: DialogInterface.OnCancelListener = DialogInterface.OnCancelListener {}
    ) {

        if (loadingProgressDialog != null) {
            loadingProgressDialog?.dismiss()
        }

//        if (loadingProgressDialog == null) {
        loadingProgressDialog = LoadingProgressDialog.Builder(this)
            .setCancelable(isCancelable)
            .setCanceledOnTouchOutside(isCanceledOnTouchOutside)
            .setDialogTip(tipContent)
            .setCancelListener(cancelListener)
            .build()
//        }

        runOnUiThread {
            if (!isFinishing) {
                loadingProgressDialog?.show()
            }
        }

    }

    fun dismissLoadingDialog() {
        loadingProgressDialog?.dismiss()
    }

    override fun onDestroy() {
        KeyboardUtils.hideSoftKeyboard(this)

        BaseActivityCollector.removeActivity(this)

        if (singleDialog != null) {
            singleDialog!!.dismiss()
        }

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }

        super.onDestroy()
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev!!.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideInput(v, ev)) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(v!!.windowToken, 0)
                afterHideKeyboardOtherBusinessInChild()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**全局点击非EditText区域自动隐藏软键盘*/
    private fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val leftTop = intArrayOf(0, 0)
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop)
            val left = leftTop[0]
            val top = leftTop[1]
            val bottom = top + v.height
            val right = left + v.width
            if (event.x > left && event.x < right && event.y > top && event.y < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false
            } else {
                return true
            }
        }
        return false
    }

    open fun afterHideKeyboardOtherBusinessInChild() {

    }


    private var singleDialog: BaseSingleDialog? = null

    @JvmOverloads
    fun showErrorMessageDialog(
        msg: String?,
        isCanAfterClickFinishActivity: Boolean = true,
        drawableId: Int = R.drawable.ic_failed,
        iEvents: IEvents = IEvents {}
    ) {
        singleDialog = BaseSingleDialog.Builder(this)
            .setDailogContent(msg ?: "").setIconRes(drawableId).setPositiveClickListener {

                singleDialog?.dismiss()

                if (iEvents != null) {
                    iEvents.biz()
                }

                if (isCanAfterClickFinishActivity) {
                    finish()
                }


            }.build()
        try {
            runOnUiThread {
                if (!isFinishing) {
                    singleDialog?.show()
                }
            }
        } catch (e: Exception) {
            LogUtil.e(e)
            ToastUtils.showGravityShortToast(JinLinApp.context!!, e.message ?: "")
        }
    }

    open fun showToast(string: String) {
        ToastUtils.showGravityShortToast(JinLinApp.context!!, string)
    }


    /**
     * @param isCanAfterClickFinishActivity 是false是不关闭Activity,为true就关闭Activity,默认true
     * @param iEvents 点击"确定"后会回调这个接口
     */
    @JvmOverloads
    fun showSuccessMessageDialog(
        msg: String?,
        isCanAfterClickFinishActivity: Boolean = true,
        iEvents: IEvents = IEvents {}
    ) {
        showErrorMessageDialog(msg, isCanAfterClickFinishActivity, R.drawable.ic_success, iEvents)
    }

    fun showErrorMessageDialog2(msg: String?) {
        showErrorMessageDialog(msg, false)
    }

    fun cancelTagInActivity(tag: String) {
        OkGoManager.instance.myCancelTag(tag)
    }

    open fun myStartActivity(cls: Class<*>) {
        ActivityUtil.startActivity(this, cls, null)
    }

    open fun myFinish() {
        Handler().postDelayed({ finish() }, 200)
    }

    open fun successCompareBiz() {

    }

    open fun resubmit() {}


    fun showErrorDialog(str: String) {
        ResultFailTipsBuilder.showFailDialog(this, str ?: "")
    }

     fun showExitDialog(){
         DialogUtils.showDialog(
             this,
             "",
             resources.getColor(R.color.c_e60012),
             "确定返回?",
             R.string.base_lbl_confirm,
             R.string.base_cancel,
             { dialog, which ->
                 myFinish()
             },
             { dialog, which -> dialog.dismiss() }
         )
    }
}








































