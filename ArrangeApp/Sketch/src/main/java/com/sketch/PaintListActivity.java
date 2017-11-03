package com.sketch;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.base.fragment.BaseSubFragment;
import com.sketch.fragment.TaichiFragment;

/**
 * Created by kan212 on 17/11/2.
 * 各种用绘制实现的自定义view
 */

public class PaintListActivity extends ListActivity{

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, PaintListActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] items = getResources().getStringArray(R.array.paint_list_activity);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position){
            case 0:
                start(new TaichiFragment());
                break;
        }
    }
    public void start(BaseSubFragment fragment){
        fragment.start(this);
    }
}
