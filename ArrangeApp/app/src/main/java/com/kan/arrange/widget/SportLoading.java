package com.kan.arrange.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by kan212 on 16/4/26.
 */
public class SportLoading extends FrameLayout{

    public SportLoading(Context context) {
        super(context);
        initView(context,null);
    }

    public SportLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public SportLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
