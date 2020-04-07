package com.bronet.blockchain.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.ReceModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MineTranAdapter extends BaseQuickAdapter<ReceModel.ResultBean,BaseViewHolder>{


    public MineTranAdapter(int layoutResId, @Nullable List<ReceModel.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull final BaseViewHolder helper, final ReceModel.ResultBean item) {
        helper.setText(R.id.name,item.getUserName());
        helper.setText(R.id.time,item.getCreateTime());
        helper.setText(R.id.num,String.valueOf(item.getQty()));
        helper.setText(R.id.status,item.getStatusMsg());
    }
}
