package com.kan.lib.widget.cprogressbtn;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 */

public class CProgressButton extends View {

    private static final int STOREWIDTH = 1;
    private Drawable mBackground;
    private CProgressDrawable mProgressDrawable;
    private SuccessDrawable mSuccessDrawable;
    private int mWidth;
    private int mHeight;
    private STATE mState = STATE.NORMAL;
    private boolean morphingCircle; //变形成圆中
    private boolean morphingNormal; //变形成正常状态中
    private float mFromCornerRadius=40;
    private float mToCornerRadius=100;
    private long mDuration=500;
    private int mProgress;
    private int mMaxProgress = 100;
    private int mStrokeColor;
    private int mStokeWidth = 0;
    private int mStokeWidthOut = 0;

    public enum STATE{
        PROGRESS,NORMAL,SUCCESS
    }

    private static final String TAG = "CProgressButton";


    public CProgressButton(Context context) {
        this(context,null);
    }

    public CProgressButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * config stroke width color
     * @param width
     * @param color
     */
    public void setStroke(int width, int color) {
        setProgressStroke(width);
        mStrokeColor = color;
    }

    private void setProgressStroke(int storewidth) {
        mStokeWidth = dip2px(getContext(),storewidth);
        mStokeWidthOut = dip2px(getContext(),storewidth);
    }

    /**
     * config bg & conor
     * @param drawable
     * @param defaultCornor
     */
    public void setbgDrawable(@DrawableRes int drawable, float defaultCornor){
        mBackground  = ContextCompat.getDrawable(getContext(), drawable);
        mFromCornerRadius = defaultCornor;
    }


    public STATE getState() {
        return mState;
    }

    public void setState(STATE state) {
        if(state == mState)
            return;
        if(getWidth() == 0 || morphingCircle || morphingNormal)
            return;
        this.mState = state;
        if(mState == STATE.PROGRESS){
            morph2Circle();
        }else if(mState == STATE.NORMAL){
            morph2Normal();
        }
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getWidth()  -getPaddingLeft() - getPaddingRight();
        mHeight = getHeight()  - getPaddingTop() - getPaddingRight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mState == STATE.NORMAL || (mState == STATE.PROGRESS && morphingCircle)) {
            setBound(0);
        }else{
            invalidate();
        }
    }



    private void setBound(int padding){
        if(mWidth == 0){
            mWidth = getWidth()  -getPaddingLeft() - getPaddingRight();
        }
        if(mHeight == 0){
            mHeight = getHeight()  - getPaddingTop() - getPaddingRight();
        }
        mBackground.setBounds(getPaddingLeft()+padding,getPaddingTop(),getPaddingLeft()+mWidth- padding,getPaddingTop()+mHeight);
        if (null != mProgressDrawable){
            mProgressDrawable.setBounds(getPaddingLeft()+padding,getPaddingTop(),getPaddingLeft()+mWidth- padding,getPaddingTop()+mHeight);
        }
        invalidate();
    }

    private void updateDrawableBounds(int w, int h) {
        // onDraw will translate the canvas so we draw starting at 0,0.
        // Subtract out padding for the purposes of the calculations below.
        w -= getPaddingRight() + getPaddingLeft();
        h -= getPaddingTop() + getPaddingBottom();

        int right = w;
        int bottom = h;
        int top = 0;
        int left = 0;

        if (mProgressDrawable != null) {
            // Maintain aspect ratio. Certain kinds of animated drawables
            // get very confused otherwise.
            final int intrinsicWidth = mProgressDrawable.getIntrinsicWidth();
            final int intrinsicHeight = mProgressDrawable.getIntrinsicHeight();
            final float intrinsicAspect = (float) intrinsicWidth / intrinsicHeight;
            final float boundAspect = (float) w / h;
            if (intrinsicAspect != boundAspect) {
                if (boundAspect > intrinsicAspect) {
                    // New width is larger. Make it smaller to match height.
                    final int width = (int) (h * intrinsicAspect);
                    left = (w - width) / 2;
                    right = left + width;
                } else {
                    // New height is larger. Make it smaller to match width.
                    final int height = (int) (w * (1 / intrinsicAspect));
                    top = (h - height) / 2;
                    bottom = top + height;
                }
            }
            mProgressDrawable.setBounds(left, top, right, bottom);
        }
    }

    public void setProgress(int progress){
        mProgress = progress;
        if(morphingCircle || morphingNormal)
            return;
        if(mProgress >= mMaxProgress){
            mProgress = mMaxProgress;
        }
        if(mProgress<=0){
            mProgress = 0;
        }
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mState == STATE.NORMAL || (mState == STATE.PROGRESS && morphingCircle)) {
            mBackground.draw(canvas);
        }
