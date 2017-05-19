package com.arrange.upload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.arrange.ArrangeApp;
import com.arrange.request.HttpUtil;
import com.arrange.utils.BitmapUtil;
import com.arrange.utils.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by kan212 on 17/3/27.
 */

public class UploadWithCompress implements PostUploadRequest.ReqestUploadImageListener{

    static UploadWithCompress mUploadWithCompress;
    private List<ImageUploadBean> mList = new ArrayList<>(2);
    static BitmapFactory.Options options;
    private UploadImageListener mUploadImageListener;

    public static UploadWithCompress getInstance(){
        if (null == mUploadWithCompress){
            mUploadWithCompress = new UploadWithCompress();
        }
        options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = 2;
        return mUploadWithCompress;
    }

    /**
     * 初始化数据
     * @param list
     */
    public UploadWithCompress setUploadData(List<ImageUploadBean> list,UploadImageListener l){
        this.mList = list;
        this.mUploadImageListener = l;
        return this;
    }


    /**
     * 开始上传
     */
    public void startUpload(){
        if (null == mList || mList.isEmpty()){
            return;
        }
        for (ImageUploadBean bean : mList){
            uploadImage(bean);
        }
    }

    /**
     * 压缩图片之后上传
     * @param bean
     */
    private void uploadImage(ImageUploadBean bean) {
        CompressSingleImgFile(bean, new CompressCallBack() {
            @Override
            public void onCompressCallBack(ImageUploadBean bean) {
                if (null == bean){
                    return;
                }
                PostUploadRequest request = new PostUploadRequest("https://bar.sports.sina.com.cn/api/upload/uppicpub",bean,mUploadWithCompress);
                Map<String, String> header = new HashMap<String, String>();
                header.put("REFERER", "http://fans.sports.sina.com.cn");
                request.setHeader(header);
                HttpUtil.addImgUploadRequest(request);
            }
        });
    }

    /**
     * 压缩图片
     * @param bean
     * @param callBack
     */
    private void CompressSingleImgFile(final ImageUploadBean bean, final CompressCallBack callBack){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(bean.localPath);
                        if (file.exists() && file.length() > 0){
                            bean.compressedData = FileUtil.getBytesFromFile(file,1024 * 1024 * 4);
//                            int angle = FileUtil.readPictureDegree(file.getAbsolutePath());
//                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),options);
//                            if (null == bitmap){
//                                callBack.onCompressCallBack(null);
//                                return;
//                            }

//                            if (file.length() > 1024 * 1024 * 0.4) {
//                                bean.compressedData = BitmapUtil.compressImageFromBitmap2Byte(FileUtil.rotaingImageView(angle, bitmap));
//                            } else {
//                                bean.compressedData = BitmapUtil.bitmap2Byte(FileUtil.rotaingImageView(angle, bitmap));
//                            }
                            callBack.onCompressCallBack(bean);
                        }
//                        UploadGif.INSTANCE.httpsPostWithPic("https://bar.sports.sina.com.cn/api/upload/uppicpub","123",bean.localPath);
                    }
                }
        ).run();
//        UploadTask mUploadTask = new UploadTask(bean.localPath);
//        mUploadTask.execute();
    }

    public static byte[] readFile(String fileName) {
        FileInputStream fis = null;
//        GZIPInputStream gis = null;
        byte[] result = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            fis = new FileInputStream(new File(fileName));
//            gis = new GZIPInputStream(fis);
            byte buffer[] = new byte[2048];
            int count;
            while ((count = fis.read(buffer)) != -1) {
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
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void sucess(ImageUploadBean mUploadBean, String url) {
        mUploadBean.serverUrl = url;
        if (!TextUtils.isEmpty(mList.get(0).serverUrl) && !TextUtils.isEmpty(mList.get(1).serverUrl)){
            if (null != mUploadImageListener){
                mUploadImageListener.sucess();
            }
        }
    }

    @Override
    public void fail() {
        mUploadImageListener.fail();
    }

    private interface CompressCallBack{
        void onCompressCallBack(ImageUploadBean bean);
    }

    public interface UploadImageListener{
        void sucess();
        void fail();
    }
}

