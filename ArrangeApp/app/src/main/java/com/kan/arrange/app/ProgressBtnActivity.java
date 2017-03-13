package com.kan.arrange.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.kan.arrange.R;
import com.kan.arrange.widget.progressbtn.ProgressButton;

/**
 * Created by kan212 on 17/3/8.
 */

public class ProgressBtnActivity extends Activity implements View.OnClickListener{

    private ProgressButton btn_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_btn);
        btn_progress = (ProgressButton) findViewById(R.id.btn_progress);
        btn_progress.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_progress:
                btn_progress.setState(ProgressButton.State.IDLE);
                break;
        }
    }

    public static void startThisActivity(MainActivity activity) {
        activity.startActivity(new Intent(activity, ProgressBtnActivity.class));
    }
}
