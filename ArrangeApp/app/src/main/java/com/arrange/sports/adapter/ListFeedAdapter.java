package com.arrange.sports.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.base.adapter.BaseScrollMonitorAdapter;

/**
 * Created by kan212 on 17/7/26.
 */

public class ListFeedAdapter extends BaseScrollMonitorAdapter{


    public ListFeedAdapter(Context context) {
        super(context);
    }

    @Override
    public int getAdapterViewScrollHeight(int firstVisibleItem, int headerViewsCount, int top) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
