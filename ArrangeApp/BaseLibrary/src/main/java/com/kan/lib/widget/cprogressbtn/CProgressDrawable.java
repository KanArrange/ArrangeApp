package com.kan.lib.widget.cprogressbtn;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 */

public class CProgressDrawable extends Drawable {

    private float mSweepAngle;
    private float mStartAngle;
    private int mSize;
    private int mStrokeWidth;
    private int mStrokeWidthOut;
    private int mStrokeColor;
    private Context mContext;
    private RectF mMiddleRect;
    private float degrees;
    private final RectF fBounds = new RectF();


    public CProgressDrawable(Context context,int size, int strokeWidth,int stokenWidthOut, int strokeColor) {
        mSize = size;
        this.mContext = context;
        this.mStrokeWidthOut = stokenWidthOut;
        mStrokeWidth = strokeWidth;
        mStrokeColor = strokeColor;
        mStartAngle = -90;
        mSweepAngle = 0;
    }

    public void setSweepAngle(float sweepAngle) {
        mSweepAngle = sweepAngle;
    }

    public int getSize() {
        return mSize;
    }

    @Override
    public void draw(Canvas canvas) {
        final Rect bounds = getBounds();

        if (mPath == null) {
            mPath = new Path();
        }
//
//
//        mPath.reset();
//        mPath.addArc(getRect(mStrokeWidthOut), 0, 360);
//        mPath.offset(bounds.left, bounds.top);
//        canvas.drawPath(mPath, createPaint(mStrokeWidthOut));
//
//        mPath.reset();
//        mPath.addArc(getRect(mStrokeWidth), mStartAngle, mSweepAngle);
//        mPath.offset(bounds.left, bounds.top);
//        canvas.drawPath(mPath, createPaint(mStrokeWidth));
//
//        mPath.reset();
//        mPath.addRect(getRectInMiddle(), Path.Direction.CCW);
//        mPath.offset(bounds.left, bounds.top);
//        canvas.drawPath(mPath, createPaint(0));

        canvas.save();
        canvas.translate(getSize()/2,getSize()/2);
        canvas.drawOval(new RectF(- getSize()/2 ,-getSize()/2 ,  getSize()/2 ,getSize()/2),createPaint(0));
        canvas.restore();

        canvas.save();
        canvas.translate((getSize())/2,getSize()/2);
        canvas.scale(1, 1);
        canvas.rotate(degrees);
        //draw two big arc
        float[] bStartAngles=new float[]{135,-45};
        for (int i = 0; i < 2; i++) {
            canvas.drawArc(new RectF(- getSize()/2 + mStrokeWidth * 4,-getSize()/2 + mStrokeWidth* 4,  getSize()/2 -mStrokeWidth*4,getSize()/2 - mStrokeWidth*4), bStartAngles[i], 90, false, createPaint(mStrokeWidth));
        }
    }


    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

    }

    private RectF getRectInMiddle() {
        int size = getSize();
        mMiddleRect = new RectF(size/3, size/3, size - size/3, size - size/3);
        return mMiddleRect;

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    private RectF mRectF;
    private Paint mPaint;
    private Path mPath;

    private RectF getRect(int stoken) {
        int size = getSize();
        int index = stoken / 2;
        mRectF = new RectF(index, index, size - index, size - index);
        return mRectF;
    }

    private Paint createPaint(int stokenWidth) {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
        }
        if(stokenWidth == 0){
            mPaint.setColor(Color.RED);
            mPaint.setStyle(Paint.Style.FILL);
        }else{
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(stokenWidth);
        }

        return mPaint;
    }

    public void start(){
        ValueAnimator rotateAnim=ValueAnimator.ofFloat(0, 180,360);
        rotateAnim.setDuration(1000);
        rotateAnim.setRepeatCount(-1);
        rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degrees = (float) animation.getAnimatedValue();
                invalidateSelf();
            }
        });
        rotateAnim.start();
    }


}
