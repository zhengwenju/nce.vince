package com.bronet.blockchain.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.TransferNodeLog;
import com.bronet.blockchain.data.TransferRewardLog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TransferRewardRecordAdapter extends BaseQuickAdapter<TransferRewardLog.ResultBean, BaseViewHolder> {
    public TransferRewardRecordAdapter(int layoutResId, @Nullable List<TransferRewardLog.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TransferRewardLog.ResultBean item) {
        helper.setText(R.id.time,item.getUpdateTime());
        helper.setText(R.id.coin_type_tv, item.getCoinName());
        helper.setText(R.id.number, item.getQty());
    }
}
