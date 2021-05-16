package com.sychen.login.diyview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class RoundRectView extends View {
    private int mColor = Color.RED;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//无锯齿

    //通过创建对象创建控件
    public RoundRectView(Context context) {
        super(context);
        init();
    }

    //通过xml声明控件调用
    public RoundRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //需要手动调用该构造方法
    public RoundRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(mColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int rectWidth = 220;
        int rectHeight = 160;
        RectF rectF = new RectF((width - rectHeight) / 2, (height - rectHeight) / 2, (width + rectWidth) / 2, (height + rectHeight) / 2);
        canvas.drawRoundRect(rectF,30,30,mPaint);
    }
}
