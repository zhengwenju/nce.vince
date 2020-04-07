package com.bronet.blockchain.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.RechargeRecordBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

/**
 * Created by 18514 on 2019/7/29.
 */

public class ExchangeRecordAdapter extends BaseQuickAdapter<RechargeRecordBean.ResultBean,BaseViewHolder>{


    public ExchangeRecordAdapter(int layoutResId, @Nullable ArrayList<RechargeRecordBean.ResultBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RechargeRecordBean.ResultBean item) {
          helper.setText(R.id.recordName,item.getCoinName());
          helper.setText(R.id.recordTime,String.valueOf(item.getUpdateTime()));
          helper.setText(R.id.recordPrice,String.valueOf(item.getQty()));
    }
}
