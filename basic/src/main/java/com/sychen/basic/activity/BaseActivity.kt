package com.sychen.basic.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sychen.basic.MyApplication.Companion.log

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("BaseActivity:onCreate${javaClass.simpleName}")
        ActivityCollector.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        log("BaseActivity:onDestroy${javaClass.simpleName}")
        ActivityCollector.removeActivity(this)
    }
}