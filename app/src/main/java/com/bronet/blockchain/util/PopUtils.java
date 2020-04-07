package com.bronet.blockchain.util;


import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

public class PopUtils {

    private Activity activity;
    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgcolor;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }

}
