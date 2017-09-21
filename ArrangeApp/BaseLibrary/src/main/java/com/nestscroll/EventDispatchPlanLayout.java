package com.nestscroll;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.base.R;
import com.base.utils.Utils;

/**
 * Created by kan212 on 17/8/21.
 */

public class EventDispatchPlanLayout extends ViewGroup {

    private static final int INVALID_POINTER = -1;

    private int mHeaderViewId = 0;
    private View mHeaderView;
    private int mTargetViewId = 0;
    private View mTargetView;
    private ITargetView mTarget;


    private int mHeaderInitOffset;
    private int mHeaderCurrentOffset;

    private int mTargetInitOffset;
    private int mTargetCurrentOffset;

    private int mMaxVelocity;
    private int mActivePointerId = INVALID_POINTER;
    private int mTouchSlop;

    private Scroller mScroller;
    private boolean mIsDragging;
    private float mInitialDownY;
    private float mInitialMotionY;


    public EventDispatchPlanLayout(Context context) {
        super(context);
    }

    public EventDispatchPlanLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EventDispatchPlanLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EventDispatchPlanLayout, 0, 0);
        mHeaderViewId = array.getResourceId(R.styleable.EventDispatchPlanLayout_header_view, 0);
        mTargetViewId = array.getResourceId(R.styleable.EventDispatchPlanLayout_target_view, 0);

        mHeaderInitOffset = array.getDimensionPixelSize(R.styleable.
                EventDispatchPlanLayout_header_init_offset, Utils.dp2px(getContext(), 20));
        mTargetInitOffset = array.getDimensionPixelSize(R.styleable.
                EventDispatchPlanLayout_target_init_offset, Utils.dp2px(getContext(), 40));
        mHeaderCurrentOffset = mHeaderInitOffset;
        mTargetCurrentOffset = mTargetInitOffset;
        array.recycle();

        ViewCompat.setChildrenDrawingOrderEnabled(this, true);

        final ViewConfiguration vc = ViewConfiguration.get(getContext());
        mMaxVelocity = vc.getScaledMaximumFlingVelocity();
        mTouchSlop = Utils.px2dp(context, vc.getScaledTouchSlop()); //系统的值是8dp,太大了。。。

        mScroller = new Scroller(getContext());
        mScroller.setFriction(0.98f);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (0 != mHeaderViewId){
            mHeaderView = findViewById(mHeaderViewId);
        }
        if (0 != mTargetViewId){
            mTargetView = findViewById(mTargetViewId);
            ensureTarget();
        }
    }

    public interface ITargetView {
        boolean canChildScrollUp();

        void fling(float vy);
    }

    private void ensureTarget() {
        if (mTargetView instanceof ITargetView){
            mTarget = (ITargetView) mTargetView;
        }else {
            throw new RuntimeException("TargetView should implement interface ITargetView");
        }
    }

    private void ensureHeaderViewAndScrollView(){
        if (null != mHeaderView && null != mTargetView){
            return;
        }
        if (null == mHeaderView && null == mTargetView && getChildCount() >= 2){
            mHeaderView = getChildAt(0);
            mTargetView = getChildAt(1);
            ensureTarget();
            return;
        }
        throw new RuntimeException("please ensure headerView and scrollView");
    }


    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        ensureHeaderViewAndScrollView();
        int headerIndex = indexOfChild(mHeaderView);
        int scrollIndex = indexOfChild(mTargetView);
        if (headerIndex < scrollIndex) {
            return i;
        }
        if (headerIndex == i) {
            return scrollIndex;
        } else if (scrollIndex == i) {
            return headerIndex;
        }
        return i;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ensureHeaderViewAndScrollView();
        int scrollMeasureWidthSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth() -getPaddingLeft() - getPaddingRight(),
                MeasureSpec.EXACTLY);
        int scrollMeasureHeightSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(),
                MeasureSpec.EXACTLY);
        mTargetView.measure(scrollMeasureWidthSpec,scrollMeasureHeightSpec);
        measureChild(mHeaderView,widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        if( 0 == getChildCount()){
            return;
        }
        ensureHeaderViewAndScrollView();
        final int childLeft =getPaddingLeft();
        final int childTop = getPaddingTop();
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
        mTargetView.layout(childLeft,childTop + mTargetCurrentOffset,
                childLeft + childWidth,childTop + childHeight + mTargetCurrentOffset);
        int refreshWidth = mHeaderView.getMeasuredWidth();
        int refreshHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.layout((width - refreshWidth)/2, mHeaderCurrentOffset,
                (width + refreshWidth) /2,mHeaderCurrentOffset + refreshHeight);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureHeaderViewAndScrollView();
        final int action = MotionEventCompat.getActionMasked(ev);
        if(!isEnabled() || mTarget.canChildScrollUp()){
            return false;
        }
        int pointerIndex;
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                mIsDragging = false;
                pointerIndex = ev.getPointerId(mActivePointerId);
                if (pointerIndex < 0){
                    return false;
                }
                mInitialDownY = ev.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0){
                    return false;
                }
                final float y = ev.getY(pointerIndex);
                startDragging(y);
                break;
            case MotionEvent.ACTION_POINTER_UP:

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    private void startDragging(float y) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }



}
