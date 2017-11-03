package com.sketch.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kan212 on 17/11/2.
 * 旋转太极
 */

public class TaichiView extends View {

    Paint mPaint;
    int mDegress;

    public TaichiView(Context context) {
        this(context,null,0);
    }

    public TaichiView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TaichiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = Math.min(getWidth(),getHeight());
        canvas.rotate(mDegress,width/2,width/2);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(width/2,width/2,width/2 + 5,mPaint);

        canvas.drawArc(new RectF(0,0,width,width),90,180,true,mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawArc(new RectF(0,0,width,width),270,180,true,mPaint);

        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(width / 2, width / 4, width / 4, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(width / 2, width / 4 * 3, width / 4, mPaint);

        canvas.drawCircle(width / 2, width / 4, width / 16, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(width / 2, width / 4 * 3, width / 16, mPaint);

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mDegress += 10;
            setDegrees(mDegress == 360 ? mDegress = 0 : mDegress);

            mHandler.sendEmptyMessageDelayed(0, 50);
        }
    };

    /**
     * handler 管理旋转
     * @param degrees
     */
    public void setDegrees(int degrees) {
        this.mDegress = degrees;
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mHandler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }
}
