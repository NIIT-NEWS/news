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
import com.alibaba.android.arouter.launcher.ARouter
import com.sychen.basic.*
import com.sychen.basic.util.FlashlightUtils
import com.sychen.basic.util.Show
import com.sychen.basic.util.dataStoreRead
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

    private val ViewModel by lazy {
        GreetViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.greet_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            ViewModel.verifyToken()
            ViewModel.status.observe(requireActivity(), {
                when (it) {
                    TokenStatus.Success -> {
                        ARouter.getInstance().build(ARouterUtil.START_MAIN_ACTIVITY)
                            .navigation()
                        requireActivity().finish()
                    }
                    TokenStatus.Expired -> {
                        Navigation.findNavController(requireView())
                            .navigate(R.id.action_greetFragment_to_loginFragment)
                    }
                    TokenStatus.Failed -> {
                        Navigation.findNavController(requireView())
                            .navigate(R.id.action_greetFragment_to_loginFragment)
                    }
                }
            })
        }, 2_000)
    }

}