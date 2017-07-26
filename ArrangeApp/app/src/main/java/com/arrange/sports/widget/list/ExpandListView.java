package com.arrange.sports.widget.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by kan212 on 17/7/26.
 */

public class ExpandListView extends RecordScrollListView{

    private OnScrollListener mSpecificOnScrollListener;
    private View mAnimHeaderView;
    private int mAnimHeaderViewHeight;

    private VelocityTracker mVelocityTracker = null;
    private int mVelocityY;

    public boolean isReLoadNoAnimHeader = true;

    public ExpandListView(Context context) {
        super(context);
        init(context);
    }

    public ExpandListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExpandListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {

    }

    /**
     * 设置带动画HeaderView，可根据当前HeaderView显示比例，上滑到顶或下滑到下一项
     *
     * @param animHeaderView
     */
    public boolean addAnimHeaderView(View animHeaderView, boolean isUsed) {
        if (null == animHeaderView) {
            return false;
        }
        mAnimHeaderView = animHeaderView;
        addHeaderView(animHeaderView);
        mAnimHeaderView.measure(0, 0);
        mAnimHeaderViewHeight = mAnimHeaderView.getMeasuredHeight();
        if (isUsed) {
            super.setOnScrollListener(mOnScrollListener);
        }
        return true;
    }

    /**
     * 设置当前listview搜索控件偏移
     */
    public void setOffset() {
//        setSelectionFromTop(0, -mAnimHeaderViewHeight);
        setSelectionFromTop(0, 0);
    }

    /**
     * 设置当前listview搜索控件偏移
     */
    public void setSmothOffset() {
        smoothScrollToPositionFromTop(0, 0);
    }

    private void addVelocityTrackerEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        mVelocityTracker.addMovement(event);
    }

    // 获得纵向的手速
    private int getTouchVelocityY() {
        if (mVelocityTracker == null)
            return 0;
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getYVelocity();
        if (0 == velocity) {
            velocity = mVelocityY;
        }
        return velocity;
    }

    /**
     * 获取当前纵向加速度
     *
     * @return
     */
    public int getVelocityY() {
        return mVelocityY;
    }

    private TouchDownCallback mTouchDownCallback;

    public void setTouchDownCallback (TouchDownCallback callback) {
        mTouchDownCallback = callback;
    }

    public interface TouchDownCallback {
        public void touchDownCallback(ExpandListView listView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        addVelocityTrackerEvent(ev);
        mVelocityY = getTouchVelocityY();
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (null != mAnimHeaderView) {
                if (null != mTouchDownCallback) {
                    mTouchDownCallback.touchDownCallback(this);
                }
            }
        }
        return super.onTouchEvent(ev);
    }

    private OnScrollListener mOnScrollListener = new OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (null != mSpecificOnScrollListener) {
                mSpecificOnScrollListener.onScrollStateChanged(view, scrollState);
            }
            View v = view.getChildAt(0);
            int top = (v == null) ? 0 : v.getTop();

            if (0 == getFirstVisiblePosition() && scrollState == SCROLL_STATE_IDLE) {
                if (mVelocityY > 0) {
                    // 下滑
                    if (top < 0 && top >= -mAnimHeaderViewHeight * 0.5f) {
                        // 上半部分
                        smoothScrollToPosition(0);
                    } else if (top > -mAnimHeaderViewHeight && top < -mAnimHeaderViewHeight * 0.5f) {
                        // 下半部分
                        setSelection(1);
                    }
                } else if (mVelocityY < 0) {
                    // 上滑，隐藏
                    setSelection(1);
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (null != mSpecificOnScrollListener) {
                mSpecificOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        }
    };

    /**
     * 设置滑动监听器
     *
     * @param l
     * @param isUsed true表示重写；false表示不重写
     */
    public void setOnScrollListener(OnScrollListener l, boolean isUsed) {
        mSpecificOnScrollListener = l;
        if (null == mAnimHeaderView || !isUsed) {
            super.setOnScrollListener(l);
        } else {
            super.setOnScrollListener(mOnScrollListener);
        }
    }

    public int getAnimHeaderViewHeight() {
        return mAnimHeaderViewHeight;
    }

}
