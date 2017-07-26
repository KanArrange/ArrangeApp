package com.arrange.sports.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.arrange.sports.bean.FeedTab;
import com.arrange.sports.fragment.FocusFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kan212 on 17/7/25.
 */

public class FocusParentAdapter extends FragmentStatePagerAdapter{

    private List<FeedTab> list = new ArrayList<>();

    public void setData(List<FeedTab> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public FocusParentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        FocusFragment focusFragment = new FocusFragment();
        return focusFragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).title;
    }
}
