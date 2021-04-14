package com.sychen.login.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sychen.basic.MessageEvent
import com.sychen.basic.MessageType
import com.sychen.login.R
import com.sychen.login.ui.welcome.GreetViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


const val TAG = "TAG"

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: GreetViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.type) {
            MessageType.TypeTwo -> {
            }
            MessageType.TypeOne -> {
                val intent = Intent()
                intent.setClass(application, UserActivity::class.java)
                startActivity(intent)
            }
        }
    }
}