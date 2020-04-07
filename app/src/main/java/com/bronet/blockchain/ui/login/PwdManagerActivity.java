package com.bronet.blockchain.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.ui.MainActivity;
import com.bronet.blockchain.util.JumpUtil;
import com.zhy.android.percent.support.PercentRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 密码管理
 * Created by 18514 on 2019/6/25.
 */

public class PwdManagerActivity extends BaseActivity {

    @BindView(R.id.modify_pwd_perlayout)
    PercentRelativeLayout modify_pwd_perlayout;
    @BindView(R.id.find_pwd_perlayout)
    PercentRelativeLayout find_pwd_perlayout;
    @BindView(R.id.btn_back)
    RelativeLayout btnBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manager_pwd;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlay(activity, LoginActivity.class);
            return;
        }

    }

    @Override
    protected void setEvent() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {
            finish();
        }
    }

    @OnClick({R.id.modify_pwd_perlayout, R.id.find_pwd_perlayout, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.modify_pwd_perlayout:
                //修改密码
                JumpUtil.overlay(PwdManagerActivity.this, ModifyPwdActivity.class);
                break;
            case R.id.find_pwd_perlayout:

                //重置密码
                JumpUtil.overlay(PwdManagerActivity.this, ResetPwdActivity.class);
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    @Override
    public void onBackPressed() {
        if (ConstantUtil.TYPE) {
            JumpUtil.overlay(activity, MainActivity.class);
            finish();
        } else {
            super.onBackPressed();
        }
    }


}
