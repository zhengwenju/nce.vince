package com.bronet.blockchain.util;

import android.util.Log;

import com.google.gson.Gson;

/**
 * 日志打印类
 * <p>
 * Created by vince on 2017/1/21.
 */
public class L {
    public static final String TAG = "DEBUG";
    public static final String HOLDER = "HOLDER";
    public static final String TEST = "daodaoxiong";

    public static boolean isDebug =true; //发布正式版本时设置为false

    public static boolean isMyPwd =false; //发布版本时就改为true

    public static void test(Object msg) {

        if (!isDebug) {
            return;
        }

        if (msg != null) {
            Log.e(TEST, getStackInfo() + msg.toString());
        } else {
            Log.e(TEST, getStackInfo() + "null");
        }
    }

    public static void i(String tag, Object msg) {
        if (msg != null) {
            Log.i(tag, getStackInfo() + msg.toString());
        } else {
            Log.i(tag, getStackInfo() + "null");
        }
    }



    public static void d(Object tag, Object msg) {
        if (msg != null) {
            Log.d(tag.getClass().getSimpleName(), getStackInfo() + msg.toString());
        } else {
            Log.d(tag.getClass().getSimpleName(), "null");
        }
    }

    public static void d(String tag, Object msg) {
        if (msg != null) {
            Log.d(tag, getStackInfo() + msg.toString());
        } else {
            Log.d(tag, "null");
        }
    }

    public static void e(Object msg) {
        if (msg != null) {
            Log.e(TEST, getStackInfo() + msg.toString());
        } else {
            Log.e(TEST, getStackInfo() + "null");
        }
    }

    public static void e(String tag, Object msg) {
        if (msg != null) {
            Log.e(tag, getStackInfo() + msg.toString());
        } else {
            Log.e(tag, "null");
        }
    }

    public static void ej(Object obj) {
        String msg = new Gson().toJson(obj);
        if (msg != null) {
            Log.e("TEST", getStackInfo() + msg);
        } else {
            Log.e("TEST", "null");
        }
    }

    public static void eF(Object obj) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] elements = Thread.getAllStackTraces().get(Thread.currentThread());
        if (elements != null) {
            for (StackTraceElement element : elements) {
                String[] structs = element.getClassName().split("\\.");
                sb.append(String.format("%s (%s.java:%s)\n", element.getClassName(), structs[structs.length - 1], element.getLineNumber()));
            }
        }
        if (obj == null) {
            sb.append("null");
        } else {
            sb.append(obj.getClass().getSimpleName());
            sb.append("    ");
            sb.append(new Gson().toJson(obj));
        }
        Log.e(TEST, sb.toString());
    }

    private static String getStackInfo() {
        return getStackInfo(6);
    }

    private static String getStackInfo(int lines) {
        StackTraceElement[] elements = Thread.getAllStackTraces().get(Thread.currentThread());
        String info = "";
        if (elements != null && elements.length > lines + 1) {
            StackTraceElement element = elements[lines];
            String[] structs = element.getClassName().split("\\.");
            String className = structs[structs.length - 1];
            className = className.split("\\$")[0];
            info += String.format("%s (%s.java:%s)\n", element.getClassName(), className, element.getLineNumber());
        }
        return info;
    }
}
