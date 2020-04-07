package com.bronet.blockchain.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.TradeList;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class PurchaseListAdapter extends BaseQuickAdapter<TradeList.ResultBean,BaseViewHolder> {
    public PurchaseListAdapter(int layoutResId, @Nullable List<TradeList.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TradeList.ResultBean item) {
        helper.setText(R.id.price,item.getPrice());
        helper.setText(R.id.number,item.getQty());
    }
}
