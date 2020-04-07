package com.bronet.blockchain.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.WithdrawLog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 18514 on 2019/7/16.
 */

public class WMAdapter extends BaseQuickAdapter<WithdrawLog.Result,BaseViewHolder> {
    public WMAdapter(@LayoutRes int layoutResId, @Nullable List<WithdrawLog.Result> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, WithdrawLog.Result s) {
        baseViewHolder.setText(R.id.name,s.getCoinName());
        baseViewHolder.setText(R.id.number,s.getQty());
        baseViewHolder.setText(R.id.type,s.getStatus());
        baseViewHolder.setText(R.id.time,s.getUpdateTime());
    }
}
