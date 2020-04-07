package com.bronet.blockchain.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import com.bronet.blockchain.ui.Constants;
import com.bronet.blockchain.ui.login.LoginActivity;

/**
 * 界面跳转 工具类
 *
 * @packageName: cn.white.ymc.wanandroidmaster.util
 * @fileName: JumpUtil
 * @date: 2018/7/20  17:17
 * @author: ymc
 * @QQ:745612618
 */

public class JumpUtil {

    /**
     * 不带参数的跳转
     *
     * @param context
     * @param targetClazz
     */
    public static void overlay(Context context, Class<? extends Activity> targetClazz) {

        Intent mIntent = new Intent(context, targetClazz);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);
    }

    public static void overlayClearTop(Context context, Class<? extends Activity> targetClazz) {
        Intent mIntent = new Intent(context, targetClazz);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(mIntent);
    }

    /**
     * 带参数不带动画的跳转
     *
     * @param context
     * @param targetClazz
     * @param bundle
     */
    public static void overlay(Context context, Class<? extends Activity> targetClazz, Bundle bundle) {
        Intent mIntent = new Intent(context, targetClazz);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            mIntent.putExtras(bundle);
        }
        context.startActivity(mIntent);
    }

    /**
     * 带参数,共享元素跳转
     *
     * @param context
     * @param targetClazz
     * @param bundle
     */
    public static void overlay(Context context, Class<? extends Activity> targetClazz, Bundle bundle, Bundle options) {
        Intent mIntent = new Intent(context, targetClazz);
        if (bundle != null) {
            mIntent.putExtras(bundle);
        }
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent, options);
    }

    /**
     *
     *
     * @param context
     * @param targetClazz
     * @param bundle
     * @param flags
     */
    public static void overlay(Context context, Class<? extends Activity> targetClazz, Bundle bundle, Integer flags) {
        Intent mIntent = new Intent(context, targetClazz);
        if (bundle != null) {
            mIntent.putExtras(bundle);
        }
        if (flags != null) {
            mIntent.setFlags(flags);
        }
        context.startActivity(mIntent);
    }


    /**
     * 界面跳转带 result
     *
     * @param context
     * @param targetClazz
     * @param requestCode
     * @param bundle
     */
    public static void startForResult(Activity context, Class<? extends Activity> targetClazz, int requestCode, Bundle bundle) {
        Intent mIntent = new Intent(context, targetClazz);
        if (bundle != null) {
            mIntent.putExtras(bundle);
        }
        context.startActivityForResult(mIntent, requestCode);
    }

    /**
     * fragment 界面跳转 带result
     *
     * @param fragment
     * @param targetClazz
     * @param requestCode
     * @param bundle
     */
    public static void startForResult(Fragment fragment, Class<? extends Activity> targetClazz, int requestCode, Bundle bundle) {
        Intent mIntent = new Intent(fragment.getActivity(), targetClazz);
        if (bundle != null) {
            mIntent.putExtras(bundle);
        }
        fragment.startActivityForResult(mIntent, requestCode);
    }

    public static int loginflag=0;
    public static void errorHandler(Context context,int errorcode,String message,boolean isLogin){
        if(context!=null) {
            try {
                L.test("errorHandler message===============>>>" + message);

                String errorCodeStr="";
                if(message.contains("401")){
                    errorCodeStr="401"+errorcode;
                }else if(message.contains("404")){
                    errorCodeStr="404"+errorcode;
                }else if(message.contains("502")){
                    errorCodeStr="502"+errorcode;
                }else if(message.contains("403")){
                    errorCodeStr="403"+errorcode;
                }
                if (message.contains("401") || message.contains("404") || message.contains("502")|| message.contains("403")) {
                    if(loginflag==0) {
                        loginflag=1;
                        Intent mIntent = new Intent(context, LoginActivity.class);
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mIntent.putExtra("error", Integer.valueOf(errorCodeStr));
                        context.startActivity(mIntent);

                        for (Activity activity : Constants.activityList) {
                            activity.finish();
                        }
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

    }

}
