package com.bronet.blockchain.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.NCEList;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 18514 on 2019/7/26.
 */

public class ERAdapter extends BaseQuickAdapter<NCEList.Result,BaseViewHolder> {
    public ERAdapter(@LayoutRes int layoutResId, @Nullable List<NCEList.Result> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, NCEList.Result result) {
        baseViewHolder.setText(R.id.types,result.getPayCoinName());
        baseViewHolder.setText(R.id.number,result.getQty());
        baseViewHolder.setText(R.id.type,result.getTradeType());
        baseViewHolder.setText(R.id.time,result.getCreateTime());
    }
}
