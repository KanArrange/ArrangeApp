package com.arrange.nestscroll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.arrange.R;
import com.arrange.app.MainActivity;
import com.arrange.nestscroll.app.CoordinatorLayoutActivity;
import com.arrange.nestscroll.app.EventDispatchPlanActivity;
import com.arrange.nestscroll.app.NestingScrollActivity;

import java.util.ArrayList;

/**
 * Created by kan212 on 17/8/21.
 * 嵌套滑动的测试Activity
 */

public class NestScrollActivity extends AppCompatActivity{

    Toolbar mToolbar;
    ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_scroll);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        ArrayList<String> mData = new ArrayList<>();
        mData.add("原始事件分发方案");
        mData.add("使用NestScrollParent与NestScrollChild接口");
        mData.add("使用CoordinatorLayout");
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mData));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                if(position == 0){
                    intent = new Intent(NestScrollActivity.this, EventDispatchPlanActivity.class);
                } else if(position == 1){
                    intent = new Intent(NestScrollActivity.this, NestingScrollActivity.class);
                } else if(position == 2){
                    intent = new Intent(NestScrollActivity.this, CoordinatorLayoutActivity.class);
                }

                if(intent != null){
                    startActivity(intent);
                }
            }
        });
    }

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity,NestScrollActivity.class));
    }
}
