package com.sketch.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.fragment.BaseSubFragment;
import com.sketch.R;

/**
 * Created by kan212 on 17/11/2.
 */

public class TaichiFragment extends BaseSubFragment {

    @Override
    public String getFragmentName() {
        return TaichiFragment.class.getName();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_paint_taichi,container,false);
        return mView;
    }
}
