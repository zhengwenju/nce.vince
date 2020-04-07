 package com.bronet.blockchain.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;


import com.bronet.blockchain.ActivityStackManager;
import com.bronet.blockchain.ScreenManager;
import com.bronet.blockchain.model.api.cookie.SharePreData;
import com.bronet.blockchain.ui.Constants;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.davik.AppDavikActivityUtil;
import com.bronet.blockchain.util.network.NetWorkBroadcastReceiver;
import com.bronet.blockchain.util.toast.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 基类
 * Created by 18514 on 2019/1/5.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public static NetWorkBroadcastReceiver.NetEvent netEvent;
    public AppDavikActivityUtil appDavikActivityUtil = AppDavikActivityUtil.getScreenManager();
    final String TAG = this.getClass().getSimpleName();

    final static int REQUEST_CODE = 1;
    /**
     * 是否沉浸状态栏
     */


    public boolean isStatusBar = true;
    /**
     * 是否允许全屏
     **/
    public boolean isFullScreen = false;
    /**
     * context
     */
    protected Context ctx;
    protected BaseActivity activity;
    /**
     * 是否输出日志信息
     */
    private boolean isDebug;
    /**
     *导入布局
     */
    protected abstract int getLayoutId();
    /**
     * 初始化界面
     */
    protected abstract void initView();
    /**
     * 初始化数据
     */
    protected abstract void initData();
    /**
     * 绑定事件
     */
    protected abstract void setEvent();
    private Unbinder bind;
    public ScreenManager screenManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        SharePreData sharePreData = new SharePreData();
        Constants.langae=sharePreData.getString(Const.LANGUAGE_KEY,"zh");
        L.test("Constants.langae=====>>>"+Constants.langae);
        Locale locale = new Locale(Constants.langae);
//        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());


        Locale myLocale = new Locale(Constants.langae);
        Resources res = getResources();// 获得res资源对象
        DisplayMetrics dm = res.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        Configuration conf = res.getConfiguration();// 获得设置对象
        conf.locale = myLocale;// 简体中文
        res.updateConfiguration(conf, dm);


        screenManager = ScreenManager.getInstance();
        screenManager.setStatusBar(isStatusBar, this);
        screenManager.setFullScreen(isFullScreen, this);

        super.onCreate(savedInstanceState);
        Log.i(TAG, "--->onCreate()");
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        appDavikActivityUtil.addActivity(this);
        activity=this;
        setBarBiack(true);
        initView();
        initData();
        setEvent();
        ctx = this;
        ActivityStackManager.getActivityStackManager().pushActivity(this);
        Constants.activityList.add(this);
        changeStatusBarTextImgColor(false);
    }

    private void changeStatusBarTextImgColor(boolean isBlack) {
        if (isBlack) {
            //设置状态栏黑色字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            //恢复状态栏白色字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

//    /**
//     * 沉浸状态栏
//     * @param hasFocus
//     */
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); } }

    /**
     * 跳转Activity
     * skip Another Activity
     *
     * @param activity
     * @param cls
     */
    public static void skipAnotherActivity(Activity activity, Class<? extends Activity> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        activity.finish();
    }
    /**
     * 退出应用
     * called while exit app.
     */
    public void exitLogic() {
        ActivityStackManager.getActivityStackManager().popAllActivity();
         System.exit(0);
    }
    /**
     * [是否设置沉浸状态栏]
     * @param statusBar
     */
    public void setStatusBar(boolean statusBar) { isStatusBar = statusBar; }
    /**
     * [是否设置全屏]
     * @param fullScreen
     */
    public void setFullScreen(boolean fullScreen) { isFullScreen = fullScreen; }
    @Override
    protected void onStart() { super.onStart(); Log.i(TAG, "--->onStart()"); }
    @Override
    protected void onResume() { super.onResume(); Log.i(TAG, "--->onResume()"); }
    @Override
    protected void onRestart() { super.onRestart(); Log.i(TAG, "--->onRestart()"); }
    @Override
    protected void onPause() { super.onPause(); Log.i(TAG, "--->onPause()"); }
    @Override
    protected void onStop() { super.onStop(); Log.i(TAG, "--->onStop()"); }
    @Override
    protected void onDestroy() {
        bind.unbind();
        appDavikActivityUtil.removeActivity(this);
        Log.i(TAG, "--->onDestroy()");
        super.onDestroy();
    }
    protected void checkCameraAndMicPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        List<String> permissionList = new ArrayList();
        if (!checkPermissionAudioRecorder()) {
            permissionList.add(Manifest.permission.RECORD_AUDIO);
        }

        if (!checkPermissionCamera()) {
            permissionList.add(Manifest.permission.CAMERA);
        }

        if (!checkPermissionStorage()) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (permissionList.size() < 1) {
            return;
        }
        String[] permissions = permissionList.toArray(new String[0]);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
    }

    private boolean checkPermissionAudioRecorder() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private boolean checkPermissionCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private boolean checkPermissionStorage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                    if (showRequestPermission) {
                        ToastUtil.show(this,permissions[i] + " 权限未申请");
                    }
                }

            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    public void setBarBiack(boolean t){
        if (t) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else{
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }
}

