package com.sychen.systemsettings

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.sychen.basic.ARouterUtil.START_SETTING_ACTIVITY
import com.sychen.basic.activity.BaseActivity
import com.sychen.basic.util.StatusBarHeight
import kotlinx.android.synthetic.main.settings_activity.*

@Route(path = START_SETTING_ACTIVITY)
class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        initView()
    }

    private fun initView() {
        guidelineSearch.setGuidelineBegin(StatusBarHeight.get())
    }
}