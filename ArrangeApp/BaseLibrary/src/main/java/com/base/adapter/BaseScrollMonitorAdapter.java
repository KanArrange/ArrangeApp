package com.base.adapter;

import android.content.Context;

/**
 * Created by kan212 on 17/7/26.
 */

public abstract class BaseScrollMonitorAdapter<T> extends BaseObjectAdapter<T>{


    public BaseScrollMonitorAdapter(Context context) {
        super(context);
    }

    public abstract int getAdapterViewScrollHeight(int firstVisibleItem, int headerViewsCount, int top);
}
