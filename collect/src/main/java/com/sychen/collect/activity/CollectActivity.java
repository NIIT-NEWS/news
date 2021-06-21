package com.sychen.collect.activity;

import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.sychen.basic.ARouterUtil;
import com.sychen.basic.activity.BaseActivity;
import com.sychen.collect.R;


@Route(path = ARouterUtil.START_COLLECT_ACTIVITY)
public class CollectActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
    }
}