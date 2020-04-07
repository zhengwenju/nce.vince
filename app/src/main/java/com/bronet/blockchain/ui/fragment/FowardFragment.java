package com.bronet.blockchain.ui.fragment;

import androidx.fragment.app.Fragment;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseFragment;
import com.flyco.tablayout.SegmentTabLayout;

import butterknife.BindView;

/**
 * 转让
 * Created by 18514 on 2019/6/27.
 */

public class FowardFragment extends BaseFragment {
    @BindView(R.id.tl_1)
    SegmentTabLayout tl1;

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void initView() {
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_quotation;
    }

    public static Fragment getInstance() {
        return new FowardFragment();
    }

}
