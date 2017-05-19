package com.arrange.upload;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kan212 on 17/2/16.
 */

public class PostUploadRequest extends Request<String> {

    private ImageUploadBean mUploadBean;
    private ReqestUploadImageListener mReqestUploadImageListener;
    private Map<String, String> mHeader;
    private String MULTIPART_FORM_DATA = "multipart/form-data";
    private String BOUNDARY = "--------------SinaChina"; //数据分隔线

    public PostUploadRequest(String postUrl, ImageUploadBean uploadBean,ReqestUploadImageListener l){
        super(Method.POST,postUrl,null);
        this.mUploadBean = uploadBean;
        this.mReqestUploadImageListener = l;
        setShouldCache(false);
        setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void setHeader(Map<String, String> header){
         this.mHeader = header;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (null == mHeader){
            mHeader = new HashMap<>();
        }
        return mHeader;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        StringBuffer sb = new StringBuffer();
        sb.append("--" + BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;");
        sb.append(" name=\"");
        sb.append("file");
        sb.append("\"");
        sb.append("; filename=\"");
        sb.append(System.currentTimeMillis() + ".jpg");
        sb.append("\"");
        sb.append("\r\n");
        sb.append("Content-Type: ");
        sb.append("image/jpg");
        sb.append("\r\n");
        sb.append("\r\n");
        try {
            bos.write(sb.toString().getBytes("utf-8"));
            bos.write(mUploadBean.compressedData);
            bos.write("\r\n".getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String endLine = "--" + BOUNDARY + "--" + "\r\n";
        try {
            bos.write(endLine.toString().getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String,String> map = new HashMap<>();
        map.put("file","file");
        return map;
    }

    @Override
    public String getBodyContentType() {
        return MULTIPART_FORM_DATA+ "; boundary=" + BOUNDARY;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String mString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));//指定response解析编码

            return Response.success(mString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {

            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        if (null != mReqestUploadImageListener){
            PostUploadParser postUploadParser = new PostUploadParser();
        }
        mReqestUploadImageListener.fail();
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        if (null != mReqestUploadImageListener){
            mReqestUploadImageListener.fail();
        }
    }

    public interface ReqestUploadImageListener{
        void sucess(ImageUploadBean mUploadBean, String url);
        void fail();
    }
}
