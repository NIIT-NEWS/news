package com.sychen.basic.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import com.alibaba.android.arouter.launcher.ARouter
import com.sychen.anew.service.NetworkService
import com.sychen.basic.MessageEvent
import com.sychen.basic.MessageType
import com.sychen.basic.MyApplication.Companion.log
import com.sychen.basic.util.DialogUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

open class BaseActivity : AppCompatActivity() {
    companion object{
        const val TAG = "BaseActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("BaseActivity:onCreate${javaClass.simpleName}")
        bindNetService()
        EventBus.getDefault().register(this)
        ActivityCollector.addActivity(this)
        ARouter.getInstance().inject(this)
    }

    private fun bindNetService() {
        val intent = Intent(this, NetworkService::class.java)
        bindService(intent, object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                (service as NetworkService.NetBinder).service.netCodeLiveData.observe(this@BaseActivity,
                    {
                        when (it) {
                            200 -> {
                            }
                            else -> {
                                Log.e(TAG, "onServiceConnected: $it" )
                                Thread {
                                    Looper.prepare()
                                    DialogUtil.alertDialog(this@BaseActivity, "服务器连接失败")
                                    Looper.loop()
                                }.start()
                            }
                        }

                    })
            }

            override fun onServiceDisconnected(name: ComponentName?) {

            }
        }, Context.BIND_AUTO_CREATE)
    }

    private fun steepStatusBar() {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
        window.addFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        log("BaseActivity:onDestroy${javaClass.simpleName}")
        ActivityCollector.removeActivity(this)
        EventBus.getDefault().unregister(this)
        Intent(this, NetworkService::class.java).also {
            stopService(it)
        }
    }

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(event: MessageEvent) {
        when (event.type) {
            MessageType.TypeTwo -> {
            }
            MessageType.TypeOne -> {
            }
        }
    }
}