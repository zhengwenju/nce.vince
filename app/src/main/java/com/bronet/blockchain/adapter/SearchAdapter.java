package com.bronet.blockchain.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.DetailsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

public class SearchAdapter extends BaseQuickAdapter<DetailsBean.ResultBean.ItemsBean, BaseViewHolder> {
    public SearchAdapter(int layoutResId, @Nullable ArrayList<DetailsBean.ResultBean.ItemsBean> data) {
        super(layoutResId, data);
    }



    @Override
    protected void convert(@NonNull BaseViewHolder helper, DetailsBean.ResultBean.ItemsBean item) {
        helper.setText(R.id.nameData,item.getUserName());
        helper.setText(R.id.time1,String.valueOf(item.getCreateTime()));
        helper.setText(R.id.money,String.valueOf(item.getBzMoney()));
        helper.setText(R.id.money_item,item.getBzCoinName());
        helper.setText(R.id.start,item.getStatusMsg());
    }
}
