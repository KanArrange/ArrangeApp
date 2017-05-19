package com.arrange.upload;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

/**
 * Created by kan212 on 17/3/27.
 */

public enum UploadGif{
    INSTANCE;

    private static MyX509TrustManager xtm = new MyX509TrustManager();
    private static MyHostnameVerifier hnv = new MyHostnameVerifier();

    private static final AllowAllHostnameVerifier HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();


    private void initHttps(){
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            X509TrustManager[] xtmArray = new X509TrustManager[] { xtm };
            sslContext.init(null, xtmArray, new java.security.SecureRandom());
        } catch (GeneralSecurityException gse) {
            gse.printStackTrace();
        }
        if (sslContext != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
                    .getSocketFactory());
        }
        HttpsURLConnection.setDefaultHostnameVerifier(hnv);
    }

    public void httpsPostWithPic(String uRL_Weibo2_Upload,
                                          String content, String filePath) {
//        initHttps();
        HttpURLConnection httpsURLCon = null;
        InputStream is = null;
        BufferedReader in = null;
        URL url;
        String retLine = "";
        try {
            url = new URL(uRL_Weibo2_Upload);
            httpsURLCon = (HttpsURLConnection) url.openConnection();
//            httpsURLCon.setHostnameVerifier(HOSTNAME_VERIFIER);
            httpsURLCon.setRequestMethod("POST");
/*          httpsURLCon.setReadTimeout(2000);
            httpsURLCon.setConnectTimeout(5000);*/
            httpsURLCon.setDoOutput(true);
            httpsURLCon.setDoInput(true);
            httpsURLCon.setRequestProperty("REFERER", "http://fans.sports.sina.com.cn");
            httpsURLCon.setRequestProperty("Content-type", "application/x-java-serialized-object");
            httpsURLCon.setRequestProperty("Connection", "Keep-Alive");
            httpsURLCon.setRequestProperty("Content-Type", "text/xml; charset=GB2312");

            httpsURLCon.setUseCaches(false);
            httpsURLCon.setInstanceFollowRedirects(true);
            // 设置浏览器编码
            httpsURLCon.setRequestProperty("Charset", "UTF-8");

            String BOUNDARY = "--------------SinaChina"; // 分隔符

            StringBuffer sb = new StringBuffer();

//            sb.append("--");
//            sb.append(BOUNDARY);
//            sb.append("\r\n");
//            sb.append("Content-Disposition: form-data; name=\"status\"\r\n\r\n");
//            sb.append(content);
//            sb.append("\r\n");
//
//            sb.append("--");
//            sb.append(BOUNDARY);
//            sb.append("\r\n");
//            sb.append("Content-Disposition: form-data; name=\"gif\"; filename=\"testc.gif\"\r\n");
//            sb.append("Content-Type: application/x-www-form-urlencoded\r\n\r\n");

            sb.append("--" + BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;");
            sb.append(" name=\"");
            sb.append("file");
            sb.append("\"");
            sb.append("; filename=\"");
            sb.append(System.currentTimeMillis() + ".gif");
            sb.append("\"");
            sb.append("\r\n");
            sb.append("Content-Type: ");
            sb.append("image/gif");
            sb.append("\r\n");
            sb.append("\r\n");

            byte[] data = sb.toString().getBytes("utf-8");
            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();

            httpsURLCon.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY); // 设置表单类型和分隔符

            OutputStream os = httpsURLCon.getOutputStream();
            os.write(data);

            FileInputStream fis = new FileInputStream(new File(filePath)); // 要上传的文件

            int rn2;
            byte[] buf2 = new byte[1024];
            while ((rn2 = fis.read(buf2, 0, 1024)) > 0) {
                os.write(buf2, 0, rn2);
            }

            os.write(end_data);

            os.flush();

            int statusCode = httpsURLCon.getResponseCode();

            if(statusCode == HttpsURLConnection.HTTP_OK){
                is = httpsURLCon.getInputStream();
            }
            else {
                is = httpsURLCon.getErrorStream();
            }

            if (is != null) {
                in = new BufferedReader(new InputStreamReader(is));
                String line = "";
                while ((line = in.readLine()) != null) {
                    retLine += line;
                }
                String resJson = retLine;
            }


        } catch (MalformedURLException e1) {
        } catch (IOException e) {
        } catch (Exception ex) {
        }
        finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(httpsURLCon != null){
                httpsURLCon.disconnect();
            }
        }
    }

}
