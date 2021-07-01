package com.sychen.user.ui.usersetname

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.util.DialogUtil
import com.sychen.basic.util.Show
import com.sychen.basic.util.dataStoreRead
import com.sychen.basic.util.dataStoreSave
import com.sychen.user.R
import com.sychen.user.network.model.User
import com.sychen.user.network.model.UserInfo
import com.sychen.user.ui.previewcamera.PreviewPhotoViewModel
import kotlinx.android.synthetic.main.set_username.*
import kotlinx.coroutines.launch

enum class InputStatus {
    InputStart, //输入
    InputEnd  //完成输入
}

class SetNameFragment : Fragment() {

    private lateinit var viewModel: SetNameViewModel
    private lateinit var userInfo: UserInfo
    private lateinit var previewPhotoViewModel: PreviewPhotoViewModel
    private val inputStatus = MutableLiveData(InputStatus.InputStart)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.set_username, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SetNameViewModel::class.java)
        previewPhotoViewModel = ViewModelProvider(this).get(PreviewPhotoViewModel::class.java)
        lifecycleScope.launch {
            userInfo = Gson().fromJson(dataStoreRead<String>("USER_INFO"), UserInfo::class.java)
        }
        initView()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initView() {
        toolbar.apply {
            setNavigationOnClickListener {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_setNameFragment_to_userSetFragment)
            }
        }
        /**
         * 获取edit_username的TextWatcher监听输入状态并记录
         */
        edit_username.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    inputStatus.value = InputStatus.InputStart
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    inputStatus.value = InputStatus.InputEnd
                }

                override fun afterTextChanged(s: Editable?) {
                    inputStatus.value = InputStatus.InputEnd
                }
            })
        }
        inputStatus.observe(requireActivity(), {
            when (inputStatus.value) {
                InputStatus.InputStart -> {
                    confirm_button.isEnabled = false
                    edit_clear.visibility = View.INVISIBLE
                }
                InputStatus.InputEnd -> {
                    //确认按钮
                    confirm_button.apply {
                        isEnabled = true
                        isClickable = true
                        background = resources.getDrawable(R.drawable.confirm_btn)
                        setTextColor(resources.getColor(R.color.white))
                        setOnClickListener { v ->
                            viewModel.updateName(edit_username.text.toString())
                                .observe(requireActivity(), {
                                    when (it) {
                                        200 -> {
                                            Navigation.findNavController(v)
                                                .navigate(R.id.action_setNameFragment_to_userSetFragment)
                                            DialogUtil.alertDialog(requireContext(), "更新成功！")
                                            saveUserNameJson(edit_username.text.toString())
                                        }
                                        201 -> {
                                            DialogUtil.alertDialog(requireContext(), "更新失败！")
                                        }
                                        else -> {
                                            DialogUtil.alertDialog(requireContext(), "更新失败！")
                                        }
                                    }
                                })

                        }
                    }
                    //清除键
                    edit_clear.apply {
                        visibility = View.VISIBLE
                        setOnClickListener {
                            edit_username.text.clear()
                        }
                    }
                }
            }
        })
    }

    private fun saveUserNameJson(name: String) {
        val newUserInfo: UserInfo = userInfo.copy(nickname = name)
        lifecycleScope.launch {
            dataStoreSave("USER_INFO", Gson().toJson(newUserInfo))
        }
    }


}