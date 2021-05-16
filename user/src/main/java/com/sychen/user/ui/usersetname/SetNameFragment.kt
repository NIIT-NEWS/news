package com.sychen.user.ui.usersetname

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sychen.basic.util.dataStoreRead
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
                    //检索用户名是否存在
                    viewModel.verifyName(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                    inputStatus.value = InputStatus.InputEnd
                }
            })
        }
        //用户存在的话，提示文字出现
        viewModel.flag.observe(requireActivity(), {
            when (it) {
                true -> {
                    hint_text.text = resources.getString(R.string.username_alread_exit)
                    confirm_button.isEnabled = false
                }
                false -> {
                    hint_text.text = " "
                    confirm_button.isEnabled = true
                }
            }

        })
        inputStatus.observe(requireActivity(), {
            when (inputStatus.value) {
                /** 键盘没有输入的时候
                 *  确认按钮不可用
                 *  输入框内容写入用户名数据
                 *  点击清除按钮可以清除edittext的text
                 */
                InputStatus.InputStart -> {
                    confirm_button.isEnabled = false
                    edit_clear.visibility = View.INVISIBLE
                }
                /**
                 * 当用户输入完成
                 * 获取edittext的值并验证
                 * 传入view model更新用户信息
                 */
                InputStatus.InputEnd -> {
                    //确认按钮
                    confirm_button.apply {
                        isEnabled = true
                        isClickable = true
                        background = resources.getDrawable(R.drawable.confirm_btn)
                        setTextColor(resources.getColor(R.color.white))
                        setOnClickListener {
                            userGson()
                            Navigation.findNavController(it)
                                .navigate(R.id.action_setNameFragment_to_userSetFragment)
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
    //转换成json数据并更新数据
    private fun userGson() {
        val gson = GsonBuilder().create()
        val updateUser = User()
        with(updateUser) {
            userid = userInfo.data.id
            username = edit_username.text.toString()
            password = userInfo.data.password
            avatar = userInfo.data.avatar
            role = userInfo.data.role
        }
        val user = gson.toJson(updateUser)
        previewPhotoViewModel.updateUserInfo(user)
    }


}