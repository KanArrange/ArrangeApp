package com.arrange.upload;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by kan212 on 17/3/27.
 */

public class UploadTask extends AsyncTask<Void,Void,Void>{

    private String Url = "https://bar.sports.sina.com.cn/api/upload/uppicpub";
    private String  mFile;
    private static int BUFFER = 1024;
    private Context mContext;


    public UploadTask(String  file){
        this.mFile = file;
    }

    @Override
    protected Void doInBackground(Void... params) {
         httpsPostWithPic(mFile);
        return null;
    }

    private void httpsPostWithPic(String filePath) {
        HttpURLConnection httpsURLCon = null;
        InputStream is = null;
        BufferedReader in = null;
        URL url;
        String retLine = "";
        try {
            url = new URL(Url);
            httpsURLCon = (HttpsURLConnection) url.openConnection();
            httpsURLCon.setRequestMethod("POST");
            httpsURLCon.setDoOutput(true);
            httpsURLCon.setDoInput(true);
            String BOUNDARY = "--------------SinaChina"; // 分隔符
            httpsURLCon.setRequestProperty("REFERER", "http://fans.sports.sina.com.cn");
            httpsURLCon.setRequestProperty("Content-type", "application/x-java-serialized-object");
            httpsURLCon.setRequestProperty("Connection", "Keep-Alive");
            httpsURLCon.setRequestProperty("Content-Encoding", "gzip");
            httpsURLCon.setRequestProperty("Accept-Encoding", "gzip");
            httpsURLCon.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);

            httpsURLCon.setUseCaches(false);
            httpsURLCon.setInstanceFollowRedirects(true);
            // 设置浏览器编码
            httpsURLCon.setRequestProperty("Charset", "UTF-8");


            StringBuffer sb = new StringBuffer();

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

             // 设置表单类型和分隔符

            OutputStream os = httpsURLCon.getOutputStream();
            os.write(data);

            FileInputStream fis = new FileInputStream(new File(filePath)); // 要上传的文件

            int rn2;
            byte[] buf2 = new byte[2048];
            while ((rn2 = fis.read(buf2, 0, 2048)) > 0) {
                os.write(buf2, 0, rn2);
            }
//            os.write(readFile(filePath));

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
                Log.e("resJson",resJson);
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

    private static byte[] compress(byte[] data) throws IOException {
        if (data == null || data.length == 0) {
            return data;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(data);
        gzip.close();
        out.flush();
        byte[] ret = out.toByteArray();
        out.close();
        return ret;
    }

    public static byte[] readFile(String fileName) {
        FileInputStream fis = null;
        GZIPInputStream gis = null;
        byte[] result = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            fis = new FileInputStream(new File(fileName));
            gis = new GZIPInputStream(fis);
            byte buffer[] = new byte[BUFFER];
            int count;
            while ((count = gis.read(buffer)) != -1) {
                baos.write(buffer, 0, count);
            }
            result = baos.toByteArray();
        } catch (FileNotFoundException e) {
        } catch (StreamCorruptedException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
                if (null != gis) {
                    gis.close();
                }
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
