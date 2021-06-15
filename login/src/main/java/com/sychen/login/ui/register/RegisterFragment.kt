package com.sychen.login.ui.register

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.marginTop
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.GsonBuilder
import com.sychen.basic.ARouterUtil
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.util.DialogUtil
import com.sychen.basic.util.Show
import com.sychen.basic.util.StatusBarHeight
import com.sychen.basic.util.dataStoreSave
import com.sychen.login.R
import com.sychen.login.database.model.User
import com.sychen.login.database.viewmodel.UserViewModel
import com.sychen.login.ui.SaveUser
import kotlinx.android.synthetic.main.register_fragment.*
import kotlinx.coroutines.launch

data class RegisterUser(
    var nickname: String = "",
    var password: String = "",
    var avatar: String = "",
    var role: String = "",
)

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private val viewModel by lazy {
        RegisterViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        register_btn.setOnClickListener {
            val nickname = register_nickname_edit.text.toString()
            val pwd = register_pwd_edit.text.toString()
            val repwd = re_pwd_edit.text.toString()
            if (pwd != repwd) {
                Show.showToastShort("密码不一致")
                return@setOnClickListener
            }
            val toJson = GsonBuilder().create().toJson(userJson(nickname, pwd))
            viewModel.registerUser(toJson).observe(requireActivity(), { userInfo ->
                lifecycleScope.launch {
                    dataStoreSave("USER_ID", userInfo.id.toString())
                    dataStoreSave("TOKEN", userInfo.token)
                }
                SaveUser.save(userInfo)
                DialogUtil.alertDialog(requireContext(),"注册成功")
                ARouter.getInstance().build(ARouterUtil.START_MAIN_ACTIVITY).navigation()
            })
        }
    }

    private fun userJson(
        nickname_edit: String,
        pwd_edit: String,
    ): RegisterUser {
        val user = RegisterUser()
        with(user) {
            nickname = nickname_edit
            password = pwd_edit
            avatar =
                "https://ccsy.oss-cn-beijing.aliyuncs.com/my_file/59a928da-678a-4fdb-8797-7fa7bb3e7a87.jpg"
            role = "user"
        }
        return user
    }

}