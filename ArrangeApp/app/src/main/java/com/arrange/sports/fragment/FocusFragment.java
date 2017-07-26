package com.arrange.sports.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arrange.R;
import com.base.fragment.BaseFragment;

/**
 * Created by kan212 on 17/7/26.
 */

public class FocusFragment extends BaseFragment{



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.sports_fragment_focus,container,false);
        return mView;
    }
}
