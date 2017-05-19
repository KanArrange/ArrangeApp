package com.arrange.app;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.arrange.R;
import com.arrange.rotat.app.FirstActivity;
import com.arrange.sports.app.FeedActivity;
import com.arrange.widget.app.WidgetActivity;

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
                FeedActivity.startThisActivity(this);
                break;
            case 1:
                WidgetActivity.startThisActivity(this);
                break;
            case 2:
                UploadGifActivity.startThisActivity(this);
                break;
            case 3:
                FirstActivity.startThisActivity(this);
                break;
        }
    }



}
