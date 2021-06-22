package com.sychen.search.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.sychen.basic.ARouterUtil;
import com.sychen.basic.activity.BaseActivity;
import com.sychen.basic.util.StatusBarHeight;
import com.sychen.search.R;
import com.sychen.search.adapter.SearchAdapter;
import com.sychen.search.adapter.SearchDiffCallBack;
import com.sychen.search.network.model.SearchNews;
import com.sychen.search.network.viewmodel.SearchViewModel;

import java.util.List;

@Route(path = ARouterUtil.START_SEARCH_ACTIVITY)
public class SearchActivity extends BaseActivity {
    private SearchViewModel viewModel;
    private SearchAdapter searchAdapter;
    private RecyclerView recyclerView;
    @Autowired(name = "SEARCH_TEXT")
    String searchText;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        initViews();
    }

    private void initViews() {
        Guideline guidelineSearch = findViewById(R.id.guidelineSearch);
        guidelineSearch.setGuidelineBegin(StatusBarHeight.INSTANCE.get());
        recyclerView = findViewById(R.id.search_recyclerView);
        searchAdapter = new SearchAdapter(new SearchDiffCallBack());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(searchAdapter);
        viewModel.searchNews(searchText);
        viewModel.searchNews.observe(this,
                searchNews -> searchAdapter.submitList(searchNews)
        );
    }
}