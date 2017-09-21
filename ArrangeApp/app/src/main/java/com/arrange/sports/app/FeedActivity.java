package com.arrange.sports.app;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.RelativeLayout;
import android.support.v4.view.ViewPager.OnPageChangeListener;


import com.arrange.R;
import com.arrange.app.MainActivity;
import com.arrange.sports.adapter.FocusParentAdapter;
import com.arrange.sports.bean.FeedTab;
import com.arrange.sports.fragment.ListFeedFragment;
import com.arrange.sports.imp.TopMarginBroadcast;
import com.arrange.sports.widget.PagerSlidingTabStrip;
import com.arrange.sports.widget.SubViewPager;
import com.arrange.sports.widget.ViewPager;
import com.base.app.BaseActivity;
import com.base.fragment.BaseFragment;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by kan212 on 17/3/24.
 */

public class FeedActivity extends BaseActivity implements ListFeedFragment.FeedListRefreshListener {

    ViewPager pager_view;
    RelativeLayout layout_head;
    SubViewPager mFocusViewPager;
    PagerSlidingTabStrip pager_tabs;
    View belowLayout;
    FocusParentAdapter mFocusParentAdapter;
    ListPageAdapter mListPageAdapter;

    Map<String, ListFeedFragment> mPagerSelectedBroad = new HashMap<String, ListFeedFragment>();// 用于通知当前tab选中状态
    TopMarginBroadcast mTopMarginBroadcast = new TopMarginBroadcast();


    public static void startThisActivity(MainActivity activity) {
        activity.startActivity(new Intent(activity,FeedActivity.class));
    }


    @Override
    public void initView() {
        setContentView(R.layout.sports_activity_feed);
        pager_view = (ViewPager) findViewById(R.id.pager_view);
        layout_head = (RelativeLayout) findViewById(R.id.layout_head);
        mFocusViewPager = (SubViewPager) findViewById(R.id.viewpager);
        pager_tabs = (PagerSlidingTabStrip) findViewById(R.id.pager_tabs);
        belowLayout = findViewById(R.id.below);

        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int height = widthPixels / 2;
        mFocusViewPager.getLayoutParams().height = height;
        layout_head.getLayoutParams().height = height;
        mFocusViewPager.setY(0);
        ViewHelper.setAlpha(belowLayout, 0.0f);

    }

    @Override
    public void initData() {
        mFocusViewPager.setCanPageScroll(false);
        mFocusViewPager.setOffscreenPageLimit(FeedTab.getDefaultList().size());
        mFocusParentAdapter = new FocusParentAdapter(getSupportFragmentManager());
        mFocusParentAdapter.setData(FeedTab.getDefaultList());
        mFocusViewPager.setAdapter(mFocusParentAdapter);

        mListPageAdapter = new ListPageAdapter(getSupportFragmentManager());
        mListPageAdapter.setData(FeedTab.getDefaultList());
        pager_view.setAdapter(mListPageAdapter);

        pager_tabs.setOnPageChangeListener(mOnPageChangeListener);
        pager_tabs.setOnTabClickListener(mOnTabClickListener);
        pager_tabs.setViewPager(pager_view);
        pager_tabs.setTabTextInfo(0x80ffffff, 0xffffffff, 16, 18, true, false, 20);
    }


    private boolean isViewPagerScrolling = false;// 标记ViewPager是否在滑动
    private boolean isPagerSelected = false;// 回调方法onPageSelected是否被调用
    private int mCurrPosition = 0;// 当前选中位置
    private int mToPosition = 0;// 将要选择切换到的位置

    OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            float scrollingPosition = position + positionOffset;
            if (mToPosition != scrollingPosition) {
                // 滑动中
                if (scrollingPosition > mCurrPosition) {
                    int toPosition = mToPosition == mCurrPosition ? (mCurrPosition + 1) : mToPosition;
                    float offset = (scrollingPosition - mCurrPosition) / (toPosition - mCurrPosition);
                    // 向右侧显示滑动
                    scrollFocusImage(mCurrPosition, toPosition, offset);
                } else if (scrollingPosition < mCurrPosition) {
                    // 向左侧显示滑动
                    int toPosition = mToPosition == mCurrPosition ? (mCurrPosition - 1) : mToPosition;
                    float offset = (scrollingPosition - toPosition) / (mCurrPosition - toPosition);
                    scrollFocusImage(mCurrPosition, toPosition, 1 - offset);
                }

            } else {
                // 滑动结束
                if (mCurrPosition > mToPosition) {
                    // 显示左侧项成功
                    scrollFocusImage(mCurrPosition, mToPosition, 1.0f);
                } else if (mCurrPosition < mToPosition) {
                    // 显示右侧项成功
                    scrollFocusImage(mCurrPosition, mToPosition, 1.0f);
                }
                mCurrPosition = mToPosition;

            }

