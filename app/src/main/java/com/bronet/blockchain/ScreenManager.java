package com.bronet.blockchain;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.base.BaseFragment;


/**
 * 屏幕管理
 * Created by 18514 on 2019/1/5.
 */
public class ScreenManager {
    private static ScreenManager instance;
    private ScreenManager() { }
    public synchronized static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }
    /**
     * 窗口全屏
     */
    public void setFullScreen(boolean isChange,BaseActivity mActivity) {
        if (!isChange) {
            return;
        }
        mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    /**
     * [沉浸状态栏]
     */
    public void setStatusBar(boolean isChange,BaseActivity mActivity) {
        if (!isChange){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //需要设置这个flag contentView才能延伸到状态栏
                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                //状态栏覆盖在contentView上面，设置透明使contentView的背景透出来
                mActivity.getWindow().setStatusBarColor(Color.TRANSPARENT);

            } else {
                //让contentView延伸到状态栏并且设置状态栏颜色透明
                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }

    }

    /**
     * [沉浸状态栏]
     */
    public void setStatusBar2(BaseActivity mActivity) {
        Window window = mActivity.getWindow();
        //默认API 最低19
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup contentView = (ViewGroup) window.getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
            contentView.getChildAt(0).setFitsSystemWindows(false);
        }
    }

}