//        else if(mState == STATE.PROGRESS && !morphingCircle){
//            int offset = (mWidth - mHeight) / 2+getPaddingLeft();
//            int size = mHeight;
//            if (mProgressDrawable == null) {
//                int left = offset;
//                mProgressDrawable = new CProgressDrawable(getContext(),size, mStokeWidth,mStokeWidthOut, mStrokeColor);
//                mProgressDrawable.setBounds(left, getPaddingTop(),left+ mHeight, getPaddingTop()+mHeight);
//            }
//            final int saveCount = canvas.save();
//            canvas.translate(offset, getPaddingTop());
//            mProgressDrawable.setCallback(this);
//            mProgressDrawable.draw(canvas);
//            canvas.restoreToCount(saveCount);
//            if (mShouldStartAnimationDrawable){
//                mProgressDrawable.start();
//                mShouldStartAnimationDrawable = false;
//            }
//        }
        else
//        if(mState == STATE.SUCCESS)
        {
            int offset = (mWidth - mHeight) / 2+getPaddingLeft();
            int size = mHeight;
            if (mSuccessDrawable == null) {
                int left = offset;
                mSuccessDrawable = new SuccessDrawable(size, mStokeWidth);
                mSuccessDrawable.setBounds(left, getPaddingTop(),left+ mHeight, getPaddingTop()+mHeight);
            }
//            float sweepAngle = (360f / mMaxProgress) * mProgress;
//            mProgressDrawable.setSweepAngle(sweepAngle);

            final int saveCount = canvas.save();
            canvas.translate(offset, getPaddingTop());
            mSuccessDrawable.setCallback(this);
            mSuccessDrawable.draw(canvas);
            canvas.restoreToCount(saveCount);
            if (mShouldStartAnimationDrawable){
                mSuccessDrawable.animSuccess();
                mShouldStartAnimationDrawable = false;
            }
        }

//
    }

    private Paint createPaint(int stokenWidth) {
        Paint mPaint;
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
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

    private boolean mShouldStartAnimationDrawable = true;

    @Override
    public void invalidateDrawable(Drawable drawable) {
        if (verifyDrawable(drawable)) {
            final Rect dirty = drawable.getBounds();
            final int scrollX = getScrollX() + getPaddingLeft();
            final int scrollY = getScrollY() + getPaddingTop();

            invalidate(dirty.left + scrollX, dirty.top + scrollY,
                    dirty.right + scrollX, dirty.bottom + scrollY);
        } else {
            super.invalidateDrawable(drawable);
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mProgressDrawable  || who == mSuccessDrawable || super.verifyDrawable(who);
    }

    private void morph2Normal() {

        ObjectAnimator cornerAnimation =
                ObjectAnimator.ofFloat(mBackground, "cornerRadius", mToCornerRadius,mFromCornerRadius);

        final int start = (mWidth-mHeight)/2;
        ValueAnimator animator = ValueAnimator.ofInt(start,0);
        animator.setDuration(mDuration)
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int)animation.getAnimatedValue();
                        setBound(value);
                    }


                });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(mDuration);
        animatorSet.playTogether(animator, cornerAnimation);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                morphingNormal = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                morphingNormal = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                morphingNormal = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }


    private void morph2Circle(){

        ObjectAnimator cornerAnimation =
                ObjectAnimator.ofFloat(mBackground, "cornerRadius", mFromCornerRadius, mToCornerRadius);

        final int end =(mWidth-mHeight)/2;
        ValueAnimator animator = ValueAnimator.ofInt(0,end);
        animator.setDuration(mDuration)
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int)animation.getAnimatedValue();
                        setBound(value);
                    }
                });


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(mDuration);
        animatorSet.playTogether(animator, cornerAnimation);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                morphingCircle = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                morphingCircle = false;
                if (null != mAnimationEndListener){
                    mAnimationEndListener.animationEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                morphingCircle = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();

    }


    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.mProgress = mProgress;
        savedState.morphingNormal = morphingNormal;
        savedState.morphingCircle = morphingCircle;

        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState savedState = (SavedState) state;
            mProgress = savedState.mProgress;
            morphingNormal = savedState.morphingNormal;
            morphingCircle = savedState.morphingCircle;
            super.onRestoreInstanceState(savedState.getSuperState());
            setProgress(mProgress);
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    static class SavedState extends BaseSavedState {

        private boolean morphingNormal;
        private boolean morphingCircle;
        private int mProgress;

        public SavedState(Parcelable parcel) {
            super(parcel);
        }

        private SavedState(Parcel in) {
            super(in);
            mProgress = in.readInt();
            morphingCircle = in.readInt() == 1;
            morphingNormal = in.readInt() == 1;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mProgress);
            out.writeInt(morphingNormal ? 1 : 0);
            out.writeInt(morphingCircle ? 1 : 0);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public interface AnimationEndListener{
        void animationEnd();
    }

    private AnimationEndListener mAnimationEndListener;

    public void setAnimationEndListener(AnimationEndListener l){
        this.mAnimationEndListener = l;
    }




}
