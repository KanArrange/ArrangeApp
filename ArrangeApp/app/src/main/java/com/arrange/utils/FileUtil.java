package com.arrange.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by kan212 on 17/3/27.
 */

public class FileUtil {

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return degree;

        }
        return degree;
    }

    /**
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        if (angle == 0) {
            return bitmap;
        } else {
            //旋转图片 动作
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            // 创建新的图片
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return resizedBitmap;
        }
    }

    /**
     * 通过URI来获取本地地址
     * @param context
     * @param uri
     * @return
     */
    public static String getPathByUri(Context context, Uri uri) {

        Cursor c = context.getContentResolver().query(uri, new String[]{
                MediaStore.Images.ImageColumns.DATA

        }, null, null, null);
        if (c == null) {
            return "";
        }
        c.moveToFirst();
        int clumIndex = c.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return c.getString(clumIndex);
    }

    public static byte[] getBytesFromFile(File file, int readlimit) {
        if (!file.exists())
            return null;
        FileInputStream fis;
        byte[] buffer;
        try {
            fis = new FileInputStream(file);
            buffer = new byte[readlimit];
            fis.read(buffer, 0, readlimit);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return buffer;
    }
}

