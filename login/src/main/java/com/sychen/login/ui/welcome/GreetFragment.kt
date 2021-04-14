package com.sychen.login.ui.welcome

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.sychen.basic.*
import com.sychen.login.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

/**
 * 在欢迎页面放置一个观察者监听token是否验证通过
 * 通过->跳转主页面
 * 不通过->跳转登录页面
 */
class GreetFragment : Fragment() {

    private lateinit var greetViewModel: GreetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.greet_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        greetViewModel = ViewModelProvider(this).get(GreetViewModel::class.java)
        /**
         * 读取文件中token
         */
        lifecycleScope.launch {
            val token = dataStoreRead<String>("TOKEN")
            greetViewModel.verifyToken(token)
        }
        /**
         * 计时2秒进入应用，检查token是否存在
         * 存在->验证->token有效->进入主页面->进入userFragment
         * 存在->验证->token过期->重新登录->进入LoginFragment
         * 不存在->进入LoginFragment
         */
        Handler(Looper.getMainLooper()).postDelayed({
            /**
             * 判断flag参数
             * param : flag --- true: token验证通过，false即为token不通过
             * flag->true->验证通过进入主页面
             * flag->false->验证失败进入登录页面
             */
            greetViewModel.flag.value.apply {
                if (this == true) {
                    lifecycleScope.launch {
                        greetViewModel.tokenInfo.observe(requireActivity(), {
                            /**
                             * 通过Bundle传递参数->USER_INFO:token信息
                             * 进入用户页面
                             */
//                            val intent = Intent()
//                            intent.setClass(requireActivity(),UserActivity::class.java)
//                            startActivity(intent)
                            //使用eventbus发送广播启动activity
                            val msg = MessageEvent(MessageType.TypeOne).put("startActivity")
                            EventBus.getDefault().post(msg)
                        })
                    }
                } else {
                    //进入登录页面
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_greetFragment_to_loginFragment)
                }
            }
        }, 2_000)
    }

}