package com.arrange.widget.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.arrange.R;
import com.arrange.widget.widget.sports.InteractPkView;

/**
 * Created by kan212 on 17/10/27.
 */

public class SportsInteractPkView extends Activity {

    private InteractPkView interact_pk_view;

    public static void stratThisActivity(WidgetActivity activity){
        activity.startActivity(new Intent(activity,SportsInteractPkView.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_interact_pk);
        interact_pk_view = (InteractPkView) findViewById(R.id.interact_pk_view);
//        interact_pk_view.invalidate();
    }
}
