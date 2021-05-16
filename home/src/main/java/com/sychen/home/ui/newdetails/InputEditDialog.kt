package com.sychen.home.ui.newdetails

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Rect
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.util.Show
import com.sychen.home.R
import com.sychen.home.R.color.black
import kotlinx.android.synthetic.main.dialog_input_edit.*
import kotlinx.android.synthetic.main.dialog_input_edit.view.*


@SuppressLint("UseCompatLoadingForDrawables")
class InputEditDialog(context: Context, theme: Int, hintString: String?) : Dialog(context, theme) {
    private val mContext: Context = context
    private val imm: InputMethodManager
    private var mLastDiff = 0

    override fun dismiss() {
        super.dismiss()
        //dismiss之前重置mLastDiff值避免下次无法打开
        mLastDiff = 0
    }

    init {
        setContentView(R.layout.dialog_input_edit)
        imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        et_input_message.apply {
            inputType = InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            isSingleLine = false
            setHorizontallyScrolling(false)
            hint = hintString
            setTextColor(resources.getColor(black))
            background = resources.getDrawable(R.drawable.edit)
            //获取焦点
            isFocusable = true
            isEnabled = true
            isFocusableInTouchMode = true
            //失去焦点收起软键盘
            setOnFocusChangeListener { v, hasFocus ->
                when (hasFocus) {
                    false -> {
                        val systemService =
                            mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        systemService.hideSoftInputFromWindow(
                            v.windowToken,
                            InputMethodManager.HIDE_NOT_ALWAYS
                        )
                    }
                    else -> {
                    }
                }

            }
            textSize = 20F
            //软键盘按键监听
            setOnEditorActionListener { v, actionId, event ->
                when (actionId) {
                    KeyEvent.KEYCODE_ENDCALL, KeyEvent.KEYCODE_ENTER -> {
                        if (et_input_message.text.isNotEmpty()) {
                            imm.hideSoftInputFromWindow(et_input_message.windowToken, 0)
                            dismiss()
                        } else {
                            Show.showToastShort("输入信息不能为空")
                        }
                        true
                    }
                    KeyEvent.KEYCODE_BACK -> {
                        dismiss()
                        false
                    }
                    else -> false
                }
            }
            setOnKeyListener { view, i, keyEvent ->
                Log.e(TAG, "onKey  ${keyEvent.characters} ")
                false
            }
        }
        confirm_btn.setOnClickListener {
            val msg: String = et_input_message.text.toString().trim()
            if (!TextUtils.isEmpty(msg)) {
                imm.showSoftInput(et_input_message, InputMethodManager.SHOW_FORCED)
                imm.hideSoftInputFromWindow(et_input_message.windowToken, 0)
                et_input_message.setText("")
                dismiss()
            } else {
                Show.showToastShort("输入信息不能为空")
            }
            et_input_message.text = null
        }
        rl_inputdlg_view.apply {
            addOnLayoutChangeListener { view, i, i1, i2, i3, i4, i5, i6, i7 ->
                val r = Rect()
                //获取当前界面可视部分
                window!!.decorView.getWindowVisibleDisplayFrame(r)
                //获取屏幕的高度
                val screenHeight: Int = window!!.decorView.rootView.height
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                val heightDifference: Int = screenHeight - r.bottom
                if (heightDifference <= 0 && mLastDiff > 0) {
                    //imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                    dismiss()
                }
                mLastDiff = heightDifference
            }
            //点击事件监听
            setOnClickListener {
                imm.hideSoftInputFromWindow(et_input_message.windowToken, 0)
                dismiss()
            }
        }
    }
}
