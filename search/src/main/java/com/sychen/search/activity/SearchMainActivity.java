package com.sychen.search.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.sychen.basic.util.StatusBarHeight;
import com.sychen.search.R;

public class SearchMainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private Toolbar toolbar;
    private TextView newsContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);
        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        newsContent = findViewById(R.id.searchNewsContent);
        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        String content = intent.getStringExtra("Content");
        newsContent.setText(content);
        toolbar.setTitle(title);
    }
}