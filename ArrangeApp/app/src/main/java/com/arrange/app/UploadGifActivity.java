package com.arrange.app;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import com.arrange.R;
import com.arrange.upload.ImageUploadBean;
import com.arrange.upload.UploadWithCompress;
import com.arrange.utils.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kan212 on 17/3/27.
 */

public class UploadGifActivity extends Activity{

    private static final int SELECT_GIF = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_upload);
        findViewById(R.id.btn_upload_gif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i,"Pick a gif"),SELECT_GIF);
            }
        });
//        PostUploadRequest request = new PostUploadRequest();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_GIF){
                uploadGif(data.getData());
            }
        }
    }

    private void uploadGif(Uri uri) {
        String type = getMimeType(uri);
        if ("image/gif".equals(type)){
            ImageUploadBean bean = new ImageUploadBean();
            bean.localPath = FileUtil.getPathByUri(this,uri);
            List<ImageUploadBean> list = new ArrayList<>();
            list.add(bean);
            UploadWithCompress.getInstance().setUploadData(list, new UploadWithCompress.UploadImageListener() {
                @Override
                public void sucess() {

                }

                @Override
                public void fail() {

                }
            }).startUpload();
        }
    }

    public String getMimeType(Uri uri) {
        String mimeType = null;
        ContentResolver cr = getContentResolver();
        mimeType = cr.getType(uri);
        if (mimeType==null || mimeType.equals("")) {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }
        return mimeType.toLowerCase();
    }


    public static void startThisActivity(MainActivity activity) {
        activity.startActivity(new Intent(activity,UploadGifActivity.class));
    }
}
