package com.bronet.blockchain.ui.gamepan.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.PrizeRecordModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class PrizeRecordAdapter extends BaseQuickAdapter<PrizeRecordModel.ResultBean,BaseViewHolder> {
    public PrizeRecordAdapter(int layoutResId, @Nullable List<PrizeRecordModel.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PrizeRecordModel.ResultBean item) {
        helper.setText(R.id.prize_time,String.valueOf(item.getCreateTime()));
        helper.setText(R.id.prize_consume,String.valueOf(item.getQty()));
        helper.setText(R.id.prize_num,String.valueOf(item.getRewardQty()));

    }
}
