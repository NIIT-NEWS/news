package com.sychen.login.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sychen.basic.*
import com.sychen.login.R
import com.sychen.login.database.viewmodel.UserViewModel
import com.sychen.login.database.model.User
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

const val TAG = "TAG"

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
        login_btn.setOnClickListener { v ->
            val account = account_edit.text.toString()
            val pwd = password_edit.text.toString()
            //进行登录请求
            loginViewModel.login(account, pwd)
            /**
             * param : flag(boolean) 验证登录请求是否成功
             * true-> 存储token、存储用户信息->跳转到用户主界面
             * false-> 提示登录失败
             */
            loginViewModel.flag.observe(requireActivity(), { flag ->
                if (flag) {
                    //获取登录信息
                    loginViewModel.loginInfo.observe(requireActivity(), { info ->
                        //存储键值对保存TOKEN,USER_ID
                        lifecycleScope.launch {
                            dataStoreSave("USER_ID",info.data.id.toString())
                            dataStoreSave("TOKEN",info.data.token)
                        }
                        /**
                         * 判断数据库中是否存在用户
                         * false->在数据库中插入用户数据
                         * true->啥也不干
                         */
                        userViewModel.verifyExist(account).observe(requireActivity(), { verify ->
                            if (verify.isEmpty()) {
                                //数据库插入数据
                                userViewModel.insertUser(
                                    User(
                                        0,
                                        info.data.username,
                                        "null",
                                        info.data.avatar,
                                        info.data.role,
                                        info.data.token
                                    )
                                )
                            }
                            //使用eventbus发送广播启动activity
                            val msg = MessageEvent(MessageType.TypeOne).put("startActivity")
                            EventBus.getDefault().post(msg)
                        })
                    })
                    showToast("登录成功")
                } else {
                    showToast("登录失败")
                }
            })
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}