package com.sychen.collect.activity;

import android.os.Bundle;

import androidx.constraintlayout.widget.Guideline;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.sychen.basic.ARouterUtil;
import com.sychen.basic.activity.BaseActivity;
import com.sychen.basic.util.StatusBarHeight;
import com.sychen.collect.R;
import com.sychen.collect.adapter.CollectAdapter;
import com.sychen.collect.adapter.CollectDiffCallBack;
import com.sychen.collect.network.viewmodel.CollectViewModel;


@Route(path = ARouterUtil.START_COLLECT_ACTIVITY)
public class CollectActivity extends BaseActivity {
    private CollectViewModel collectViewModel;
    private CollectAdapter collectAdapter;
    private RecyclerView recyclerView;
    @Autowired(name = "COLLECT_TEXT")
    String collectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        collectViewModel = new ViewModelProvider(this).get(CollectViewModel.class);
        initViews();
    }

    private void initViews() {
        Guideline guidelineCollect = findViewById(R.id.guidelineCollect);
        guidelineCollect.setGuidelineBegin(StatusBarHeight.INSTANCE.get());
        recyclerView = findViewById(R.id.collect_recyclerView);
        collectAdapter = new CollectAdapter(new CollectDiffCallBack());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(collectAdapter);
        collectViewModel.collectNews(collectText);
        collectViewModel.collectNews.observe(this,
                collectNews -> collectAdapter.submitList(collectNews)
        );
    }
}