package com.bronet.blockchain.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.TradeBuyList;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MyTwoListAdapter extends BaseQuickAdapter<TradeBuyList.ResultBean.ItemsBean.ExchangeItemsBean,BaseViewHolder> {
    public MyTwoListAdapter(int layoutResId, @Nullable List<TradeBuyList.ResultBean.ItemsBean.ExchangeItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TradeBuyList.ResultBean.ItemsBean.ExchangeItemsBean item) {
        helper.setText(R.id.time,String.valueOf(item.getCreateTime()));
        helper.setText(R.id.nceNum,String.valueOf(item.getQty()));
    }
}
