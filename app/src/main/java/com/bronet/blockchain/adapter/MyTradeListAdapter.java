package com.bronet.blockchain.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.MyTradeList;
import com.bronet.blockchain.data.TransCoins;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MyTradeListAdapter extends BaseQuickAdapter<MyTradeList.ResultBean.ItemsBean,BaseViewHolder> {
    private TransCoins.ResultBean bean;
    public MyTradeListAdapter(int layoutResId, @Nullable List<MyTradeList.ResultBean.ItemsBean> data,TransCoins.ResultBean bean) {
        super(layoutResId, data);
        this.bean=bean;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MyTradeList.ResultBean.ItemsBean item) {
        helper.setText(R.id.time,String.valueOf(item.getCreateTime()));
        helper.setText(R.id.num,String.valueOf(item.getQty()+" "+bean.getInCoinName()));
        helper.setText(R.id.Charge,String.valueOf(item.getFee()+" "+bean.getInCoinName()));
        helper.setText(R.id.rate,String.valueOf(item.getRange()));
        helper.setText(R.id.price,String.valueOf(item.getFinalPrice()+" "+bean.getOutCoinName()));



    }
}
