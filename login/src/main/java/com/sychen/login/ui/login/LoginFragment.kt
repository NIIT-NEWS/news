package com.sychen.login.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.alibaba.android.arouter.launcher.ARouter
import com.sychen.basic.*
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.util.DialogUtil
import com.sychen.basic.util.Show
import com.sychen.basic.util.dataStoreSave
import com.sychen.login.R
import com.sychen.login.database.viewmodel.UserViewModel
import com.sychen.login.database.model.User
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        initView()
    }

    //初始化页面
    private fun initView() {
        //登录按钮监听
        login_btn.setOnClickListener {
            login_loader.visibility = View.VISIBLE
            val account = account_edit.text.toString()
            val pwd = password_edit.text.toString()
            //进行登录请求
            loginViewModel.login(account, pwd).observe(requireActivity(), { userInfo ->
                when (userInfo.code) {
                    200 -> {
                        login_loader.visibility = View.GONE
                        DialogUtil.alertDialog(requireContext(),"登录成功")
                        lifecycleScope.launch {
                            dataStoreSave("USER_ID", userInfo.data.id.toString())
                            dataStoreSave("TOKEN", userInfo.data.token)
                        }
                        userViewModel.verifyExist(account).observe(requireActivity(), { verify ->
                            if (verify.isEmpty()) {
                                //数据库插入数据
                                userViewModel.insertUser(
                                    User(
                                        0,
                                        userInfo.data.username,
                                        "null",
                                        userInfo.data.avatar,
                                        userInfo.data.role,
                                        userInfo.data.token
                                    )
                                )
                            }
                        })
                        Show.showToastShort("登录成功")
                        ARouter.getInstance().build(ARouterUtil.START_MAIN_ACTIVITY).navigation()
                        requireActivity().finish()
                    }
                    201 -> {
                        Show.showToastShort("登录失败，用户名或密码错误")
                        login_loader.visibility = View.GONE
                    }
                    else -> {
                        Show.showToastShort("错误")
                        login_loader.visibility = View.GONE
                    }
                }
            })
        }
        go_rigster.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

}