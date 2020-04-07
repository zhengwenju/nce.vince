package com.bronet.blockchain.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.ui.login.InvitationCodeActivity;
import com.bronet.blockchain.util.JumpUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**协议
 * Created by 18514 on 2019/7/17.
 */

public class XYActivity extends BaseActivity {
    @BindView(R.id.authorize_return)
    ImageView authorize_return;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_xy;
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
    @OnClick({R.id.authorize_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.authorize_return:
                finish();
                break;
        }
    }
}
