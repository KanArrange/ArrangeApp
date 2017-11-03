package com.arrange.widget.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.arrange.R;
import com.arrange.app.MainActivity;

/**
 * Created by kan212 on 17/3/24.
 */

public class WidgetActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] items = getResources().getStringArray(R.array.wighet_list);

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
            case 4:
                CardFlipActivity.stratThisActivity(this);
                break;
            case 5:
                SportsInteractPkView.stratThisActivity(this);
                break;
            case 6:
                RegularPentagonActivity.stratThisActivity(this);
                break;
        }
    }

    public static void startThisActivity(MainActivity activity) {
        activity.startActivity(new Intent(activity, WidgetActivity.class));
    }
}
