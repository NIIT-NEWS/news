package com.sychen.login.ui

import android.content.Context
import androidx.lifecycle.Observer
import com.sychen.basic.MyApplication.Companion._context
import com.sychen.login.database.model.User
import com.sychen.login.database.viewmodel.UserViewModel
import com.sychen.login.network.model.UserInfo

object SaveUser {
    private val userViewModel by lazy {
        UserViewModel(_context!!)
    }

    fun save(userInfo: UserInfo) {
        val value = userViewModel.verifyExist(userInfo.username).value
        if (value!=null) {
            userViewModel.insertUser(
                User(0,
                userInfo.username,
                "null",
                userInfo.avatar,
                userInfo.role,
                userInfo.token)
            )
        }
    }


}