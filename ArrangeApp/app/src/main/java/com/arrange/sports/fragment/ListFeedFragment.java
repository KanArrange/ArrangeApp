package com.arrange.sports.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.arrange.R;
import com.arrange.sports.adapter.ListFeedAdapter;
import com.arrange.sports.imp.TopMarginReceiver;
import com.arrange.sports.widget.list.ExpandListView;
import com.base.fragment.BaseFragment;

/**
 * Created by kan212 on 17/7/26.
 */

public class ListFeedFragment extends BaseFragment implements AbsListView.OnScrollListener, TopMarginReceiver {

    public ExpandListView mListView;
    public View mView;
    private FeedListRefreshListener mFeedListRefreshListener;
    private boolean isScrollToFoot;
    ListFeedAdapter adapter;
    int mScrollHeight;
    public int mEmptyHeight = 0;// 空占位布局高度（同时为当前页最大偏移量）
    public int mIgnoreScrollHeight = 0;// 被过滤掉的滑动值（主要指：对于搜索框的纵向滚动忽略）
    private boolean isSelected = false;
    private int mID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.sports_fragment_feed,container,false);
        mListView = (ExpandListView) mView.findViewById(R.id.list);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] strs = getActivity().getResources().getStringArray(R.array.test_list);
        adapter = new ListFeedAdapter(getActivity());
        mListView.setAdapter(adapter);
        mListView.setOnScrollListener(this,false);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE){

        }else if (scrollState == SCROLL_STATE_TOUCH_SCROLL){

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 判断是否到达最后一项
        isScrollToFoot = firstVisibleItem + visibleItemCount == totalItemCount;
        // 计算当前ListView纵向滑动距离，并通过接口方法进行反馈
        View firstChild = mListView.getChildAt(0);
        if (null != firstChild && null != mListView && mListView instanceof ExpandListView && null != adapter) {
            ExpandListView expandListView = (ExpandListView) mListView;
            int headerScrollHeight = expandListView.getHeaderScrollHeight(firstVisibleItem);
            int adapterScrollHeight = adapter.getAdapterViewScrollHeight(firstVisibleItem, mListView.getHeaderViewsCount(), firstChild.getTop());
            mScrollHeight = headerScrollHeight + adapterScrollHeight;
            if (null != mFeedListRefreshListener) {
                // 在滑动区间在搜索高度区间，不做焦点图滑动
                int souSuoViewHeight = expandListView.getAnimHeaderViewHeight();
                int scrollHeightWithoutSouSuo = mScrollHeight - souSuoViewHeight;
                if (scrollHeightWithoutSouSuo < 0) {
                    mIgnoreScrollHeight = mScrollHeight;
                    scrollHeightWithoutSouSuo = 0;
                } else {
                    mIgnoreScrollHeight = souSuoViewHeight;
                }
                if (isSelected) {
                    mFeedListRefreshListener.feedListScroll(scrollHeightWithoutSouSuo);
                }
            }
        }
    }


    public void setFeedListRefreshListener(FeedListRefreshListener listener) {
        mFeedListRefreshListener = listener;
    }

    public int getHeaderLeftHeight() {
        int effectScrollHeight = mScrollHeight - getIgnoreScrollHeight();
        if (effectScrollHeight < 0) {
            effectScrollHeight = 0;
        }
        int result = mEmptyHeight - effectScrollHeight;
        if (result < 0) {
            result = 0;
        }
        return result;
    }

    /**
     * 获取被忽略的纵向滑动值
     *
     * @return
     */
    public int getIgnoreScrollHeight() {
        return mIgnoreScrollHeight;
    }

    @Override
    public void topMarginReceiver(String fromPagerId, String toPagerId, int maxMaigin, float precent) {
        if (null == mListView || fromPagerId.equals(toPagerId)) {
            return;
        }
        int scrollMaxMargin = 0;
        if (fromPagerId.equals(mID)) {// 判断是否为当前页
            scrollMaxMargin = (int) (-maxMaigin * precent);
        } else if (toPagerId.equals(mID)) {// 判断是否为切换页
            scrollMaxMargin = (int) (maxMaigin * (1 - precent));
        }
        //Fabric上的bug#4731，猜测是页面销毁时候的broadcast的回调，或者是页面创建流程和broadcast的时序问题，先增加容错
        if (null == mListView) {
            return;
        }
        FrameLayout.LayoutParams headerTopLp = (FrameLayout.LayoutParams) mListView.getLayoutParams();
        headerTopLp.setMargins(headerTopLp.leftMargin, scrollMaxMargin, headerTopLp.rightMargin, headerTopLp.bottomMargin);
        mListView.setLayoutParams(headerTopLp);
        topMarginReceiver(scrollMaxMargin);
    }

    /**
     * 其他关联布局topMargin适配
     *
     * @param scrollMaxMargin
     */
    protected void topMarginReceiver(int scrollMaxMargin) {

    }

    public interface FeedListRefreshListener{
        void feedListRefresh(int scrollY);
        void feedListScroll(int scrooY);
    }
}
