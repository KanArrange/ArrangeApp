package com.arrange.sports.okhttp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by kan212 on 17/7/13.
 */

public class TestInterceptor implements Interceptor {

    String mUrl = "https://www.baidu.com/";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();
        System.out.println("url=" + url);
        Response response = null;
        if (url.equals(mUrl)) {
            String responseString = "{\"message\":\"我是模拟的数据\"}";//模拟的错误的返回值
            response = new Response.Builder()
                    .code(400)
                    .request(request)
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                    .addHeader("content-type", "application/json")
                    .build();
        } else {
            response = chain.proceed(request);
        }
        return response;
    }
}
