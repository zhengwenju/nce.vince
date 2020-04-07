
package com.bronet.blockchain.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.ui.fragment.IdentiFragment;
import com.bronet.blockchain.ui.fragment.TransferFragment;
import com.bronet.blockchain.util.Action;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 私募界面
 */
public final class PrivateAccountActivity extends BaseActivity {
    @BindView(R.id.tl_1)
    SegmentTabLayout tl1;

    private IdentiFragment ldentifyFragment;

    private TransferFragment transferFragment;
    private FragmentTransaction fragmentTransaction;
    String[] title = {"认筹", "转让"};

    private MyReceiver recevier;
    private IntentFilter intentFilter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_privateaccount;
    }

    protected void initView() {
        tl1.setTabData(title);
        tl1.setCurrentTab(0);
        initFragment();

    }
    private void initFragment() {
        //获取布局管理器
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //Fragment
        ldentifyFragment = new IdentiFragment();
        transferFragment = new TransferFragment();
        //加入布局管理器
        fragmentTransaction.add(R.id.frame_layout, ldentifyFragment);
        fragmentTransaction.add(R.id.frame_layout, transferFragment);
        //默认展示
        fragmentTransaction.hide(transferFragment);
        //提交
        fragmentTransaction.commit();
    }
    @Override
    protected void initData() {
        recevier = new MyReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(Action.IdentyAction);
        registerReceiver(recevier,intentFilter);
    }

    @Override
    protected void setEvent() {
        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(ldentifyFragment);
                fragmentTransaction.hide(transferFragment);
                if (position == 0) {
                    fragmentTransaction.show(ldentifyFragment);
                } else {
                    fragmentTransaction.show(transferFragment);
                    transferFragment.refreshData();
                }
                fragmentTransaction.commit();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(recevier!=null){
            unregisterReceiver(recevier);
        }
    }

    @OnClick({R.id.btn_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            default:
                break;
        }
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(ldentifyFragment);
            fragmentTransaction.hide(transferFragment);
            fragmentTransaction.show(ldentifyFragment);

            ldentifyFragment.reset(intent);

            fragmentTransaction.commit();

        }
    }
}