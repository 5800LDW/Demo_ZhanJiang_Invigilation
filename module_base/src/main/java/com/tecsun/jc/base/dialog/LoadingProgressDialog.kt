package com.tecsun.jc.base.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import com.tecsun.jc.base.R
import kotlinx.android.synthetic.main.dialog_all_loading.*

class LoadingProgressDialog private constructor(context: Context, builder: Builder): AlertDialog(context, R.style.Custom_Progress) {

    private var dialogTip: String? = null
    private var isLoadingCancelable: Boolean = false
    private var isLoadingCanceledOnTouchOutside: Boolean = false
    private var cancelListener: DialogInterface.OnCancelListener? = null

    init {
        this.dialogTip = builder.dialogTip
        this.isLoadingCancelable = builder.builderCancelable
        this.isLoadingCanceledOnTouchOutside = builder.isCanceledOnTouchOutside
        this.cancelListener = builder.istener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_all_loading)

        setCancelable(isLoadingCancelable)
        setCanceledOnTouchOutside(isLoadingCanceledOnTouchOutside)

        tv_all_load.text = dialogTip

        cancelListener?.let {
            setOnCancelListener(cancelListener)
        }
    }

    class Builder constructor(var context: Context) {

        internal var dialogTip: String? = null
        internal var builderCancelable: Boolean = false
        internal var isCanceledOnTouchOutside: Boolean = false
         var istener: DialogInterface.OnCancelListener? = null

        fun setCancelable(isCancelable: Boolean): Builder {
            this.builderCancelable = isCancelable
            return this
        }

        fun setCanceledOnTouchOutside(isCanceledOnTouchOutside: Boolean): Builder {
            this.isCanceledOnTouchOutside = isCanceledOnTouchOutside
            return this
        }

        fun setDialogTip(tipContent: String?): Builder {
            this.dialogTip = tipContent
            return this
        }

        fun setCancelListener(l: DialogInterface.OnCancelListener): Builder {
            this.istener = l
            return this
        }

        fun build(): LoadingProgressDialog {
            return LoadingProgressDialog(context, this)
        }

    }

}