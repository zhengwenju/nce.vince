package com.bronet.blockchain.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.Credits;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 18514 on 2019/7/10.
 */

public class CreditAdapter extends BaseQuickAdapter<Credits.Items,BaseViewHolder> {
    public CreditAdapter(@LayoutRes int layoutResId, @Nullable List<Credits.Items> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Credits.Items s) {
        baseViewHolder.setText(R.id.name,s.getName());
        baseViewHolder.setText(R.id.numbers,"+"+s.getTotalScore());

    }
}
