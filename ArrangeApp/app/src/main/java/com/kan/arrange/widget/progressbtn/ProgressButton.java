package com.kan.arrange.widget.progressbtn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import android.widget.Button;

import com.kan.arrange.R;


/**
 * Created by kan212 on 17/3/8.
 */

public class ProgressButton extends Button {

    private String mIdleText = "签到加人气";
    private StrokeGradientDrawable background;
    private State mState;
    private float mCornerRadius;
    private boolean mIndeterminateProgressMode = true;
    private boolean mMorphingInProgress;
    private boolean mConfigurationChanged;
    private StateListDrawable mIdleStateDrawable;
    private int mStrokeWidth;

    private CircularAnimatedDrawable mAnimatedDrawable;
    private CircularProgressDrawable mProgressDrawable;

    private int mPaddingProgress = 0;
    private int mMaxProgress = 100;
    private int mProgress = 10;

    public enum State{
        PROGRESS,IDLE,COMPLETE,SUCCESS,ERROR
    }


    public ProgressButton(Context context) {
        super(context);
        init(context,null);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mState = State.IDLE;
        mStrokeWidth = (int) getContext().getResources().getDimension(R.dimen.pbt_stroke_width);
        mCornerRadius = getResources().getDimension(R.dimen.pbt_cornor_radius);
        setText(mIdleText);
        initIdleStateDrawable();
        setBackgroundCompat(background.getGradientDrawable());
    }

    public void setBackgroundCompat(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    private void initAttributes(Context context, AttributeSet attrs) {
    }


    private void initIdleStateDrawable() {
        if (null == background){
            background = createDrawable(getResources().getColor(R.color.pbt_background));
        }
    }

    private StrokeGradientDrawable createDrawable(int color) {
        GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.pbt_background).mutate();
        drawable.setColor(color);
        drawable.setCornerRadius(mCornerRadius);
        StrokeGradientDrawable strokeGradientDrawable = new StrokeGradientDrawable(drawable);
        strokeGradientDrawable.setStrokeColor(color);
        strokeGradientDrawable.setStrokeWidth(mStrokeWidth);
        return strokeGradientDrawable;
    }

    public void setState(State state){
        switch (state){
            case IDLE:
                morphToProgress();
                break;
            case PROGRESS:
                invalidate();
                break;
            case SUCCESS:

                break;
            case ERROR:

                break;
        }
    }

    private void morphToProgress() {
        setWidth(getWidth());
        setText("");

        MorphingAnimation animation = createProgressMorphing(mCornerRadius, getHeight(), getWidth(), getHeight());

        animation.setFromColor(getResources().getColor(R.color.pbt_background));
        animation.setToColor(getResources().getColor(R.color.pbt_background));

        animation.setFromStrokeColor(getResources().getColor(R.color.pbt_background));
        animation.setToStrokeColor(getResources().getColor(R.color.pbt_background));

        animation.setListener(mProgressStateListener);
        animation.start();
    }

    private OnAnimationEndListener mProgressStateListener = new OnAnimationEndListener() {
        @Override
        public void onAnimationEnd() {
            mMorphingInProgress = false;
            mState = State.PROGRESS;
            setState(mState);
        }
    };

    private MorphingAnimation createProgressMorphing(float fromCorner, float toCorner, int fromWidth, int toWidth) {
        mMorphingInProgress = true;


        MorphingAnimation animation = new MorphingAnimation(this, background);
        animation.setFromCornerRadius(fromCorner);
        animation.setToCornerRadius(toCorner);

        animation.setPadding(0);

        animation.setFromWidth(fromWidth);
        animation.setToWidth(toWidth);

        if (mConfigurationChanged) {
            animation.setDuration(MorphingAnimation.DURATION_INSTANT);
        } else {
            animation.setDuration(MorphingAnimation.DURATION_NORMAL);
        }
        mConfigurationChanged = false;

        return animation;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ( mState == State.PROGRESS) {
            if (mIndeterminateProgressMode) {
                drawIndeterminateProgress(canvas);
            } else {
                drawProgress(canvas);
            }
        }
    }

    private void drawProgress(Canvas canvas) {
        if (mAnimatedDrawable == null) {
            int offset = (getWidth() - getHeight()) / 2;
            mAnimatedDrawable = new CircularAnimatedDrawable(R.color.blue, mStrokeWidth);
            int left = offset + mPaddingProgress;
            int right = getWidth() - offset - mPaddingProgress;
            int bottom = getHeight() - mPaddingProgress;
            int top = mPaddingProgress;
            mAnimatedDrawable.setBounds(left, top, right, bottom);
            mAnimatedDrawable.setCallback(this);
            mAnimatedDrawable.start();
        } else {
            mAnimatedDrawable.draw(canvas);
        }
    }

    private void drawIndeterminateProgress(Canvas canvas) {
        if (mProgressDrawable == null) {
            int offset = (getWidth() - getHeight()) / 2;
            int size = getHeight() - mPaddingProgress * 2;
            mProgressDrawable = new CircularProgressDrawable(size, mStrokeWidth, R.color.blue);
            int left = offset + mPaddingProgress;
            mProgressDrawable.setBounds(left, mPaddingProgress, left, mPaddingProgress);
        }
        float sweepAngle = (360f / mMaxProgress) * mProgress;
        mProgressDrawable.setSweepAngle(sweepAngle);
        mProgressDrawable.draw(canvas);
    }


}
