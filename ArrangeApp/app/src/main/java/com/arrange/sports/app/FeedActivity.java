package com.arrange.sports.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;

import com.arrange.app.MainActivity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kan212 on 17/3/24.
 */

public class FeedActivity extends FragmentActivity{
    OkHttpClient mClient;
    String mUrl = "https://www.baidu.com/";

    public static void startThisActivity(MainActivity activity) {
        activity.startActivity(new Intent(activity,FeedActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mClient = new OkHttpClient.Builder()
                .addInterceptor(new RetryIntercepter(3))//重试
//                .addInterceptor(logging)//网络日志
                .addInterceptor(new TestInterceptor())//模拟网络请求
                .build();
        Request request = new Request.Builder()
                .url(mUrl)
                .build();
        Response response = null;
        try {
            response = mClient.newCall(request).execute();
            System.out.println("onResponse:" + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
