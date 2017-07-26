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
import com.arrange.sports.widget.PagerSlidingTabStrip;
import com.arrange.sports.widget.SubViewPager;
import com.arrange.sports.widget.ViewPager;
import com.base.app.BaseActivity;
import com.base.fragment.BaseFragment;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;


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


    OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

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
    }

}
