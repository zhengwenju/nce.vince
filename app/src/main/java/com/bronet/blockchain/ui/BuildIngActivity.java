package com.bronet.blockchain.ui;

import android.os.Bundle;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.zhy.android.percent.support.PercentRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 建设中
 * Created by 18514 on 2019/7/16.
 */

public class BuildIngActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_b_i;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setEvent() {

    }


    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }
}
