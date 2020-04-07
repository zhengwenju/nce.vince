package com.bronet.blockchain.ui.my;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.ui.Constants;
import com.bronet.blockchain.ui.LanguageActivity;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.util.JumpUtil;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 * Created by 18514 on 2019/7/6.
 */

public class SetActivity extends BaseActivity {
    @BindView(R.id.version)
    TextView versions;
    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;
    @BindView(R.id.language_perlayout)
    PercentRelativeLayout language;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void initView() {
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlay(activity, LoginActivity.class);
            return;
        }
    }

    @Override
    protected void initData(){
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
        versions.setText(version);
    }

    @Override
    protected void setEvent() {
        Constants.activityList.add(this);
//        switchLanguage(Constants.langae);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtil.overlay(SetActivity.this,  LanguageActivity.class);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }

//    private void switchLanguage(String language) {
//        Resources resources = getResources();
//        Configuration config = resources.getConfiguration();
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        switch (language) {
//            case "CN":
//                config.locale = Locale.CHINESE;
//                resources.updateConfiguration(config, dm);
//                break;
//            case "US":
//                config.locale = Locale.ENGLISH;
//                resources.updateConfiguration(config, dm);
//                break;
//            default:
//                config.locale = Locale.US;
//                resources.updateConfiguration(config, dm);
//                break;
//        }
//    }
}
