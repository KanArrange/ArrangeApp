package com.kan.arrange.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kan212 on 16/4/21.
 */
public class LoadingView extends View {
    private static final int DEFAULT_SIZE = 200; //View默认大小
    private int widthForUnspecified;
    private int heightForUnspecified;
    private Paint mPaint;
    private float mStrokeWidth = 2;//描边宽度
    private int colorStroke = Color.YELLOW;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec,true),measure(heightMeasureSpec,false));
    }

    /**
     * 计算屏幕的时候取出来宽高
     * @param origin
     * @param isWidth
     * @return
     */
    private int measure(int origin,boolean isWidth) {
        int result;
        int specMode = MeasureSpec.getMode(origin);
        int specSize = MeasureSpec.getSize(origin);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = specSize;
                if (isWidth) {
                    widthForUnspecified = result;
                }else {
                    heightForUnspecified = result;
                }
                break;
            default:
                if (isWidth) {//宽或高未指定的情况下，可以由另一端推算出来 - -如果两边都没指定就用默认值
                    result = widthForUnspecified;
                } else {
                    result = heightForUnspecified;
                }
                if (result == 0) {
                    result = DEFAULT_SIZE;
                }
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initParams(canvas);
        initPaints(canvas);
        drawFirst(canvas);
    }

    private void drawFirst(Canvas canvas) {
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(colorStroke);

        Path path = new Path();
        path.moveTo(0,0);
        path.lineTo(0,getHeight() / 2);
        path.lineTo(getWidth() * 2 / 5,getHeight() / 2 + 50);
        path.lineTo(getWidth() / 2 ,getHeight() * 2 /5);
        path.lineTo(getWidth() /3 ,0);
        path.lineTo(0,0);

        canvas.drawPath(path,mPaint);

    }

    private void initPaints(Canvas canvas) {
        if (mPaint == null) {
            mPaint = new Paint();
        } else {
            mPaint.reset();
        }
        mPaint.setAntiAlias(true);//边缘无锯齿
    }

    private void initParams(Canvas canvas) {
    }


}
