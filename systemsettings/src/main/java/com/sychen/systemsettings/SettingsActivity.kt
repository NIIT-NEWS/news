package com.sychen.systemsettings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import com.alibaba.android.arouter.facade.annotation.Route
import com.sychen.basic.ARouterUtil.USER_SET_TO_SET_ACTIVITY
import com.sychen.basic.activity.BaseActivity
import com.sychen.basic.util.StatusBarHeight
import kotlinx.android.synthetic.main.settings_activity.*

@Route(path = USER_SET_TO_SET_ACTIVITY)
class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        initView()
    }

    private fun initView() {
        guideline2.setGuidelineBegin(StatusBarHeight.get())
    }
}