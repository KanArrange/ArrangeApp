package com.datastruct;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.arrange.R;
import com.arrange.app.MainActivity;
import com.base.fragment.BaseSubFragment;

import com.datastruct.Fragment.SortFragment;

/**
 * Created by kan212 on 17/8/7.
 */

public class StructureActivity extends ListActivity{

    public static void startThisActivity(MainActivity activity) {
        activity.startActivity(new Intent(activity,StructureActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] items = getResources().getStringArray(R.array.data_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        switch (position){
            case 0:
                start(new SortFragment());
                break;
        }
    }

    public void start(BaseSubFragment fragment){
        fragment.start(this);
    }
}