            // 顶部焦点图横向切换
            mFocusViewPager.scrollTo((int) (mFocusViewPager.getWidth() * scrollingPosition), 0);

        }

        @Override
        public void onPageSelected(int position) {
            isPagerSelected = true;
            mToPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };

    /**
     * 焦点图滑动处理（根据新闻ViewPager的横向滑动来控制触发）
     *
     * @param currPosition   当前选中项索引
     * @param targetPosition 目标项索引
     * @param heightOffset   当前滑动偏移比例
     */
    private void scrollFocusImage(int currPosition, int targetPosition, float heightOffset) {
        if (heightOffset >= 1.0f) {
            heightOffset = 1.0f;
        }
        if (heightOffset <= -1.0f) {
            heightOffset = -1.0f;
        }
        int tabHeight = pager_tabs.getHeight();// 新闻Tab栏高度
        int headerHeight = layout_head.getHeight();// 整个焦点图高度
        int maxHeaderMargin = headerHeight - tabHeight;// 获取焦点图最大滑动值，用于Tab栏透明度处理
        int currPagerHeaderLeftHeight = 0;// 当前页焦点图剩余显示高度（包含新闻Tab）
        int targetPagerHeaderLeftHeight = 0;// 切换页焦点图图剩余显示高度（包含新闻Tab）
        Iterator<Map.Entry<String, ListFeedFragment>> iterator = mPagerSelectedBroad.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ListFeedFragment> item = iterator.next();
            String newsId = item.getKey();
            ListFeedFragment fragment = item.getValue();
            if (newsId.equals(mListPageAdapter.getID(currPosition))) {
                currPagerHeaderLeftHeight = fragment.getHeaderLeftHeight();
            } else if (newsId.equals(mListPageAdapter.getID(targetPosition))) {
                targetPagerHeaderLeftHeight = fragment.getHeaderLeftHeight();
            }
        }
        int maxScrollHeight = currPagerHeaderLeftHeight - targetPagerHeaderLeftHeight;// 当前滑动Margin最大区间，带方向，用于Tab间切换处理
        float currOffset = maxScrollHeight * heightOffset;// 当前纵向偏移
        int currHeaderMargin = maxHeaderMargin - currPagerHeaderLeftHeight;// 当前焦点图的TopMargin值
        if (currHeaderMargin < 0) {
            currHeaderMargin = 0;
        }
        int newHeaderMargin = (int) (currHeaderMargin + currOffset);
        if (newHeaderMargin < 0) {// 规范焦点图Margin最小值
            newHeaderMargin = 0;
        }
        if (newHeaderMargin > maxHeaderMargin) {// 规范焦点图Margin最da值
            newHeaderMargin = maxHeaderMargin;
        }

        // 焦点图滑动处理
        layout_head.scrollTo(0, newHeaderMargin);
        // 当前Tab和nextTab滑动处理，监听器通知注册页面当前焦点图滚动情况
        String fromPagerId = mListPageAdapter.getID(currPosition);
        String toPagerId = mListPageAdapter.getID(targetPosition);
        mTopMarginBroadcast.broadcast(fromPagerId, toPagerId, maxScrollHeight, heightOffset);

        // Tab透明度处理
        int belowLayoutHeight = belowLayout.getHeight();
        int tabLayoutHeight = pager_tabs.getHeight();
        int alphaMaxEffectHeight = belowLayoutHeight - tabLayoutHeight;// 透明度控制有效最大有效值
        if (0 == alphaMaxEffectHeight) {
            return;
        }
        int alphaHeight = newHeaderMargin - (maxHeaderMargin - alphaMaxEffectHeight);// 当前透明度作用偏移大小（用于控制透明度大小）
        float alpha = (float) alphaHeight / (float) alphaMaxEffectHeight;
        if (alpha >= 1.0f) {
            ViewHelper.setAlpha(belowLayout, 1.0f);
        } else {
            ViewHelper.setAlpha(belowLayout, alpha);
        }
    }

    PagerSlidingTabStrip.OnTabClickListener mOnTabClickListener = new PagerSlidingTabStrip.OnTabClickListener(){

        @Override
        public void onTabClicked(View v, int currPosition, int clickPosition) {

        }
    };

    @Override
    public void feedListRefresh(int scrollY) {

    }

    @Override
    public void feedListScroll(int scrooY) {

    }

    public class ListPageAdapter extends FragmentStatePagerAdapter{

        private List<FeedTab> list = new ArrayList<>();

        public void setData(List<FeedTab> list){
            this.list = list;
            notifyDataSetChanged();
        }

        public ListPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ListFeedFragment fragment = new ListFeedFragment();
            fragment.setFeedListRefreshListener(FeedActivity.this);
            mTopMarginBroadcast.registerTopMarginReceiver(fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            return null == list ? 0 : list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).title;
        }

        public String getID(int position) {
            return list.get(position).id;
        }
    }

}
