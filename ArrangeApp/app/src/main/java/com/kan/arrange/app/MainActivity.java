package com.kan.arrange.app;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kan.arrange.R;

public class MainActivity extends ListActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] items = getResources().getStringArray(R.array.sample_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        switch (position) {
            case 0:
                CProgressActivity.startThisActivity(this);
                break;
            case 1:
                ProgressBtnActivity.startThisActivity(this);
                break;
            case 2:
                IndicatorActivity.stratThisActivity(this);
                break;
            case 3:
                OvalProgressActivity.stratThisActivity(this);
                break;
        }
    }
}
