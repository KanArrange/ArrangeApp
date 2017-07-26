package com.arrange.sports.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arrange.R;
import com.arrange.sports.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PagerSlidingTabStrip extends HorizontalScrollView {

    public interface IconTabProvider {
        int getPageIconResId(int position);
    }

    // @formatter:off
    private static final int[] ATTRS = new int[]{
            android.R.attr.textSize,
            android.R.attr.textColor
    };
    // @formatter:on

    private LinearLayout.LayoutParams defaultTabLayoutParams;
    private LinearLayout.LayoutParams expandedTabLayoutParams;

    private final PageListener pageListener = new PageListener();
    public OnPageChangeListener delegatePageListener;

    private LinearLayout tabsContainer;
    private ViewPager pager;

    private int tabCount;

    /**
     * 切换之前的位置
     */
    private int prePosition = 0;

    /**
     * 是否页面切换操作
     */
    private boolean isPageSelected = false;

    private int currentPosition = 0;
    private float currentPositionOffset = 0f;

    private Paint rectPaint;
    private Paint dividerPaint;

    private int indicatorColor = 0Xffda1d09;// 0xFF666666  ;
    private int underlineColor = 0x1A000000;
    private int dividerColor = 0x1A000000;

    private boolean shouldExpand = false;//true:屏幕宽度平均分
    private boolean textAllCaps = true;

    private int scrollOffset = 115;//滚动偏移 要使PagerSlidingTabStrip的滚动速度小于indicator的滚动速度 //默认52
    private int indicatorHeight = /*8*/2;
    private int underlineHeight = 2;
    private int dividerPadding = 12;//分割线的上下的padding
    private int tabPadding = /*24*/12;//这个是tab的左右padding。
    private int dividerWidth = 1;//分割线宽度
    private int indicatorOffset = 15;
    private int cursorVerticalOffset;

    private int tabTextSize = 16;
    private int indicatorTextSize = 16;
    private int tabTextColor = 0xFF404040;/*0xFF666666 */
    private Typeface tabTypeface = null;
    private int tabTypefaceStyle = Typeface.NORMAL;
    private boolean isIndicatorTextBlod = false, isTabTextBlod = false;

    private int lastScrollX = 0;

    private int tabBackgroundResId = android.R.color.transparent;

    private Locale locale;

    public PagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setFillViewport(true);
        setWillNotDraw(false);

        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(tabsContainer);
        setBackgroundColor(0x00000000);// 0xfff1f1f1

        DisplayMetrics dm = getResources().getDisplayMetrics();

        scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
        indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
        underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
        dividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPadding, dm);
        tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
        dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
        tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);
        indicatorTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, indicatorTextSize, dm);

        // get system attrs (android:textSize and android:textColor)

        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);

