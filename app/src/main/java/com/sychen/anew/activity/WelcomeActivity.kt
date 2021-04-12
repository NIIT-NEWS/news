package com.sychen.anew.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.sychen.anew.R
import java.util.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(applicationContext,MainActivity::class.java))
                finish()
            }
        }, 3000)
    }
}