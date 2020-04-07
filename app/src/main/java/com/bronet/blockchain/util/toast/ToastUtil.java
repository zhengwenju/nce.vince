package com.bronet.blockchain.util.toast;

/**
 * Created by Administrator on 2017/4/17.
 */

import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bronet.blockchain.R;

/**
 * Toast统一管理类
 */
public class ToastUtil {

    private ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

//    /**
//     * 短时间显示Toast
//     *
//     * @param context
//     * @param message
//     */
//    public static void showShort(Context context, CharSequence message) {
//        if (isShow)
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//    }

    /**
     * 短时间显示Toast
     *Mai
     * @param context
     * @param message
     */
    public static void showShortCenter(Context context, CharSequence message) {
        if (isShow){
            message = "\b\b"+message+"\b\b";
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShow){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow){
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow){
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

//    /**
//     * 自定义显示Toast时间
//     *
//     * @param context
//     * @param message
//     * @param duration
//     */
//    public static void show(Context context, CharSequence message, int duration) {
//        if (isShow){
//            Toast.makeText(context, message, duration).show();
//        }
//    }

    public static void show(Context context, CharSequence message) {
        if (isShow){
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();


            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.FullScreenDialog);
            final AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            dialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            Window window = dialog.getWindow();
            window.setContentView(R.layout.alter);
            View btnYes = window.findViewById(R.id.btn_yes);
            TextView content = (TextView)window.findViewById(R.id.content);
            content.setText(message);
            // 自定义图片的资源
            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });


        }
    }

//    /**
//     * 自定义显示Toast时间
//     *
//     * @param context
//     * @param message
//     * @param duration
//     */
//    public static void show(Context context, int message, int duration) {
//        if (isShow){
//            Toast.makeText(context, message, duration).show();
//        }
//    }

}