package com.arrange.sports.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.arrange.R;
import com.arrange.sports.adapter.ListFeedAdapter;
import com.arrange.sports.widget.list.ExpandListView;
import com.base.fragment.BaseFragment;

/**
 * Created by kan212 on 17/7/26.
 */

public class ListFeedFragment extends BaseFragment implements AbsListView.OnScrollListener{

    public ExpandListView mListView;
    public View mView;
    private FeedListRefreshListener mFeedListRefreshListener;
    private boolean isScrollToFoot;
    ListFeedAdapter adapter;

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
        isScrollToFoot = firstVisibleItem + visibleItemCount == totalItemCount;
        View firstChild = mListView.getChildAt(0);
        if (null != mListView && null != adapter && null != firstChild){
            int headerScrollHeight = mListView.getHeaderScrollHeight(firstVisibleItem);
            int adapterScrollHeight = adapter.getAdapterViewScrollHeight(firstVisibleItem, mListView.getHeaderViewsCount(), firstChild.getTop());

        }
    }


    public void setFeedListRefreshListener(FeedListRefreshListener listener) {
        mFeedListRefreshListener = listener;
    }


    public interface FeedListRefreshListener{
        void feedListRefresh(int scrollY);
        void feedListScroll(int scrooY);
    }
}
