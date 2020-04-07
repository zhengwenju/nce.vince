package com.bronet.blockchain.ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.model.api.cookie.SharePreData;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;

import java.util.Locale;

public class LanguageActivity extends BaseActivity {
    private RadioGroup radio;
    private RadioButton radioButtonen;
    private RadioButton radioButtonzh;
    public Bundle s;
    private ImageView back;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_language;
    }

    @Override
    protected void initView() {

        Constants.activityList.add(this);
        radio = (RadioGroup) findViewById(R.id.radio);
        radioButtonen = (RadioButton) findViewById(R.id.radio_en);
        radioButtonzh = (RadioButton) findViewById(R.id.radio_zh);
        back = findViewById(R.id.back_iv);
        if (Constants.langae.equals("zh")) {
            radioButtonzh.setChecked(true);
        } else {
            radioButtonen.setChecked(true);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setEvent() {
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_zh) {
                    Constants.langae = "zh";
                    radioButtonen.setChecked(false);
                    Locale locale = new Locale(Constants.langae);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    Resources resources = getResources();
                    resources.updateConfiguration(config, resources.getDisplayMetrics());
                    JumpUtil.overlay(LanguageActivity.this,MainActivity.class);
                    //让之前打开的所有界面全部彻底关闭
                    for (Activity activity : Constants.activityList) {
                        activity.finish();
                    }
                } else {
                    radioButtonzh.setChecked(false);
                    Constants.langae = "en";
                    Locale locale = new Locale(Constants.langae);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    Resources resources = getResources();
                    resources.updateConfiguration(config, resources.getDisplayMetrics());
                    JumpUtil.overlay(LanguageActivity.this,MainActivity.class);
                    //让之前打开的所有界面全部彻底关闭
                    for (Activity activity : Constants.activityList) {
                        activity.finish();
                    }
                }
                L.test("1Constants.langae==========>>>"+Constants.langae);
                SharePreData sharePreData = new SharePreData();
                sharePreData.add(Const.LANGUAGE_KEY, Constants.langae);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}



