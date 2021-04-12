package com.sychen.login.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.sychen.basic.DataStoreUtils
import com.sychen.login.R
import com.sychen.login.database.UserViewModel
import com.sychen.login.database.model.User
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

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

    private fun initView() {
        login_btn.setOnClickListener { v->
            val account = account_edit.text.toString()
            val pwd = password_edit.text.toString()
            loginViewModel.login(account, pwd)
            loginViewModel.flag.observe(requireActivity(), { flag ->
                Log.e(TAG, "initView: $flag")
                if (flag) {
                    loginViewModel.loginInfo.observe(requireActivity(), { info ->
                        Log.e(TAG, "initView: $info")
                        lifecycleScope.launch {
                            createData(info.data.token)
                        }
                        userViewModel.verifyExist(account).observe(requireActivity(), { verify ->
                            Log.e(TAG, "initView: $verify")
                            if (verify.isEmpty()) {
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
                            } else {

                            }
                            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_userFragment)
                            Log.e(TAG, "initView: $verify")
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

    private suspend fun createData(message: String) {
        DataStoreUtils.writeDataToDataStore(requireContext(),message)
    }

    private suspend fun getData(){
        val readDataToDataStore: Flow<String> = DataStoreUtils.readDataToDataStore(requireContext())
        readDataToDataStore.collect{
            Log.e("tag",it)
        }
    }

}