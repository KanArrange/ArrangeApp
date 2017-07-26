package com.arrange.sports.widget.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by kan212 on 17/7/26.
 * 计算header和footer的高度
 */

public abstract class RecordScrollListView extends ListView{

    private ArrayList<HeightView> mMeasureHeaderList = new ArrayList<HeightView>();
    private ArrayList<HeightView> mMeasureFooterList = new ArrayList<HeightView>();

    public RecordScrollListView(Context context) {
        super(context);
    }

    public RecordScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecordScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private class HeightView {
        View view;
        int height;
    }

    private void doMeasure(View view, ArrayList<HeightView> list) {
        if (null != view) {
            view.measure(0, 0);
            int height = view.getMeasuredHeight();
            HeightView heightView = new HeightView();
            heightView.view = view;
            heightView.height = height;
            list.add(heightView);
        }
    }

    private void doMeasure(View view, int height, ArrayList<HeightView> list) {
        if (null != view) {
            HeightView heightView = new HeightView();
            heightView.view = view;
            heightView.height = height;
            list.add(heightView);
        }
    }

    private void removeMeasure(View view, ArrayList<HeightView> list) {
        if (null != view && null != list && list.size() > 0) {

            Iterator<HeightView> iterator = list.iterator();
            while (iterator.hasNext()) {
                HeightView heightView = iterator.next();
                if (heightView.view == view) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    @Override
    public void addHeaderView(View v) {
        super.addHeaderView(v);
        doMeasure(v, mMeasureHeaderList);
    }

    public void addHeaderView(View v, int height) {
        super.addHeaderView(v);
        doMeasure(v, height, mMeasureHeaderList);
    }

    @Override
    public boolean removeHeaderView(View v) {
        boolean result = false;
        try {
            result = super.removeHeaderView(v);
            if (result) {
                removeMeasure(v, mMeasureHeaderList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void addFooterView(View v, Object data, boolean isSelectable) {
        super.addFooterView(v, data, isSelectable);
        doMeasure(v, mMeasureFooterList);
    }

    @Override
    public boolean removeFooterView(View v) {
        boolean result = false;
        try {
            result = super.removeFooterView(v);
            if (result) {
                removeMeasure(v, mMeasureFooterList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取HeaderView的滑动距离
     *
     * @param firstVisibleItem
     * @return
     */
    public int getHeaderScrollHeight(int firstVisibleItem) {
        int result = 0;
        for (int i = 0, headerCount = getHeaderViewsCount(), size = mMeasureHeaderList.size(); i < firstVisibleItem && i < headerCount && i < size; i++) {
            HeightView heightView = mMeasureHeaderList.get(i);
            if (null != heightView) {
                result += heightView.height;
            }
        }

        return result;
    }

    /**
     * 获取FooterView的滑动距离
     *
     * @param firstVisibleItem
     * @return
     */
    public int getFooterScrollHeight(int firstVisibleItem) {
        int result = 0;

        return result;
    }
}
