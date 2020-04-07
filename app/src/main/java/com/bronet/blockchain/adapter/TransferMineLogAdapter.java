package com.bronet.blockchain.adapter;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.TransferMineLog;
import com.bronet.blockchain.data.TransferNodeLog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TransferMineLogAdapter extends BaseQuickAdapter<TransferMineLog.ResultBean, BaseViewHolder> {
    public TransferMineLogAdapter(int layoutResId, @Nullable List<TransferMineLog.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TransferMineLog.ResultBean item) {
        helper.setText(R.id.time, item.getCreateTime());
        helper.setText(R.id.surplus_quantity, item.getBalanceQty());
        helper.setText(R.id.number, item.getQty());
    }
}
