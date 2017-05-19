package com.arrange.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by kan212 on 17/3/27.
 */

public class BitmapUtil {

    public static byte[] compressImageFromBitmap2Byte(Bitmap image) {
        System.gc();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 60;

        if ( baos.toByteArray().length / 1024>200) {
            baos.reset();

            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        return baos.toByteArray();
    }

    public static byte[] bitmap2Byte(Bitmap image) {
        System.gc();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

}
