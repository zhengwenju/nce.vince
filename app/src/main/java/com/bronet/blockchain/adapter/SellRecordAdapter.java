package com.bronet.blockchain.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.TradeSellList;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class SellRecordAdapter extends BaseQuickAdapter<TradeSellList.ResultBean,BaseViewHolder> {
    public SellRecordAdapter(int layoutResId, @Nullable List<TradeSellList.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TradeSellList.ResultBean item) {
        try {
                helper.setText(R.id.price, item.getPrice());
                helper.setText(R.id.number, item.getQty());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
