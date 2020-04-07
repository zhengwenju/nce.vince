package com.bronet.blockchain.util;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bronet.blockchain.R;

public class DialogUtil{
    public static void show(Context context, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.FullScreenDialog);
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

    public static void showFrozen(Context context, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.FullScreenDialog);
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