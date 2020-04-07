package com.bronet.blockchain.ui;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.ui.login.PwdManagerActivity;
import com.bronet.blockchain.ui.my.MyDataActivity;
import com.bronet.blockchain.ui.my.SetActivity;
import com.bronet.blockchain.util.JumpUtil;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.versioninfo_tv)
    TextView versioninfoTv;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Constants.activityList.add(this);
        getVersion();
    }
    private void getVersion() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        if(Constants.langae.equals("zh")) {
            versioninfoTv.setText("版本信息：v" + version);
        }else {
            versioninfoTv.setText("version：v" + version);
        }
    }
    @OnClick({R.id.account_manager_tv, R.id.modify_pwd_tv, R.id.language_tv, R.id.versioninfo_tv,R.id.btn_back,R.id.relativeLayout0})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_manager_tv:
                //退出登录

                for (Activity activity : Constants.activityList) {
                    activity.finish();
                }

                ConstantUtil.ID="";
                JumpUtil.overlay(this, LoginActivity.class);
//                MainActivity activity = (MainActivity) SettingActivity.this;
                ConstantUtil.TYPE=true;

                break;
            case R.id.modify_pwd_tv:
                JumpUtil.overlay(this, PwdManagerActivity.class);
                break;
            case R.id.language_tv:
                JumpUtil.overlay(this,  LanguageActivity.class);
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.relativeLayout0:
                JumpUtil.overlay(this,  MyDataActivity.class);
                break;
        }
    }

    @Override
    protected void setEvent() {

    }
}
