package com.arrange.rotat.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.arrange.R;
import com.arrange.app.MainActivity;
import com.base.swlayout.BaseEffects;
import com.base.swlayout.SwichLayoutInterFace;
import com.base.swlayout.SwitchLayout;

/**
 * SwitchLayout
 * 
 */
public class FirstActivity extends Activity implements SwichLayoutInterFace {
	private Button btn_ok, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7,
			btn_8, btn_9, btn_10, btn_11, btn_12, btn_13, btn_14;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rotat_first);
		initView();

		SwitchLayout.getSlideFromBottom(this, false,
				BaseEffects.getQuickToSlowEffect());
		initListener();

	}

	@Override
	protected void onPause() {
		super.onPause();
	}


	@Override
	protected void onStop() {
		super.onStop();
	}

	private void initListener() {
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(FirstActivity.this, SecondActivity.class);
				in.putExtra("key", 0);
				FirstActivity.this.startActivity(in);
			}
		});

		btn_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(FirstActivity.this, SecondActivity.class);
				in.putExtra("key", 1);
				FirstActivity.this.startActivity(in);
			}
		});
		btn_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(FirstActivity.this, SecondActivity.class);
				in.putExtra("key", 2);
				FirstActivity.this.startActivity(in);
			}
		});
		btn_3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(FirstActivity.this, SecondActivity.class);
				in.putExtra("key", 3);
				FirstActivity.this.startActivity(in);
			}
		});
		btn_4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(FirstActivity.this, SecondActivity.class);
				in.putExtra("key", 4);
				FirstActivity.this.startActivity(in);
			}
		});
		btn_5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(FirstActivity.this, SecondActivity.class);
				in.putExtra("key", 5);
				FirstActivity.this.startActivity(in);
			}
		});
		btn_6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(FirstActivity.this, SecondActivity.class);
				in.putExtra("key", 6);
				FirstActivity.this.startActivity(in);
			}
		});

		btn_7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(FirstActivity.this, SecondActivity.class);
				in.putExtra("key", 7);
				FirstActivity.this.startActivity(in);
			}
		});
		btn_8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(FirstActivity.this, SecondActivity.class);
				in.putExtra("key", 8);
				FirstActivity.this.startActivity(in);
			}
		});
		btn_9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(FirstActivity.this, SecondActivity.class);
				in.putExtra("key", 9);
				FirstActivity.this.startActivity(in);
			}
		});
		btn_10.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(FirstActivity.this, SecondActivity.class);
				in.putExtra("key", 10);
				FirstActivity.this.startActivity(in);
			}
		});
		btn_11.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(FirstActivity.this, SecondActivity.class);
				in.putExtra("key", 11);
				FirstActivity.this.startActivity(in);
			}
		});
		btn_12.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(FirstActivity.this, SecondActivity.class);
				in.putExtra("key", 12);
				FirstActivity.this.startActivity(in);
			}
		});
		btn_13.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(FirstActivity.this, SecondActivity.class);
				in.putExtra("key", 13);
				FirstActivity.this.startActivity(in);
			}
		});
		btn_14.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(FirstActivity.this, SecondActivity.class);
				in.putExtra("key", 14);
				FirstActivity.this.startActivity(in);
			}
		});
	}

	private void initView() {
		btn_ok = (Button) this.findViewById(R.id.btn_ok);
		btn_1 = (Button) this.findViewById(R.id.btn_1);
		btn_2 = (Button) this.findViewById(R.id.btn_2);
		btn_3 = (Button) this.findViewById(R.id.btn_3);
		btn_4 = (Button) this.findViewById(R.id.btn_4);
		btn_5 = (Button) this.findViewById(R.id.btn_5);
		btn_6 = (Button) this.findViewById(R.id.btn_6);
		btn_7 = (Button) this.findViewById(R.id.btn_7);
		btn_8 = (Button) this.findViewById(R.id.btn_8);
		btn_9 = (Button) this.findViewById(R.id.btn_9);
		btn_10 = (Button) this.findViewById(R.id.btn_10);
		btn_11 = (Button) this.findViewById(R.id.btn_11);
		btn_12 = (Button) this.findViewById(R.id.btn_12);
		btn_13 = (Button) this.findViewById(R.id.btn_13);
		btn_14 = (Button) this.findViewById(R.id.btn_14);

	}

	public static void startThisActivity(MainActivity activity) {
		activity.startActivity(new Intent(activity,FirstActivity.class));
	}

	@Override
	public void setEnterSwichLayout() {

	}

	@Override
	public void setExitSwichLayout() {
		SwitchLayout.get3DRotateFromLeft(this, false, null);
	}
}
