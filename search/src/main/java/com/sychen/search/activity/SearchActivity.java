package com.sychen.search.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sychen.basic.ARouterUtil;
import com.sychen.basic.activity.BaseActivity;
import com.sychen.search.R;

@Route(path = ARouterUtil.START_SEARCH_ACTIVITY)
public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
}