//		tabTextSize = a.getDimensionPixelSize(0, tabTextSize);
//		tabTextColor = a.getColor(1, tabTextColor);

        a.recycle();

        // get custom attrs

        a = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTabStrip);

        indicatorColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsIndicatorColor, indicatorColor);
        underlineColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsUnderlineColor, underlineColor);
        dividerColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsDividerColor, dividerColor);
        indicatorHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsIndicatorHeight, indicatorHeight);
        underlineHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsUnderlineHeight, underlineHeight);
        dividerPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsDividerPadding, dividerPadding);
        tabPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsTabPaddingLeftRight, tabPadding);
        tabBackgroundResId = a.getResourceId(R.styleable.PagerSlidingTabStrip_pstsTabBackground, tabBackgroundResId);
        shouldExpand = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsShouldExpand, shouldExpand);
        scrollOffset = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsScrollOffset, scrollOffset);
        textAllCaps = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsTextAllCaps, textAllCaps);

        a.recycle();

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Style.FILL);

        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setStrokeWidth(dividerWidth);

        defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

        if (locale == null) {
            locale = getResources().getConfiguration().locale;
        }
    }


    public void setViewPager(ViewPager pager) {
        this.pager = pager;

        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        pager.setOnPageChangeListener(pageListener);

        notifyDataSetChanged();
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.delegatePageListener = listener;
    }

    public void notifyDataSetChanged() {
        if (null == pager || null == pager.getAdapter() || pager.getAdapter().getCount() == 0) {
            return;
        }
        tabsContainer.removeAllViews();

        tabCount = pager.getAdapter().getCount();


        for (int i = 0; i < tabCount; i++) {
            if (pager.getAdapter() instanceof IconTabProvider) {
                addIconTab(i, ((IconTabProvider) pager.getAdapter()).getPageIconResId(i));
            } else {
                addTextTab(i, pager.getAdapter().getPageTitle(i).toString());
            }

        }
        /**记录第一次显示位置 作为上一次位置*/
        prePosition = pager.getCurrentItem();
        updateTabStyles();

        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {

                if (Build.VERSION.SDK_INT < 16) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                currentPosition = pager.getCurrentItem();
                scrollToChild(currentPosition, 0);
            }
        });

    }

    private void addTextTab(final int position, String title) {

        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setSingleLine();

        addTab(position, tab);
    }

    private void addIconTab(final int position, int resId) {

        ImageButton tab = new ImageButton(getContext());
        tab.setImageResource(resId);

        addTab(position, tab);

    }

    private void addTab(final int position, View tab) {
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mTabClickListener) {
                    mTabClickListener.onTabClicked(v, pager.getCurrentItem(), position);
                }
                pager.setCurrentItem(position);
            }
        });

        tab.setPadding(tabPadding, 0, tabPadding, 0);
        tabsContainer.addView(tab, position, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
    }

    private void updateTabStyles() {
        for (int i = 0; i < tabCount; i++) {
            if (i == pager.getCurrentItem()) {
                updateTextViewStyle(i, indicatorColor, indicatorTextSize, isIndicatorTextBlod);//初始化
            } else {
                updateTextViewStyle(i, tabTextColor, tabTextSize, isTabTextBlod);
            }
        }
    }

    private void updateTextViewStyle(int i, int tabTc, int tabTs, boolean isBold) {
        if (i < 0 || i > tabCount) {
            return;
        }
        View v = tabsContainer.getChildAt(i);
        if (v == null) {
            return;
        }

        v.setBackgroundResource(tabBackgroundResId);

        if (v instanceof TextView) {

            TextView tab = (TextView) v;

            tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTs);
            tab.setTypeface(tabTypeface, tabTypefaceStyle);
            tab.setTextColor(tabTc);
            if (isBold) {
                tab.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
            } else {
                tab.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//不加粗
            }

            if (textAllCaps) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    tab.setAllCaps(true);
                } else {
                    tab.setText(tab.getText().toString().toUpperCase(locale));
                }
            }
        }




    }

    private void scrollToChild(int position, int offset) {
        if (tabCount == 0) {
            return;
        }

        int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

        if (position > 0 || offset > 0) {
            newScrollX -= scrollOffset;
        }

        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || tabCount == 0) {
            return;
        }

        final int height = getHeight() - cursorVerticalOffset;

        // draw indicator line

        rectPaint.setColor(indicatorColor);

        // default: line below current tab
        View currentTab = tabsContainer.getChildAt(currentPosition);
        float lineLeft = currentTab.getLeft() + getPaddingLeft();
        float lineRight = currentTab.getRight() + getPaddingLeft();

        // if there is an offset, start interpolating left and right coordinates between current and next tab
        if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {

            View nextTab = tabsContainer.getChildAt(currentPosition + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();

            lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
            lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
        }

//		canvas.drawRect(lineLeft, height - indicatorHeight, lineRight, height, rectPaint);
        canvas.drawRect(lineLeft + indicatorOffset, height - indicatorHeight, lineRight - indicatorOffset, height, rectPaint);

        // draw underline

//		rectPaint.setColor(underlineColor);
//		canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, rectPaint);

        // draw divider

//		dividerPaint.setColor(dividerColor);
//		for (int i = 0; i < tabCount - 1; i++) {
//			View tab = tabsContainer.getChildAt(i);
//			canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(), height - dividerPadding, dividerPaint);
//		}
    }

    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentPosition = position;
            currentPositionOffset = positionOffset;
            if (position >= 1 && position < tabsContainer.getChildCount()) {
                View view = tabsContainer.getChildAt(position);
                if (null != view) {
                    scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));
                }
            }
            invalidate();
            if (delegatePageListener != null) {
                delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(pager.getCurrentItem(), 0);
            }
            if (delegatePageListener != null) {
                delegatePageListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (delegatePageListener != null) {
                delegatePageListener.onPageSelected(position);
            }

            isPageSelected = true;
            /**更新tab显示*/
            updateTabStyles();
            /**设置上一次位置为当前位置*/
            prePosition = pager.getCurrentItem();
            notifySelectedObserver(position, pager.getAdapter().getPageTitle(position).toString());
        }

    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
    }

    public void setIndicatorColorResource(int resId) {
        this.indicatorColor = getResources().getColor(resId);
        invalidate();
    }

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public void setIndicatorHeight(int indicatorLineHeightPx) {
        this.indicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        this.underlineColor = getResources().getColor(resId);
        invalidate();
    }

    public int getUnderlineColor() {
        return underlineColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        invalidate();
    }

    public void setDividerColorResource(int resId) {
        this.dividerColor = getResources().getColor(resId);
        invalidate();
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setUnderlineHeight(int underlineHeightPx) {
        this.underlineHeight = underlineHeightPx;
        invalidate();
    }

    public int getUnderlineHeight() {
        return underlineHeight;
    }

    public void setDividerPadding(int dividerPaddingPx) {
        this.dividerPadding = dividerPaddingPx;
        invalidate();
    }

    public int getDividerPadding() {
        return dividerPadding;
    }

    public void setScrollOffset(int scrollOffsetPx) {
        this.scrollOffset = scrollOffsetPx;
        invalidate();
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void setShouldExpand(boolean shouldExpand) {
        this.shouldExpand = shouldExpand;
        requestLayout();
    }

    public boolean getShouldExpand() {
        return shouldExpand;
    }

    public boolean isTextAllCaps() {
        return textAllCaps;
    }

    public void setAllCaps(boolean textAllCaps) {
        this.textAllCaps = textAllCaps;
    }

    public void setTextSize(int textSizePx) {
        this.tabTextSize = textSizePx;
        updateTabStyles();
    }

    public int getTextSize() {
        return tabTextSize;
    }

    public void setTextColor(int textColor) {
        this.tabTextColor = textColor;
        updateTabStyles();
    }

    public void setTextColorResource(int resId) {
        this.tabTextColor = getResources().getColor(resId);
        updateTabStyles();
    }

    public int getTextColor() {
        return tabTextColor;
    }

    public void setTypeface(Typeface typeface, int style) {
        this.tabTypeface = typeface;
        this.tabTypefaceStyle = style;
        updateTabStyles();
    }

    public void setTabBackground(int resId) {
        this.tabBackgroundResId = resId;
    }

    public int getTabBackground() {
        return tabBackgroundResId;
    }

    public void setTabPaddingLeftRight(int paddingPx) {
        this.tabPadding = paddingPx;
        updateTabStyles();
    }

    public int getTabPaddingLeftRight() {
        return tabPadding;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPosition = savedState.currentPosition;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = currentPosition;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
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

    private OnTabClickListener mTabClickListener;

    public void setOnTabClickListener(OnTabClickListener listener) {
        mTabClickListener = listener;
    }

    public interface OnTabClickListener {
        public void onTabClicked(View v, int currPosition, int clickPosition);
    }

    public void setTabTextInfo(int tabTextColor, int indicatorTextColor, int tabTextSize,
                               int indicatorTextSize, boolean isIndicatorTextBlod, boolean isTabTextBlod, int cursorVerticalOffset) {
        this.tabTextColor = tabTextColor;
        this.tabTextSize = tabTextSize;
        this.indicatorColor = indicatorTextColor;
        this.indicatorTextSize = indicatorTextSize;
        this.isIndicatorTextBlod = isIndicatorTextBlod;
        this.isTabTextBlod = isTabTextBlod;
        DisplayMetrics dm = getResources().getDisplayMetrics();
        this.tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);
        this.indicatorTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, indicatorTextSize, dm);
        this.cursorVerticalOffset = cursorVerticalOffset;
        notifyDataSetChanged();
        invalidate();
    }

    public void setTabTextInfo(int tabTextColor, int indicatorTextColor, int tabTextSize,
                               int indicatorTextSize, boolean isIndicatorTextBlod, boolean isTabTextBlod, int cursorVerticalOffset, int indicatorOffset) {
        this.indicatorOffset = indicatorOffset;
        setTabTextInfo(tabTextColor, indicatorTextColor, tabTextSize, indicatorTextSize, isIndicatorTextBlod, isTabTextBlod, cursorVerticalOffset);
    }

    /**
     * 选中观察者接口
     */
    public interface PagerSelectedObserver {

        /**
         * 配置当前观察者的tabName
         *
         * @param tabName
         */
        public void setTabName(String tabName);

        /**
         * 观察者回馈接口
         *
         * @param viewPager
         * @param tag       当前PagerSlidingTabStrip对象的标签
         * @param position  当前选中tab的索引
         * @param tabName   当前选中tab的名称
         */
        public void pagerSelectedTab(ViewPager viewPager, Object tag, int position, String tabName);
    }

    private List<PagerSelectedObserver> observers = new ArrayList<PagerSelectedObserver>();

    /**
     * 注册观察者
     *
     * @param observer
     * @return
     */
    public boolean addSelectedObserver(PagerSelectedObserver observer, int position) {
        if (null != observer) {
            observer.setTabName(pager.getAdapter().getPageTitle(position).toString());
            return observers.add(observer);
        }
        return false;
    }

    /**
     * 注销观察者
     *
     * @param observer
     * @return
     */
    public boolean removeSelectedObserver(PagerSelectedObserver observer) {
        if (null != observer) {
            return observers.remove(observer);
        }
        return false;
    }

    /**
     * 通知
     *
     * @param position
     * @param tabName
     */
    public void notifySelectedObserver(int position, String tabName) {
        for (int i = 0, s = observers.size(); i < s; i++) {
            PagerSelectedObserver observer = observers.get(i);
            if (null != observer) {
                observer.pagerSelectedTab(pager, getTag(), position, tabName);
            }
        }
    }


}
