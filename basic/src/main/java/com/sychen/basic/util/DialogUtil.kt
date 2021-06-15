package com.sychen.basic.util

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import com.sychen.basic.MyApplication.Companion.getContext
import com.sychen.basic.R
import kotlinx.android.synthetic.main.alert_dialog.*
import kotlinx.android.synthetic.main.alert_dialog.progress_dialog_confirm

object DialogUtil {
    var dialog: Dialog? = null

    fun alertDialog(context: Context, message: String) {
        Dialog(context, R.style.DialogTheme).apply {
            setContentView(R.layout.alert_dialog)
            window!!.apply {
                setGravity(Gravity.CENTER)
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
            show()
            progress_dialog_confirm.setOnClickListener {
                dismiss()
            }
            alert_content.text = message
        }
    }

    fun progressBarDialog(context: Context) {
        Dialog(context, R.style.DialogTheme).apply {
            setContentView(R.layout.progress_dialog)
            window!!.apply {
                setGravity(Gravity.CENTER)
                setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
            show()
            progress_dialog_confirm.setOnClickListener {
                dismiss()
            }
        }
    }
}

class ProgressDialog(context: Context) : Dialog(context) {
    companion object {
        private var instance: Dialog? = null
            get() : Dialog? {
                if (field == null) {
                    field = ProgressDialog(getContext().applicationContext)
                }
                return field
            }
        @Synchronized
        fun get(): Dialog {
            instance = Dialog(getContext().applicationContext, R.style.DialogTheme).apply {
                setContentView(R.layout.progress_dialog)
                window!!.apply {
                    setGravity(Gravity.CENTER)
                    setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
                progress_dialog_confirm.setOnClickListener {
                    dismiss()
                }
            }
            return instance!!
        }
    }
}