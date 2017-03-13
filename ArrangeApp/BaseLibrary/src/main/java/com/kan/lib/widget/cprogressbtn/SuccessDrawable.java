package com.kan.lib.widget.cprogressbtn;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by kan212 on 17/3/10.
 */

public class SuccessDrawable extends Drawable{

    private int mSize;
    private int mStrokeWidth;
    private Path mPath;
    private Paint mPaint;
    private float lenFinish,length;
    private enum State{
        RIGHT,NULL,
    }

    private State mState = State.NULL;

    public SuccessDrawable(int size, int strokeWidth){
        mSize = size;
        mStrokeWidth = strokeWidth;
        initPath();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    public int getIntrinsicHeight() {
        return getSize();
    }

    @Override
    public int getIntrinsicWidth() {
        return getSize();
    }

    private void initPath() {
        mPath = new Path();
        mPath.moveTo(mSize * 0.9f, mSize * 0.3f);
        mPath.lineTo(mSize * 0.4f, mSize * 0.8f);
        mPath.lineTo(mSize * 0.1f, mSize * 0.6f);
        PathMeasure pm = new PathMeasure(mPath, false);
        lenFinish = pm.getLength();
    }


    private void setPhase(float phase) {
        mPaint.setPathEffect(new DashPathEffect(new float[]{length, length}, -length * phase));
        invalidateSelf();
    }

    public void animSuccess() {
        length = lenFinish;
        mState = State.RIGHT;
        startAnim();
    }


    private void startAnim() {
        final ObjectAnimator animator = ObjectAnimator.ofFloat(this, "Phase", 1f, 0f);
        animator.setDuration(1400);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });
        animator.start();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(getSize()/2,getSize()/2);
        canvas.drawOval(new RectF(- getSize()/2 ,-getSize()/2 ,  getSize()/2 ,getSize()/2),createPaint(0));
        canvas.restore();
        if (mState == State.NULL){
            return;
        }
        canvas.drawPath(mPath, mPaint);
    }

    private Paint createPaint(int stokenWidth) {
        Paint mBackPaint = new Paint();
        mBackPaint.setAntiAlias(true);
        if(stokenWidth == 0){
            mBackPaint.setColor(Color.RED);
            mBackPaint.setStyle(Paint.Style.FILL);
        }else{
            mBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mBackPaint.setColor(Color.BLACK);
            mBackPaint.setStyle(Paint.Style.STROKE);
            mBackPaint.setStrokeWidth(stokenWidth);
        }

        return mBackPaint;
    }

    public int getSize() {
        return mSize;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
