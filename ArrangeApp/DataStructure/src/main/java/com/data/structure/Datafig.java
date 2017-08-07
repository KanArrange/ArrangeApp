package com.data.structure;

import android.util.Log;

import java.io.File;
import java.util.Date;

/**
 * Created by kan212 on 17/8/7.
 */

public class Datafig {

    public static boolean isDebug = true;
    public static boolean isLogSave = true;
    public static final String TAG = "Config";
    public static String APP_TAG = "data_structure  ";

    public static void e(Object msg) {
        if (isDebug)
            Log.e(getTag(4), APP_TAG + (msg == null ? "null" : msg.toString()));
    }

    public static void d(Object msg) {
        if (isDebug)
            Log.d(getTag(4), APP_TAG + (msg == null ? "null" : msg.toString()));
    }

    public static void i(Object msg) {
        if (isDebug)
            Log.i(getTag(4), APP_TAG + (msg == null ? "null" : msg.toString()));
    }

    private static File logFile;
    private static Date logDate;


    public static String getTag(int level) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[level];
        StringBuilder sb = new StringBuilder();
        sb.append(getSimpleClassName(ste.getClassName()));
        sb.append('.');
        sb.append(ste.getMethodName());
        sb.append('(');
        sb.append(ste.getLineNumber());
        sb.append(')');
        return sb.toString();
    }

    public static String getSimpleClassName(String path) {
        int index = path.lastIndexOf('.');
        if (index < 0) {
            return path;
        } else {
            return path.substring(index + 1);
        }
    }
}

