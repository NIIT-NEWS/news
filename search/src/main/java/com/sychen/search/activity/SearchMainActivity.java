package com.sychen.search.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.sychen.basic.util.StatusBarHeight;
import com.sychen.search.R;

/**
 * 那么以下两种获得Intent的方法在开启Activity时,有什么本质区别吗?
 * Intent intent1 = getIntent(); //此方法在MainActivity中调用
 * Intent intent2 = new Intent(MainActivity.this, MainActivity.class);
 * 如果利用intent2去startActivity() 那么会开启一个全新的Activity
 *
 * Glide框架是google推荐的Android图片加载框架，使用起来非常轻便，比如以下代码就可以实现在fragment内，以fitCenter方式加载图片，未加载成功前显示placeholder。
 *
 * Glide.with(fragment)
 *    .load(myUrl)
 *    .placeholder(placeholder)
 *    .fitCenter()
 *    .into(imageView);
 */

public class SearchMainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private androidx.appcompat.widget.Toolbar toolbar;
    private TextView newsContent;
    private ImageView newsPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);
        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);//获得activity.xml文件中toolbar的id
        newsContent = findViewById(R.id.newsContent);
        newsPicture = findViewById(R.id.newsPicture);
        Guideline guideline = findViewById(R.id.searchguideline);
        guideline.setGuidelineBegin(StatusBarHeight.INSTANCE.get());
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