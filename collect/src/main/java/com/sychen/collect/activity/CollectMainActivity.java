package com.sychen.collect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sychen.collect.R;

public class CollectMainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private androidx.appcompat.widget.Toolbar toolbar;
    private TextView newsContent;
    private ImageView newsPicture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_main);
        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar1);//获得activity.xml文件中toolbar的id
        newsContent = findViewById(R.id.newsContents);
        newsPicture = findViewById(R.id.newsPictures);
        Intent intent = getIntent();//获得Intent值，在activity之间跳转时使用
        String title = intent.getStringExtra("Title");//获得值
        String content = intent.getStringExtra("Content");
        String picture = intent.getStringExtra("Picture");
        newsContent.setText(content);
        toolbar.setTitle(title);
        Glide.with(this)
                .load(picture)
                .into(newsPicture);
    }
}
