package com.sychen.systemsettings.ui.card

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.sychen.basic.util.SharedPreferencesUtil
import com.sychen.systemsettings.R
import kotlinx.android.synthetic.main.alert_dialog.*
import kotlinx.android.synthetic.main.fragment_card_set.*

class CardSetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_set, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    /**
     * cardBackgroundColor 设置背景色
     * cardCornerRadius 设置圆角大小
     * cardElevation 设置z轴阴影
     * cardMaxElevation 设置z轴最大高度值
     * cardUseCompatPadding 是否使用CompadPadding
     * cardPreventCornerOverlap 是否使用PreventCornerOverlap
     * contentPadding 内容的padding (Left,Top,Right,Bottom)
     * app:cardMaxElevation //最大阴影大小，该值最小2px
     */
    private fun initView() {
        with(CardRadius) {
            valueMax = 100
            valueMin = 0
            valueSelected = SharedPreferencesUtil.sharedPreferencesLoad<Int>("CARD_RADIUS").toString().toInt()
            setUnit("")
            setSelectedListener {
                cardView.radius = it.toFloat()
                SharedPreferencesUtil.sharedPreferencesSave("CARD_RADIUS", it)
            }
        }
        with(CardElevation) {
            valueMax = 100
            valueMin = 0
            valueSelected = SharedPreferencesUtil.sharedPreferencesLoad<Int>("CARD_ELEVATION").toString().toInt()
            setUnit("")
            setSelectedListener {
                cardView.cardElevation = it.toFloat()
                SharedPreferencesUtil.sharedPreferencesSave("CARD_ELEVATION", it)
            }
        }
        card_confirm_btn.setOnClickListener {
            alertDialog(requireContext(),"完成设置")
        }
        toolbar.setNavigationOnClickListener { v->
            Navigation.findNavController(v)
                .navigate(R.id.action_cardSetFragment_to_mainFragment)
        }
    }
    private fun alertDialog(context: Context, message: String) {
        Dialog(context, R.style.DialogTheme).apply {
            setContentView(R.layout.alert_dialog)
            window!!.apply {
                setGravity(Gravity.CENTER)
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
            show()
            progress_dialog_confirm.setOnClickListener {
                dismiss()
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_cardSetFragment_to_mainFragment)

            }
            alert_content.text = message
        }
    }
}