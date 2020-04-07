

package com.bronet.blockchain.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.Assets;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 18514 on 2019/7/15.
 */

public class AssetsAdaptertest extends BaseQuickAdapter<Assets.Items,BaseViewHolder> {
    public AssetsAdaptertest(@LayoutRes int layoutResId, @Nullable List<Assets.Items> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Assets.Items items) {
        baseViewHolder.setText(R.id.name,items.getCoinType());
        baseViewHolder.setText(R.id.number,items.getBanlance());
        baseViewHolder.setText(R.id.number2,items.getFreeze());
        baseViewHolder.setText(R.id.number3,items.getUsd());
    }
}
