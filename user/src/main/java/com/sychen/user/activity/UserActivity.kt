package com.sychen.user.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sychen.basic.activity.BaseActivity
import com.sychen.user.R

class UserActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
    }
}