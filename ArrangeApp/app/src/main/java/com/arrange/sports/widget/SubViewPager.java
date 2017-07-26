/**
 * Project Name:SinaSports
 * File Name:SubViewPager.java
 * Package Name:cn.com.sina.sports.widget
 * Date:2014-7-28下午6:41:14
 * Copyright (c) 2014, hushuan@staff.sina.com.cn All Rights Reserved.
 */

package com.arrange.sports.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * ClassName:SubViewPager <br/>
 * Function:解决上下滑动及左右滑动不冲突<br/>
 * Date: 2014-7-28 下午6:41:14 <br/>
 *
 * @author hushuan
 * @see
 * @since JDK 1.6
 */
public class SubViewPager extends ViewPager {
    /**
     * Position of the last motion event.
     */
    private float mLastMotionX, mLastMotionY;
    /**
     * ID of the active pointer. This is used to retain consistency during
     * drags/flings if multiple pointers are used.
     */
    protected int mActivePointerId = INVALID_POINTER;
    /**
     * Sentinel value for no current active pointer. Used by
     * {@link #mActivePointerId}.
     */
    private static final int INVALID_POINTER = -1;

    private boolean canPageScroll = true;

    private int mTouchSlop;

    public SubViewPager(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public SubViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public void setCanPageScroll(boolean canPageScroll) {
        this.canPageScroll = canPageScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = ev.getX();
                mLastMotionY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float x = ev.getX();
                final float y = ev.getY();
                final float xDistance = Math.abs(mLastMotionX - x);
                final float yDistance = Math.abs(mLastMotionY - y);
                if (canPageScroll && xDistance > yDistance && xDistance > mTouchSlop) {
                    return true;
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = ev.getX();
                mLastMotionY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float x = ev.getX();
                final float dx = x - mLastMotionX;
                if (canPageScroll && Math.abs(dx) > mTouchSlop) {
                    if (isFistItem()) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else if (isLastItem()) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else {
                    return false;
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(ev);
    }

    private boolean isFistItem() {
        return 0 == getCurrentItem();
    }

    private boolean isLastItem() {
        if (null == getAdapter()) {
            return true;
        }
        return getCurrentItem() >= (getAdapter().getCount() - 1);
    }

}
