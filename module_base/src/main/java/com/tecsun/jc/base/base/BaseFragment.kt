package com.tecsun.jc.base.base

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.tecsun.jc.base.R
import com.tecsun.jc.base.dialog.LoadingProgressDialog

abstract class BaseFragment : Fragment() {

    lateinit var mView: View
    private var loadingProgressDialog: LoadingProgressDialog? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (getLayoutId() == 0) {
            return null
        }

        mView = inflater.inflate(getLayoutId(), container, false)
        return mView
    }

    open fun initView(savedInstanceState: Bundle?) {}

    fun getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //防止点击穿透
        if (mView != null) {
            view.isClickable = true
        }
    }

    fun showLoadingDialog() {
        showLoadingDialog(tipContent = context?.getString(R.string.base_tip_load))
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

//        if (loadingProgressDialog == null) {
        if (loadingProgressDialog != null) {
            loadingProgressDialog?.dismiss()
        }
        loadingProgressDialog = LoadingProgressDialog.Builder(context!!)
            .setCancelable(isCancelable)
            .setCanceledOnTouchOutside(isCanceledOnTouchOutside)
            .setDialogTip(tipContent)
            .setCancelListener(cancelListener)
            .build()
//        }

        activity?.let {
            if (!it.isFinishing) {
                loadingProgressDialog?.show()
            }
        }
    }

    fun dismissLoadingDialog() {
        loadingProgressDialog?.dismiss()
    }
}