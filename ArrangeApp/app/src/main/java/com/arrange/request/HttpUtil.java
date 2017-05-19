package com.arrange.request;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.arrange.ArrangeApp;

/**
 * Created by kan212 on 17/3/27.
 */

public class HttpUtil {

    public static void addImgUploadRequest(Request<String> request) {
        if (request == null) {
            return;
        }
        RequestQueue mRequestQueue = Volley.newRequestQueue(ArrangeApp.mCotext);
        mRequestQueue.add(request);
    }

}
